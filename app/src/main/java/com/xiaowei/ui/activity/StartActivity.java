package com.xiaowei.ui.activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.blibrary.utils.PermissionUtils.PermissionHelper;
import com.example.blibrary.utils.PermissionUtils.PermissionInterface;
import com.example.blibrary.utils.PermissionsManager;
import com.example.blibrary.utils.T;
import com.xiaowei.R;
import com.xiaowei.bean.AdvertBean;
import com.xiaowei.net.ErrorUtils.ShowError;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.activity.Guide.AdvertActivity;
import com.xiaowei.ui.activity.Guide.GuideActivity;
import com.xiaowei.utils.SharedPreferencesUtils;


import rx.Subscriber;


public class StartActivity extends BaseActivity {
    private final int REQUEST_VIDEO_PERMISSION = 1;
    private final static String TAG = "StartActivity";
    Activity activity;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);


        startMain();


    }

    public void startMain() {
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SharedPreferencesUtils.IsFirstOpenApp(this);
        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
        mHandler.sendEmptyMessageDelayed(1, 0);
            return;
        } else {
            // 如果不是第一次启动app，则正常显示启动屏
            setContentView(R.layout.activity_start);
            activity = this;
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                        getAdvertData();
                    break;
                case 1:
                    intent = new Intent(StartActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }

        ;
    };

    //获取数据
    public void getAdvertData() {
        NetWorks.getAdvert(new Subscriber<AdvertBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ShowError.log(e,activity);
            }

            @Override
            public void onNext(AdvertBean advertBean) {
                if (advertBean.getCode()==0){
                    AdvertBean.AdverBean posBean = null;
                    for (int i=0;i<advertBean.getData().size();i++){
                        if (advertBean.getData().get(i).getPosition()==1){
                            posBean=advertBean.getData().get(i);

                        }
                    }
                    if (posBean!=null){
                        intent = new Intent(StartActivity.this, AdvertActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("advertbean",advertBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else {
                        intent = new Intent(StartActivity.this, MainActivity.class);
//                        Bundle bundle=new Bundle();
//                        bundle.putSerializable("advertbean",advertBean);
//                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });

    }
}
