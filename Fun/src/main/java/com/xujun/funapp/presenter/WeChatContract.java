package com.xujun.funapp.presenter;

import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.mvp.BaseView;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public interface WeChatContract {

    public interface View extends BaseView {

        public void onReveive(String s);

        public void onError(Throwable throwable);

    }

    public interface Presenter extends BasePresenter {
        public void getData(String url, String typeId,String key,int page, int needContent);

    }
}
