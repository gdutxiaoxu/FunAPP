package com.xujun.funapp.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.Toast;

import com.xujun.funapp.common.APP;

public class UIUtils {

    private UIUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Toast mToast;//Toast是单例的，注意其显示方式

    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    public static String getString(int id) {
        return getResources().getString(id);
    }

    public static Resources getResources() {
        return APP.getInstance().getResources();
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    public static void showShortText(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static void showShortText(String text) {

        if (mToast == null) {
            mToast = Toast.makeText(APP.getInstance(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static float getDimension(int id) {
        return getResources().getDimension(id);
    }

    public static void showShortText(final Activity mActivity, final String text) {
        if (isMainThread()) {
            if (mToast == null) {
                mToast = Toast.makeText(mActivity, "", Toast.LENGTH_SHORT);
            }
            mToast.setText(text);
            mToast.show();
        } else {
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(mActivity, "",
                                Toast.LENGTH_SHORT);
                    }
                    mToast.setText(text);
                    mToast.show();

                }
            });
        }

    }

    /**
     * 判断是否在主线程
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
