package com.qiang.baselib.utils;

import android.content.Context;

/**
 * @author lixiqiang
 * @date： 2019/9/10 0010
 */
public class ScreenUtils {

    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWith(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
