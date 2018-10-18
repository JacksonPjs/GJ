package com.xiaowei.ui.activity.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.Guide.GuideActivity;

import java.nio.Buffer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.photo)
    ImageView photo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        activity=this;
    }
    public void initData(){

    }


    @OnClick({R.id.back, R.id.about_rl, R.id.contact_rl, R.id.exit_rl, R.id.news})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.news:
                intent=new Intent(activity,NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.about_rl:
                break;
            case R.id.contact_rl:
                intent=new Intent(activity,ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_rl:
                intent=new Intent(activity,GuideActivity.class);
                intent.putExtra("flag","exit");
                startActivity(intent);
                break;
        }
    }
}
