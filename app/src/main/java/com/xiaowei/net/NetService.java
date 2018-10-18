package com.xiaowei.net;

import com.xiaowei.bean.LoginBean;
import com.xiaowei.bean.ProductListBean;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface NetService {
    //服务器路径
    public static final String API_SERVER = "http://47.106.123.95:9999/";//测试地址
//    public static String API_SERVER = "http://www.chebaojr.com/app/";  //上线地址

    /**
     * /**
     * 1,登录
     */
    @POST("login.html")
    Observable<LoginBean> login(@Query("userName") String userName, @Query("pwd") String pwd);

    /**
     * /**
     * 1,产品列表
     */
    @GET("/product/productList")
    Observable<ProductListBean> productList(@Query("page") String page, @Query("size") String pageSize, @Query("minLoan") String minLoan
            , @Query("maxLoan") String maxLoan, @Query("minTerm") String minTerm, @Query("maxTerm") String maxTerm, @Query("sort") String sort);
}
