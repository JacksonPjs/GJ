package com.xiaowei.ui.activity.Login;

import android.app.Activity;
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blibrary.log.Logger;
import com.example.blibrary.utils.LoginRegisterUtils;
import com.example.blibrary.utils.T;
import com.example.blibrary.utils.TimeUtils;
import com.xiaowei.R;
import com.xiaowei.bean.LoginBean;
import com.xiaowei.bean.YzmBean;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.MainActivity;
import com.xiaowei.utils.SharedPreferencesUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class LoginActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.get_regist)
    TextView getRegist;
    @Bind(R.id.login_phone)
    EditText phone;
    @Bind(R.id.yzm)
    EditText yzm;
    @Bind(R.id.cbox)
    CheckBox checkBox;
    boolean isCheck = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activity = this;
        checkBox.setChecked(true);//默认选中
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
        TimeUtils.setCountDownTimerListener(new TimeUtils.CountDownTimerlistener() {
            @Override
            public void onTick(String time) {
                getRegist.setEnabled(false);
                getRegist.setText(time);
            }

            @Override
            public void onFinish() {
                getRegist.setEnabled(true);
                getRegist.setText(getResources().getString(R.string.done));
                TimeUtils.timerCancel();
            }
        });
    }

    @OnClick({R.id.get_regist, R.id.regist_go})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.get_regist:
                getYzm();
                TimeUtils.timerStart(30 * 1000, 1000);
                break;
            case R.id.regist_go:
//                 intent=new Intent(activity,MainActivity.class);
//                startActivity(intent);
                loginTerm();

//                loginTerm();
//                regist(phone.getText().toString(), password.getText().toString(), yzm.getText().toString(), tuijian.getText().toString() + "");


                break;
        }
    }

    public void getYzm() {
        NetWorks.getYzm(phone.getText().toString(), new Subscriber<YzmBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(YzmBean yzmBean) {
                Log.e("yzm==", yzmBean.getData() + "");
                yzm.setText(yzmBean.getData() + "");
            }
        });
    }


    public void login() {
        NetWorks.login(phone.getText().toString(), yzm.getText().toString(), "2", new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.ShowToastForLong(LoginActivity.this, "网络异常");
                Logger.e(e.toString());
            }



            @Override
            public void onNext(LoginBean loginBean) {
                if (loginBean.getCode() == 0) {
                    T.ShowToastForShort(activity, "登录成功");
//                    SharedPreferencesUtils.setIsLogin(activity,true);
                    SharedPreferencesUtils.savaUser(activity,loginBean,"");
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    TimeUtils.timerCancel();
                    finish();
                } else {
                    T.ShowToastForShort(activity, "登录失败");

                }
            }
        });

    }

    /*
     * 登录条件判断
     * */
    public void loginTerm() {
        if (LoginRegisterUtils.isNullOrEmpty(phone)) {
            T.ShowToastForShort(this, "手机号码未输入");
            return;
        }

        if (!LoginRegisterUtils.isPhone(phone)) {
            T.ShowToastForShort(this, "手机号码不正确");
            return;
        }


        if (LoginRegisterUtils.isNullOrEmpty(yzm)) {
            T.ShowToastForShort(this, "手机验证码未输入");
            return;
        }
        if (!isCheck) {
            T.ShowToastForShort(this, getString(R.string.tv_not_readandagree));
            return;
        }
        login();
    }


}
