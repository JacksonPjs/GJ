package com.example.blibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * 权限管理
 *
 */

public class PermissionsManager {


    public static void requstPersmissions(Activity activity, String[] permissions, int REQUEST_VIDEO_PERMISSIONS) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_VIDEO_PERMISSIONS);
    }

    /**
     * 判断还有哪些权限未允许
     * 如果返回的数组长度为0，说明所用权限都允许了
     *
     * @param mActivity
     * @param permissions
     * @return
     */
    public static String[] haveNoPermissions(Context mActivity, String[] permissions) {
        ArrayList<String> haveNo = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                haveNo.add(permission);
                Log.i("haveNoPermissions",permission);
            }
        }

        return haveNo.toArray(new String[haveNo.size()]);
    }
}
