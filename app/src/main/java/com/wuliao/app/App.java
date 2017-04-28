package com.wuliao.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import io.realm.Realm;

/**
 * Created by Swy on 2017/4/3.
 */

public class App extends Application {
    public static boolean VOICE_IS_OPEN=true;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        SpeechUtility.createUtility(App.this, SpeechConstant.APPID+"=58dce383");
        if (getSharedPreferences("user_setting",MODE_PRIVATE).getBoolean("voiceIsOpen",true)){
            VOICE_IS_OPEN=true;
        }else {
            VOICE_IS_OPEN=false;
        }
        if (getSharedPreferences("user_setting",MODE_PRIVATE).getInt("theme", 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
