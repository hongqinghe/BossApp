package com.android.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class Util {


    public static int getWindowWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        // 得到窗口属性
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 得到窗口宽度（像素）
        int width = metric.widthPixels;

        return width;
    }

    public static int getWindowHeight(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        // 得到窗口属性
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 得到窗口高度（像素）
        int height = metric.heightPixels;

        return height;
    }

    /**
     * 根据手机的分辨率从 DP的单位 转成为PX(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale);
    }

    public static int sp2px(Context context, float spValue) {

        final float
                fontScale = context.getResources().getDisplayMetrics().scaledDensity;

        return (int) (spValue * fontScale + 0.5f);

    }

    public static int getStatusHeight(Activity context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
        }
        return sbar;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 1).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            // 获取软件版本号
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 1).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
