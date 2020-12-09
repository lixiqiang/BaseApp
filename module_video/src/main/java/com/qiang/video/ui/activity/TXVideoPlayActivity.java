package com.qiang.video.ui.activity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qiang.baselib.Constant;
import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.baselib.utils.FileUtils;
import com.qiang.video.R;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import me.jessyan.autosize.utils.LogUtils;


/**
 * @author lixiqiang
 * @date：2019/9/10 0010
 */
public class TXVideoPlayActivity extends BaseActivity {

    private static final String TAG = "TXVideoPlayActivity";


    TXCloudVideoView txVideoView;
    TXVodPlayer txVodPlayer;
    SeekBar seekBar;
    TextView seekBarProgress;

    boolean isTrackingSeekBar;

    long time;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tx_video_play;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        txVideoView = findViewById(R.id.tx_video_play_view);
        seekBar = findViewById(R.id.seek_bar);
        seekBarProgress = findViewById(R.id.tv_seek_bar);

        initVideoView();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                LogUtils.e("seekBar   progress=" + progress + ",fromUser=" + fromUser);
                seekBarProgress.setText(String.format("%02d:%02d", progress / 1000 / 60, progress / 1000 % 60) + " / " +
                        String.format("%02d:%02d", seekBar.getMax() / 1000 / 60, seekBar.getMax() / 1000 % 60));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                isTrackingSeekBar = true;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                LogUtils.e("seekBar " + seekBar.getProgress());
                txVodPlayer.seek(seekBar.getProgress() / 1000.f);
                txVodPlayer.resume();
                isTrackingSeekBar = false;

            }
        });

    }

    private void initVideoView() {

//        TXLivePlayer livePlayer = new TXLivePlayer(this);
//
//        livePlayer.setPlayerView(txVideoView);
//        String url = "";
//        time = System.currentTimeMillis();
//        LogUtils.e("time=" + time);
//        livePlayer.startPlay(url, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
//        livePlayer.setPlayListener(new ITXLivePlayListener() {
//            @Override
//            public void onPlayEvent(int event, Bundle bundle) {
//                if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
//                    LogUtils.e("time=" + (System.currentTimeMillis() - time));
//                }
//
//            }
//
//            @Override
//            public void onNetStatus(Bundle bundle) {
//
//            }
//        });


        txVodPlayer = new TXVodPlayer(this);
        txVodPlayer.setPlayerView(txVideoView);

        /**
         * RENDER_MODE_FILL_SCREEN ：将图像等比例铺满整个屏幕，多余部分裁剪掉，
         *                           此模式下画面不会留黑边，但可能因为部分区域被裁剪而显示不全
         *
         * RENDER_MODE_ADJUST_RESOLUTION ：将图像等比例缩放，适配最长边，缩放后的宽和高都不会超
         *                                 过显示区域，居中显示，画面可能会留有黑边。
         */
//        txVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        txVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);

        // 播放速度
        txVodPlayer.setRate(1.0f);

        String url = Constant.VIDEO_1;

        txVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle bundle) {

                LogUtils.e("error = " + event);

                if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {

                    // 视频总长
                    int duration = bundle.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);
                    // 缓冲进度
                    int playable = bundle.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);
                    // 播放进度
                    int progress = bundle.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);

                    if (!isTrackingSeekBar) {
                        seekBar.setMax(duration);
                        seekBar.setProgress(progress);
                        seekBar.setSecondaryProgress(playable);
                    }
                    LogUtils.e("duration=" + duration + "progress=" + progress);

                    // 视频第一帧
                } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
                    LogUtils.e("time=" + (System.currentTimeMillis() - time));
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });

        // 开启缓存
        TXVodPlayConfig config = new TXVodPlayConfig();
        config.setCacheFolderPath(FileUtils.createFile("video"));
        config.setMaxCacheItems(10);
        txVodPlayer.setConfig(config);

        txVodPlayer.startPlay(url);

        time = System.currentTimeMillis();
        LogUtils.e("time=" + time);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (txVodPlayer != null) {
            txVodPlayer.pause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (txVodPlayer != null) {
            txVodPlayer.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (txVodPlayer != null) {
            txVodPlayer.stopPlay(true);
            txVideoView.onDestroy();
        }
    }
}
