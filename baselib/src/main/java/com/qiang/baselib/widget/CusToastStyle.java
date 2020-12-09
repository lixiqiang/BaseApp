package com.qiang.baselib.widget;

import android.view.Gravity;

import com.hjq.toast.IToastStyle;

/**
 * @author lixiqiang
 * @data：2019/4/8 0008
 */
public class CusToastStyle implements IToastStyle {

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 0;
    }

    @Override
    public int getZ() {
        return 30;
    }

    @Override
    public int getCornerRadius() {
        return 30;
    }

    @Override
    public int getBackgroundColor() {
        return 0Xb8000000;
    }

    @Override
    public int getTextColor() {
        return 0XEEFFFFFF;
    }

    @Override
    public float getTextSize() {
        return 14;
    }

    @Override
    public int getMaxLines() {
        return 3;
    }

    @Override
    public int getPaddingStart() {
        return 24;
    }


    @Override
    public int getPaddingTop() {
        return 15;
    }

    @Override
    public int getPaddingEnd() {
        return getPaddingStart();
    }


    @Override
    public int getPaddingBottom() {
        return getPaddingTop();
    }
}
