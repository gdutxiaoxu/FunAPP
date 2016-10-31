package com.xujun.funapp.common;

import com.xujun.funapp.common.util.WriteLogUtil;

/**
 * Created by xujun、on 2016/4/20.
 */
public class APP extends BaseApp {

    private static APP app;
    private static final String TAG = "xujun";

    //网路连接
    private boolean isWifi;//wifi是否连接
    private boolean isMobile;//手机是否连接
    private boolean isNetworkConn;//是否有网

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public boolean isNetworkConn() {
        return isNetworkConn;
    }

    public void setNetworkConn(boolean networkConn) {
        isNetworkConn = networkConn;
    }

    @Override
    protected void initData() {
        super.initData();
        app = this;
        WriteLogUtil.init(app);
        // Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());


    }

    public static APP getInstance() {
        return app;
    }


}
