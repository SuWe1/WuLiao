package com.wuliao.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

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

    public static boolean isInstallChrome(Context context){
        //检查是否安装Chrome
        String packageName = "com.android.chrome";
        Intent browserIntent = new Intent();
        browserIntent.setPackage(packageName);
        List<ResolveInfo> activitiesList = context.getPackageManager().queryIntentActivities(
                browserIntent, -1);
        if (activitiesList.size() > 0){
            // 使用Chrome Custom Tab打开
            return true;
        }else {
            //使用自定义的WebViewActivit打开
            return false;
        }
    }
}
