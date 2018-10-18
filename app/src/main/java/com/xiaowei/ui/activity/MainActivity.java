package com.xiaowei.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.blibrary.banner.Banner;
import com.example.blibrary.banner.BannerIndicator;
import com.example.blibrary.citypicker.CityPicker;
import com.example.blibrary.citypicker.adapter.OnPickListener;
import com.example.blibrary.citypicker.model.City;
import com.example.blibrary.citypicker.model.HotCity;
import com.example.blibrary.citypicker.model.LocateState;
import com.example.blibrary.citypicker.model.LocatedCity;
import com.example.blibrary.utils.PermissionUtils.PermissionHelper;
import com.example.blibrary.utils.PermissionUtils.PermissionInterface;
import com.example.blibrary.utils.StringUtil;
import com.example.blibrary.utils.T;
import com.xiaowei.bean.ScreenBean;
import com.xiaowei.ui.Adapter.HomeAdapter;
import com.xiaowei.MyApplication;
import com.xiaowei.R;
import com.xiaowei.bean.ProductListBean;
import com.xiaowei.net.NetWorks;
import com.xiaowei.ui.Adapter.itemTextviewAdapter;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.ui.activity.Personal.PersonalActivity;
import com.xiaowei.utils.IntentUtils;
import com.xiaowei.widget.Dialog.AdvertDialog;
import com.xiaowei.widget.Dialog.CustomDialog;
import com.xiaowei.widget.DividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class MainActivity extends BaseActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, PermissionInterface {
    // 定义一个变量，来标识是否退出app
    private static boolean isExit = false;
    private String TAG = "com.xiaowei.ui.activity.MainActivity";
    Activity activity;
    @Bind(R.id.recycle)
    RecyclerView recyclerView;
    @Bind(R.id.textview_auto_roll)
    TextSwitcher textSwitcher;
    @Bind(R.id.main_banner)
    Banner banner;
    @Bind(R.id.indicator)
    BannerIndicator bannerIndicator;
    @Bind(R.id.screen_home)
    TextView screenHome;
    @Bind(R.id.tv_sort)
    TextView sortHome;
    @Bind(R.id.city)
    TextView city;
    HomeAdapter adapter;
    List<ProductListBean.DataBean.contentBean> datas;
    private int index = 0;
    private BitHandler bitHandler;
    AdvertDialog advertDialog = null;
    int page = 1;
    int pageSize = 10;
    String minLoan = "";//最小金额
    String maxLoan = "";//最大金额
    String minTerm = "";//最低期限(天)
    String maxTerm = "";//最高期限
    //    String condition="speed";//精准speed/rate/amount
    int sort = 1;//1/-1  （1：升序，-1：降序）
    boolean isfirst = true;
    String locationCity = null;
    String locationProvince = null;
    /*筛选pop声明*/
    int mScreenWidth;
    int mScreenHeight;
    int mPopupWindowWidth;
    int mPopupWindowHeight;
    PopupWindow mPopupWindow;
    List<String> loanList;
    List<String> termList;
    List<String> precisionList;
    List<ScreenBean> beans;
    itemTextviewAdapter loanAdapter;
    RecyclerView loanView;
    TextView del, complete;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    /*声明额度pop*/
    int ScreenWidth;
    int ScreenHeight;
    int PopupWindowWidth;
    int PopupWindowHeight;
    PopupWindow PopupWindow;
    TextView sortDi, sortGao;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    AMapLocationClientOption option = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ButterKnife.bind(this);
        initData();

    }

    @Override
    protected void onPause() {
        banner.pauseScroll();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁

    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.resumeScroll();
    }

    public void initData() {
        datas = new ArrayList<>();
        initMap();
        advertDialog = new AdvertDialog(activity);
        showDialog();

        initBanner();
        initNews();
        initRecycle();
        getData();
        //权限
        PermissionHelper permissionHelper = new PermissionHelper(this, this);
        permissionHelper.requestPermissions();
    }

    /*公告*/
    public void initNews() {
        bitHandler = new BitHandler();
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(activity);
                textView.setSingleLine();
                //  textView.setTextSize(Utils.sp2px(getContext(),12));
                textView.setTextSize(12);
                textView.setTextColor(Color.parseColor("#333231"));
                textView.setEllipsize(TextUtils.TruncateAt.END);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(lp);
                return textView;
            }
        });
    }

    /*banner*/
    public void initBanner() {

        List<String> drawables = new ArrayList<>();
        drawables.add("1");
//        drawables.add("2");
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

    /*地图相关*/
    public void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
//该方法默认为false。
        option.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        option.setOnceLocationLatest(true);


        //关闭缓存机制
        option.setLocationCacheEnable(false);

//设置是否返回地址信息（默认返回地址信息）
        option.setNeedAddress(true);

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                        //定位成功回调信息，设置相关消息
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = new Date(aMapLocation.getTime());
//                    df.format(date);//定位时间
//                    aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    aMapLocation.getCountry();//国家信息
                        aMapLocation.getProvince();//省信息
                        aMapLocation.getCity();//城市信息
                        aMapLocation.getDistrict();//城区信息
//                    aMapLocation.getStreet();//街道信息
//                    aMapLocation.getStreetNum();//街道门牌号信息
//                    aMapLocation.getCityCode();//城市编码
//                    aMapLocation.getAdCode();//地区编码
                        //获取定位信息
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(
                                aMapLocation.getCity() + ""
                                        + aMapLocation.getCityCode() + ""
                                        + aMapLocation.getAdCode() + ""
                                        + aMapLocation.getStreet() + ""
                                        + aMapLocation.getStreetNum());
                        Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                        locationCity = aMapLocation.getCity();
                        locationProvince = aMapLocation.getProvince();
                        city.setText(locationCity + "");

                        CityPicker.getInstance().locateComplete(new LocatedCity(locationCity, locationProvince, ""), LocateState.SUCCESS);


                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        T.ShowToastForLong(activity, "定位失败");
                        CityPicker.getInstance().locateComplete(new LocatedCity("定位失败", locationProvince, ""), LocateState.FAILURE);

                    }
                }
            }
        };


    }

    //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
    @Override
    public int getPermissionsRequestCode() {
        return 10000;
    }

    //设置该界面所需的全部权限
    @Override
    public String[] getPermissions() {
        return needPermissions;


    }

    //权限请求用户已经全部允许
    @Override
    public void requestPermissionsSuccess() {
        if (null != mLocationClient) {
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
    @Override
    public void requestPermissionsFail() {
        showMissingPermissionDialog();
    }

    //公告handler
    class BitHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (textSwitcher != null) {
//                textSwitcher.setText(datas.get(index).getName());
                textSwitcher.setText("据统计，申请5款产品以上，贷款成功率超过99%");
                bitHandler.removeMessages(0);
//                index++;
//                if (index == datas.size()) {
//                    index = 0;
//                    bitHandler.sendEmptyMessageDelayed(0, 2000);
//
//                } else if (index < datas.size()) {
//                    bitHandler.sendEmptyMessageDelayed(0, 2000);
//                }

            }

        }
    }

    /*
     * 获取数据
     * */
    public void getData() {

        bitHandler.sendEmptyMessage(0);
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
                        setDatas(contentBean);
                    }
                });
    }

    /*
     * 设置数据
     * */
    public void setDatas(List<ProductListBean.DataBean.contentBean> contentBean) {
//        bitHandler.removeMessages(0);
        datas.clear();
        if (contentBean.size() > 0) {
            datas.addAll(contentBean);
//            bitHandler.sendEmptyMessage(0);
        }
        adapter.notifyDataSetChanged();
    }

    /*点击事件*/
    @OnClick({R.id.gonggao, R.id.screen_home, R.id.call, R.id.tv_sort, R.id.mine})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.mine:
                intent = new Intent(activity, PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.screen_home://筛选
                View popview = View.inflate(MainActivity.this, R.layout.pop_screen_home, null);

                initPopuptWindow(popview);

                break;
            case R.id.call://打电话
//                if (isNeedCheck) {
////                    checkPermissions(needPermissions);
////                }

                PickCity(null, null);

                break;
            case R.id.tv_sort://
                View popsortview = View.inflate(MainActivity.this, R.layout.pop_sort_home, null);
                initSortPopuptWindow(popsortview);
                break;
        }

    }

    //广告弹窗
    public void showDialog() {
        if (isfirst) {
            advertDialog.setOnClickListener(new AdvertDialog.OnClickListener() {
                @Override
                public void onFinish() {
                    isfirst = false;
                }

                @Override
                public void onDraw() {
                    isfirst = false;

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("http://api.baiyiwangluo.com/h5/invite.jsp");//此处填链接
                    intent.setData(content_url);
                    startActivity(intent);

//                    SharedPreferencesUtils.setIsFirst(activity, false);

                }
            });
            advertDialog.show();

        }
    }

    /*
     * 选择城市
     * */
    private List<HotCity> hotCities;

    /*定位选择*/
    public void PickCity(String lCity, String province) {
        if (true) {
            hotCities = new ArrayList<>();
            hotCities.add(new HotCity("北京", "北京", "101010100"));
            hotCities.add(new HotCity("上海", "上海", "101020100"));
            hotCities.add(new HotCity("广州", "广东", "101280101"));
            hotCities.add(new HotCity("深圳", "广东", "101280601"));
            hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        } else {
            hotCities = null;
        }
        final LocatedCity locatedCity = null;
        if (StringUtil.isNullOrEmpty(lCity)) {
//            locatedCity = null;
        } else {
            locatedCity.setName(locationCity);
            locatedCity.setProvince(locationProvince + "");
        }
        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())
                .setLocatedCity(locatedCity)
