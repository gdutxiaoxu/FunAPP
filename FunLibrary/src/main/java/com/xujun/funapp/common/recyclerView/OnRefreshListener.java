package com.xujun.funapp.common.recyclerView;

import android.view.ViewGroup;

/**
 * @ explain:
 * @ author：xujun on 2016-8-9 12:14
 * @ email：gdutxiaoxu@163.com
 */
public interface OnRefreshListener {

    public void onRefresh(ViewGroup viewGroup);

    public void onLoadMore(ViewGroup viewGroup);
}
