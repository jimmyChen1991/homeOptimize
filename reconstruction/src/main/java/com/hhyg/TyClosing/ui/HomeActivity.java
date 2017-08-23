package com.hhyg.TyClosing.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.apiService.HomeSevice;
import com.hhyg.TyClosing.apiService.HotsearchWordSevice;
import com.hhyg.TyClosing.config.Constants;
import com.hhyg.TyClosing.di.componet.DaggerCommonNetParamComponent;
import com.hhyg.TyClosing.di.componet.DaggerHomeComponent;
import com.hhyg.TyClosing.di.module.CommonNetParamModule;
import com.hhyg.TyClosing.entities.home.ContentRes;
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.hhyg.TyClosing.entities.home.ReqParam;
import com.hhyg.TyClosing.entities.search.HotsearchWord;
import com.hhyg.TyClosing.global.MyApplication;
import com.hhyg.TyClosing.mgr.ClosingRefInfoMgr;
import com.hhyg.TyClosing.mgr.ShoppingCartMgr;
import com.hhyg.TyClosing.ui.adapter.home.BannerAdapter;
import com.hhyg.TyClosing.ui.adapter.home.BrandBannerAdapter;
import com.hhyg.TyClosing.ui.adapter.home.CateAdapter;
import com.hhyg.TyClosing.ui.adapter.home.GoodsAdapter;
import com.hhyg.TyClosing.ui.view.SPScrollview;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    @Inject
    @Named("slowSevice")
    HomeSevice slowSevice;
    @Inject
    @Named("fastSevice")
    HomeSevice fastSevice;
    @Inject
    Gson gson;
    @Inject
    ReqParam param;
    @Inject
    HeaderAndFooterWrapper wrapper;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.rv)
    SPScrollview scrollview;
    @BindView(R.id.swipe_container)
    SmartRefreshLayout swipeContainer;
    @BindView(R.id.searchbar_top)
    View topSearchBar;
    @BindView(R.id.hhyg_icon)
    ImageButton hhygIcon;
    @BindView(R.id.search_wrap)
    View searchWrap;
    @Inject
    CompositeDisposable disposable;
    @Inject
    HotsearchWordSevice recommonSevice;
    @BindView(R.id.hotsearch_content)
    TextView topbarHotSearchWord;
    TextView searchWord;
    @BindView(R.id.bar_start)
    TextView searchStart;
    TextView searchStartHead;
    @BindView(R.id.shopcat_point)
    TextView shopcartNum;
    Banner mBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        home.setBackgroundResource(R.drawable.home_icon_pressed);
        topSearchBar.setVisibility(View.GONE);
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                fillScrollData();
            }
        });
        DaggerHomeComponent.builder()
                .applicationComponent(MyApplication.GetInstance().getComponent())
                .commonNetParamComponent(DaggerCommonNetParamComponent.builder().commonNetParamModule(new CommonNetParamModule()).build())
                .build()
                .inject(this);
        scrollview.setListener(new SPScrollview.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int dy) {
                if (dy > 550) {
                    topSearchBar.setVisibility(View.VISIBLE);
                } else {
                    topSearchBar.setVisibility(View.GONE);
                }
            }
        });
        fillScrollData();
        Log.d(TAG, "created");
    }

    private void fillScrollData() {
        Observable.just(param)
                .flatMap(new Function<ReqParam, ObservableSource<ContentRes>>() {
                    @Override
                    public ObservableSource<ContentRes> apply(@NonNull ReqParam reqParam) throws Exception {
                        return slowSevice.getHomeContent(gson.toJson(reqParam));
                    }
                })
                .doOnNext(new Consumer<ContentRes>() {
                    @Override
                    public void accept(@NonNull ContentRes contentRes) throws Exception {
                        if (contentRes.getData().getTy_pad_index_new_xianshitehui() != null && contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods() != null) {
                            for (GoodsBean bean : contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods()) {
                                handlerGoodsBeanPrice(bean);
                            }
                        }
                        if(contentRes.getData().getTy_pad_index_new_goodstopic() != null){
                            for (ContentRes.DataBean.TyPadIndexNewGoodstopicBean tyPadIndexNewGoodstopicBean : contentRes.getData().getTy_pad_index_new_goodstopic()) {
                                if (tyPadIndexNewGoodstopicBean.getGoods() != null) {
                                    for (GoodsBean bean : tyPadIndexNewGoodstopicBean.getGoods()) {
                                        handlerGoodsBeanPrice(bean);
                                    }
                                }
                            }
                        }
                        if(contentRes.getData().getTy_pad_index_new_recommendgood() != null){
                            for (ContentRes.DataBean.TyPadIndexNewRecommendgoodBean recommendgoodBean : contentRes.getData().getTy_pad_index_new_recommendgood()) {
                                if (recommendgoodBean.getGoods() != null) {
                                    for (GoodsBean bean : recommendgoodBean.getGoods()) {
                                        handlerGoodsBeanPrice(bean);
                                    }
                                }
                            }
                        }
                    }
                })
                .doOnNext(new Consumer<ContentRes>() {
                    @Override
                    public void accept(@NonNull ContentRes contentRes) throws Exception {
                        if (contentRes.getData().getTy_pad_index_new_xianshitehui() != null && contentRes.getData().getTy_pad_index_new_xianshitehui().getColor() != null) {
                            String color = contentRes.getData().getTy_pad_index_new_xianshitehui().getColor();
                            if (contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods() != null) {
                                for (GoodsBean bean : contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods()) {
                                    String html = "<font color='" + color + "'>" + bean.getCheapPrice() + "</font>";
                                    bean.setCheapPrice(html);
                                    bean.setSetColor(true);
                                }
                            }
                        }
                        if(contentRes.getData().getTy_pad_index_new_goodstopic() != null){
                            for (ContentRes.DataBean.TyPadIndexNewGoodstopicBean tyPadIndexNewGoodstopicBean : contentRes.getData().getTy_pad_index_new_goodstopic()) {
                                if (tyPadIndexNewGoodstopicBean.getColor() != null) {
                                    String color = tyPadIndexNewGoodstopicBean.getColor();
                                    if (tyPadIndexNewGoodstopicBean.getGoods() != null) {
                                        for (GoodsBean bean : tyPadIndexNewGoodstopicBean.getGoods()) {
                                            String html = "<font color='" + color + "'>" + bean.getCheapPrice() + "</font>";
                                            bean.setCheapPrice(html);
                                            bean.setSetColor(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(swipeContainer.isRefreshing()){
                            swipeContainer.finishRefresh();
                        }
                    }
                })
                .subscribe(new Observer<ContentRes>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ContentRes contentRes) {
                        Log.d(TAG, "homeContent_next");
                        if(scrollview.getChildCount() != 0){
                            scrollview.removeAllViews();
                        }
                        initView(contentRes);
                        getSearchWord();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.toString());
                        Toasty.error(HomeActivity.this,getString(R.string.netconnect_exception), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "completer");
                    }
                });
    }

    private void getSearchWord() {
        Observable.just(param)
                .flatMap(new Function<ReqParam, ObservableSource<HotsearchWord>>() {
                    @Override
                    public ObservableSource<HotsearchWord> apply(@NonNull ReqParam reqParam) throws Exception {
                        return recommonSevice.getRecommend(gson.toJson(reqParam));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotsearchWord>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull HotsearchWord hotsearchWord) {
                        if(hotsearchWord == null || hotsearchWord.getRecommend() == null || hotsearchWord.getRecommend().size() == 0){
                            searchStart.setText(getString(R.string.search_indictor));
                            searchStartHead.setText(getString(R.string.search_indictor));
                            searchWord.setText(null);
                            topbarHotSearchWord.setText(null);
                            MyApplication.GetInstance().setHotSearchWord(null);
                            return;
                        }
                        MyApplication.GetInstance().setHotSearchWord(hotsearchWord);
                        searchStart.setText(getString(R.string.everyone_search));
                        searchStartHead.setText(getString(R.string.everyone_search));
                        searchWord.setText(MyApplication.GetInstance().getHotSearchWord());
                        topbarHotSearchWord.setText(MyApplication.GetInstance().getHotSearchWord());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchStart.setText(getString(R.string.search_indictor));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handlerGoodsBeanPrice(GoodsBean bean) {
        GoodsBean.ActiveinfoBean aBean = bean.getActiveinfo();
        if (aBean != null && aBean.getActive_type() != null && aBean.getActive_type().equals("1")) {
            bean.setCheapPrice(Constants.PRICE_TITLE + aBean.getActive_price());
            bean.setNormalPrice(Constants.PRICE_TITLE + bean.getPrice());
        } else {
            bean.setCheapPrice(Constants.PRICE_TITLE + bean.getPrice());
            bean.setNormalPrice(Constants.PRICE_TITLE + bean.getMarket_price());
        }
    }

    private void initView(final ContentRes contentRes) {
        View headView = LayoutInflater.from(this).inflate(R.layout.home_head, null);
        searchWord = (TextView) headView.findViewById(R.id.hotsearch_word);
        searchStartHead = (TextView) headView.findViewById(R.id.bar_start);
        //处理Banner
        mBanner = (Banner) headView.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new BannerImageLoader());
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(HomeActivity.this, SpecialActivity.class);
                intent.putExtra("specialid", contentRes.getData().getTy_pad_index_new_slider().get(position).getSpecialid());
                startActivity(intent);
            }
        });
        mBanner.setImages(contentRes.getData().getTy_pad_index_new_slider());
        mBanner.start();

        View hhygIcon = headView.findViewById(R.id.hhyg_icon);
        hhygIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(HomeActivity.this,SalerMainPageActivity.class);
                startActivity(it);
            }
        });
        View searchWrap = headView.findViewById(R.id.search_wrap);
        searchWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(HomeActivity.this,SearchActivity.class);
                startActivity(it);
            }
        });

        RecyclerView cateRv = (RecyclerView) headView.findViewById(R.id.cate_rv);
        cateRv.setNestedScrollingEnabled(false);
        cateRv.setLayoutManager(new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false));
        CateAdapter cateAdapter = new CateAdapter(this);
        cateAdapter.setmData((ArrayList<ContentRes.DataBean.TyPadIndexNewCateBean>) contentRes.getData().getTy_pad_index_new_cate());
        cateRv.setAdapter(cateAdapter);

        ViewGroup bannerGroup = (ViewGroup) headView.findViewById(R.id.banner_item);
        if(contentRes.getData().getTy_pad_index_new_advertising() != null){
            for (final ContentRes.DataBean.TyPadIndexNewAdvertisingBean advertisingBean :contentRes.getData().getTy_pad_index_new_advertising()){
                ImageView adImg = (ImageView) LayoutInflater.from(this).inflate(R.layout.home_advertising, null);
                bannerGroup.addView(adImg);
                int screenWidth = 1536;
                ViewGroup.LayoutParams lp = adImg.getLayoutParams();
                lp.width = screenWidth;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                adImg.setLayoutParams(lp);
                adImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(v.getContext(), SpecialActivity.class);
                        it.putExtra("specialid", advertisingBean.getSpecialid());
                        v.getContext().startActivity(it);
                    }
                });
                adImg.setMaxWidth(screenWidth);
                adImg.setMaxHeight(screenWidth * 5);
                if(!TextUtils.isEmpty(advertisingBean.getImgurl())){
                    Picasso.with(this).load(advertisingBean.getImgurl()).into(adImg);
                }
            }
        }
        if (contentRes.getData().getTy_pad_index_new_xianshitehui() != null) {
            final ContentRes.DataBean.TyPadIndexNewXianshitehuiBean baen = contentRes.getData().getTy_pad_index_new_xianshitehui();
            View bannerView = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
            ImageView imgV = (ImageView) bannerView.findViewById(R.id.banner_img);
            imgV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(v.getContext(), SpecialActivity.class);
                    it.putExtra("specialid", baen.getSpecialid());
                    v.getContext().startActivity(it);
                }
            });
            if(!TextUtils.isEmpty(baen.getImgurl())){
                Picasso.with(this).load(baen.getImgurl()).into(imgV);
            }
            RecyclerView rv = (RecyclerView) bannerView.findViewById(R.id.banner_rv);
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rv.setHasFixedSize(true);
            BannerAdapter bannerAdapter = new BannerAdapter(this);
            bannerAdapter.setSpecialId(baen.getSpecialid());
            bannerAdapter.setmData((ArrayList<GoodsBean>) baen.getGoods());
            rv.setAdapter(bannerAdapter);
            bannerGroup.addView(bannerView);
        }

        if (contentRes.getData().getTy_pad_index_new_hotbrand() != null) {
            ContentRes.DataBean.TyPadIndexNewHotbrandBean bean = contentRes.getData().getTy_pad_index_new_hotbrand();
            View brandBanner = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
            ImageView imgV = (ImageView) brandBanner.findViewById(R.id.banner_img);
            if (!TextUtils.isEmpty(bean.getMore_brand_image())) {
                Picasso.with(this).load(bean.getMore_brand_image()).into(imgV);
            }
            RecyclerView rv = (RecyclerView) brandBanner.findViewById(R.id.banner_rv);
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rv.setHasFixedSize(true);
            BrandBannerAdapter brandBannerAdapter = new BrandBannerAdapter(this);
            brandBannerAdapter.setmData((ArrayList<ContentRes.DataBean.TyPadIndexNewHotbrandBean.HotbrandBean>) bean.getHotbrand());
            rv.setAdapter(brandBannerAdapter);
            bannerGroup.addView(brandBanner);
        }

        if (contentRes.getData().getTy_pad_index_new_goodstopic() != null) {
            for (final ContentRes.DataBean.TyPadIndexNewGoodstopicBean newGoodstopicBean : contentRes.getData().getTy_pad_index_new_goodstopic()) {
                View bannerView = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
                ImageView imgV = (ImageView) bannerView.findViewById(R.id.banner_img);
                imgV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(v.getContext(), SpecialActivity.class);
                        it.putExtra("specialid", newGoodstopicBean.getSpecialid());
                        v.getContext().startActivity(it);
                    }
                });
                if(!TextUtils.isEmpty(newGoodstopicBean.getImgurl())){
                    Picasso.with(this).load(newGoodstopicBean.getImgurl()).into(imgV);
                }
                RecyclerView rv = (RecyclerView) bannerView.findViewById(R.id.banner_rv);
                rv.setNestedScrollingEnabled(false);
                rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                rv.setHasFixedSize(true);
                BannerAdapter bannerAdapter = new BannerAdapter(this);
                bannerAdapter.setSpecialId(newGoodstopicBean.getSpecialid());
                bannerAdapter.setmData((ArrayList<GoodsBean>) newGoodstopicBean.getGoods());
                rv.setAdapter(bannerAdapter);
                bannerGroup.addView(bannerView);
            }
        }

        if (contentRes.getData().getTy_pad_index_new_recommendgood() != null) {
            for (ContentRes.DataBean.TyPadIndexNewRecommendgoodBean recommendBean : contentRes.getData().getTy_pad_index_new_recommendgood()) {
                View rvWrap = LayoutInflater.from(this).inflate(R.layout.home_goods_rv, null);
                RecyclerView rv = (RecyclerView) rvWrap.findViewById(R.id.rv);
                GridLayoutManager gridlayout = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
                gridlayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int size = 4;
                        if (position != 0) {
                            size = 1;
                        }
                        return size;
                    }
                });
                rv.setLayoutManager(gridlayout);
                rv.setNestedScrollingEnabled(false);
                GoodsAdapter goodsAdapter = new GoodsAdapter(this);
                goodsAdapter.setData(recommendBean);
                rv.setAdapter(goodsAdapter);
                bannerGroup.addView(rvWrap);
            }
        }
        scrollview.addView(headView);
    }

    private void unregistSaler() {
        final Dialog dialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.allshop_salerlogout_dialog, null);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setLayout(880, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView exitContent=(TextView)layout.findViewById(R.id.warnning_content);
        exitContent.setText("确认退出账号  "+ ClosingRefInfoMgr.getInstance().getSalerInfo().getUserName()+"  " +ClosingRefInfoMgr.getInstance().getSalerInfo().getSalerName()+"?");
        Button exitBtn = (Button) layout.findViewById(R.id.summit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent it = new Intent();
                it.setClass(HomeActivity.this, SalerLoginActivity.class);
                startActivity(it);
                finish();
            }
        });
        Button cancelBtn = (Button) layout.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        shopcartNum.setText(String.valueOf(ShoppingCartMgr.getInstance().getAll().size()));
        if(mBanner != null){
            mBanner.startAutoPlay();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            unregistSaler();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mBanner != null){
            mBanner.stopAutoPlay();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @OnClick({R.id.brand, R.id.cate, R.id.shopcat})
    public void onViewClicked(View view) {
        Intent it = new Intent();
        switch (view.getId()) {
            case R.id.brand:
                it.setClass(HomeActivity.this, BrandActivity.class);
                break;
            case R.id.cate:
                it.setClass(HomeActivity.this, CategoryActivity.class);
                break;
            case R.id.shopcat:
                it.setClass(HomeActivity.this, ShopCartActivity.class);
                break;
        }
        startActivity(it);
    }

    @OnClick(R.id.hhyg_icon)
    public void onViewClicked1(View view) {
        Intent it = new Intent();
        it.setClass(this,SalerMainPageActivity.class);
        startActivity(it);
    }

    @OnClick(R.id.search_wrap)
    public void onViewClicked2(View view) {
        Intent it = new Intent();
        it.setClass(this,SearchActivity.class);
        startActivity(it);
    }

    static class BannerImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ContentRes.DataBean.TyPadIndexNewSliderBean bean = (ContentRes.DataBean.TyPadIndexNewSliderBean) path;
            if(!TextUtils.isEmpty(bean.getImgurl())){
                Picasso.with(context).load(bean.getImgurl()).into(imageView);
            }
        }

    }
}