//                .enableAnimation(enable)
//                .setAnimationStyle(anim)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
//                        currentTV.setText(data == null ? "杭州" : String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                        if (data != null) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                    Toast.LENGTH_SHORT)
                                    .show();
                            city.setText("" + data.getName());
                        }
                    }

                    @Override
                    public void onLocate() {
//                        CityPicker.getInstance().locateComplete(new LocatedCity("杭州", null, null), LocateState.SUCCESS);

                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (null != mLocationClient) {
                                    //给定位客户端对象设置定位参数
                                    mLocationClient.setLocationOption(option);
                                    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                                    mLocationClient.stopLocation();
                                    mLocationClient.startLocation();
                                }

//                                locatedCity.setProvince(locationProvince);
//                                locatedCity.setName(locationCity);
                            }
                        }, 3000);
                    }
                })
                .show();
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);


        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 设置额度高低pop框
     */
    private void initSortPopuptWindow(View view) {
        // 获取屏幕的width和height
        ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        ScreenHeight = getWindowManager().getDefaultDisplay().getHeight();

        //加载pop框的视图布局view
        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的内容
        // 参数2：width 指定PopupWindow的width
        // 参数3：height 指定PopupWindow的height
        PopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //获取pop框的宽和高
        PopupWindowWidth = PopupWindow.getWidth();
        PopupWindowHeight = PopupWindow.getHeight();
        // 需要设置一下此参数，点击外边可消失
        PopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失    这两步用于点击手机的返回键的时候，不是直接关闭activity,而是关闭pop框
        PopupWindow.setOutsideTouchable(true);

        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        PopupWindow.setFocusable(true);

//        //设置动画   采用属性动画
        // 动画效果必须放在showAsDropDown()方法上边，否则无效

        PopupWindow.setAnimationStyle(R.style.style_pop_animation);

//
//获取需要在其上方显示的控件的位置信息
        if (Build.VERSION.SDK_INT != 24) {
            //只有24这个版本有问题，好像是源码的问题
            PopupWindow.showAsDropDown(sortHome);
        } else {
            //7.0 showAsDropDown没卵子用 得这么写
            int[] location = new int[2];
            sortHome.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            PopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + sortHome.getHeight());
        }
        sortDi = view.findViewById(R.id.sort_di);
        sortGao = view.findViewById(R.id.sort_gao);

        sortDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow.dismiss();
                sort = -1;
                getData();
            }
        });
        sortGao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow.dismiss();
                sort = 1;
                getData();
            }
        });

        PopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopupWindow = null;// 当点击屏幕时，使popupWindow消失


            }
        });
    }

    /**
     * 设置筛选pop框
     */
    private void initPopuptWindow(View view) {
        // 获取屏幕的width和height
        mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();

        //加载pop框的视图布局view
        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的内容
        // 参数2：width 指定PopupWindow的width
        // 参数3：height 指定PopupWindow的height
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //获取pop框的宽和高
        mPopupWindowWidth = mPopupWindow.getWidth();
        mPopupWindowHeight = mPopupWindow.getHeight();
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失    这两步用于点击手机的返回键的时候，不是直接关闭activity,而是关闭pop框
        mPopupWindow.setOutsideTouchable(true);

        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        mPopupWindow.setFocusable(true);

//        //设置动画   采用属性动画
        // 动画效果必须放在showAsDropDown()方法上边，否则无效

        mPopupWindow.setAnimationStyle(R.style.style_pop_animation);

        backgroundAlpha(0.5f);
//
//获取需要在其上方显示的控件的位置信息
        if (Build.VERSION.SDK_INT != 24) {
            //只有24这个版本有问题，好像是源码的问题
            mPopupWindow.showAsDropDown(screenHome);
        } else {
            //7.0 showAsDropDown没卵子用 得这么写
            int[] location = new int[2];
            screenHome.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + screenHome.getHeight());
        }
        loanView = view.findViewById(R.id.loanview);
        del = view.findViewById(R.id.del);
        complete = view.findViewById(R.id.complete);
        initPopData();
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindow = null;// 当点击屏幕时，使popupWindow消失
                backgroundAlpha(1.0f);// 当点击屏幕时，使半透明效果取消


            }
        });

    }

    /*pop子控件数据
     * */
    public void initPopData() {
        loanList = new ArrayList();
        termList = new ArrayList();
        precisionList = new ArrayList();
        beans = new ArrayList<>();
        ScreenBean bean = new ScreenBean();
        loanList.add("不限额度");
        loanList.add("1000以下");
        loanList.add("1000~3000");
        loanList.add("3000~5000");
        loanList.add("5000~10000");
        loanList.add("10000以上");
        bean.setTitle("贷款金额");
        bean.setName(loanList);
        beans.add(bean);

        ScreenBean bean1 = new ScreenBean();
        bean1.setTitle("贷款期限");

        termList.add("不限期限");
        termList.add("7天");
        termList.add("15天");
        termList.add("15-30天");
        termList.add("30天以上");
        bean1.setName(termList);

        beans.add(bean1);

        ScreenBean bean2 = new ScreenBean();
        bean2.setTitle("精准标签");
        precisionList.add("下款速度最快");
        precisionList.add("最热门");
        precisionList.add("通过率最高");
        bean2.setName(precisionList);
        beans.add(bean2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        loanView.setLayoutManager(manager);


        loanAdapter = new itemTextviewAdapter(beans, activity);


        loanView.setAdapter(loanAdapter);
        loanAdapter.setOnItemClickLitener(new itemTextviewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int pos, int itempos) {
//                loanAdapter.setSelection(pos);
                switch (pos) {
                    case 0:
                        switch (itempos) {
                            case 0:
                                maxLoan = "";
                                minLoan = "";
                                break;
                            case 1:
                                maxLoan = 1000 + "";
                                minLoan = "0";
                                break;
                            case 2:
                                maxLoan = 3000 + "";
                                minLoan = 1000 + "";
                                break;
                            case 3:
                                maxLoan = 5000 + "";
                                minLoan = 3000 + "";
                                break;
                            case 4:
                                maxLoan = "10000";
                                minLoan = 5000 + "";
                                break;
                            case 5:
                                maxLoan = "";
                                minLoan = 10000 + "";
                                break;

                        }
                        break;
                    case 1:
                        switch (itempos) {
                            case 0:
                                minTerm = "";
                                maxTerm = "";
                                break;
                            case 1:
                                minTerm = 6 + "";
                                maxTerm = "0";
                                break;
                            case 2:
                                minTerm = 14 + "";
                                maxTerm = 7 + "";
                                break;
                            case 3:
                                minTerm = 29 + "";
                                maxTerm = 15 + "";
                                break;
                            case 4:
                                minTerm = "";
                                maxTerm = 30 + "";
                                break;
                        }
                        break;
                    case 2:

                        break;

                }
            }

            @Override
            public void OnItemLongClick(int pos) {

            }
        });


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                page = 1;
                pageSize = 10;
                minLoan = "";//最小金额
                maxLoan = "";//最大金额
                minTerm = "";//最低期限(天)
                maxTerm = "";//最高期限
//    String condition="speed";//精准speed/rate/amount
                sort = 1;//1/-1  （1：升序，-1：降序）
                getData();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                getData();
            }
        });
    }

    // 设置popupWindow背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        // 0.0-1.0
        getWindow().setAttributes(lp);
    }


    /*返回键监听*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    /*退出app
     * */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            MyApplication.instance.Allfinlish();

            System.exit(0);
        }
    }


}
