package com.xiaowei.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.blibrary.utils.T;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.ui.activity.web.WebActivity;

public class IntentUtils {


    public static void GoChrome(Context context,String url) {
        Intent intent = new Intent();

        if (SharedPreferencesUtils.IsLogin(context)){
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);//此处填链接
            intent.setData(content_url);
            context.startActivity(intent);
        }else {
            intent=new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }

    }

    public static void GoWeb(Context context,String url,String title) {
        Intent intent = new Intent(context,WebActivity.class);
        if (SharedPreferencesUtils.IsLogin(context)){
            intent.putExtra("url",url);
            intent.putExtra("title",title);
            context.startActivity(intent);
        }else {
            intent=new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }

    }

    public static void GoWeb(Context context,String url,String title,String downloadLink) {
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("downloadLink",downloadLink);
        context.startActivity(intent);
    }
    public static void GoActivity(Context context , Class<T> acivity) {
        Intent intent = new Intent();
        intent.setClass(context,acivity);
        context.startActivity(intent);
    }

    /**
     * 跳转到微信
     * @param context
     */
    public static void GoWeChat(Context context) {
        try {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_LONG).show();
        }
    }
}
