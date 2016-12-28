package com.xujun.funapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.xujun.funapp.common.Constants;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class BaseFragment extends Fragment {

    public void readyGo(Class<?> clazz) {
        this.readyGo(clazz, null, null);
    }

    public void readyGo(Class<?> clazz, Parcelable parcelable) {
        this.readyGo(clazz, Constants.IntentConstants. DEFAULT_PARCEABLE_NAME, parcelable);
    }

    public void readyGo(Class<?> clazz, String name, Parcelable parcelable) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != parcelable) {
            intent = intent.putExtra(name, parcelable);
        }
        startActivity(intent);
    }

    protected <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }
}
