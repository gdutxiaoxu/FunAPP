package com.xujun.funapp.presenter;

import com.xujun.funapp.common.mvp.DefaultContract;

/**
 * @ explain:图片详情的Presenter
 * @ author：xujun on 2016/10/19 23:12
 * @ email：gdutxiaoxu@163.com
 */
public class PictureDetailPresenter extends DefaultContract.DefaultPresenter<PictureDetailContract.View>
        implements PictureDetailContract.Presenter {

    public PictureDetailPresenter(PictureDetailContract.View view) {
        super(view);
    }

    @Override
    public void getPictureList(int id) {

    }

    @Override
    public void getComments(int id) {

    }
}
