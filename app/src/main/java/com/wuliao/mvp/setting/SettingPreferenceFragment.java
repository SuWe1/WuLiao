package com.wuliao.mvp.setting;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wuliao.R;

/**
 * Created by Swy on 2017/4/4.
 */

public class SettingPreferenceFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter presenter;
    private Toolbar toolbar;
    private Preference volumePreference;

    public SettingPreferenceFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference_fragment);
        initView(getView());
        findPreference("isVoice").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.setVoiceIsOpen(preference);
                return false;
            }
        });
        findPreference("clear_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.cleanCache();
                return false;
            }
        });
        volumePreference=findPreference("voice_volume");
        volumePreference.setSummary(presenter.getVolumeSummary());
        volumePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                presenter.setVoiceVolume(preference,newValue);
                volumePreference.setSummary(presenter.getVolumeSummary());
                return true;
            }
        });
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {

    }

    @Override
    public void showCleanCacheSuccess() {

    }
}
