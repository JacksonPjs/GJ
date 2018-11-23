package com.xiaowei.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blibrary.utils.T;
import com.xiaowei.R;
import com.xiaowei.bean.BannerBean;
import com.xiaowei.bean.BaseBean;
import com.xiaowei.bean.DetailsItemBean;
import com.xiaowei.bean.ProductDetailsBean;
import com.xiaowei.net.ErrorUtils.ShowError;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.Adapter.ProductDetailsAdapter;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.utils.DataUtils;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.utils.SharedPreferencesUtils;
import com.xiaowei.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * @author jackson
 * @date 2018/11/14
 * 产品详情页
 */
public class ProductDetailsActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycle_details)
    RecyclerView recyclerView;
    ProductDetailsAdapter adapter;
    @Bind(R.id.image)
    ImageView imageView;
    @Bind(R.id.details_name)
    TextView name;
    @Bind(R.id.details_tips)
    TextView tip;
    @Bind(R.id.deatils_num)
    TextView deatilsNum;
    @Bind(R.id.details_ede)
    TextView detailsEde;
    @Bind(R.id.details_data)
    TextView detailsData;
    @Bind(R.id.details_lilv)
    TextView detailsLilv;
    @Bind(R.id.details_time)
    TextView detailsTime;

    int productId;
    List<DetailsItemBean> datas;
    ProductDetailsBean.DataBean dataBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        ButterKnife.bind(this);
        activity = this;
        initView();

    }

    private void initView() {
        datas = new ArrayList<>();
        toolbar.setBackgroundResource(R.color.colorPrimary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);//禁用滑动事件
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new ProductDetailsAdapter(datas, this);
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData() {
        productId = getIntent().getIntExtra("productid", -1);
        getProductDetail(productId);
    }

    /*点击事件*/
    @OnClick({R.id.btn_apply})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_apply:
                if (!SharedPreferencesUtils.IsLogin(this)) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (dataBean!=null)
                IntentUtils.GoWeb(this, dataBean.getJumpLink(), dataBean.getName(),dataBean.getAndroidLink());

                break;
        }
    }

    public void getProductDetail(int productId) {
        NetWorks.getProductDetail(productId + "", new Subscriber<ProductDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ShowError.log(e,activity);
            }

            @Override
            public void onNext(ProductDetailsBean productDetailsBean) {
                if (productDetailsBean.getCode() == 0) {
                    setData(productDetailsBean);

                }
            }
        });
    }

    public void setData(ProductDetailsBean productDetailsBean) {
        ProductDetailsBean.DataBean d = productDetailsBean.getData();
        dataBean=d;
        Glide.with(this)
                .load(d.getIcon())
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        name.setText("" + d.getName());
        tip.setText("" + d.getSynopsis());
        deatilsNum.setText("已" + d.getApplicants()+"人申请");
        String moneyShow = "";
        if (d.getMinLoan() == d.getMaxLoan()) {
            moneyShow = DataUtils.t1chager(d.getMinLoan());
        } else {
            moneyShow = DataUtils.t1chager(d.getMinLoan()) + "~" + DataUtils.t1chager(d.getMaxLoan());
        }
        detailsEde.setText(moneyShow);

        detailsLilv.setText(d.getDayRateStr() + "/日");
        detailsTime.setText("" + d.getLoanTimeStr());
        String dataShow="";
        if (d.getMinTerm()==d.getMaxTerm()){
            dataShow=DataUtils.t2chager(d.getMinTerm());
        }else {
            dataShow=DataUtils.t2chager(d.getMinTerm())+"~"+DataUtils.t2chager(d.getMaxTerm());
        }
        detailsData.setText(dataShow);


        DetailsItemBean itemBean = new DetailsItemBean();
        itemBean.setTitle("办理流程");
        itemBean.setDetails("" + d.getProcess());
        datas.add(itemBean);
        DetailsItemBean itemBean1 = new DetailsItemBean();
        itemBean1.setTitle("申请条件");
        itemBean1.setDetails("" + d.getApplicationConditions());
        datas.add(itemBean1);
        DetailsItemBean itemBean2 = new DetailsItemBean();
        itemBean2.setTitle("所需材料");
        itemBean2.setDetails("" + d.getRequiredMaterials());
        datas.add(itemBean2);
        adapter.notifyDataSetChanged();


    }

}
