package com.xiaowei.ui.activity.Guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xiaowei.R;
import com.xiaowei.ui.activity.Login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends Activity {

    Activity activity;
    @Bind(R.id.image)
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        activity = this;
        initData();

    }

    public void initData(){
        imageView.setBackgroundResource(R.mipmap.bg_guide4);
    }

    @OnClick({R.id.bt_next})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_next:
                intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
