package com.xujun.funapp.common;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

/**
 * Created by Domen、on 2016/4/20.
 */
public class APP extends Application{

    private static APP app;
    String Tag = "mobileOA";

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
        // 初始化Loggger的tag
        Settings settings = Logger.init(Tag);



//        Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());

    }

    public static APP getApplication() {
        return app;
    }
}
