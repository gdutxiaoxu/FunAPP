package com.xujun.funapp.view.main.fragment.news;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xujun.funapp.beans.NewsClassify;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.BaseViewPagerFragemnt;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.network.BaiDuNewsConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/16 23:47
 * @ email：gdutxiaoxu@163.com
 */
public class NewsFragment extends BaseViewPagerFragemnt<NewsPresenter> implements DefaultContract
        .View {

    private ArrayList<Fragment> mFragments;
    private static final String[] mTitles= BaiDuNewsConfig.mTitles;
    private BaseFragmentAdapter mBaseFragmentAdapter;
    private List<NewsClassify> mClassifyList;

    @Override
    protected BaseFragmentAdapter getViewPagerAdapter() {
        mFragments = new ArrayList<>();
        mClassifyList = BaiDuNewsConfig.getInstance().getList();
        for(int i=0;i<mTitles.length;i++){
            NewsClassify newsClassify = mClassifyList.get(i);
            String type = newsClassify.type;
            NewsListFragment newsListFragment = NewsListFragment.newInstance(type);
            mFragments.add(newsListFragment);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(int  i=0;i<mFragments.size();i++){
            Fragment fragment = mFragments.get(i);
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
