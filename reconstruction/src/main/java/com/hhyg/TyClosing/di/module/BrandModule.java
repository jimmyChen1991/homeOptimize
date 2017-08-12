package com.hhyg.TyClosing.di.module;

import com.google.gson.Gson;
import com.hhyg.TyClosing.apiService.BrandSevice;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.brand.ReqParam;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/8/9.
 */
@Module
public class BrandModule {

    @Provides
    @Named("slowSevice")
    BrandSevice provideHomeSevice(@Named("slowIndexApi") Retrofit retrofit){
        return retrofit.create(BrandSevice.class);
    }

    @Provides
    @Named("fastSevice")
    BrandSevice provideFastHomeSevice(@Named("fastIndexApi") Retrofit retrofit){
        return retrofit.create(BrandSevice.class);
    }

    @Provides
    Gson provideGson(){
        return new Gson();
    }

    @Provides
    ReqParam provideParam(CommonParam param){
        ReqParam reqParam = new ReqParam();
        reqParam.setChannel(param.getChannelId());
        reqParam.setImei(param.getImei());
        reqParam.setPlatformId(param.getPlatformId());
        reqParam.setShopid(param.getShopId());
        return reqParam;
    }

    @Provides
    CompositeDisposable provideDisposable(){
        return new CompositeDisposable();
    }
}
