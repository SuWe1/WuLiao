package com.wuliao.retrofit;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Swy on 2017/4/1.
 */

public class RetrofitClient {
    private static String baseTuringUrl="http://www.tuling123.com/";
    private static TuringApi turingApi;

    private static Converter.Factory gsonConverterFactory= GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static TuringApi getTuringApi(){
        if (turingApi==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(baseTuringUrl)
                    .addConverterFactory(gsonConverterFactory)
//                    .addConverterFactory(ResponseConvertFactory.create())
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            turingApi=retrofit.create(TuringApi.class);
        }
        return turingApi;
    }
}
