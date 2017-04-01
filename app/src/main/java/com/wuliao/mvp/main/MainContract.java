package com.wuliao.mvp.main;

import com.wuliao.mvp.BasePresenter;
import com.wuliao.mvp.BaseView;
import com.wuliao.source.ChatBean;

/**
 * Created by Swy on 2017/3/31.
 */

public interface MainContract {
    interface Presenter extends BasePresenter{
        void sendMsg(String text);
        void startReading(int position);
        void setting();
    }

    interface View extends BaseView<Presenter>{
        void showResponse(ChatBean chatBean);
        void showNoNetError();
    }
}
