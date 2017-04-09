package com.xujun.funapp.common.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.xujun.funapp.common.ActivityCollector;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.mylibrary.utils.LUtils;

import org.simple.eventbus.EventBus;

/**
 * @ explain:
 * @ author：xujun on 2016/5/26 12:13
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseMVPActivity<T extends ViewDataBinding, E extends BasePresenter> extends
        BaseActivity {

    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    public static final String TAG = "tag";

    protected Context mContext;

    protected T mBind;
    protected E mPresenter;
    private Parcelable mParcelableExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WriteLogUtil.d(this.getClass().getSimpleName() + " onCreate");
        //        设置屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.add(this);

        initWindows();
        // 切换夜间模式的
        boolean isNightMode = SPUtils.getBoolean(Constants.SPConstants.isNightMode);
        if (false == isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        LUtils.d(this.getClass().getSimpleName() + ">>>>>>>>>>>onCreate()");
        // base setup
        Intent intent = getIntent();
        if (intent != null) {
            initIntent(intent);
        }

        mParcelableExtra = intent.getParcelableExtra(DEFAULT_PARCEABLE_NAME);
        if (mParcelableExtra != null) {
            getParcexbleExtras(mParcelableExtra);
        }

        mContext = this;
        dialog = new ProgressDialog(this);

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            mPresenter = setPresenter();
            if (mPresenter != null) {
                mPresenter.start();
            }
            mBind = DataBindingUtil.setContentView(this, getContentViewLayoutID());
            if (hasEventBus()) {
                EventBus.getDefault().register(this);
            }
            initView(mBind);
            initListener();
            initData();
        } else {
            Log.w("BaseActivity", "onCreate: Error contentView");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WriteLogUtil.d(this.getClass().getSimpleName() + " onDestroy");
        mBind.unbind();
        ActivityCollector.remove(this);
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
        }

        if (mPresenter != null) {
            mPresenter.stop();
        }
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        LUtils.d(this.getClass().getSimpleName() + ">>>>>>>>>>>onDestroy()");
    }

    protected boolean hasEventBus() {
        return false;
    }

    protected void initIntent(Intent intent) {
    }

    protected void initData() {

    }

    protected <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    protected void getParcexbleExtras(Parcelable parcelable) {

    }

    protected abstract E setPresenter();

    protected abstract void initView(T bind);

    protected void initListener() {
    }

    /**
     * 在setContentView前初始化Window设置
     */
    protected void initWindows() {
    }

    /**
     * 获取其他组件传来的数据
     */
    protected void getBundleExtras(Bundle extras) {
    }

    /**
     * 获取Layout的id
     */
    protected abstract int getContentViewLayoutID();


}
