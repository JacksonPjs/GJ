package com.xiaowei.net;

import com.xiaowei.bean.AdvertBean;
import com.xiaowei.bean.BannerBean;
import com.xiaowei.bean.BaseBean;
import com.xiaowei.bean.LoginBean;
import com.xiaowei.bean.LoginOutBean;
import com.xiaowei.bean.NoticeBean;
import com.xiaowei.bean.ProductDetailsBean;
import com.xiaowei.bean.ProductListBean;
import com.xiaowei.bean.YzmBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
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
    Observable<LoginBean> login(@Query("phone") String phone, @Query("validateCode") String validateCode, @Query("registerClient") String registerClient, @Query("uniquelyIdentified") String uniquelyIdentified);

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
            , @Query("maxLoan") String maxLoan, @Query("minTerm") String minTerm, @Query("maxTerm") String maxTerm, @Query("condition") String condition, @Query("sort") String sort);

    /**
     * 4..获取公告
     */
    @GET("/user/validateCode")
    Observable<YzmBean> getValidateCode(@Query("phone") String phone);

    /**
     * 5.登出
     */
    @GET("/user/logout")
    Observable<LoginOutBean> loginOut();

    /**
     * 6.产品流量增加（点击产品后）
     */
    @GET("/product/flowIncrease")
    Observable<BaseBean> productFlowIncrease(@Query("userId") String userId, @Query("productId") String productId);

    /**
     * 7.广告流量增加（点击广告后）
     */
    @GET("/adAndNotice/flowIncrease")
    Observable<BaseBean> adAndNoticeFlowIncrease(@Query("userId") String userId, @Query("adId") String adId);

    /**
     * 8.游客登录
     */
    @POST("/user/touristLogin")
    Observable<BaseBean> touristLogin();

    /**
     * 9.问题反馈
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/user/submitFeedback")
    Observable<BaseBean> submitFeedback(@Body RequestBody route);

    /**
     * 10.产品详情
     */
    @GET("/product/getProductDetail/{productId}")
    Observable<ProductDetailsBean> getProductDetail(@Path("productId") String productId);


}
