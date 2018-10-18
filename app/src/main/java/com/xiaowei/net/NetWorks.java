package com.xiaowei.net;

import com.xiaowei.bean.LoginBean;
import com.xiaowei.bean.ProductListBean;

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
    public static void login(String cellPhone, String pwd, Subscriber<LoginBean> observer) {
        setSubscribe(service.login(cellPhone, pwd), observer);
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
    public static void productList(String page, String pageSize, String minLoan, String maxLoan, String minTerm, String maxTerm, String sort, Subscriber<ProductListBean> observer) {
        setSubscribe(service.productList(page,pageSize,minLoan,maxLoan,minTerm,maxTerm,sort), observer);
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
