package com.qiang.video.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.bean.InfoCode;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.source.UrlSource;
import com.qiang.baselib.Constant;
import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.baselib.utils.FileUtils;
import com.qiang.video.R;

import me.jessyan.autosize.utils.LogUtils;

/**
 * @author lixiqiang
 * @date：2019/11/4 0004
 */
public class AliyunPlayActivity extends BaseActivity {


    SurfaceView surfaceView;

    AliPlayer aliPlayer;


    long time;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aliyun_play;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        surfaceView = findViewById(R.id.surface_view);

        aliPlayer = AliPlayerFactory.createAliPlayer(AliyunPlayActivity.this);


        String url = Constant.VIDEO_1;

        UrlSource urlSource = new UrlSource();
        urlSource.setUri(url);
        aliPlayer.setDataSource(urlSource);

        aliPlayer.setScaleMode(IPlayer.ScaleMode.SCALE_ASPECT_FIT);

        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.mEnable = true;
        cacheConfig.mMaxDurationS = 600;
        cacheConfig.mMaxSizeMB = 300;
        cacheConfig.mDir = FileUtils.createFile("aliyun_video_cache");
        aliPlayer.setCacheConfig(cacheConfig);

        aliPlayer.prepare();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliPlayer.setDisplay(holder);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                aliPlayer.redraw();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                aliPlayer.setDisplay(null);

            }
        });
        aliPlayer.start();


        time = System.currentTimeMillis();
        LogUtils.e("aliyun time =" + time);

        aliPlayer.setOnErrorListener(new IPlayer.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e("aliyun", errorInfo.getCode() + "," + errorInfo.getMsg());
            }
        });

        aliPlayer.setOnInfoListener(new IPlayer.OnInfoListener() {
            @Override
            public void onInfo(InfoBean infoBean) {
                if (infoBean.getCode() == InfoCode.CacheSuccess) {
                    Log.e("aliyun", "cache success");

                } else if (infoBean.getCode() == InfoCode.CacheError) {
                    Log.e("aliyun", "cache error ," + infoBean.getExtraMsg());
                }

            }
        });

        aliPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() {
            @Override
            public void onRenderingStart() {

                LogUtils.e("aliyun time =" + (System.currentTimeMillis() - time));

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aliPlayer != null) {
            aliPlayer.release();
        }
    }
}
