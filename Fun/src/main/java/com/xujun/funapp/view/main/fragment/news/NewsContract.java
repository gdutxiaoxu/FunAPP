package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.mvp.BaseView;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 22:31
 * @ email：gdutxiaoxu@163.com
 */
public class NewsContract {

    public interface View<T>  extends BaseView<T>{

    }

    public interface Presenter<V extends BaseView> extends BasePresenter<V>{

    }
}
