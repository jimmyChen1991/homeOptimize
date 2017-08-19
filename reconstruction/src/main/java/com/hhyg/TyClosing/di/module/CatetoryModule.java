package com.hhyg.TyClosing.di.module;

import com.google.gson.Gson;
import com.hhyg.TyClosing.apiService.HotsearchWordSevice;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/8/17.
 */
@Module
public class CatetoryModule {

    @Provides
    HotsearchWordSevice provideHotsearchSevice(@Named("slowIndexApi") Retrofit retrofit){
        return retrofit.create(HotsearchWordSevice.class);
    }

    @Provides
    Gson a(){
        return new Gson();
    }
}
