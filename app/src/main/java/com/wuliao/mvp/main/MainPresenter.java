package com.wuliao.mvp.main;

import android.content.Context;
import android.util.Log;

import com.wuliao.retrofit.RetrofitClient;
import com.wuliao.source.TuringBean;
import com.wuliao.source.turing.TuringNews;
import com.wuliao.util.NetworkUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Swy on 2017/3/31.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context context;
    private MainContract.View view;

    private static final String APIKey="65e6df4ce4d8425eae2c0e287138d165";

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
        if (NetworkUtil.networkConnected(context)){
            RetrofitClient.getTuringApi().getResponse(APIKey,text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TuringBean<List<TuringNews>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "onError: "+e.getMessage());
                        }

                        @Override
                        public void onNext(TuringBean<List<TuringNews>> listTuringBean) {
                            Log.i(TAG, "onNext: "+listTuringBean.getCode()+listTuringBean.getText()+listTuringBean.getData());
                            List<TuringNews> news=listTuringBean.getData();
                            Log.i(TAG, "onNext: "+news.size());
                        }
                    });
        }else {
            view.showNoNetError();
        }
    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void setting() {

    }
}
