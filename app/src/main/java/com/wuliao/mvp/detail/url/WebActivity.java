package com.wuliao.mvp.detail.url;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wuliao.R;

/**
 * Created by Swy on 2017/4/4.
 */

public class WebActivity extends AppCompatActivity {
    private WebFragment webFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        if (savedInstanceState!=null){
            webFragment= (WebFragment) getSupportFragmentManager().getFragment(savedInstanceState,"webFragment");
        }else {
            webFragment=WebFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,webFragment).commit();
        }
        Intent intent=getIntent();
        WebPresenter presenter=new WebPresenter(webFragment,WebActivity.this);
        presenter.setUrl(intent.getStringExtra("url"));
        presenter.setTitle(intent.getStringExtra("title"));

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (webFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"webFragment",webFragment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
