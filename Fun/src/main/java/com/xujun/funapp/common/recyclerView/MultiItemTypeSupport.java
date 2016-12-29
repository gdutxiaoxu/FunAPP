package com.xujun.funapp.common.recyclerView;

/**
 * @author xujun  on 2016/12/29.
 * @email gdutxiaoxu@163.com
 */

public interface MultiItemTypeSupport<T> {

    public int getItemType(T t,int position);
    public int getLayoutId(int itemType);
}
