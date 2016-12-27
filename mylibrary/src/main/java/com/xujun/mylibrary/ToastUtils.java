package com.xujun.mylibrary;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class ToastUtils {

    static Application mApplication;
    private static Toast mToast;

    public static void init(Application application) {
        mApplication = application;

    }

    public static void show(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mApplication, "", Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();

    }

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }

    public static void show(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();

    }

    public static void show(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(mApplication, "", Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(resId);
        mToast.show();

    }
}
