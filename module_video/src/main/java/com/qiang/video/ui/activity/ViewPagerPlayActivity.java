package com.qiang.video.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.qiang.baselib.Constant;
import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BaseFragmentStateAdapter;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.video.R;
import com.qiang.video.ui.fragment.VideoPlayFragment;
import com.qiang.video.ui.module.VideoBean;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import me.jessyan.autosize.utils.LogUtils;

/**
 * @author lixiqiang
 * @date：2019/11/18 0018
 */
public class ViewPagerPlayActivity extends BaseActivity {

    VerticalViewPager viewPager;

    BaseFragmentStateAdapter adapter;

    ArrayList<VideoBean> videoBeans = new ArrayList<>();

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager_play;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.vertical_view_pager);
        for (int i = 0; i < 10; i++) {
            VideoBean bean = new VideoBean();
            bean.setId(i);
            bean.setAvatar(Constant.IMAGE_4);
            if (i % 2 == 0) {
                bean.setUrl(Constant.VIDEO_1);
            } else {
                bean.setUrl(Constant.VIDEO_2);
            }
            videoBeans.add(bean);
            fragments.add(VideoPlayFragment.newInstance(bean));
        }
        adapter = new BaseFragmentStateAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == videoBeans.size() - 2) {
                    LogUtils.e("load");
                    loadVideos();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadVideos() {
        for (int i = 0; i < 10; i++) {
            VideoBean bean = new VideoBean();
            bean.setId(i);
            bean.setAvatar(Constant.IMAGE_4);
            if (i % 2 == 0) {
                bean.setUrl(Constant.VIDEO_1);
            } else {
                bean.setUrl(Constant.VIDEO_2);
            }
            videoBeans.add(bean);
            fragments.add(VideoPlayFragment.newInstance(bean));
        }
        adapter.notifyDataSetChanged();
    }

}

