package com.wuliao.mvp.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.wuliao.R;
import com.wuliao.retrofit.RetrofitClient;
import com.wuliao.source.ChatBean;
import com.wuliao.source.TuringBean;
import com.wuliao.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
    private SharedPreferences sp;

    private static final String APIKey="65e6df4ce4d8425eae2c0e287138d165";

    public static final int REQUEST_PERMISSION_CAMERA_CODE=1;//权限状态码

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
        sp=context.getSharedPreferences("user_setting",Context.MODE_PRIVATE);
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
    Log.i(TAG, "onNext: "+listTuringBean.getCode()+listTuringBean.getText()+listTuringBean.getUrl());
    List<TuringNews> news=listTuringBean.getUrl();
    Log.i(TAG, "onNext: "+news.size());
    }
    }
     * @param text
     */
    @Override
    public void sendMsg(String text) {
        if (NetworkUtil.networkConnected(context)){
            RetrofitClient.getTuringApi().getResponse(APIKey,text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TuringBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(TuringBean turingBean) {
                            getResult(turingBean);
                            Log.i(TAG, "onNext: code: "+turingBean.getCode()+"  text:  "+turingBean.getText()+" url:  "+turingBean.getUrl());
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
        }else if (code==RESPONSE_CODE_URL | code==RESPONSE_CODE_COOKBOOK){
            chatbean.setView(VIEW_URL);
            chatbean.setText(turing.getText());
            chatbean.setUrl(turing.getUrl());
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
    public void voiceToText() {
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_PERMISSION_CAMERA_CODE);
        }
        RecognizerDialog dialog=new RecognizerDialog(context,null);
        dialog.setParameter(SpeechConstant.DOMAIN, "iat");
        dialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT,"mandarin ");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        dialog.show();
    }



    //语音识别结果
    private void printResult(RecognizerResult recognizerResult){
        //识别完整结果由多段json结果合起来的
        String text=parseIatResult(recognizerResult.getResultString());
        /*StringBuilder sb=new StringBuilder();
        sb.append(text);
        Log.i(TAG, "printResult: "+sb.toString());*/
        view.getVoice(text);
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    @Override
    public void textToVoice(String text) {
        String volume=sp.getString("voice_volume","80");
        String speed=sp.getString("voice_velocity","50");
        String name=sp.getString("voice_man","xiaoyan");
        SpeechSynthesizer mIts=SpeechSynthesizer.createSynthesizer(context,null);
        mIts.setParameter(SpeechConstant.VOICE_NAME,name);
        mIts.setParameter(SpeechConstant.SPEED,speed);
        mIts.setParameter(SpeechConstant.VOLUME,volume);
        mIts.startSpeaking(text,mSynListener);
    }

    SynthesizerListener mSynListener=new SynthesizerListener() {
        //开始播放
        @Override
        public void onSpeakBegin() {

        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }
        //暂停播放
        @Override
        public void onSpeakPaused() {

        }
        //恢复播放回调接口
        @Override
        public void onSpeakResumed() {

        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }
        //会话结束回调接口，没有错误时，error为null
        @Override
        public void onCompleted(SpeechError speechError) {

        }
        //会话事件回调接口
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };


    @Override
    public void startReading(int position) {

    }

    @Override
    public void setting() {

    }
}
