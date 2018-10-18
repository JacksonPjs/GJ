package com.xiaowei.ui.activity.Guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blibrary.utils.TimeUtils;
import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.MainActivity;
import com.xiaowei.ui.activity.StartActivity;
import com.xiaowei.utils.IntentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvertActivity extends BaseActivity {

    Activity activity;
    @Bind(R.id.go)
    TextView go;
    @Bind(R.id.advert_img)
    ImageView advertIimg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        ButterKnife.bind(this);
        activity=this;
        initData();
    }

    public void initData(){
        TimeUtils.timerStart(5*1000,1000);

//
        TimeUtils.setCountDownTimerListener(new TimeUtils.CountDownTimerlistener() {
            @Override
            public void onTick(String time) {
                go.setText(getString(R.string.skip)+time+"s");
            }

            @Override
            public void onFinish() {
                go.setText(getResources().getString(R.string.skip));
                TimeUtils.timerCancel();
                startMain();

            }
        });

    }

    @OnClick({R.id.go,R.id.advert_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
                TimeUtils.timerCancel();
                startMain();
                break;
            case R.id.advert_img:
                IntentUtils.GoChrome(activity);
                break;
        }
    }

    public void startMain(){
        Intent intent=new Intent(activity,MainActivity.class);
        startActivity(intent);
        finish();
    }

    Handler mHandler = new Handler() {
        Intent intent = null;

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
//                    intent = new Intent(StartActivity.this, GestureVerifyActivity.class);
//                    startActivity(intent);
//                    finish();
                    break;
            }

        }

        ;
    };
}
