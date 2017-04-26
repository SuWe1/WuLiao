package com.wuliao.one;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wuliao.app.BeanTypes;
import com.wuliao.mvp.detail.url.WebActivity;
import com.wuliao.retrofit.RetrofitClient;
import com.wuliao.source.one.One;
import com.wuliao.source.one.OneBean;
import com.wuliao.util.NetworkUtil;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Swy on 2017/4/16.
 */

public class OnePresenter implements OneContract.Presenter {
    private static final String TAG = "OnePresenter";
    private Context context;
    private OneContract.View view;

    private ArrayList<OneBean> list = new ArrayList<>();


    private static final String channel = "wdj";
    private static final String uuid = "ffffffff-a90e-706a-63f7-ccf973aae5ee";
    private static final String platform = "android";
    private static final String version = "4.0.2";

    public OnePresenter(Context context, OneContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void subscribe() {
        Log.i(TAG, "subscribe is start");
        if (NetworkUtil.networkConnected(context)) {
            RetrofitClient.getOneApi().getOneResponse(channel, version, uuid, platform)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<One>() {
                        @Override
                        public void onCompleted() {
                            Log.i(TAG, "onCompleted: " + "Completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "onError: " + e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(One one) {
                            if (one.getRes() == 0) {
                                for (OneBean item : one.getData()) {
                                    list.add(item);
                                }
                            }
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            Log.i(TAG, "onStart begin");
                        }
                    });
        } else {
            view.showNotNetError();
        }
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadPosts(boolean cleaing) {
        if (cleaing) {
            view.showLoading();
        }else {
            list.clear();
        }
        Log.i(TAG, "loadPosts is success 1");
        subscribe();
        Log.i(TAG, "loadPosts is success 2");
        view.showResult(list);
        Log.i(TAG, "loadPosts is success 3");
        view.Stoploading();
    }


    @Override
    public void reflush() {
        loadPosts(true);
    }

    @Override
    public void loadMore() {
        loadPosts(true);
    }

    @Override
    public void StartReading(int positon) {
        OneBean oneBean=list.get(positon);
        Intent intent=new Intent(context, WebActivity.class);
        intent.putExtra("url",oneBean.getShare_url());
        intent.putExtra("title",oneBean.getTitle());
        intent.putExtra("type", BeanTypes.TYPE_ONE);
        context.startActivity(intent);
    }
}
