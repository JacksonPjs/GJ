package com.xiaowei.net;

import com.xiaowei.bean.AdvertBean;
import com.xiaowei.bean.BannerBean;
import com.xiaowei.bean.LoginBean;
import com.xiaowei.bean.NoticeBean;
import com.xiaowei.bean.ProductListBean;
import com.xiaowei.bean.YzmBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface NetService {
    //服务器路径
//    public static final String API_SERVER = "http://192.168.1.217:8080/";//测试地址
    public static final String API_SERVER = "http://47.106.123.95:9999/";//上线地址

    /**
     * /**
     * 1,登录
     */
//    @POST("login.html")
    @POST("/user/login")
    Observable<LoginBean> login(@Query("phone") String phone, @Query("validateCode") String validateCode, @Query("registerClient") String registerClient );

    /**
     * /**
     * 2.获取开屏与弹窗广告
     */
    @GET("/adAndNotice/getOpeningAndPopUpAd")
    Observable<AdvertBean> getOpeningAndPopUpAd();

    /**
     * /**
     * 3.获取轮播图广告
     */
    @GET("/adAndNotice/getCarouselAd")
    Observable<BannerBean> getBannerList();
 /**
     * /**
     * 4..获取公告
     */
    @GET("/adAndNotice/getCotice")
    Observable<NoticeBean> getNotice();

    /**
     * /**
     * 5,产品列表
     */
    @GET("/product/productList")
    Observable<ProductListBean> productList(@Query("page") String page, @Query("size") String pageSize, @Query("minLoan") String minLoan
            , @Query("maxLoan") String maxLoan, @Query("minTerm") String minTerm, @Query("maxTerm") String maxTerm, @Query("sort") String sort);
/**
     * 4..获取公告
     */
    @GET("/user/validateCode")
    Observable<YzmBean> getValidateCode(@Query("phone") String phone);


}
