package com.xujun.funapp.common;

import android.support.v4.view.ViewPager;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.FragmentViewPagerBinding;
import com.xujun.funapp.widget.TabPagerIndicator;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 20:58
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseViewFragment<P extends BasePresenter> extends
        BindingBaseFragment<FragmentViewPagerBinding,P>  {

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
        mFragmentAdapter=getViewPagerAdapter();
        if(mFragmentAdapter==null){
            throw new IllegalStateException("You must init ViewPager Adapter first");
        }
        mViewPager.setAdapter(mFragmentAdapter);
        mIndicator.setViewPager(mViewPager);
    }

    protected abstract BaseFragmentAdapter getViewPagerAdapter();
}
