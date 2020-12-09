package com.qiang.baselib.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * @author lixiqiang
 * @date： 2019/9/10 0010
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    public P presenter;
    private View rootView;

    public Context mContext;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        initView(savedInstanceState, rootView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    protected abstract int getLayoutId();

    protected abstract P initPresenter();

    protected abstract void initView(Bundle savedInstanceState, View rootView);

    protected abstract void initData();


    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }

        super.onDestroy();
    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
