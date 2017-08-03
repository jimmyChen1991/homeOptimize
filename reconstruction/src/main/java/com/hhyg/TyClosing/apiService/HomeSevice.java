package com.hhyg.TyClosing.apiService;

import com.hhyg.TyClosing.entities.home.ContentRes;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 2017/8/2.
 */

public interface HomeSevice {

    @POST("index.php?r=tyhomeapi/home")
    @FormUrlEncoded
    Observable<ContentRes> getHomeContent(@Field("parameter") String param);

}
