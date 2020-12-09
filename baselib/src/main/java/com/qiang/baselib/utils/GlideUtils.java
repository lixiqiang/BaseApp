package com.qiang.baselib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author lixiqiang
 * @date： 2019/9/10 0010
 */
public class GlideUtils {


    public static void load(Context context, String url, int error, ImageView imageView) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(error)
                .error(error)
                .centerCrop();

        Glide.with(context).load(url)
                .apply(requestOptions)
                .into(imageView);

    }

    public static void load(Context context, String url, int error, int round, ImageView imageView) {
        RoundedCorners roundedCorners = new RoundedCorners(round);
        RequestOptions requestOptions = new RequestOptions()
                .transform(roundedCorners)
                .placeholder(error)
                .error(error)
                .centerCrop();

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }
}
