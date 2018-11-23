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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blibrary.utils.Utils;
import com.xiaowei.R;
import com.xiaowei.ui.Adapter.ViewPagerFramentAdapter;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.fragment.PlatformNotificationFragment;
import com.xiaowei.ui.fragment.RemindNotificationFragment;
import com.xiaowei.widget.Dialog.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*消息中心*/

public class NewsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    Activity activity;
    @Bind(R.id.news)
    ImageView news;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.remind_tv)
    TextView tvRemind;
    @Bind(R.id.platform_tv)
    TextView tvPlatform;
    @Bind(R.id.platform_view)
    ImageView platformView;
    @Bind(R.id.remind_view)
    ImageView remindView;


    CustomDialog.Builder builder;

    List<Fragment> fragmentList;
    ViewPagerFramentAdapter viewPagerFramentAdapter;

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
        fragmentList = new ArrayList<>();
        viewPagerFramentAdapter = new ViewPagerFramentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerFramentAdapter);
        viewPager.setOnPageChangeListener(this);
        PlatformNotificationFragment platformNotificationFragment = new PlatformNotificationFragment();
//        Bundle bundle3 = new Bundle();
//        bundle3.putString("id", id);
//        fragment_day3.setArguments(bundle3);
//        bundle3.putInt("borrowStatus", borrowStatus);
        fragmentList.add(platformNotificationFragment);
        fragmentList.add(new RemindNotificationFragment());
        viewPagerFramentAdapter.notifyDataSetChanged();
    }

    public void initData() {


    }

    @OnClick({R.id.back, R.id.news, R.id.remind_rl, R.id.platform_rl})
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
            case R.id.platform_rl:
                viewPager.setCurrentItem(0);
                break;
            case R.id.remind_rl:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switchBtn(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void switchBtn(int position) {
        tvPlatform.setTextColor(Utils.getColor(this, R.color.black_homne_tv));
        tvRemind.setTextColor(Utils.getColor(this, R.color.black_homne_tv));
        platformView.setVisibility(View.INVISIBLE);
        remindView.setVisibility(View.INVISIBLE);
        switch (position) {
            case 0:
                tvPlatform.setTextColor(Utils.getColor(this, R.color.colorPrimary));
                platformView.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvRemind.setTextColor(Utils.getColor(this, R.color.colorPrimary));
                remindView.setVisibility(View.VISIBLE);
                break;

        }

    }

}
