package com.xiaowei.ui.activity.Guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blibrary.utils.TimeUtils;
import com.xiaowei.R;
import com.xiaowei.bean.AdvertBean;
import com.xiaowei.bean.BaseBean;
import com.xiaowei.bean.NoticeBean;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.ui.activity.MainActivity;
import com.xiaowei.ui.activity.StartActivity;
import com.xiaowei.utils.DeviceUtils;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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
        setContentView(R.layout.activity_advert);
        ButterKnife.bind(this);
        activity=this;
        initData();
    }

    public void initData(){
        TimeUtils.timerStart(3*1000,1000);
        getAdvertData();
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
                IntentUtils.GoChrome(activity);
                if(posBean!=null){
                    String androidid=DeviceUtils.getUniqueId(activity);
                    commitData(SharedPreferencesUtils.getParam(activity,"userid","")+"",posBean.getId()+"",androidid);
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
    //获取数据
    public void getAdvertData() {
        NetWorks.getAdvert(new Subscriber<AdvertBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AdvertBean advertBean) {
                bean=advertBean;


                if (advertBean.getCode()==0){
                    for (int i=0;i<advertBean.getData().size();i++){
                        if (advertBean.getData().get(i).getPosition()==1){
                            posBean=advertBean.getData().get(i);
                            Glide.with(activity)
                                    .load(posBean.getImage())
                                    .error(R.mipmap.bg_start)
                                    .into(advertImg);
                        }
                    }
                }

            }
        });

    }
    /*提交数据*/
    public void commitData( String userid, String adid,String androidid){
        NetWorks.adAndNoticeFlowIncrease(userid,adid ,androidid,new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {

            }
        });
    }
}
