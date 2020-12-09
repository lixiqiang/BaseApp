package com.qiang.baselib.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.hjq.toast.ToastUtils;
import com.qiang.baselib.widget.CusToastStyle;
import com.tencent.mmkv.MMKV;

/**
 * @author lixiqiang
 * @date： 2019/9/10 0010
 */
public class BaseApplication extends Application {

    public static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        MultiDex.install(this);
        ToastUtils.init(this);
        ToastUtils.initStyle(new CusToastStyle());
        String rootDir = MMKV.initialize(this);

        Log.e("base", "rootDir=" + rootDir);
    }

    public static Context getContext() {
        return context;
    }
}
