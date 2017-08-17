package com.hhyg.TyClosing.apiService;

import com.hhyg.TyClosing.entities.search.HotsearchWord;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 2017/8/14.
 */

public interface HotsearchWordSevice {
    @POST("index.php?r=hotsearch/hotsearch")
    @FormUrlEncoded
    Observable<HotsearchWord> getRecommend(@Field("parameter") String parameter);
}
