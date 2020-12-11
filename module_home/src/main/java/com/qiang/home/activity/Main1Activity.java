package com.qiang.home.activity;


import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.home.R;
import com.qiang.home.fragment.DiscoverFragment;
import com.qiang.home.fragment.HomeFragment;
import com.qiang.home.fragment.VideoFragment;
import com.qiang.home.model.TabEntity;

import java.util.ArrayList;

/**
 * @author lixiqiang
 * @date： 2020/12/11 0011
 */
public class Main1Activity extends BaseActivity {

    FrameLayout frameLayout;
    CommonTabLayout tabLayout;

    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        frameLayout = findViewById(R.id.frame_layout);
        tabLayout = findViewById(R.id.tab_layout);
        String[] titles = getResources().getStringArray(R.array.main_tab_title);
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            TabEntity tabEntity = new TabEntity(titles[i], R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            tabEntities.add(tabEntity);
        }
        tabLayout.setTabData(tabEntities);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (savedInstanceState == null) {
            HomeFragment fragment = HomeFragment.newInstance(1);
            fragments.add(fragment);
            transaction.add(R.id.frame_layout, fragment, titles[0]);
            DiscoverFragment discoverFragment = DiscoverFragment.newInstance();
            fragments.add(discoverFragment);
            transaction.add(R.id.frame_layout, discoverFragment, titles[2]);
            VideoFragment fragment2 = VideoFragment.newInstance();
            fragments.add(fragment2);
            transaction.add(R.id.frame_layout, fragment2, titles[2]);
            HomeFragment fragment3 = HomeFragment.newInstance(3);
            fragments.add(fragment3);
            transaction.add(R.id.frame_layout, fragment3, titles[3]);
        } else {
            for (int i = 0; i < titles.length; i++) {
                fragments.add(manager.findFragmentByTag(titles[i]));
            }
        }
        transaction.commitAllowingStateLoss();
        showFragment(0);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showFragment(position);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    public void showFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (position == i) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

}
