package com.xiaowei.ui.activity.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blibrary.utils.LoginRegisterUtils;
import com.example.blibrary.utils.T;
import com.xiaowei.R;
import com.xiaowei.bean.BaseBean;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * @author jackson
 * @date 2018/11/9
 * 问题反馈
 */
public class FeedbackActivity extends BaseActivity {
    @Bind(R.id.edit_feedback)
    EditText editText;
    @Bind(R.id.commit)
    Button commit;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        activity=this;
        title.setText("" + getResources().getString(R.string.tv_feedback));
        toolbar.setBackgroundResource(R.color.colorPrimary);

    }

    @OnClick({R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:
                if (!SharedPreferencesUtils.IsLogin(this)){
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                if (LoginRegisterUtils.isNullOrEmpty(editText)){
                    T.ShowToastForLong(this,"内容不能为空");
                    return;
                }



                submitFeedback();
                break;
        }
    }

    private void submitFeedback() {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("userId", SharedPreferencesUtils.getParam(this,"userid",""));
            requestData.put("content", editText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());

        NetWorks.submitFeedback( requestBody, new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getCode()==0){
                    editText.setText("");
                    T.ShowToastForLong(activity,"提交成功");
                }
            }
        });
    }


}
