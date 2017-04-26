package com.wuliao.mvp.detail.url;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wuliao.app.BeanTypes;

/**
 * Created by Swy on 2017/4/4.
 */

public class WebPresenter implements WebContract.Presenter {
    private WebContract.View view;
    private Context context;

    private String url;
    private String title;
    private BeanTypes type;

    public BeanTypes getType() {
        return type;
    }

    public void setType(BeanTypes type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebPresenter(WebContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void load() {
        if (type== BeanTypes.TYPE_ONE){
            view.hideInternetTitle();
        }
        if (url==null){
            view.showLoadingError();
            return;
        }
        view.showLoading();
        view.setTitle(title);
        view.showResultInWebView(url);
        view.stopLoading();
    }

    @Override
    public void openInBrower() {
        try {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }catch (Exception e){
            view.showBrowserNotFoundError();
        }
    }

    @Override
    public void copyUrl() {
        ClipboardManager manager= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data=ClipData.newPlainText("text",url);
        manager.setPrimaryClip(data);
        view.showTextCopied();
    }
}
