package com.xiaowei.net;

import android.content.Context;


import com.xiaowei.MyApplication;
import com.xiaowei.utils.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/2.
 */

public class AddCookiesInterceptor implements Interceptor {
  private Context context;

  public AddCookiesInterceptor(Context context) {
    super();
    this.context = context;

  }

  @Override
  public Response intercept(Chain chain) throws IOException {

    final Request.Builder builder = chain.request().newBuilder();


    builder.addHeader("content-type","application/json;charset=UTF-8");

    //   Logger.e("------------------------------------111");
    //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可
//        if (SharedPreferencesUtils.IsLogin(context)){
//
//       //     Logger.e("------------------------------------");
            StringBuilder sb = new StringBuilder();
//            sb.append(" _ed_token_");
//            sb.append("=");
//            sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context,"token",""));
//            sb.append(";");
//
//            sb.append(" _ed_username_");
//            sb.append("=");
//            sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context,"name",""));
//            sb.append(";");
//
//            sb.append(" _ed_cellphone_");
//            sb.append("=");
//            sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context,"phone",""));
//            sb.append(";");
          sb.append(SharedPreferencesUtils.getParam(MyApplication.context,"cookie",""));

            builder.addHeader("Cookie", sb.toString());
////
//////            Logger.d("Cookie---"+sb.toString());
////
//        }



    return chain.proceed(builder.build());
  }
}