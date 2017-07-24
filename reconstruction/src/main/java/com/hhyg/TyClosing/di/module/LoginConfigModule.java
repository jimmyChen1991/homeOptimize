package com.hhyg.TyClosing.di.module;

import com.google.gson.Gson;
import com.hhyg.TyClosing.apiService.LoginConfigService;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.loginconfig.LoginConfigParam;
import com.hhyg.TyClosing.global.MyApplication;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/6/27.
 */
@Module
public class LoginConfigModule {

    @Provides
    LoginConfigService provideService(@Named("slowIndexApi") Retrofit retrofit){
        return retrofit.create(LoginConfigService.class);
    }

    @Provides
    LoginConfigParam provideConfigParam(){
        LoginConfigParam param = new LoginConfigParam();
        param.setImei(MyApplication.GetInstance().getAndroidId());
        return param;
    }

    @Provides
    Gson provideGson(){
        return new Gson();
    }
}
