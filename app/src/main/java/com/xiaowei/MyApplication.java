package com.xiaowei;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xiaowei.net.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static Context context ;

    public static MyApplication instance;
    List<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        context= this ;
        instance = this;
        activities = new ArrayList<Activity>();
        //初始化单列的OkHttpClient 对象
        initOkHttpUtils();
    }
    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = OkHttpUtils.getOkHttpClient();
        Log.i(TAG,"---->initOkHttpUtils: "+okHttpClient.toString());

    }


    public void Allfinlish() {
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);

            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}
