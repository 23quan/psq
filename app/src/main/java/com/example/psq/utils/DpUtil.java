package com.example.psq.utils;

import android.content.Context;

import com.example.psq.base.BaseApplication;

public class DpUtil {
    //获取屏幕高像素
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //获取屏幕宽像素
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * dp转px
     */
    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = BaseApplication.context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(float spValue) {
        final float scaledDensity = BaseApplication.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(float pxValue) {
        final float scaledDensity = BaseApplication.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }
}
