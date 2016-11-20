package com.xujun.funapp.view.main;

import android.support.v4.app.Fragment;

import com.xujun.funapp.view.main.fragment.news.NewsFragment;
import com.xujun.funapp.view.main.fragment.picture.PictureFragment;
import com.xujun.funapp.view.main.fragment.setting.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:02
 * @ email：gdutxiaoxu@163.com
 */
public class MainFragmentFactory {

    private List<Fragment> mFragmentList;
    private static MainFragmentFactory mInstance;

    public static MainFragmentFactory getInstance() {
        if (mInstance == null) {
            mInstance = new MainFragmentFactory();
        }
        return mInstance;
    }

    public Fragment get(int position) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
            mFragmentList.add(new NewsFragment());
            mFragmentList.add(new NewsFragment());
            mFragmentList.add(new PictureFragment());
            mFragmentList.add(new SettingFragment());
        }

        return mFragmentList.get(position);

    }

    public List<Fragment> getFragmentList() {
        return mFragmentList;
    }
}
