package com.xujun.funapp.common;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import com.xujun.funapp.R;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.network.NetworkChangeListener;
import com.xujun.funapp.network.NetworkConnectChangedReceiver;

/**
 * Created by xujun、on 2016/4/20.
 */
public class APP extends Application {

    private static APP app;
    String Tag = "xujun";

    //网路连接
    private boolean isWifi;//wifi是否连接
    private boolean isMobile;//手机是否连接
    private boolean isNetworkConn;//是否有网
    private NetworkChangeListener mNetworkChangeListener;
    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;

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
    public void onCreate() {
        super.onCreate();
        init();
        initReceiver();
    }

    private void initReceiver() {
        if(mNetworkChangeListener==null){
            mNetworkChangeListener = new NetworkChangeListener();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetworkChangeListener,filter);

        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();

        registerReceiver(mNetworkConnectChangedReceiver,filter);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void init() {

        app = this;
        WriteLogUtil.init(app);


        // Thread.setDefaultUncaughtExceptionHandler(UnCatchExceptionHandler.getInstance());

    }

    public static APP getInstance() {
        return app;
    }

    /**
     * 当判断当前手机没有网络时选择是否打开网络设置
     * @param context
     */
    public static void showNoNetWorkDlg( final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_launcher)         //
                .setTitle(R.string.app_name)            //
                .setMessage( "当前无网络" ).setPositiveButton( "设置" , new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到系统的网络设置界面
                Intent intent = null ;
                // 先判断当前系统版本
                if (android.os.Build.VERSION.SDK_INT > 10 ){  // 3.0以上
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    intent.setClassName( "com.android.settings" ,
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS );
                }
                context.startActivity(intent);

            }
        }).setNegativeButton( "知道了" , null ).show();
    }

}
