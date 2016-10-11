package com.xujun.funapp.common;

import android.support.v4.view.ViewPager;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureClassify;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.databinding.FragmentViewPagerBinding;
import com.xujun.funapp.view.main.fragment.picture.PicturePresenter;
import com.xujun.funapp.widget.TabPagerIndicator;

/**
 * @ explain:
 * @ author：xujun on 2016/10/7 22:51
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseViewPagerFragemnt extends BindingBaseFragment<FragmentViewPagerBinding,PicturePresenter>
        implements DefaultContract.View<PictureClassify>{

    private ViewPager mViewPager;
    private TabPagerIndicator mIndicator;
    private BaseFragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected void initView(FragmentViewPagerBinding binding) {
        mViewPager = binding.viewPager;
        mIndicator = binding.indicator;
        mFragmentAdapter = getViewPagerAdapter();
        mViewPager.setAdapter(mFragmentAdapter);
        mIndicator.setViewPager(mViewPager);

    }

    protected abstract BaseFragmentAdapter getViewPagerAdapter() ;


    @Override
    protected PicturePresenter setPresenter() {
        return new PicturePresenter(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(PictureClassify pictureClassify) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onLocal(PictureClassify pictureClassify) {

    }
}
