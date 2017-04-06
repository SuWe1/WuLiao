package com.wuliao.mvp.about;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.wuliao.R;



public class AboutPresenter implements AboutContract.Presenter {
    private Context context;
    private AboutContract.View view;
    private AppCompatActivity activity;
    private SharedPreferences sp;
    private CustomTabsIntent.Builder customTabsIntent;//Chrome Custom Tab 会默认用chrome打开 相比webview加载速度更快

    public AboutPresenter(AppCompatActivity activity, AboutContract.View view) {
        this.activity=activity;
        this.view = view;
        this.view.setPresenter(this);

        sp=activity.getSharedPreferences("user_setting",Context.MODE_PRIVATE);

//        customTabsIntent = new CustomTabsIntent.Builder();
//        customTabsIntent.setToolbarColor(activity.getResources().getColor(R.color.colorPrimary));
//        customTabsIntent.setShowTitle(true);
    }

    @Override
    public void commitBug() {
        //万一用户没有安装右键app 所有虚弱try catch
        try {
            Uri uri=Uri.parse(activity.getString(R.string.sendto));
            Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic));
            intent.putExtra(Intent.EXTRA_TEXT,
                    activity.getString(R.string.device_model) + Build.MODEL + "\n"
                            + activity.getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                            + activity.getString(R.string.about_version));
            activity.startActivity(intent);
        }catch (android.content.ActivityNotFoundException ex){
            view.showFeedbackError();
        }
    }

    long[] hits = new long[3];
    @Override
    public void showEasterEgg() {
        System.arraycopy(hits,1,hits,0,hits.length-1);
        hits[hits.length - 1] = SystemClock.uptimeMillis();
        if (hits[0] >= (SystemClock.uptimeMillis() - 500)) {
            AlertDialog dialog = new AlertDialog.Builder(activity).create();
            dialog.setCancelable(false);
            dialog.setTitle(R.string.easter_egg);
            dialog.setMessage(activity.getString(R.string.easter_egg_content));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
