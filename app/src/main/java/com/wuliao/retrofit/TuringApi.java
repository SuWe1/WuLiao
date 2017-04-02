package com.wuliao.retrofit;

import com.wuliao.source.TuringBean;
import com.wuliao.source.turing.TuringCookbook;
import com.wuliao.source.turing.TuringUrl;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Swy on 2017/4/1.
 */

public interface TuringApi {

    @POST("openapi/api")
    Observable<ResponseBody> getResponseResponseBody(@Query("key") String key, @Query("info") String info);

    @POST("openapi/api")
    Observable<TuringBean> getResponse(@Query("key") String key, @Query("info") String info);

    @POST("openapi/api")
    Observable<TuringBean<List<TuringCookbook>>> getResponseCookBook(@Query("key") String key, @Query("info") String info);

    @POST("openapi/api")
    Observable<TuringBean<TuringUrl>> getResponseUrl(@Query("key") String key, @Query("info") String info);


}
