package com.wuliao.mvp.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.Preference;
import android.util.Log;

import com.wuliao.R;
import com.wuliao.app.App;
import com.wuliao.realm.RealmHelper;
import com.wuliao.source.one.OneBean;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Swy on 2017/4/4.
 */

public class SettingPreferencePresenter implements SettingContract.Presenter {

    private static final String TAG = "SettingPreferencePresen";
    private Context context;
    private SharedPreferences sp;
    private SettingContract.View view;
    private SharedPreferences.Editor editor;
    private Realm realm;

    public SettingPreferencePresenter(Context context, SettingContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        sp =context.getSharedPreferences("user_setting",Context.MODE_PRIVATE);
        editor=sp.edit();
        realm= RealmHelper.newRealmInstance();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setVoiceVelocity(Preference preference, Object newValue) {
        editor.putString("voice_velocity", (String) newValue);
        editor.apply();
    }

    @Override
    public void setVoiceVolume(Preference preference, Object newValue) {
        editor.putString("voice_volume", (String) newValue);
        editor.apply();
    }

    @Override
    public void setVoiceMan(Preference preference, Object newValue) {
        editor.putString("voice_man", (String) newValue);
        editor.apply();
    }

    @Override
    public void setVoiceIsOpen(Preference preference) {
        if (App.VOICE_IS_OPEN){
            sp.edit().putBoolean("voiceIsOpen",false).apply();
            App.VOICE_IS_OPEN=false;
        }else {
            sp.edit().putBoolean("voiceIsOpen",true).apply();
            App.VOICE_IS_OPEN=true;
        }
    }

    @Override
    public void cleanCache() {
        final RealmResults<OneBean> results=realm.where(OneBean.class).findAll();
        //realm对数据的所有处理都必须在事务中进行
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        Log.i(TAG, "cleanCache is success");
        view.showCleanCacheSuccess();
    }

    @Override
    public String getVolumeSummary() {
        String [] options=context.getResources().getStringArray(R.array.voice_volume);
        String str=sp.getString("voice_volume","80");//缺省值50
        switch (str){
            case "10":
                return options[0];
            case "20":
                return options[1];
            case "30":
                return options[2];
            case "40":
                return options[3];
            case "60":
                return options[5];
            case "70":
                return options[6];
            case "80":
                return options[7];
            case "90":
                return options[8];
            case "100":
                return options[9];
            default:
                return options[4];
        }
    }

    @Override
    public String getVelocitySummary() {
        String [] options=context.getResources().getStringArray(R.array.voice_velocity);
        String str=sp.getString("voice_velocity","50");//缺省值50
        switch (str){
            case "30":
                return options[0];
            case "80":
                return options[2];
            default:
                return options[1];
        }
    }

    @Override
    public String getManSummary() {
        String [] options=context.getResources().getStringArray(R.array.voice_man);
        String str=sp.getString("voice_man","xiaoyan");//缺省值50
        switch (str){
            case "xiaoyu":
                return options[1];
            default:
                return options[0];
        }
    }
}
