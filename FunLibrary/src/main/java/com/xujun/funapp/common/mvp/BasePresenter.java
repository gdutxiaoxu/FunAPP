package com.xujun.funapp.common.mvp;

/**
 * Created by Domen、on 2016/4/22.
 */
public interface BasePresenter<T extends BaseView> {

    void start();

    void stop();
}
