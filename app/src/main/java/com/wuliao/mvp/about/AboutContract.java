package com.wuliao.mvp.about;

import com.wuliao.mvp.BasePresenter;
import com.wuliao.mvp.BaseView;



public interface AboutContract {
    interface  Presenter extends BasePresenter{
        //提交bug
        void commitBug();
        //小彩蛋
        void showEasterEgg();
    }
    interface  View extends BaseView<Presenter> {
        void showFeedbackError();
    }
}
