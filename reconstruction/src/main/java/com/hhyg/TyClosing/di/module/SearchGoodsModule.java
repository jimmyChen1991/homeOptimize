package com.hhyg.TyClosing.di.module;

import android.app.Activity;
import android.support.annotation.LayoutRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hhyg.TyClosing.R;
import com.hhyg.TyClosing.entities.search.FilterChangedRaw;
import com.hhyg.TyClosing.entities.search.PeopertyOfCate;
import com.hhyg.TyClosing.entities.search.SearchFilterRes;
import com.hhyg.TyClosing.entities.shopcart.ShopcartListParam;
import com.hhyg.TyClosing.fragment.LoadingDialogFragment;
import com.hhyg.TyClosing.mgr.ClosingRefInfoMgr;
import com.hhyg.TyClosing.ui.SearchGoodActivity;
import com.hhyg.TyClosing.ui.adapter.search.GoodRecAdapter;
import com.hhyg.TyClosing.ui.adapter.search.HorizontalFilterAdapter;
import com.hhyg.TyClosing.ui.adapter.search.PeopertyPopAdapter;
import com.hhyg.TyClosing.apiService.SearchSevice;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.search.SearchGoodsParam;
import com.hhyg.TyClosing.entities.search.SearchType;
import com.hhyg.TyClosing.ui.adapter.search.VerticalFilterAdapter;
import com.hhyg.TyClosing.ui.adapter.search.VerticalFilterItemAdapter;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/6/9.
 */
@Module
public class SearchGoodsModule {
    private SearchGoodsParam.DataBean beanParam;
    private Activity context;
    public SearchGoodsModule(SearchGoodsParam.DataBean beanParam,Activity c) {
        this.beanParam = beanParam;
        context = c ;
    }

    @Provides
    @Named("action")
    String provideAction(SearchType type){
        String res;
        if(type == SearchType.ACTIVITY || type == SearchType.PRIVILEGE){
            res = "essearch/activegoods";
        }else{
            res = "essearch/searchgoods";
        }
        return res;
    }

    @Provides
    SearchGoodsParam provideSearchGoodParam(CommonParam commonParam, SearchGoodsParam.DataBean bean)
    {
        SearchGoodsParam param = new SearchGoodsParam();
        param.setChannel(commonParam.getChannelId());
        param.setImei(commonParam.getImei());
        param.setShopid(commonParam.getShopId());
        param.setPlatformId(commonParam.getPlatformId());
        param.setData(bean);
        return param;
    }

    @Provides
    SearchGoodsParam.DataBean provideBean(){
        SearchGoodsParam.DataBean bean = new SearchGoodsParam.DataBean();
        bean.setPageNo(1);
        bean.setPageSize("99");
        bean.setAvailable("1");
        bean.setSortType("0");
        bean.setClass1Id(beanParam.getClass1Id());
        bean.setClass2Id(beanParam.getClass2Id());
        bean.setClass3Id(beanParam.getClass3Id());
        bean.setBrandId(beanParam.getBrandId());
        bean.setKeyword(beanParam.getKeyword());
        bean.setActivityId(beanParam.getActivityId());
        return bean;
    }

    @Provides
    ShopcartListParam shopcartListParam(CommonParam commonParam){
        ShopcartListParam param = new ShopcartListParam();
        param.setChannel(commonParam.getChannelId());
        param.setImei(commonParam.getImei());
        param.setShopid(commonParam.getShopId());
        param.setPlatformId(commonParam.getPlatformId());
        param.setDeliverplace(""+ClosingRefInfoMgr.getInstance().getCurPickupId());
        return param;
    }

    @Provides
    @Named("slowSevice")
    SearchSevice provideSlowService(@Named("slowIndexApi") Retrofit retrofit){
        return  retrofit.create(SearchSevice.class);
    }

    @Provides
    @Named("fastSevice")
    SearchSevice provideFastService(@Named("fastIndexApi") Retrofit retrofit){
        return  retrofit.create(SearchSevice.class);
    }

    @Provides
    Gson provideGson(){
        return new Gson();
    }

    @Provides
    @LayoutRes int layoutName(){
        return R.layout.adapter_searchgood;
    }

    @Provides
    GoodRecAdapter provideGoodAdapter(@LayoutRes int layout,SearchType type){
        GoodRecAdapter adapter = new GoodRecAdapter(layout,type);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        return adapter;
    }

    @Provides
    LoadingDialogFragment provideDialog(){
        return new LoadingDialogFragment();
    }

    @Provides
    HorizontalFilterAdapter provideHorizontalAdapter(){
        HorizontalFilterAdapter adapter = new HorizontalFilterAdapter();
        return adapter;
    }

    @Provides
    PeopertyPopAdapter providePopAdapter(){
        return new PeopertyPopAdapter();
    }

    @Provides
    SearchType provideType(){
        int value = context.getIntent().getIntExtra(context.getString(R.string.search_type),1);
        SearchType type = value == -1 ? null : SearchType.values()[value];
        return type;
    }

    @Provides
    VerticalFilterAdapter verticalFilterAdapter(){
        VerticalFilterAdapter adapter = new VerticalFilterAdapter();
        return adapter;
    }

    @Provides
    CompositeDisposable provideDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    ArrayList<PeopertyOfCate> peopertyOfCates(){
        return new ArrayList<>();
    }

    @Provides
    VerticalFilterItemAdapter verticalFilterItemAdapter(){
        VerticalFilterItemAdapter adapter = new VerticalFilterItemAdapter();
        return adapter;
    }

    @Provides
    SearchGoodActivity.FilterHelper helper(){
        return new SearchGoodActivity.FilterHelper(new SearchFilterRes());
    }

    @Provides
    FilterChangedRaw provideChangedRaw(){
        FilterChangedRaw raw = new FilterChangedRaw();
        raw.setAvailable("1");
        return raw;
    }
}
