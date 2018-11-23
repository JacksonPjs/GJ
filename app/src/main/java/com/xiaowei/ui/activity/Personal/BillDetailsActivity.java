package com.xiaowei.ui.activity.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jackson
 * @date 2018/11/19
 * 账单详情
 */
public class BillDetailsActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        ButterKnife.bind(this);
        activity = this;
        initView();
    }

    private void initView() {
        toolbar.setBackgroundResource(R.color.colorPrimary);
        title.setText(getString(R.string.bill_title));
    }

    @OnClick({R.id.btn_commit, R.id.img_updata})
    public void OnClick(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.img_updata:
                intent=new Intent(this,AddBillActivity.class);
                startActivity(intent);
                break;
        }
    }
}
