package com.xiaowei.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blibrary.banner.Banner;
import com.example.blibrary.banner.BannerIndicator;
import com.example.blibrary.utils.T;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaowei.R;
import com.xiaowei.bean.ProductListBean;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.Adapter.HomeAdapter;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class DesignActivity extends BaseActivity {
    Activity activity;
    @Bind(R.id.recycle)
    RecyclerView recyclerView;
    @Bind(R.id.main_banner)
    Banner banner;
    @Bind(R.id.indicator)
    BannerIndicator bannerIndicator;
    @Bind(R.id.refrelayout)
    RefreshLayout refreshLayout;

    HomeAdapter adapter;
    List<ProductListBean.DataBean.contentBean> datas;

    int page = 1;
    int pageSize = 10;
    String minLoan = "";//最小金额
    String maxLoan = "";//最大金额
    String minTerm = "";//最低期限(天)
    String maxTerm = "";//最高期限
    //    String condition="speed";//精准speed/rate/amount
    int sort = 1;//1/-1  （1：升序，-1：降序）
    int type=0;//1为加载，0为刷新

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        activity = this;
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        initBanner();
        initRecycle();
        initRefresh();
        getData(0);
    }

    /*banner*/
    public void initBanner() {

        List<String> drawables = new ArrayList<>();
        drawables.add("1");
        drawables.add("2");
//        drawables.add("3");
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


        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(getBaseContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                IntentUtils.GoChrome(activity);

            }
        });
    }

    /*列表初始化*/
    public void initRecycle() {
        datas=new ArrayList<>();
        adapter = new HomeAdapter(datas, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));


        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void OnItemViewClick(int pos) {
//                Intent intent = new Intent(activity, DesignActivity.class);
//                startActivity(intent);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://api.baiyiwangluo.com/h5/invite.jsp");//此处填链接
                intent.setData(content_url);
                startActivity(intent);


            }
        });
    }

    public void initRefresh(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    /*
     * 获取数据
     * */
    public void getData(final int type) {

        NetWorks.productList(page + "", pageSize + "", minLoan + "", maxLoan + "",
                minTerm + "", maxTerm + "", sort + "", new Subscriber<ProductListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        T.ShowToastForLong(activity, "网络异常");
                    }

                    @Override
                    public void onNext(ProductListBean product) {
                        List<ProductListBean.DataBean.contentBean> contentBean = product.getData().getContent();
                        setDatas(contentBean,type);
                    }
                });
    }

    /*
     * 设置数据
     * */
    public void setDatas(List<ProductListBean.DataBean.contentBean> contentBean,int type) {
//        bitHandler.removeMessages(0);
        if (type==0)
        datas.clear();

        if (contentBean.size() > 0) {
            datas.addAll(contentBean);
//            bitHandler.sendEmptyMessage(0);
        }else{
            T.ShowToastForLong(activity,"没有新数据");
        }
        adapter.notifyDataSetChanged();
    }

}