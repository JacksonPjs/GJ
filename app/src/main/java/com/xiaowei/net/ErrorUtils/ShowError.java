package com.xiaowei.net.ErrorUtils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.blibrary.utils.T;
import com.xiaowei.bean.ProductListBean;
import com.xiaowei.ui.activity.Login.LoginActivity;
import com.xiaowei.utils.SharedPreferencesUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.adapter.rxjava.HttpException;

public class ShowError {
    public static void log(Throwable e, Activity activity) {
        Intent intent = null;
        try {

            if (e instanceof SocketTimeoutException) {//请求超时
                T.ShowToastForLong(activity, "请求超时");
            } else if (e instanceof ConnectException) {//网络连接超时
                T.ShowToastForLong(activity, "网络连接超时");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                T.ShowToastForLong(activity, "安全证书异常");

            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
//                            mOnSuccessAndFaultListener.onFault("网络异常，请检查您的网络状态");
                    T.ShowToastForLong(activity, "网络异常，请检查您的网络状态");

                } else if (code == 404) {
//                            mOnSuccessAndFaultListener.onFault("请求的地址不存在");
                    T.ShowToastForLong(activity, "请求的地址不存在");
                } else if (code == 401) {
                    SharedPreferencesUtils.SetLogin(activity,false);
                    if (exceptionRetrurn!=null){
                        exceptionRetrurn.onNotPermission();
                    }
//                    T.ShowToastForLong(activity, "登陆异常");

//                    intent=new Intent(activity,LoginActivity.class);
//                    activity.startActivity(intent);
//                    activity.finish();

                } else {
                    T.ShowToastForLong(activity, "网络异常");

//                            mOnSuccessAndFaultListener.onFault("请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
//                        mOnSuccessAndFaultListener.onFault("域名解析失败");
                T.ShowToastForLong(activity, "网络异常");
            } else {
                T.ShowToastForLong(activity, "网络异常");
//                        mOnSuccessAndFaultListener.onFault("error:" + e.getMessage());
            }
        } catch (Exception e2) {
            T.ShowToastForLong(activity, "网络异常");
            e2.printStackTrace();
        } finally {
            Log.e("ShowError", "error:" + e.getMessage());

        }


    }
     static ExceptionRetrurn exceptionRetrurn;
    public static void  setExceptionRetrurn(ExceptionRetrurn exceptionRetrurn1){
        exceptionRetrurn=exceptionRetrurn1;
    }
    public interface  ExceptionRetrurn{
         void onNotPermission();
    }

}
