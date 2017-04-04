package com.wuliao.retrofit.useless;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.wuliao.source.TuringBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Swy on 2017/4/1.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    //错误码 参数key错误  请求内容info为空 当天请求次数用完  数据格式异常
    private static final int RESPONSE_KEY_ERROR=40001;
    private static final int RESPONSE_INFONULL_ERROR=40002;
    private static final int RESPONSE_EXHASUT_ERROR=40004;
    private static final int RESPONSE_FORMAT_ERROR=40007;

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private String name;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter,String name) {
        this.gson = gson;
        this.adapter = adapter;
        this.name=name;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response=value.string();
        TuringBean turingBean=gson.fromJson(response,TuringBean.class);
        int code=turingBean.getCode();
        if (code== RESPONSE_KEY_ERROR|code ==RESPONSE_INFONULL_ERROR|code==RESPONSE_EXHASUT_ERROR|code==RESPONSE_FORMAT_ERROR){
            throw new ApiException(code);
        }else {
                Object data=turingBean.getUrl();
                response=data.toString();
            return adapter.fromJson(response);
        }
    }
}
