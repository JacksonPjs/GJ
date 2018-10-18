package com.xiaowei.ui.activity.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blibrary.utils.LoginRegisterUtils;
import com.example.blibrary.utils.T;
import com.example.blibrary.utils.TimeUtils;
import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.get_regist)
    TextView getRegist;
    @Bind(R.id.login_phone)
    EditText phone;
    @Bind(R.id.yzm)
    EditText yzm;
    @Bind(R.id.cbox)
    CheckBox checkBox;
    boolean isCheck = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
        switch (view.getId()){
            case R.id.get_regist:
                TimeUtils.timerStart(30*1000,1000);
                break;
            case R.id.regist_go:
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


//                regist(phone.getText().toString(), password.getText().toString(), yzm.getText().toString(), tuijian.getText().toString() + "");


                break;
        }
    }
}
