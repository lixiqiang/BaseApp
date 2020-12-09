package com.qiang.video.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qiang.baselib.base.BaseFragment;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.baselib.utils.GlideUtils;
import com.qiang.video.R;
import com.qiang.video.ui.module.VideoBean;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import me.jessyan.autosize.utils.LogUtils;


/**
 * @author lixiqiang
 * @date：2019/11/19 0019
 */
public class VideoPlayFragment extends BaseFragment {

    TXCloudVideoView videoView;
    ImageView ivCover;
    VideoBean videoBean;

    TXVodPlayer vodPlayer;

    boolean isInit = false;
    boolean isPlay = false;
    boolean isVisible = false;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_play;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {

        if (getArguments() != null) {
            videoBean = getArguments().getParcelable("video");
        }
        if (videoBean == null) {
            return;
        }
        videoView = rootView.findViewById(R.id.video_view);
        ivCover = rootView.findViewById(R.id.iv_cover);

        isInit = true;
        GlideUtils.load(mContext, videoBean.getAvatar(), R.mipmap.ic_launcher, ivCover);
        vodPlayer = new TXVodPlayer(mContext);
        vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        vodPlayer.setPlayerView(videoView);

        vodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle bundle) {
                if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
                    ivCover.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });

        loadData();
    }

    @Override
    protected void initData() {
    }


    public void loadData() {

        if (!isInit) {
            return;
        }

        if (isVisible) {
            playVideo();
            isPlay = true;
        } else if (isPlay) {
            stopLoad();
        }
    }

    public void playVideo() {
        LogUtils.e("VideoPlay   play");
        vodPlayer.startPlay(videoBean.getUrl());
    }

    private void stopLoad() {
        vodPlayer.stopPlay(true);
        ivCover.setVisibility(View.VISIBLE);
        LogUtils.e("VideoPlay   end");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;

        loadData();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (vodPlayer != null) {
            vodPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (vodPlayer != null) {
            vodPlayer.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isInit = false;
        isPlay = false;
        if (vodPlayer != null) {
            vodPlayer.stopPlay(true);
        }
        if (videoView != null) {
            videoView.onDestroy();
        }
    }

    public static VideoPlayFragment newInstance(VideoBean bean) {
        Bundle args = new Bundle();
        args.putParcelable("video", bean);
        VideoPlayFragment fragment = new VideoPlayFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
