package com.xiaowei.ui.activity.Personal;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.widget.Dialog.CustomDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*消息中心*/

public class NewsActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.news)
    ImageView news;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    CustomDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        activity = this;
        initView();
        initData();
    }

    public void initView() {
        news.setVisibility(View.VISIBLE);
        news.setImageResource(R.mipmap.icon_del);
        title.setText("消息中心");
        toolbar.setBackgroundResource(R.color.colorPrimary);
    }

    public void initData() {

    }

    @OnClick({R.id.back, R.id.news})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.news:
                builder = new CustomDialog.Builder(activity);
                builder.setTitle("提示");

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setMessage("确定清空消息记录");
                builder.create().show();
                break;
        }
    }

}
