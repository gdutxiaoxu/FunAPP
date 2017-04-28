package com.xujun.funapp.common.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Window;

import com.xujun.funapp.R;
import com.xujun.funapp.common.ActivityCollector;
import com.xujun.funapp.common.ThemeManager;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.mylibrary.utils.LUtils;

import org.simple.eventbus.EventBus;

/**
 * @ explain:
 * @ author：xujun on 2016/5/26 12:13
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseMVPActivity<T extends ViewDataBinding, E extends BasePresenter> extends
        BaseActivity implements ThemeManager.OnThemeChangeListener {

    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    public static final String TAG = "tag";

    protected Context mContext;

    protected T mBind;
    protected E mPresenter;
    private Parcelable mParcelableExtra;

    // 默认是日间模式
    private int theme = R.style.DayAppTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WriteLogUtil.d(this.getClass().getSimpleName() + " onCreate");
        //        设置屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.add(this);

        initWindows();
       /* // 切换夜间模式的
        boolean isNightMode = SPUtils.getBoolean(Constants.SPConstants.isNightMode);
        if (false == isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }*/

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
    public void onThemeChanged() {
       initTheme();
    }

    public void initTheme() {

        // 设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(ThemeManager.getCurrentThemeRes
                    (this, R.color.colorPrimary)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.unregisterThemeChangeListener(this);
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


    /**
     * 获取Layout的id
     */
    protected abstract int getContentViewLayoutID();

    protected boolean hasEventBus() {
        return false;
    }

    protected void initIntent(Intent intent) {
    }


    protected abstract void initView(T bind);

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



}
