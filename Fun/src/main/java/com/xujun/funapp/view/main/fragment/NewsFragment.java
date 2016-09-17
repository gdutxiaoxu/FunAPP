package com.xujun.funapp.view.main.fragment;

import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.FragemntNewsBinding;

/**
 * @ explain:
 * @ author：xujun on 2016/9/16 23:47
 * @ email：gdutxiaoxu@163.com
 */
public class NewsFragment extends BindingBaseFragment<FragemntNewsBinding, BasePresenter> {
    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void initView(FragemntNewsBinding binding) {

    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
