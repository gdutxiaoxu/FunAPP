package com.xujun.funapp.view.main.fragment.picture;

import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.mvp.BaseView;
import com.xujun.funapp.common.mvp.DefaultContract;

/**
 * @ explain:
 * @ author：xujun on 2016/9/18 21:18
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListContract {

    public interface View<V> extends DefaultContract.View<V> {

    }

    public interface Presenter<P extends BaseView> extends BasePresenter{

        void getPictureList(String page, String rows, int id);

    }
}
