package com.wuliao.mvp.setting;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
    private Preference volumePreference,velocityPreference,manPreference;

    public SettingPreferenceFragment() {
    }

    public static SettingPreferenceFragment newInstance() {

        return new SettingPreferenceFragment();
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
        velocityPreference=findPreference("voice_velocity");
        velocityPreference.setSummary(presenter.getVelocitySummary());
        velocityPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                presenter.setVoiceVelocity(preference,newValue);
                velocityPreference.setSummary(presenter.getVelocitySummary());
                return true;
            }
        });
        manPreference=findPreference("voice_man");
        manPreference.setSummary(presenter.getManSummary());
        manPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                presenter.setVoiceMan(preference,newValue);
                manPreference.setSummary(presenter.getManSummary());
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initView(View view) {
        toolbar= (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        if (presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void showCleanCacheSuccess() {
        Snackbar.make(toolbar,R.string.clear_success,Snackbar.LENGTH_SHORT).show();
    }
}
