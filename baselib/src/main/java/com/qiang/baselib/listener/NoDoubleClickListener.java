package com.qiang.baselib.listener;

import android.view.View;


/**
 * @author lixiqiang
 * @date：2019/9/10 0010
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {


    private static final int MIN_CLICK_DELAY_TIME = 1000;

    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }
    }

    protected abstract void onNoDoubleClick(View view);

}
