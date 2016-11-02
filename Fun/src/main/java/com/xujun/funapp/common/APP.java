package com.xujun.funapp.common;

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
        app = this;
        WriteLogUtil.init(app);
        // Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());
        showWifiDlg(app);


    }

    public static APP getInstance() {
        return app;
    }


}
