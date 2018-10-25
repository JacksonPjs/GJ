package com.xiaowei.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.xiaowei.ui.activity.web.WebActivity;

public class IntentUtils {

    public static void GoChrome(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://api.baiyiwangluo.com/h5/invite.jsp");//此处填链接
        intent.setData(content_url);
        context.startActivity(intent);
    }

    public static void GoWeb(Context context,String url,String title) {
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
}
