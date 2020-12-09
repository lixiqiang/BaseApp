package com.qiang.video.ui.adapter;


import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qiang.baselib.utils.GlideUtils;
import com.qiang.video.R;
import com.qiang.video.ui.module.VideoBean;

import java.util.List;

/**
 * @author lixiqiang
 * @date：2020/5/6 0006
 */
public class VideoPlayAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    private Context context;

    public VideoPlayAdapter(Context context, @Nullable List<VideoBean> data) {
        super(R.layout.item_video_play, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VideoBean item) {
        GlideUtils.load(context, item.getAvatar(), 0, (ImageView) helper.getView(R.id.iv_cover));
    }
}
