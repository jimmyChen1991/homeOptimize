package com.hhyg.TyClosing.di.module;

import com.google.gson.Gson;
import com.hhyg.TyClosing.apiService.BindSaler2oderSevice;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.order.BindSalerReq;
import com.hhyg.TyClosing.mgr.ClosingRefInfoMgr;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/8/14.
 */
@Module
public class PaySuccessModule {

    @Provides
    BindSaler2oderSevice provideSevice(@Named("slowIndexApi")Retrofit retrofit){
        return retrofit.create(BindSaler2oderSevice.class);
    }

    @Provides
    BindSalerReq provideParam(CommonParam param){
        BindSalerReq req = new BindSalerReq();
        req.setShopid(param.getShopId());
        req.setImei(param.getImei());
        req.setChannel(param.getChannelId());
        req.setPlatformid(param.getPlatformId());
        req.setSalerid(ClosingRefInfoMgr.getInstance().getSalerId());
        return req;
    }

    @Provides
    Gson provideGson(){
        return new Gson();
    }
}
