package com.xiaowei.ui.activity.Guide;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blibrary.banner.Banner;
import com.example.blibrary.banner.BannerIndicator;
import com.example.blibrary.banner.Guide;
import com.example.blibrary.utils.PermissionUtils.PermissionHelper;
import com.example.blibrary.utils.PermissionUtils.PermissionInterface;
import com.xiaowei.R;
import com.xiaowei.ui.Adapter.ViewPagerAdapter;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.ui.activity.MainActivity;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 引导页
 * */
public class GuideActivity extends BaseActivity implements PermissionInterface {
    Activity activity;
    @Bind(R.id.guide_banner)
    Guide banner;
    @Bind(R.id.indicator)
    BannerIndicator bannerIndicator;

    @Bind(R.id.bt_next)
    TextView next;
    List<String> drawables;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        activity = this;
        initData();

    }


    private void initData() {
       drawables = new ArrayList<>();
        drawables.add("1");
        drawables.add("2");
        drawables.add("3");
        banner.setInterval(3000);
        banner.setPageChangeDuration(300);
        banner.setBannerDataInit(new Banner.BannerDataInit() {
            @Override
            public ImageView initImageView() {
                return (ImageView) getLayoutInflater().inflate(R.layout.imageview, null);
            }

            @Override
            public void initImgData(ImageView imageView, Object imgPath) {
                if (imgPath.equals("1"))
                    imageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.mipmap.banner));
                if (imgPath.equals("2"))
                    imageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.mipmap.icon_call));

                if (imgPath.equals("3"))
                    imageView.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.mipmap.ic_launcher));

            }
        });
        banner.setDataSource(drawables);

        //----------------------indicator start------------------------------
        bannerIndicator = (BannerIndicator) findViewById(R.id.indicator);
        bannerIndicator.setIndicatorSource(
                ContextCompat.getDrawable(getBaseContext(), R.mipmap.zuobiao_dangqian_banner),//select
                ContextCompat.getDrawable(getBaseContext(), R.mipmap.baisezuobiao_banner),//unselect
                50//widthAndHeight
        );
        banner.attachIndicator(bannerIndicator);
        //----------------------indicator end------------------------------


        banner.setOnBannerItemClickListener(new Guide.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(getBaseContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                IntentUtils.GoChrome(activity);

            }
        });
        banner.setOnBannerItemChangeListener(new Guide.OnBannerItemChangeListener() {
            @Override
            public void onItemChage(int position) {
//                Toast.makeText(getBaseContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                Log.e("pos==",""+position);
                if (position==drawables.size()){
                    next.setVisibility(View.VISIBLE);
                }else {
                    next.setVisibility(View.GONE);
                }
            }
        });


        String flag = getIntent().getStringExtra("flag");
        if (flag.equals("exit")) {

            fromExit();
        }
        SharedPreferencesUtils.setIsFirst(this, true);
        PermissionHelper helpe=new PermissionHelper(this,this);
        helpe.requestPermissions();
    }

    @OnClick({R.id.bt_next})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bt_next:
                intent=new Intent(activity,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /*退出登陆后进入该界面
     * */
    public void fromExit() {
//        viewPager.setCurrentItem(2);
//        banner.s
        banner.setCurrentItem(drawables.size());
    }

    @Override
    protected void onPause() {
        banner.pauseScroll();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.resumeScroll();
    }
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    };

    @Override
    public int getPermissionsRequestCode() {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        banner.pauseScroll();

        return needPermissions;


    }

    @Override
    public void requestPermissionsSuccess() {
//权限请求用户已经全部允许
        banner.resumeScroll();
    }

    @Override
    public void requestPermissionsFail() {
//权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
        banner.resumeScroll();
    }
}
