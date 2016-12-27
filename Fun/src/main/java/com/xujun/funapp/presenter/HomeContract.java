package com.xujun.funapp.presenter;

import com.xujun.funapp.common.mvp.BaseView;
import com.xujun.funapp.common.mvp.DefaultContract;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public interface HomeContract {

    public interface View extends BaseView{

    }


    public class Presenter extends DefaultContract.Presenter<HomeContract.View> {

        public Presenter(HomeContract.View view) {
            super(view);
        }
    }
}
