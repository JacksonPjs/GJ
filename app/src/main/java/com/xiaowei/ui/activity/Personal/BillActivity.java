package com.xiaowei.ui.activity.Personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.blibrary.loadinglayout.LoadingLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaowei.R;
import com.xiaowei.ui.Adapter.BillAdapter;
import com.xiaowei.ui.activity.BaseActivity;
import com.xiaowei.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jackson
 * @date 2018/11/19
 * 账单界面
 */
public class BillActivity extends BaseActivity {
    Activity activity;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

//    @Bind(R.id.refreshLayout)
//    RefreshLayout refreshLayout;

    @Bind(R.id.recycle)
    RecyclerView recyclerView;
    BillAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        activity=this;
        initView();

    }
    private void initView(){
//        toolbar.setBackgroundResource(R.color.colorPrimary);
        title.setText(getString(R.string.bill_title));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));


        recyclerView.setNestedScrollingEnabled(false);
//        refreshLayout.setRefreshHeader(new ClassicsHeader(activity));


        initData();
    }

    private void initData(){
        List<String> list=new ArrayList();
        for (int i=0;i<10;i++){
            list.add(""+getString(R.string.exit_app));
        }
        adapter=new BillAdapter(this,list);
        recyclerView.setAdapter(adapter);
        adapter.setOnBillClickListener(new BillAdapter.OnBillClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent=new Intent(activity,BillDetailsActivity.class);
                startActivity(intent);
            }
        });
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh();
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishLoadMoreWithNoMoreData();
//                refreshLayout.setNoMoreData(false);
//            }
//        });
    }

    @OnClick({R.id.analysis_rl,R.id.btn_add})
    public void OnClick(View view){
        Intent intent=null;
        switch (view.getId()){
            case R.id.analysis_rl:
                intent =new Intent(this,AnalysisActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add:
                intent=new Intent(this,AddBillActivity.class);
                startActivity(intent);
                break;
        }

    }
}
