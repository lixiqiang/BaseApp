package com.qiang.baselib.base;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.trello.rxlifecycle2.LifecycleTransformer;


/**
 * @author lixiqiang
 * @date： 2019/9/10 0010
 */
public abstract class BaseLazyFragment<P extends BasePresenter> extends BaseFragment implements BaseView {


    public P presenter;

    private boolean isLazyLoaded;

    private boolean isPrepared;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    @Override
    protected abstract P initPresenter();


    @UiThread
    protected abstract void onLazyLoad();

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }
}
