package com.qiang.video.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.baselib.listener.NoDoubleClickListener;
import com.qiang.video.R;

import me.jessyan.autosize.utils.LogUtils;

public class MainActivity extends BaseActivity {


    private TextView txVideoPlay;

    private TextView viewPagerPlay;

    private TextView receclerViewPlay;


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        txVideoPlay = findViewById(R.id.tv_tx_video_play);
        viewPagerPlay = findViewById(R.id.view_pager);
        receclerViewPlay = findViewById(R.id.recycler_view);
        TextView aliyunVideo = findViewById(R.id.tv_aliyun_video_play);
        txVideoPlay.setOnClickListener(listener);
        viewPagerPlay.setOnClickListener(listener);
        receclerViewPlay.setOnClickListener(listener);
        aliyunVideo.setOnClickListener(listener);

    }


    public NoDoubleClickListener listener = new NoDoubleClickListener() {
        @Override
        protected void onNoDoubleClick(View view) {

            if (view.getId() == R.id.tv_tx_video_play) {
                Intent intent = new Intent(MainActivity.this, TXVideoPlayActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.tv_aliyun_video_play) {
                Intent intent = new Intent(MainActivity.this, AliyunPlayActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.view_pager) {
                Intent intent = new Intent(MainActivity.this, ViewPagerPlayActivity.class);
                startActivity(intent);

            } else if (view.getId() == R.id.recycler_view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewPlayActivity.class);
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.e("onSaveInstanceState");
    }
}
