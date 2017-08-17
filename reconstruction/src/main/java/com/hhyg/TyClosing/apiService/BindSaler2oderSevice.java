package com.hhyg.TyClosing.apiService;

import com.hhyg.TyClosing.entities.order.BindSalerRes;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 2017/8/14.
 */

public interface BindSaler2oderSevice {
    @POST("index.php?r=saler/changesaler")
    @FormUrlEncoded
    Observable<BindSalerRes> bindSaler(@Field("parameter") String param);
}
