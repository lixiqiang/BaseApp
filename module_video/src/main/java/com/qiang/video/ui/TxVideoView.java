package com.qiang.video.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.qiang.baselib.utils.FileUtils;
import com.qiang.video.R;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * @author lixiqiang
 * @date：2020/6/12 0012
 */
public class TxVideoView extends RelativeLayout {


    private TXCloudVideoView txVideoView;

    private TXVodPlayer txVodPlayer;

    public String url;

    public TxVideoView(Context context) {
        super(context);
        initView(context);
    }


    public TxVideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        LayoutInflater.from(context).inflate(R.layout.tx_video_view, this);
        txVideoView = findViewById(R.id.tx_video_view);

        txVodPlayer = new TXVodPlayer(context);
        txVodPlayer.setPlayerView(txVideoView);
        txVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        // 播放速度
        txVodPlayer.setRate(1.0f);
        // 开启缓存
        TXVodPlayConfig config = new TXVodPlayConfig();
        config.setCacheFolderPath(FileUtils.createFile("video"));
        config.setMaxCacheItems(10);
        txVodPlayer.setConfig(config);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TXCloudVideoView getTxVideoView() {
        return txVideoView;
    }

    public TXVodPlayer getTxVodPlayer() {
        return txVodPlayer;
    }


    public void startPlay() {
        if (txVodPlayer != null && !TextUtils.isEmpty(url)) {
            txVodPlayer.startPlay(url);
        }
    }

    public void pause() {
        if (txVodPlayer != null) {
            txVodPlayer.pause();
        }
    }

    public void resume() {
        if (txVodPlayer != null) {
            txVodPlayer.resume();
        }
    }

    public void release() {
        if (txVodPlayer != null) {
            txVodPlayer.stopPlay(true);
            txVideoView.onDestroy();
        }
    }
}
