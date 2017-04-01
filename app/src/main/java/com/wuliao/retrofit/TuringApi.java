package com.wuliao.retrofit;

import com.wuliao.source.TuringBean;
import com.wuliao.source.turing.TuringNews;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Swy on 2017/4/1.
 */

public interface TuringApi {
    @POST("openapi/api")
    Observable<TuringBean<List<TuringNews>>> getResponse(@Query("key") String key, @Query("info") String info);
}
