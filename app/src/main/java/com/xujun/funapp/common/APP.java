package com.xujun.funapp.common;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.okhttputils.OkHttpUtils;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.szl.mobileoa.network.Network;

import org.litepal.LitePalApplication;

import java.io.IOException;

/**
 * Created by Domen、on 2016/4/20.
 */
public class APP extends android.support.multidex.MultiDexApplication {

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
        MultiDex.install(this);
    }

    private void init() {

        app = this;
        // 初始化Loggger的tag
        Settings settings = Logger.init(Tag);

        // 初始化
        OkHttpUtils.init(getApplication());
        LitePalApplication.initialize(this);
        try {
            Network.getInstance().setCertificates(getAssets().open("mobileoa.cer"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());

    }

    public static APP getApplication() {
        return app;
    }
}
