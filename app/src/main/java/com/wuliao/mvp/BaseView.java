package com.wuliao.mvp;

import android.view.View;

/**
 * Created by lizhaotailang on 2017/2/10.
 */

public interface BaseView<T> {

    void initView(View view);

    void setPresenter(T presenter);

}
