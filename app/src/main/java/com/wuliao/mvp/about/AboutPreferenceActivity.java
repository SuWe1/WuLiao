package com.wuliao.mvp.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wuliao.R;



public class AboutPreferenceActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);
        initView();

        AboutPreferenceFragment fragment=new AboutPreferenceFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.about_container,fragment).commit();
        new AboutPresenter(AboutPreferenceActivity.this,fragment);
    }
    private void initView(){
         setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
