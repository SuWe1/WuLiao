package com.wuliao.mvp.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wuliao.R;

/**
 * Created by Swy on 2017/4/4.
 */

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        initView();
        SettingPreferenceFragment fragment=SettingPreferenceFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.settings_container,fragment).commit();
        new SettingPreferencePresenter(SettingActivity.this,fragment);
    }

    private void initView(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
