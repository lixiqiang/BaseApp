package com.qiang.baselib.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiqiang
 * @date： 2019/9/10 0010
 */
public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();


    public BaseFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        if (fragments != null) {
            return fragments.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        }
        return "";
    }
}
