package com.xiaowei.ui.activity.Personal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blibrary.log.Logger;
import com.example.blibrary.utils.T;
import com.xiaowei.App.Constants;
import com.xiaowei.MyApplication;
import com.xiaowei.R;
import com.xiaowei.bean.LoginBean;
import com.xiaowei.bean.LoginOutBean;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.Guide.GuideActivity;
import com.xiaowei.ui.activity.Guide.WelcomeActivity;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.utils.AppUtils;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.utils.SharedPreferencesUtils;
import com.xiaowei.widget.Dialog.CustomDialog;

import java.nio.Buffer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class PersonalActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.version_go)
    TextView versionTv;
    @Bind(R.id.photo)
    ImageView photo;
    CustomDialog.Builder builder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        activity = this;
        initData();
    }

    public void initData() {
        versionTv.setText(AppUtils.getVersionName(activity) + "");
        name.setText(SharedPreferencesUtils.getParam(activity,"phone","")+"");
    }

    public void loginOut() {
        NetWorks.loginOut(new Subscriber<LoginOutBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.ShowToastForLong(activity, "网络异常");
                Logger.e(e.toString());
            }

            @Override
            public void onNext(LoginOutBean LoginOutBean) {
                if (LoginOutBean.isData()) {
                    T.ShowToastForLong(activity, "登出成功");
//                    SharedPreferencesUtils.setParam(MyApplication.context, "cookie", "");
//                    SharedPreferencesUtils.savaUser(activity, new LoginBean(), "");
                    SharedPreferencesUtils.clearAll(activity);
                    Intent intent = new Intent(activity, WelcomeActivity.class);
                    startActivity(intent);
                    MyApplication.instance.Allfinlish();
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.about_rl, R.id.contact_rl, R.id.exit_rl, R.id.news})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.news:
                intent = new Intent(activity, NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.about_rl:
                IntentUtils.GoWeb(activity,Constants.aboutuslUrl,"关于我们");

                break;
            case R.id.contact_rl:
                intent = new Intent(activity, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_rl:
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
                        loginOut();

                        dialog.dismiss();
                    }
                });

                builder.setMessage("确定退出登录");
                builder.create().show();
                break;
        }
    }
}
