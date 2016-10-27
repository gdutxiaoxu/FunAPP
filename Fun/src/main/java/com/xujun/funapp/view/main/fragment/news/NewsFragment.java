package com.xujun.funapp.view.main.fragment.news;

import android.support.v4.app.Fragment;

import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.BaseViewPagerFragemnt;
import com.xujun.funapp.common.mvp.DefaultContract;

import java.util.ArrayList;

/**
 * @ explain:
 * @ author：xujun on 2016/9/16 23:47
 * @ email：gdutxiaoxu@163.com
 */
public class NewsFragment extends BaseViewPagerFragemnt<NewsPresenter> implements DefaultContract
        .View {

    private ArrayList<Fragment> mFragments;
    private static final String[] mTitles=new String[]{
           "农业要闻","农业新闻","地方农业","农业新闻","地方新闻","政策热点","技术快讯"
    };
    private BaseFragmentAdapter mBaseFragmentAdapter;

    @Override
    protected BaseFragmentAdapter getViewPagerAdapter() {
        mFragments = new ArrayList<>();
        mBaseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments,
                mTitles);
        return mBaseFragmentAdapter;
    }

    @Override
    protected NewsPresenter setPresenter() {
        return new NewsPresenter(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onLocal(Object o) {

    }
}
