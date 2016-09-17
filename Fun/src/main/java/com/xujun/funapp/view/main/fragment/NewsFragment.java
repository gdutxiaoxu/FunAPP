package com.xujun.funapp.view.main.fragment;

import com.xujun.funapp.R;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.FragmentNewsBinding;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @ explain:
 * @ author：xujun on 2016/9/16 23:47
 * @ email：gdutxiaoxu@163.com
 */
public class NewsFragment extends BindingBaseFragment<FragmentNewsBinding, BasePresenter> {

    private BGARefreshLayout mRefreshLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(FragmentNewsBinding binding) {
        mRefreshLayout = binding.refreshLayout;

    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
