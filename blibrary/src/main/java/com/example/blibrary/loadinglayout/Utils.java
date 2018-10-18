package com.example.blibrary.loadinglayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * create by Weavey
 * on date 2016-01-06
 * TODO
 */

public class Utils {


    public static Drawable getDrawble(Context conetxt, @DrawableRes int id){
        return ContextCompat.getDrawable(conetxt,id);
    }

    public static int getColor(Context conetxt, @ColorRes int id){
        return  ContextCompat.getColor(conetxt,id);
    }

    public static String getString(Context conetxt, @StringRes int id){
        return  conetxt.getResources().getString(id);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, int dip) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static <T extends View> T findViewById(View v, int id) {


        return (T) v.findViewById(id);
    }

    public static int dp2px(Context context, float dpValue) {
        if (dpValue <= 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static String formatNum(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    public static String formatMillisecond(int millisecond) {
        String retMillisecondStr;

        if (millisecond > 99) {
            retMillisecondStr = String.valueOf(millisecond / 10);
        } else if (millisecond <= 9) {
            retMillisecondStr = "0" + millisecond;
        } else {
            retMillisecondStr = String.valueOf(millisecond);
        }

        return retMillisecondStr;
    }

}