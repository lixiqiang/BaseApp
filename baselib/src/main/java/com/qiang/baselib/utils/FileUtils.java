package com.qiang.baselib.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.qiang.baselib.base.BaseApplication;

import java.io.File;


/**
 * @author lixiqiang
 * @date：2019/9/12 0012
 */
public class FileUtils {


    public static String getDiskCacheDir() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // SDCard/Android/data/应用包名/cache
            if (BaseApplication.getContext().getExternalCacheDir() != null) {
                cachePath = BaseApplication.getContext().getExternalCacheDir().getPath();
            }
        } else {
            //  /data/data/应用包名/cache
            cachePath = BaseApplication.getContext().getCacheDir().getPath();
        }
        return cachePath;
    }

    public static String createFile(String path, String fileName) {

        if (!TextUtils.isEmpty(path)) {
            File file = new File(path, fileName);
            if (!file.exists()) {
                file.mkdir();
            }
            return file.getPath();
        }
        return null;
    }

    public static String createFile(String fileName) {

        if (TextUtils.isEmpty(getDiskCacheDir())) {
            return null;
        }
        File file = new File(getDiskCacheDir(), fileName);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getPath();
    }

}
