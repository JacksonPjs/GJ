package com.xiaowei.ui.activity.Personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaowei.R;
import com.xiaowei.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jackson
 * @date 2018/11/9
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initData();
    }

    private void initData(){
        title.setText(""+getResources().getString(R.string.tv_feedback));
        toolbar.setBackgroundResource(R.color.colorPrimary);

    }



}
