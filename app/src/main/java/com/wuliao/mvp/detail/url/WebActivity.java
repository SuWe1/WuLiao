package com.wuliao.mvp.detail.url;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.wuliao.R;
import com.wuliao.app.BeanTypes;

/**
 * Created by Swy on 2017/4/4.
 */

public class WebActivity extends AppCompatActivity {
    private WebFragment webFragment;
    private  WebView webView;
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
        presenter.setType((BeanTypes) intent.getSerializableExtra("type"));
        webView= (WebView) findViewById(R.id.web_view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (webFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"webFragment",webFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*if (keyCode==KeyEvent.KEYCODE_BACK){
            //监考还回键
            if (webView.canGoBack()){
                webView.goBack();
            }
        }*/
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
