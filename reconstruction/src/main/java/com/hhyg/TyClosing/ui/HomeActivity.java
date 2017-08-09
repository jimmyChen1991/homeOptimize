package com.hhyg.TyClosing.ui;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.apiService.HomeSevice;
import com.hhyg.TyClosing.di.componet.DaggerCommonNetParamComponent;
import com.hhyg.TyClosing.di.componet.DaggerHomeComponent;
import com.hhyg.TyClosing.di.module.CommonNetParamModule;
import com.hhyg.TyClosing.entities.home.ContentRes;
import com.hhyg.TyClosing.entities.home.GoodsBean;
import com.hhyg.TyClosing.entities.home.ReqParam;
import com.hhyg.TyClosing.global.MyApplication;
import com.hhyg.TyClosing.ui.adapter.home.BannerAdapter;
import com.hhyg.TyClosing.ui.adapter.home.BrandBannerAdapter;
import com.hhyg.TyClosing.ui.adapter.home.CateAdapter;
import com.hhyg.TyClosing.ui.adapter.home.GoodsAdapter;
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
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    HeaderAndFooterWrapper wrapper;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        DaggerHomeComponent.builder()
                .applicationComponent(MyApplication.GetInstance().getComponent())
                .commonNetParamComponent(DaggerCommonNetParamComponent.builder().commonNetParamModule(new CommonNetParamModule()).build())
                .build()
                .inject(this);
        home.setBackgroundResource(R.drawable.home_icon_pressed);
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
                        if(contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods() != null){
                            for (GoodsBean bean : contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods()){
                                handlerGoodsBeanPrice(bean);
                            }
                        }
                        for (ContentRes.DataBean.TyPadIndexNewGoodstopicBean tyPadIndexNewGoodstopicBean: contentRes.getData().getTy_pad_index_new_goodstopic()){
                            if(tyPadIndexNewGoodstopicBean.getGoods() != null){
                                for (GoodsBean bean : tyPadIndexNewGoodstopicBean.getGoods()){
                                    handlerGoodsBeanPrice(bean);
                                }
                            }
                        }
                        for (ContentRes.DataBean.TyPadIndexNewRecommendgoodBean recommendgoodBean : contentRes.getData().getTy_pad_index_new_recommendgood()){
                            if(recommendgoodBean.getGoods() != null){
                                for (GoodsBean bean : recommendgoodBean.getGoods()){
                                    handlerGoodsBeanPrice(bean);
                                }
                            }
                        }
                    }
                })
                .doOnNext(new Consumer<ContentRes>() {
                    @Override
                    public void accept(@NonNull ContentRes contentRes) throws Exception {
                        if(contentRes.getData().getTy_pad_index_new_xianshitehui().getColor() != null){
                            String color = contentRes.getData().getTy_pad_index_new_xianshitehui().getColor();
                            if(contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods() != null){
                                for (GoodsBean bean : contentRes.getData().getTy_pad_index_new_xianshitehui().getGoods()){
                                    String html = "<font color='" + color + "'>" + bean.getCheapPrice() + "</font>";
                                    bean.setCheapPrice(html);
                                    bean.setSetColor(true);
                                }
                            }
                        }

                        for (ContentRes.DataBean.TyPadIndexNewGoodstopicBean tyPadIndexNewGoodstopicBean: contentRes.getData().getTy_pad_index_new_goodstopic()){
                            if(tyPadIndexNewGoodstopicBean.getColor() != null){
                                String color = tyPadIndexNewGoodstopicBean.getColor();
                                if(tyPadIndexNewGoodstopicBean.getGoods() != null){
                                    for (GoodsBean bean : tyPadIndexNewGoodstopicBean.getGoods()){
                                        String html = "<font color='" + color + "'>" + bean.getCheapPrice() + "</font>";
                                        bean.setCheapPrice(html);
                                        bean.setSetColor(true);
                                    }
                                }
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentRes>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull ContentRes contentRes) {
                        initView(contentRes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handlerGoodsBeanPrice(GoodsBean bean) {
        GoodsBean.ActiveinfoBean aBean = bean.getActiveinfo();
        if(aBean != null && aBean.getActive_type() != null && aBean.getActive_type().equals("1")){
            bean.setCheapPrice(aBean.getActive_price());
            bean.setNormalPrice(bean.getPrice());
        }else{
            bean.setCheapPrice(bean.getPrice());
            bean.setNormalPrice(bean.getMarket_price());
        }
    }

    private void initView(final ContentRes contentRes) {
        View headView = LayoutInflater.from(this).inflate(R.layout.home_head, null);

        //处理Banner
        Banner banner = (Banner) headView.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new BannerImageLoader());
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
        banner.setImages(contentRes.getData().getTy_pad_index_new_slider());
        banner.start();

        RecyclerView cateRv = (RecyclerView) headView.findViewById(R.id.cate_rv);
        cateRv.setLayoutManager(new GridLayoutManager(this,6,GridLayoutManager.VERTICAL,false));
        CateAdapter cateAdapter = new CateAdapter(this);
        cateAdapter.setmData((ArrayList<ContentRes.DataBean.TyPadIndexNewCateBean>) contentRes.getData().getTy_pad_index_new_cate());
        cateRv.setAdapter(cateAdapter);


        ViewGroup bannerGroup = (ViewGroup) headView.findViewById(R.id.banner_item);
        if(contentRes.getData().getTy_pad_index_new_xianshitehui() != null){
            ContentRes.DataBean.TyPadIndexNewXianshitehuiBean baen = contentRes.getData().getTy_pad_index_new_xianshitehui();
            View bannerView = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
            ImageView imgV = (ImageView) bannerView.findViewById(R.id.banner_img);
            Picasso.with(this).load(baen.getImgurl()).into(imgV);
            RecyclerView rv = (RecyclerView) bannerView.findViewById(R.id.banner_rv);
            rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            rv.setHasFixedSize(true);
            BannerAdapter bannerAdapter = new BannerAdapter(this);
            bannerAdapter.setmData((ArrayList<GoodsBean>) baen.getGoods());
            rv.setAdapter(bannerAdapter);
            bannerGroup.addView(bannerView);
        }

        if(contentRes.getData().getTy_pad_index_new_hotbrand() != null){
            ContentRes.DataBean.TyPadIndexNewHotbrandBean bean = contentRes.getData().getTy_pad_index_new_hotbrand();
            View brandBanner = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
            ImageView imgV = (ImageView) brandBanner.findViewById(R.id.banner_img);
            if(!TextUtils.isEmpty(bean.getMore_brand_image())){
                Picasso.with(this).load(bean.getMore_brand_image()).into(imgV);
            }
            RecyclerView rv = (RecyclerView) brandBanner.findViewById(R.id.banner_rv);
            rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            rv.setHasFixedSize(true);
            BrandBannerAdapter brandBannerAdapter = new BrandBannerAdapter(this);
            brandBannerAdapter.setmData((ArrayList<ContentRes.DataBean.TyPadIndexNewHotbrandBean.HotbrandBean>) bean.getHotbrand());
            rv.setAdapter(brandBannerAdapter);
            bannerGroup.addView(brandBanner);
        }

        if(contentRes.getData().getTy_pad_index_new_goodstopic() != null){
            for (ContentRes.DataBean.TyPadIndexNewGoodstopicBean newGoodstopicBean : contentRes.getData().getTy_pad_index_new_goodstopic()){
                View bannerView = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
                ImageView imgV = (ImageView) bannerView.findViewById(R.id.banner_img);
                Picasso.with(this).load(newGoodstopicBean.getImgurl()).into(imgV);
                RecyclerView rv = (RecyclerView) bannerView.findViewById(R.id.banner_rv);
                rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
                rv.setHasFixedSize(true);
                BannerAdapter bannerAdapter = new BannerAdapter(this);
                bannerAdapter.setmData((ArrayList<GoodsBean>) newGoodstopicBean.getGoods());
                rv.setAdapter(bannerAdapter);
                bannerGroup.addView(bannerView);
            }
        }



        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4,GridLayoutManager.VERTICAL, false);
        final ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean> datas = (ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean>) contentRes.getData().getTy_pad_index_new_recommendgood();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spansize = 4;
                if(datas != null){
                    int size = datas.size();
                    int heads[] = new int[size];
                    int count = 0;
                    for (int index = 0; index < size ; index ++){
                        ContentRes.DataBean.TyPadIndexNewRecommendgoodBean bean = datas.get(index);
                        count += 1;
                        if(bean.getGoods() != null){
                            count += bean.getGoods().size();
                        }
                        if(index > 0){
                            heads[index] = count + 1;
                        }
                    }
                    for (int index = 0 ; index < size ;index ++){
                        if(position == heads[index]){
                            spansize = 1;
                        }
                    }
                }
                return spansize;
            }
        });
        GoodsAdapter goodsAdapter = new GoodsAdapter(this);
        goodsAdapter.setmData((ArrayList<ContentRes.DataBean.TyPadIndexNewRecommendgoodBean>) contentRes.getData().getTy_pad_index_new_recommendgood());
        wrapper = new HeaderAndFooterWrapper(goodsAdapter);
        wrapper.addHeaderView(headView);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(wrapper);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.home, R.id.brand, R.id.cate, R.id.shopcat})
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

    static class BannerImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            ContentRes.DataBean.TyPadIndexNewSliderBean bean = (ContentRes.DataBean.TyPadIndexNewSliderBean) path;
            Picasso.with(context).load(bean.getImgurl()).into(imageView);
        }

    }
}
