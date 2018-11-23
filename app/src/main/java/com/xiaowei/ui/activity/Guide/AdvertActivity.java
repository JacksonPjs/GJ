package com.xiaowei.ui.activity.Guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blibrary.utils.TimeUtils;
import com.xiaowei.App.Constants;
import com.xiaowei.R;
import com.xiaowei.bean.AdvertBean;
import com.xiaowei.bean.BaseBean;
import com.xiaowei.bean.NoticeBean;
import com.xiaowei.net.ErrorUtils.ShowError;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.ui.activity.MainActivity;
import com.xiaowei.ui.activity.StartActivity;
import com.xiaowei.utils.DeviceUtils;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
/*开屏广告页*/
public class AdvertActivity extends BaseActivity {

    Activity activity;
    @Bind(R.id.go)
    TextView go;
    @Bind(R.id.advert_img)
    ImageView advertImg;
    AdvertBean bean;
    AdvertBean.AdverBean posBean=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advert);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        activity=this;
        initData();
    }

    public void initData(){

        TimeUtils.timerStart(3*1000,1000);
        Bundle bundle= getIntent().getExtras();
        bean = (AdvertBean) bundle.getSerializable("advertbean");
        if (bean==null)
            finish();

        for (int i=0;i<bean.getData().size();i++){
            if (bean.getData().get(i).getPosition()==1){
                posBean=bean.getData().get(i);
                Glide.with(activity)
                        .load(posBean.getImage())
                        .error(R.mipmap.bg_start)
                        .into(advertImg);
            }
        }
//
        TimeUtils.setCountDownTimerListener(new TimeUtils.CountDownTimerlistener() {
            @Override
            public void onTick(String time) {
                go.setText(getString(R.string.skip)+time+"s");
            }

            @Override
            public void onFinish() {
                go.setText(getResources().getString(R.string.skip));
                TimeUtils.timerCancel();
                startMain();

            }
        });

    }

    @OnClick({R.id.go,R.id.advert_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
                TimeUtils.timerCancel();
                startMain();
                break;
            case R.id.advert_img:
                if(posBean!=null){
                    if (SharedPreferencesUtils.IsLogin(this)){
                        IntentUtils.GoChrome(activity,posBean.getUrl()+"");
                        commitData(SharedPreferencesUtils.getParam(activity,"userid","")+"",
                                posBean.getId()+"");
                    } else {
                        Intent intent=new Intent(this,LoginActivity.class);
                        intent.putExtra("intentflag",Constants.INTENTCODE_ADVERT);
                        startActivity(intent);
                        finish();
                    }


                }

                break;
        }
    }



    public void startMain(){
        Intent intent=new Intent(activity,MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("advertbean",bean);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /*提交数据*/
    public void commitData( String userid, String adid){
        NetWorks.adAndNoticeFlowIncrease(userid,adid,new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ShowError.log(e,activity);
            }

            @Override
            public void onNext(BaseBean baseBean) {

            }
        });
    }
}
