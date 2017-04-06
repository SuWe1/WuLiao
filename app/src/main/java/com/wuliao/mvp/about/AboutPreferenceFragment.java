package com.wuliao.mvp.about;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wuliao.R;

/**
 * PreferenceFragmentCompat 需要 preferenceTheme 来设置
 * 详情见style.xml
 */

public class AboutPreferenceFragment extends PreferenceFragmentCompat implements AboutContract.View {
    private Context context;

    private Toolbar toolbar;
    private AboutContract.Presenter presenter;
    public AboutPreferenceFragment() {
    }

    public static AboutPreferenceFragment newInstance() {
        return new AboutPreferenceFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.about_preferenc_fragment);
        initView(getView());
        findPreference("commit_bug").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.commitBug();
                return false;
            }
        });
        findPreference("author").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.showEasterEgg();
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showFeedbackError() {
        Snackbar.make(toolbar, R.string.no_app_store_found, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {

        if (presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void initView(View view) {
        toolbar= (Toolbar) getActivity().findViewById(R.id.toolbar);
    }
}
