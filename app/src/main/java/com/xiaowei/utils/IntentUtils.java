package com.xiaowei.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    public static void GoChrome(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://api.baiyiwangluo.com/h5/invite.jsp");//此处填链接
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
