package com.xujun.funapp.common;

import android.view.ViewGroup;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 20:23
 * @ email：gdutxiaoxu@163.com
 */
public interface OnRefreshListener {

    void onRefresh(ViewGroup viewGroup);
    void onLoadMore(ViewGroup viewGroup);
}
