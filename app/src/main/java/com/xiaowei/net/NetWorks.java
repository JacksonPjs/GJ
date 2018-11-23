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
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetWorks extends RetrofitUtils {
    protected static final NetService service = getRetrofit().create(NetService.class);

    /**
     * 登录
     *
     * @param observer
     */
    public static void login(String cellPhone, String validateCode, String registerClient,String androidid, Subscriber<LoginBean> observer) {
        setSubscribe(service.login(cellPhone, validateCode, registerClient,androidid), observer);
    }

    /**
     * 获取开屏与弹窗广告
     *
     * @param observer
     */
    public static void getAdvert(Subscriber<AdvertBean> observer) {
        setSubscribe(service.getOpeningAndPopUpAd(), observer);
    }

    /*
     *获取轮播图广告
     * @param observer
     */
    public static void getBannerList(Subscriber<BannerBean> observer) {
        setSubscribe(service.getBannerList(), observer);
    }

    /*
     *获取公告
     * @param observer
     */
    public static void getNotice(Subscriber<NoticeBean> observer) {
        setSubscribe(service.getNotice(), observer);
    }
    /*
     *登出
     * @param observer
     */

    public static void loginOut(Subscriber<LoginOutBean> observer) {
        setSubscribe(service.loginOut(), observer);
    }

    /**
     * 产品列表
     *
     * @param page
     * @param pageSize
     * @param minLoan
     * @param maxLoan
     * @param minTerm
     * @param maxTerm
     * @param
     * @param sort
     * @param observer
     */
    public static void productList(String page, String pageSize, String minLoan, String maxLoan, String minTerm, String maxTerm, String condition,String sort, Subscriber<ProductListBean> observer) {
        setSubscribe(service.productList(page, pageSize, minLoan, maxLoan, minTerm, maxTerm,condition, sort), observer);
    }

    /*
     *获取公告
     * @param observer
     */
    public static void getYzm(String phone, Subscriber<YzmBean> observer) {
        setSubscribe(service.getValidateCode(phone), observer);
    }

    /*
     *广告流量增加（点击广告后）
     * @param observer
     */
    public static void adAndNoticeFlowIncrease(String userId, String adId,Subscriber<BaseBean> observer) {
        setSubscribe(service.adAndNoticeFlowIncrease(userId,adId), observer);
    }
    /*
     *产品流量增加（点击产品后）
     * @param observer
     */
    public static void productFlowIncrease(String userId, String productId,Subscriber<BaseBean> observer) {
        setSubscribe(service.productFlowIncrease(userId,productId), observer);
    }
 /*
     *游客登录
     * @param observer
     */
    public static void touristLogin(Subscriber<BaseBean> observer) {
        setSubscribe(service.touristLogin(), observer);
    }
    /*
     *游客登录
     * @param observer
     */
    public static void submitFeedback(RequestBody route, Subscriber<BaseBean> observer) {
        setSubscribe(service.submitFeedback(route), observer);
    }
    /*
     *产品详情
     * @param observer
     */
    public static void getProductDetail(String productId,Subscriber<ProductDetailsBean> observer) {
        setSubscribe(service.getProductDetail(productId), observer);
    }


    /**
     * 插入观察者-泛型
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Subscriber<T> observer) {

        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
