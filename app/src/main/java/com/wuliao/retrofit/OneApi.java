package com.wuliao.retrofit;

import com.wuliao.source.OneBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Swy on 2017/4/16.
 */

public interface OneApi {

    @GET("api/channel/reading/more/0")
    Observable<OneBean> getOneResponse(@Query("channel")String channel,@Query("version")String version,
                                       @Query("uuid")String uuid,@Query("platform")String platform);
}
