package com.wuliao.mvp.main;

import android.content.Context;

/**
 * Created by Swy on 2017/3/31.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context context;
    private MainContract.View view;

    public MainPresenter(Context context, MainContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void sendMsg(String text) {

    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void setting() {

    }
}
