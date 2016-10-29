package com.xujun.funapp.common;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.common.util.WriteLogUtil;

/**
 * Created by xujun、on 2016/4/20.
 */
public class APP extends Application {

    private static APP app;
    String Tag = "xujun";

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //        MultiDex.install(this);
    }

    private void init() {

        app = this;
        Logger.init(Tag);
        WriteLogUtil.init(app);


        //        Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());

    }

    public static APP getApplication() {
        return app;
    }
}
