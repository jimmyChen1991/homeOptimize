package com.hhyg.TyClosing.apiService;

import com.hhyg.TyClosing.entities.loginconfig.LoginConfigRes;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 2017/6/27.
 */

public interface LoginConfigService {
    @POST("index.php?r=config/index")
    @FormUrlEncoded
    Observable<LoginConfigRes> getLoginConfig(@Field("parameter") String param);
}
