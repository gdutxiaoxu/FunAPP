package com.xujun.funapp.presenter;

import com.xujun.funapp.common.mvp.BaseView;
import com.xujun.funapp.common.mvp.DefaultContract;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public interface WeChatContract {

    public interface View extends BaseView{

    }


    public static class Presenter extends DefaultContract.Presenter<WeChatContract.View>{

        public Presenter(View view) {
            super(view);
        }
    }
}
