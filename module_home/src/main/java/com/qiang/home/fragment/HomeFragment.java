package com.qiang.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.qiang.baselib.base.BaseFragment;
import com.qiang.baselib.base.BaseFragmentStateAdapter;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.baselib.listener.NoDoubleClickListener;
import com.qiang.baselib.utils.ScreenUtils;
import com.qiang.home.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiqiang
 * @date：2019/10/15 0015
 */
public class HomeFragment extends BaseFragment {


    ImageView scanIcon;

    AppBarLayout appBarLayout;

    RelativeLayout searchLayout;

//    RecyclerView recyclerView;

    ViewPager viewPager;

    MagicIndicator indicator;

    ArrayList<String> titles = new ArrayList<>();

    List<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {

        scanIcon = rootView.findViewById(R.id.iv_scan);
//        recyclerView = rootView.findViewById(R.id.recycler_view);
        indicator = rootView.findViewById(R.id.magic_indicator);
        viewPager = rootView.findViewById(R.id.view_pager);
        appBarLayout = rootView.findViewById(R.id.appBarLayout);
        searchLayout = rootView.findViewById(R.id.searchLayout);
        int type = 0;
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }

        titles.add("全部");
        titles.add("推荐");
        titles.add("LoL");
        titles.add("颜值");
        titles.add("户外");
        for (int i = 0; i < titles.size(); i++) {
            fragments.add(ItemFragment.newInstance(i));
        }
        BaseFragmentStateAdapter adapter = new BaseFragmentStateAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        initIndicator();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int off) {
                // 0 appBar完全显示  topBarHeight 完全滑出
                int offSet = Math.abs(off);
                int topBarHeight = appBarLayout.getTotalScrollRange();
                float alpha = offSet / ((float) topBarHeight);
                searchLayout.setAlpha(1 - alpha);
                Log.e("appBarLayout", "offSet = " + offSet + ",topBarHeight = " + topBarHeight);

            }
        });


    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView titleView = new ColorTransitionPagerTitleView(context);
                titleView.setNormalColor(ContextCompat.getColor(context, R.color.color_black));
                titleView.setSelectedColor(ContextCompat.getColor(context, R.color.color_ff3d00));
                titleView.setText(titles.get(index));

                titleView.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(ScreenUtils.dip2px(context, 12));
                indicator.setLineHeight(ScreenUtils.dip2px(context, 4));
                indicator.setRoundRadius(ScreenUtils.dip2px(context, 2));
                indicator.setYOffset(ScreenUtils.dip2px(context, 5));
                indicator.setColors(ContextCompat.getColor(context, R.color.color_ff3d00));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    @Override
    protected void initData() {


    }


    public static HomeFragment newInstance(int type) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }
}
