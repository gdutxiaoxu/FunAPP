package com.xujun.funapp.common;

import android.support.v7.app.AppCompatDelegate;

import com.xujun.funapp.common.util.WriteLogUtil;

/**
 * Created by xujun、on 2016/4/20.
 */
public class APP extends BaseApp {

    private static APP app;
    private static final String TAG = "xujun";



    @Override
    protected void initData() {
        super.initData();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        app = this;
        WriteLogUtil.init(app);
        // Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());



    }

    public static APP getInstance() {
        return app;
    }


}
