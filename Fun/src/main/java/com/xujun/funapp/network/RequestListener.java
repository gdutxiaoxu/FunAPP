package com.xujun.funapp.network;

/**
 * @ explain:
 * @ author：xujun on 2016/10/19 23:40
 * @ email：gdutxiaoxu@163.com
 */
public interface RequestListener<T> {

    public void onSuccess(T t);
    public void onError(Throwable throwable);
}
