package com.hhyg.TyClosing.di.module;

import com.google.gson.Gson;
import com.hhyg.TyClosing.apiService.HomeSevice;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.home.ReqParam;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/8/2.
 */
@Module
public class HomeModule {

    @Provides
    @Named("slowSevice")
    HomeSevice provideHomeSevice(@Named("slowIndexApi") Retrofit retrofit){
        return retrofit.create(HomeSevice.class);
    }

    @Provides
    @Named("fastSevice")
    HomeSevice provideFastHomeSevice(@Named("fastIndexApi") Retrofit retrofit){
        return retrofit.create(HomeSevice.class);
    }

    @Provides
    Gson provideGson(){
        return new Gson();
    }

    @Provides
    ReqParam provideReqparam(CommonParam param){
        ReqParam res = new ReqParam();
        res.setChannel(param.getChannelId());
        res.setImei(param.getImei());
        res.setPlatformId(param.getPlatformId());
        res.setShopid(param.getShopId());
        return res;
    }
}
