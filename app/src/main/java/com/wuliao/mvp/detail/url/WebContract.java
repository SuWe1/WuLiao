package com.wuliao.mvp.detail.url;

import com.wuliao.mvp.BasePresenter;
import com.wuliao.mvp.BaseView;

/**
 * Created by Swy on 2017/4/4.
 */

public interface WebContract {
    interface Presenter extends BasePresenter{
        void load();
        void openInBrower();
        void copyUrl();
    }
    interface View extends BaseView<Presenter>{
        // 设置标题
        void setTitle(String title);
        void showLoading();
        void stopLoading();
        void showLoadingError();
        void  showNotNetError();
        // 用户选择在浏览器中打开时，如果没有安装浏览器，显示没有找到浏览器错误
        void showBrowserNotFoundError();
        // 显示已复制文字内容
        void showTextCopied();
        void showResultInWebView(String url);
    }
}
