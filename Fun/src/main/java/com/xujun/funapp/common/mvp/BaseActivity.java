package com.xujun.funapp.common.mvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xujun.funapp.common.ActivityCollector;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.PermissonListener;

import java.util.ArrayList;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class BaseActivity extends AppCompatActivity {

    protected static final String DEFAULT_NAME = "DEFAULT_NAME";
    protected static final String DEFAULT_PARCEABLE_NAME = "DEFAULT_PARCEABLE_NAME";
    protected static final String DEFAULT_PARCEABLE_LIST_NAME = "DEFAULT_PARCEABLE_LIST_NAME";
    public static final String TAG = "tag";

    protected ProgressDialog dialog;

    private static PermissonListener mPermissonListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void requestPermissions(String[] permissions, PermissonListener
            perissonListener) {
        //  这个  top Activity 是为了支持静态方法
        Activity top = ActivityCollector.getTop();
        if (top == null) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        mPermissonListener = perissonListener;
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(top, permission) != PackageManager
                    .PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(top, permissions, 1);
                list.add(permission);
            }

        }
        if (list.isEmpty()) {
            mPermissonListener.onGranted();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        list.add(permission);
                    }
                }
                if (list.isEmpty()) {
                    mPermissonListener.onGranted();
                } else {
                    mPermissonListener.onDenied(list);
                }
                break;
            default:
                break;
        }
    }

    protected void showFragment(int containerId, Fragment from, Fragment to, String tag) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(containerId, to, tag).commit();
            // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }

    protected void replaceFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment).commit();
    }

    /**
     * 启动Activity
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    /**
     * 启动Activity并传递数据
     */
    public void readyGo(Class<?> clazz, String extraString) {
        Intent intent = new Intent(this, clazz);
        if (null != extraString) {
            intent.putExtra(Constants.IntentConstants.DEFAULT_STRING_NAME,extraString);
        }
        startActivity(intent);
    }

    public void readyGo(Class<?> clazz, Parcelable parcelable) {
        this.readyGo(clazz, DEFAULT_PARCEABLE_NAME, parcelable);
    }

    public void readyGo(Class<?> clazz, String name, Parcelable parcelable) {
        Intent intent = new Intent(this, clazz);
        if (null != parcelable) {
            intent = intent.putExtra(name, parcelable);
        }
        startActivity(intent);
    }

    public void readyGo(Class<?> clazz, Parcelable parcelable, String action) {
        Intent intent = new Intent(this, clazz);
        intent.setAction(action);
        if (null != parcelable) {
            intent = intent.putExtra(DEFAULT_PARCEABLE_NAME, parcelable);
        }
        startActivity(intent);
    }

    public void readyGo(Class<? extends Activity> clazz, ArrayList<? extends Parcelable>
            parcelableList) {
        this.readyGo(clazz, DEFAULT_PARCEABLE_LIST_NAME, parcelableList);
    }

    public void readyGo(Class<? extends Activity> clazz, String name, ArrayList<? extends
            Parcelable> parcelableList) {
        Intent intent = new Intent(this, clazz);
        if (null != parcelableList) {
            intent = intent.putExtra(name, parcelableList);
        }
        startActivity(intent);
    }

    /**
     * 启动Activity并传递数据
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 启动Activity然后结束本Activity
     */
    public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * 启动Activity并传递数据,然后结束本Activity
     */
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 显示菊花
     */
    public void showProressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.show();
    }

    /**
     * 显示带信息的菊花
     */
    public void showProressDialog(String msg) {

        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setMessage(msg);
        dialog.show();
    }

    /**
     * 隐藏菊花
     */
    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    /**
     * 根据字符串弹出Toast
     */
    protected void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据资源id弹出Toast
     */
    protected void showToast(int msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
