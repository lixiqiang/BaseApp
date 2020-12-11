package com.qiang.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.qiang.baselib.Constant;
import com.qiang.baselib.base.BaseActivity;
import com.qiang.baselib.base.BasePresenter;
import com.qiang.baselib.utils.GlideUtils;
import com.qiang.home.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.LogUtils;


/**
 * @author lixiqiang
 * @date：2019/10/24 0024
 */
public class SplashActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

//        hideStatusBar(true);
        imageView = findViewById(R.id.iv_image);

        LogUtils.e("RxJava along =");

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(4)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.e("RxJava along =" + aLong);
                        if (aLong == 1) {
                            GlideUtils.load(SplashActivity.this, Constant.IMAGE_4, 0, imageView);
                        } else if (aLong >= 3) {
                            finish();
                            Intent intent = new Intent(SplashActivity.this, Main1Activity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(intent);


    }

}
