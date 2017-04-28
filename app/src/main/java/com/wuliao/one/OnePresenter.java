package com.wuliao.one;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wuliao.app.BeanTypes;
import com.wuliao.mvp.detail.url.WebActivity;
import com.wuliao.realm.RealmHelper;
import com.wuliao.retrofit.RetrofitClient;
import com.wuliao.source.one.One;
import com.wuliao.source.one.OneBean;
import com.wuliao.util.NetworkUtil;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
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
    private boolean isClear=true;
    private Realm realm;


    private static final String channel = "wdj";
    private static final String uuid = "ffffffff-a90e-706a-63f7-ccf973aae5ee";
    private static final String platform = "android";
    private static final String version = "4.0.2";

    public OnePresenter(Context context, OneContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        realm = RealmHelper.newRealmInstance();
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
                            if (isClear){
                                list.clear();
                            }
                            if (one.getRes() == 0) {
                                for (final OneBean item : one.getData()) {
                                    list.add(item);
                                    /**
                                     * realm.copyToRealm(item); ==>  realm.copyToRealmOrUpdate(item);
                                     * issue:https://github.com/realm/realm-java/issues/2053
                                     */
                                    if (searchIsIfExits(item.getId())){
                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                OneBean one=realm.copyToRealmOrUpdate(item);
                                            }
                                        });
                                        Log.i(TAG, "realm insert success ");
                                    }else {
                                        Log.i(TAG, "item is exist");
                                    }
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
            RealmResults<OneBean> results=realm.where(OneBean.class).findAll();
            //获取最近的十个数据
            for (int i=0;i<10;i++){
                list.add(results.get(i));
            }
//            view.showNotNetError();
        }
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadPosts(boolean cleaing) {
        if (cleaing) {
            view.showLoading();
        }
        subscribe();
        view.showResult(list);
        Log.i(TAG, "list size = "+list.size());
        view.Stoploading();
    }

    private boolean searchIsIfExits(String id) {
        OneBean oneBean=realm.where(OneBean.class).equalTo("id",id).findFirst();
        if (oneBean==null){
            return true;
        }
        return false;
    }

    private void updateRealmResult(OneBean newOneBean){
        OneBean oldOneBean=realm.where(OneBean.class).equalTo("id",newOneBean.getId()).findFirst();
        oldOneBean.setTitle(newOneBean.getTitle());
        oldOneBean.setLike_count(newOneBean.getLike_count());
        oldOneBean.setItem_id(newOneBean.getItem_id());
        oldOneBean.setAuthor(newOneBean.getAuthor());
        oldOneBean.setImg_url(newOneBean.getImg_url());
        oldOneBean.setPost_date(newOneBean.getPost_date());
        oldOneBean.setForward(newOneBean.getForward());
        oldOneBean.setShare_url(newOneBean.getShare_url());

    }


    @Override
    public void reflush() {
        isClear=true;
        loadPosts(true);
    }

    @Override
    public void loadMore() {
        loadPosts(true);
    }

    @Override
    public void StartReading(int positon) {
        OneBean oneBean = list.get(positon);
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", oneBean.getShare_url());
        intent.putExtra("title", oneBean.getTitle());
        intent.putExtra("type", BeanTypes.TYPE_ONE);
        context.startActivity(intent);
    }
}
