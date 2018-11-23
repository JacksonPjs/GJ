package com.xiaowei.widget.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author jackson
 * @date 2018/11/16
 */
public abstract class BasePopwindows extends PopupWindow {
    private Context mContext;

    public BasePopwindows(Context context) {
        super(context);
        mContext = context;
    }



    protected View initLayout(int layoutId) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        //setContentView(view);
        return view;
    }

    public void setBackground(int colorId){
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(mContext,colorId));
        setBackgroundDrawable(dw);
    }

    protected void setSize(int width,int height){
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    /**
     * 设置布局文件子类
     *
     * @return
     */
    protected abstract int setLayout();

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT != 24) {
            //只有24这个版本有问题，好像是源码的问题
            super.showAsDropDown(anchor);
        } else {
            //7.0 showAsDropDown没卵子用 得这么写
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
           showAtLocation(anchor, Gravity.NO_GRAVITY, x, y + anchor.getHeight());
        }
    }

    // 设置popupWindow背景透明度
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        // 0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
