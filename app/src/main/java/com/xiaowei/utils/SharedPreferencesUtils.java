package com.xiaowei.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xiaowei.bean.LoginBean;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float,
 * Long类型的参数 同样调用getParam就能获取到保存在手机里面的数据
 * <p>
 * 保存数据 SharedPreferencesUtils.setParam(this, "String", "xiaanming");
 * SharedPreferencesUtils.setParam(this, "int", 10);
 * SharedPreferencesUtils.setParam(this, "boolean", true);
 * SharedPreferencesUtils.setParam(this, "long", 100L);
 * SharedPreferencesUtils.setParam(this, "float", 1.1f);
 * <p>
 * <p>
 * 获取数据 SharedPreferencesUtils.getParam(TimerActivity.this, "String", "");
 * SharedPreferencesUtils.getParam(TimerActivity.this, "int", 0);
 * SharedPreferencesUtils.getParam(TimerActivity.this, "boolean", false);
 * SharedPreferencesUtils.getParam(TimerActivity.this, "long", 0L);
 * SharedPreferencesUtils.getParam(TimerActivity.this, "float", 0.0f);
 *
 * @author xiaanming
 */
public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "xiaowei";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key,
                                  Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 清除数据
     *
     * @param context
     * @return
     */
    public static void clearAll(Context context) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();// 获取编辑器
        // 登录成功后先清除原来的数据
        editor.clear();
        editor.commit();

    }

    /**
     * 保存数据
     *
     * @param context
     * @return
     */
    public static void savaUser(Context context, LoginBean bean, String phone) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();// 获取编辑器
        editor.putBoolean("islogin", true);//判断是否登录
        editor.putString("userid",bean.getData()+"");
//		editor.putBoolean("existsPaypwd", customerBean.isExistsPaypwd());//判断是否登录
        editor.putString("phone", phone);// 保存手机号

        editor.commit();

    }


    public static boolean IsLogin(Context context) {
        return (boolean) SharedPreferencesUtils.getParam(context, "islogin", false);
    }
    public static void SetLogin(Context context,boolean is) {
         SharedPreferencesUtils.setParam(context, "islogin", is);
    }
    public static String getPhone(Context context){
        return (String) SharedPreferencesUtils.getParam(context, "phone", "");

    }


    public static void setIsFirst(Context context, boolean IsFirst) {
        SharedPreferencesUtils.setParam(context, "IsFirst", IsFirst);
    }
    /*判断是否第一次打开app*/
    public static boolean IsFirstOpenApp(Context context) {
        return (boolean) SharedPreferencesUtils.getParam(context, "IsFirst", true);
    }
}
