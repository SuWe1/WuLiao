package com.wuliao.mvp.main;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.wuliao.R;
import com.wuliao.retrofit.RetrofitClient;
import com.wuliao.source.ChatBean;
import com.wuliao.source.TuringBean;
import com.wuliao.source.turing.TuringCookbook;
import com.wuliao.source.turing.TuringNews;
import com.wuliao.source.turing.TuringUrl;
import com.wuliao.util.NetworkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.wuliao.source.ChatBean.VIEW_TEXT;
import static com.wuliao.source.ChatBean.VIEW_URL;

/**
 * Created by Swy on 2017/3/31.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context context;
    private MainContract.View view;

    private static final String APIKey="65e6df4ce4d8425eae2c0e287138d165";
    //请求成功码 文本、链接、新闻、菜谱
    public static final int RESPONSE_CODE_TEXT=100000;
    public static final int RESPONSE_CODE_URL=200000;
    public static final int RESPONSE_CODE_NEWS=302000;
    public static final int RESPONSE_CODE_COOKBOOK=308000;
    //错误码 参数key错误  请求内容info为空 当天请求次数用完  数据格式异常
    public static final int RESPONSE_KEY_ERROR=40001;
    public static final int RESPONSE_INFONULL_ERROR=40002;
    public static final int RESPONSE_EXHASUT_ERROR=40004;
    public static final int RESPONSE_FORMAT_ERROR=40007;

    Gson gson=new Gson();
    public MainPresenter(Context context, MainContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    /**
     * new Subscriber<TuringBean<List<TuringNews>>>() {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
    Log.i(TAG, "onError: "+e.getMessage());
    }

    @Override
    public void onNext(TuringBean<List<TuringNews>> listTuringBean) {
    Log.i(TAG, "onNext: "+listTuringBean.getCode()+listTuringBean.getText()+listTuringBean.getData());
    List<TuringNews> news=listTuringBean.getData();
    Log.i(TAG, "onNext: "+news.size());
    }
    }
     * @param text
     */
    @Override
    public void sendMsg(String text) {
        if (NetworkUtil.networkConnected(context)){
            RetrofitClient.getTuringApi().getResponseResponseBody(APIKey,text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "onError: "+e.getMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.i(TAG, "onNext: "+responseBody.toString());
                            TuringBean turing= null;
                            try {
                                turing = gson.fromJson(responseBody.string(),TuringBean.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getResult(turing);
                            Log.i(TAG, "onNext: "+turing.getCode()+"    "+turing.getText());
                        }
                    });
        }else {
            view.showNoNetError();
        }
    }

    private void getResult(TuringBean turing){
        int code=turing.getCode();
        ChatBean chatbean=new ChatBean();
        if (code==RESPONSE_CODE_TEXT){
            chatbean.setText(turing.getText());
            view.showResponse(chatbean);
        }else if (code==RESPONSE_CODE_URL){
            TuringUrl turingurl=gson.fromJson(String.valueOf(turing.getCode()),TuringUrl.class);
            chatbean.setView(VIEW_URL);
            chatbean.setText(turingurl.getUrl());
        }else if (code==RESPONSE_CODE_NEWS){
            TuringNews turingnews=gson.fromJson((JsonElement) turing.getData(),TuringNews.class);
        }else if (code==RESPONSE_CODE_COOKBOOK){
            TuringCookbook turingcookbook=gson.fromJson((JsonElement) turing.getData(),TuringCookbook.class);
        }else if (code==RESPONSE_EXHASUT_ERROR){
            chatbean.setText(String.valueOf(R.string.response_exhasut_error));
            chatbean.setView(VIEW_TEXT);
        }else if (code==RESPONSE_INFONULL_ERROR){
            chatbean.setText(turing.getText());
            chatbean.setView(VIEW_TEXT);
        }
        view.showResponse(chatbean);
    }
    @Override
    public void startReading(int position) {

    }

    @Override
    public void setting() {

    }
}
