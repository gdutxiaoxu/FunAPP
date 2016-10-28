package com.xujun.funapp.common;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.FragmentViewPagerBinding;

/**
 * @ explain:
 * @ author：xujun on 2016/10/7 22:51
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseViewPagerFragemnt<P extends BasePresenter > extends
        BindingBaseFragment<FragmentViewPagerBinding,P>{

    private ViewPager mViewPager;

    private BaseFragmentAdapter mFragmentAdapter;
    private TabLayout mTabLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected void initView(FragmentViewPagerBinding binding) {
        mViewPager = binding.viewPager;
        mTabLayout = binding.tabLayout;
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mFragmentAdapter = getViewPagerAdapter();
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * 这个方式是用来初始化ViewPager的adapter的
     * @return
     */
    protected abstract BaseFragmentAdapter getViewPagerAdapter() ;





}
