package com.hhyg.TyClosing.apiService;

import com.hhyg.TyClosing.entities.brand.Res;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 2017/8/9.
 */

public interface BrandSevice {

    @POST("index.php?r=brand/brand")
    @FormUrlEncoded
    Observable<Res> getBrands(@Field("parameter") String parameter);
}
