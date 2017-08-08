package com.hhyg.TyClosing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hhyg.TyClosing.entities.home.ReqParam;
import com.hhyg.TyClosing.global.MyApplication;
import com.hhyg.TyClosing.ui.adapter.home.BannerAdapter;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

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

        ViewGroup bannerGroup = (ViewGroup) headView.findViewById(R.id.banner_item);
        if(contentRes.getData().getTy_pad_index_new_xianshitehui() != null){
            ContentRes.DataBean.TyPadIndexNewXianshitehuiBean baen = contentRes.getData().getTy_pad_index_new_xianshitehui();
            View xianshiView = LayoutInflater.from(this).inflate(R.layout.home_banner, null);
            ImageView imgV = (ImageView) xianshiView.findViewById(R.id.banner_img);
            Picasso.with(this).load(baen.getImgurl()).into(imgV);
            RecyclerView rv = (RecyclerView) xianshiView.findViewById(R.id.banner_rv);
            rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            rv.setHasFixedSize(true);
            rv.setAdapter(new BannerAdapter(R.layout.home_smallitem,baen.getGoods()));
            bannerGroup.addView(xianshiView);
        }

        HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
        wrapper.addHeaderView(headView);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
