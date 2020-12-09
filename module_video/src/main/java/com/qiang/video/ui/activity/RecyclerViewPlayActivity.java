package com.qiang.video.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qiang.baselib.Constant;
import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.video.R;
import com.qiang.video.ui.TxVideoView;
import com.qiang.video.ui.adapter.VideoPlayAdapter;
import com.qiang.video.ui.module.VideoBean;
import com.qiang.video.ui.viewpager.OnViewPagerListener;
import com.qiang.video.ui.viewpager.ViewPagerLayoutManager;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;

import java.util.ArrayList;

import me.jessyan.autosize.utils.LogUtils;


/**
 * @author lixiqiang
 * @date：2019/9/16 0016
 */
public class RecyclerViewPlayActivity extends BaseActivity {

    private static final String TAG = "RecyclerViewPlayActivity";

    RecyclerView recyclerView;

    SwipeRefreshLayout refreshLayout;

    VideoPlayAdapter adapter;

    ArrayList<VideoBean> videoList = new ArrayList<>();

    ViewPagerLayoutManager layoutManager;

    int curPlayPos = -1;


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.refresh_layout);
        for (int i = 0; i < 10; i++) {
            VideoBean bean = new VideoBean();
            bean.setId(i);
            bean.setAvatar(Constant.IMAGE_4);
            if (i % 2 == 0) {
                bean.setUrl(Constant.VIDEO_1);
            } else {
                bean.setUrl(Constant.VIDEO_2);
            }
            videoList.add(bean);
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                videoList.clear();
                for (int i = 0; i < 10; i++) {
                    VideoBean bean = new VideoBean();
                    bean.setId(i);
                    bean.setAvatar(Constant.IMAGE_4);
                    if (i % 2 == 0) {
                        bean.setUrl(Constant.VIDEO_1);
                    } else {
                        bean.setUrl(Constant.VIDEO_2);
                    }
                    videoList.add(bean);
                }

                curPlayPos = -1;
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });


        layoutManager = new ViewPagerLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VideoPlayAdapter(this, videoList);
        recyclerView.setAdapter(adapter);

        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

                LogUtils.e("onInitComplete");

                playVideo(0);

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {

                releaseVideo(position);
                LogUtils.e("onPageRelease position = " + position);

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {

                playVideo(position);
                LogUtils.e("loadVideo position = " + position + " size = " + videoList.size());
//                if (position == videoList.size() - 1) {
//                    loadVideo();
//                }
                LogUtils.e("onPageSelected position = " + position);
            }
        });
    }


    private void playVideo(int position) {

        if (curPlayPos == position) {
            return;
        }
        View itemView = layoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }
        TxVideoView txVideoView = itemView.findViewById(R.id.video_view);
        final ImageView coverImage = itemView.findViewById(R.id.iv_cover);
        txVideoView.setUrl(videoList.get(position).getUrl());
        txVideoView.startPlay();
        curPlayPos = position;
        txVideoView.getTxVodPlayer().setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle bundle) {
                if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
                    coverImage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });
    }

    private void pauseVideo(int position) {
        View itemView = layoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }
        TxVideoView txVideoView = itemView.findViewById(R.id.video_view);
        txVideoView.pause();
    }

    private void resumeVideo(int position) {
        View itemView = layoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }
        TxVideoView txVideoView = itemView.findViewById(R.id.video_view);
        txVideoView.resume();
    }

    private void releaseVideo(int position) {

        View itemView = layoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }
        TxVideoView txVideoView = itemView.findViewById(R.id.video_view);
        ImageView coverImage = itemView.findViewById(R.id.iv_cover);
        txVideoView.release();
        coverImage.setVisibility(View.VISIBLE);
    }


    private void loadVideo() {

        for (int i = 0; i < 10; i++) {
            VideoBean bean = new VideoBean();
            bean.setId(i);
            bean.setAvatar(Constant.IMAGE_4);
            if (i % 2 == 0) {
                bean.setUrl(Constant.VIDEO_1);
            } else {
                bean.setUrl(Constant.VIDEO_2);
            }
            videoList.add(bean);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onPause() {
        super.onPause();
        pauseVideo(curPlayPos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeVideo(curPlayPos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseVideo(curPlayPos);
    }
}
