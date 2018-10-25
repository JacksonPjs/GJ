package com.xiaowei.ui.activity.Personal;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class ContactActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.news)
    ImageView news;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    CustomDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        activity = this;
        initView();
        initData();
    }

    public void initView() {
        news.setVisibility(View.GONE);
        title.setText("联系我们");
        toolbar.setBackgroundResource(R.color.colorPrimary);
    }

    public void initData() {

    }

    @OnClick({R.id.back, R.id.call})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.call:
                builder = new CustomDialog.Builder(activity);
                builder.setTitle("联系客服");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(activity, new String[]{
                                    Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            call();
                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
                    }
                });

                builder.setMessage(""+getString(R.string.phonenum));
                builder.create().show();
                break;
        }
    }
    private void call() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + getString(R.string.phonenum)));
            startActivity(intent);
        }
    }

    //动态权限结果返回
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults != null && grantResults.length > 0) {
            switch (requestCode) {
                case 1:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        call();
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
