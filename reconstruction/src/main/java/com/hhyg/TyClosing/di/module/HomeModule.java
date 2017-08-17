package com.hhyg.TyClosing.di.module;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hhyg.TyClosing.apiService.HomeSevice;
import com.hhyg.TyClosing.apiService.HotsearchWordSevice;
import com.hhyg.TyClosing.entities.CommonParam;
import com.hhyg.TyClosing.entities.home.ReqParam;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
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

    @Provides
    HeaderAndFooterWrapper provideWrapper(){
        return new HeaderAndFooterWrapper(new RecyclerView.Adapter() {
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
    }

    @Provides
    CompositeDisposable provideDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    HotsearchWordSevice provideHotsearchSevice(@Named("slowIndexApi") Retrofit retrofit){
        return retrofit.create(HotsearchWordSevice.class);
    }
}
