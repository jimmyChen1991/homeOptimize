package com.hhyg.TyClosing.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.google.gson.Gson;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.apiService.BrandSevice;
import com.hhyg.TyClosing.di.componet.DaggerBrandComponent;
import com.hhyg.TyClosing.di.componet.DaggerCommonNetParamComponent;
import com.hhyg.TyClosing.di.module.CommonNetParamModule;
import com.hhyg.TyClosing.entities.brand.BrandInfo;
import com.hhyg.TyClosing.entities.brand.BrandSection;
import com.hhyg.TyClosing.entities.brand.ReqParam;
import com.hhyg.TyClosing.entities.brand.Res;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.global.MyApplication;
import com.hhyg.TyClosing.mgr.ShoppingCartMgr;
import com.hhyg.TyClosing.ui.adapter.brand.BrandAdapter;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.transform.Source;

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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BrandActivity extends AppCompatActivity {

    private static final String TAG = "BrandActivity";

    @BindView(R.id.hhyg_icon)
    ImageButton hhygIcon;
    @BindView(R.id.search_wrap)
    RelativeLayout searchWrap;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.brand)
    ImageButton brand;
    @BindView(R.id.cate)
    ImageButton cate;
    @BindView(R.id.shopcat)
    ImageButton shopcat;
    @Inject
    ReqParam reqParam;
    @Inject
    @Named("fastSevice")
    BrandSevice fastSevice;
    @Inject
    @Named("slowSevice")
    BrandSevice slowSevice;
    @Inject
    Gson gson;
    @Inject
    CompositeDisposable disposable;
    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;
    @BindView(R.id.side_bar)
    WaveSideBar sideBar;
    @BindView(R.id.hotsearch_content)
    TextView hotWord;
    @BindView(R.id.shopcat_point)
    TextView shopcartNum;
    private BrandAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brand);
        ButterKnife.bind(this);
        DaggerBrandComponent.builder()
                .applicationComponent(MyApplication.GetInstance().getComponent())
                .commonNetParamComponent(DaggerCommonNetParamComponent.builder().commonNetParamModule(new CommonNetParamModule()).build())
                .build()
                .inject(this);
        hhygIcon.setBackgroundResource(R.drawable.back);
        brand.setBackgroundResource(R.drawable.brand_icon_pressed);
        if(MyApplication.GetInstance().getHotSearchWord() != null){
            hotWord.setText(MyApplication.GetInstance().getHotSearchWord());
        }
        Observable.just(reqParam)
                .flatMap(new Function<ReqParam, ObservableSource<Res>>() {
                    @Override
                    public ObservableSource<Res> apply(@NonNull ReqParam reqParam) throws Exception {
                        return slowSevice.getBrands(gson.toJson(reqParam));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Res>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Res res) {
                        Log.d(TAG, res.toString());
                        initSideBar(res);
                        initRv(res);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toasty.error(BrandActivity.this,getString(R.string.netconnect_exception), Toast.LENGTH_LONG).show();
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initSideBar(final Res res){
        ArrayList<String> source = new ArrayList<>();
        if(res.getData().getKeys() != null){
            source = (ArrayList<String>) res.getData().getKeys();
        }
        source.add(0,"hot");
        String[] sArray = new String[source.size()];
        for (int index = 0; index < source.size() ; index ++){
            sArray[index] = source.get(index);
        }
        if(sArray.length > 1){
            sideBar.setIndexItems(sArray);
        }
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (BrandSection section : mAdapter.getData()){
                    if(section.isHeader && section.header.equals(index)){
                        int position = mAdapter.getData().indexOf(section);
                        Log.d(TAG, "ss" + position);
                        GridLayoutManager layoutManager = (GridLayoutManager) rvContacts.getLayoutManager();
                        layoutManager.scrollToPositionWithOffset(position,0);
                        break;
                    }
                }
            }
        });
    }

    private void initRv(final Res res){
        ArrayList<BrandSection> dataSet = new ArrayList<>();
        if (res.getData().getHotbrand() != null) {
            BrandSection head = new BrandSection(true, "hot");
            dataSet.add(head);
            for (BrandInfo info : res.getData().getHotbrand()) {
                BrandSection item = new BrandSection(info);
                dataSet.add(item);
            }
        }

        if(res.getData().getInfo() != null){
            for (String key : res.getData().getInfo().keySet()){
                BrandSection head = new BrandSection(true, key);
                dataSet.add(head);
                for (BrandInfo info : res.getData().getInfo().get(key)){
                    BrandSection item = new BrandSection(info);
                    dataSet.add(item);
                }
            }
        }

        mAdapter = new BrandAdapter(R.layout.brand_adapter_item,R.layout.brand_adapter_head,dataSet);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BrandSection section = mAdapter.getItem(position);
                if(!section.isHeader){
                    Intent intent = new Intent(BrandActivity.this, SearchGoodActivity.class);
                    SearchGoodsParam.DataBean bean = new SearchGoodsParam.DataBean();
                    bean.setBrandId(section.t.getBrandid());
                    intent.putExtra(getString(R.string.search_token),bean);
                    intent.putExtra(getString(R.string.search_content),section.t.getName());
                    intent.putExtra(getString(R.string.search_type), SearchType.BRAND.ordinal());
                    startActivity(intent);
                }
            }
        });
        rvContacts.setLayoutManager(new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false));
        rvContacts.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shopcartNum.setText(String.valueOf(ShoppingCartMgr.getInstance().getAll().size()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @OnClick({R.id.search_wrap})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.search_wrap:
                intent.setClass(this, SearchActivity.class);
                break;
        }
        startActivity(intent);
    }

    @OnClick({R.id.hhyg_icon})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.hhyg_icon:
                finish();
                break;
        }
    }

    @OnClick({R.id.home, R.id.cate, R.id.shopcat})
    public void onViewClicked1(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.home:
                intent.setClass(this, HomeActivity.class);
                break;
            case R.id.cate:
                intent.setClass(this, CategoryActivity.class);
                break;
            case R.id.shopcat:
                intent.setClass(this, ShopCartActivity.class);
                break;
        }
        startActivity(intent);
    }

}
