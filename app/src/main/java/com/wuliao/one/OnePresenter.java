package com.wuliao.one;

import android.content.Context;
import android.util.Log;

import com.wuliao.R;
import com.wuliao.retrofit.RetrofitClient;
import com.wuliao.source.OneBean;
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

    private ArrayList<OneBean> list;


    private static final String channel="wdj";
    private static final String uuid="ffffffff-a90e-706a-63f7-ccf973aae5ee";
    private static final String platform="android";

    public OnePresenter(Context context, OneContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void subscribe() {
        if (NetworkUtil.isInstallChrome(context)){
            RetrofitClient.getOneApi().getOneResponse(channel,context.getString(R.string.one_version),uuid,platform)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<OneBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "onError: "+e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(OneBean oneBean) {
                            Log.i(TAG, "onNext: "+oneBean.getTitle());
                            list.add(oneBean);
                        }
                    });
        }else {
            view.showNotNetError();
        }
        view.Stoploading();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadPosts(boolean cleaing) {
        if (cleaing){
            view.showLoading();
        }
        subscribe();
        view.showResult(list);

    }

    @Override
    public void reflush() {
        loadPosts(true);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void StartReading(int positon) {

    }
}
