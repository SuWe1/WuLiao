package com.wuliao.app;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by Swy on 2017/4/3.
 */

public class App extends Application {
    public static boolean VOICE_IS_OPEN=true;
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(App.this, SpeechConstant.APPID+"=58dce383");
        if (getSharedPreferences("user_setting",MODE_PRIVATE).getBoolean("voiceIsOpen",true)){
            VOICE_IS_OPEN=true;
        }else {
            VOICE_IS_OPEN=false;
        }
    }
}
