package com.wuliao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 11033 on 2017/3/4.
 */

public class NetworkUtil {
    //是否连接网路
    public static boolean networkConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if (info!=null){
                return info.isAvailable();
            }
        }
        return false;
    }

    //是否连接wifi
    public static boolean wifiConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if (info!=null){
                if (info.getType()==ConnectivityManager.TYPE_WIFI)
                    return info.isAvailable();
            }
        }
        return false;
    }

    //移动数据是否打开
    public static boolean mobileDataConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if (info!=null){
                if (info.getType()==ConnectivityManager.TYPE_MOBILE)
                    return info.isAvailable();
            }
        }
        return false;
    }
}
