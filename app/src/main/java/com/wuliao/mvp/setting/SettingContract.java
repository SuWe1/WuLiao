package com.wuliao.mvp.setting;

import android.support.v7.preference.Preference;

import com.wuliao.mvp.BasePresenter;
import com.wuliao.mvp.BaseView;

/**
 * Created by Swy on 2017/4/4.
 */

public interface SettingContract {
    interface Presenter extends BasePresenter{
        void setVoiceVelocity(Preference preference, Object newValue);
        void setVoiceVolume(Preference preference,Object newValue);
        void setVoiceMan(Preference preference,Object newValue);
        void setVoiceIsOpen(Preference preference);
        void cleanCache();
        String getVolumeSummary();
        String getVelocitySummary();
        String getManSummary();
    }

    interface View extends BaseView<Presenter>{
        void showCleanCacheSuccess();
    }
}
