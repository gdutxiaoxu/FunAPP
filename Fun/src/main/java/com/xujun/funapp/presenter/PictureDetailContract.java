package com.xujun.funapp.presenter;

import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.mvp.BaseView;

/**
 * @ explain:
 * @ author：xujun on 2016/10/19 22:58
 * @ email：gdutxiaoxu@163.com
 */
public class PictureDetailContract {



    public interface View extends BaseView{
        void onReceivePictureList(PictureDetailBean pictureDetailBean);

    }

    public interface Presenter extends BasePresenter{
        void  getPictureList(int id);
        void getComments(int id);
    }
}
