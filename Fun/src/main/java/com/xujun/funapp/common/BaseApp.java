package com.xujun.funapp.common;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import com.xujun.funapp.R;
import com.xujun.funapp.network.NetworkChangeListener;
import com.xujun.funapp.network.NetworkConnectChangedReceiver;

/**
 * @ explain:
 * @ author：xujun on 2016/10/31 22:44
 * @ email：gdutxiaoxu@163.com
 */
public class BaseApp extends Application {

    private NetworkChangeListener mNetworkChangeListener;
    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;
    //表示是否连接
    public boolean isConnected;
    //    表示是否是移动网络
    public boolean isMobile;
    //    表示是否是WiFi
    public boolean isWifi;
    //    表示WiFi开关是否打开
    public boolean isEnablaWifi;
    //    表示移动网络数据是否打开
    public boolean isEnableMobile;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    protected void initData() {
        initReceiver();
    }

    private void initReceiver() {
        if (mNetworkChangeListener == null) {
            mNetworkChangeListener = new NetworkChangeListener();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetworkChangeListener, filter);

        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();

        registerReceiver(mNetworkConnectChangedReceiver, filter);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 当判断当前手机没有网络时选择是否打开网络设置
     *
     * @param context
     */
    public static void showNoNetWorkDlg(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_launcher)         //
                .setTitle("网络设置")            //
                .setMessage("当前无网络").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到系统的网络设置界面
                Intent intent = null;
                // 先判断当前系统版本
                if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    intent.setClassName("com.android.settings",
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }
                context.startActivity(intent);

            }
        }).setNegativeButton("知道了", null).show();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public boolean isEnablaWifi() {
        return isEnablaWifi;
    }

    public void setEnablaWifi(boolean enablaWifi) {
        isEnablaWifi = enablaWifi;
    }

    public boolean isEnableMobile() {
        return isEnableMobile;
    }

    public void setEnableMobile(boolean enableMobile) {
        isEnableMobile = enableMobile;
    }
}
