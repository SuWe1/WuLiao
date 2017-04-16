package com.wuliao.util;

import android.content.Context;
import android.os.Build;

/**
 * Created by Swy on 2017/4/16.
 */

public class PhoneUtil {

    //android 获取当前手机型号
    public static String getPhoneModel(Context context){
        Build bd = new Build();
        return  bd.MODEL;
    }

    //android 获取当前手机品牌
    public static String getPhoneProduct(Context context) {
        Build bd = new Build();
        return  bd.PRODUCT;
    }
}
