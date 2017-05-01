package com.wuliao.one;

import com.wuliao.mvp.BasePresenter;
import com.wuliao.mvp.BaseView;
import com.wuliao.source.one.OneBean;

import java.util.ArrayList;

/**
 * Created by Swy on 2017/4/16.
 */

public interface OneContract {
    interface Presenter extends BasePresenter{
        // 请求数据
        void loadPosts(boolean cleaing);
        //刷新数据
        void  reflush();
        //加载更多
        void loadMore();
        //显示详情
        void StartReading(int positon);
    }
    interface View extends BaseView<Presenter>{
        void showError();
        void showLoading();
        void Stoploading();
        void showResult(ArrayList<OneBean> list);
        void showNotNetError();
        void notifyDataChanged();
    }
}
