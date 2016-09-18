package com.xujun.funapp.view.main.fragment.picture;

import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.PictureListModel;

import org.simple.eventbus.Subscriber;

/**
 * @ explain:
 * @ author：xujun on 2016/9/18 21:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListPresenter extends DefaultContract.DefaultPresenter<PictureListContract.View>
        implements PictureListContract.Presenter {

    final String mTag;
    public static final String[] tags = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7"
    };

    @Subscriber
    public void onReceive(PictureListBean pictureListBean) {
        PictureListContract.View view = getView();
        if (view != null) {
            if (pictureListBean.tag.equals(mTag)) {
                view.onSuccess(pictureListBean);
            }

        }
    }

    @Subscriber
    public void onError(Throwable throwable){
        PictureListContract.View view = getView();
        if (view != null) {
           view.onError(throwable);

        }
    }

    public PictureListPresenter(PictureListContract.View view, String tag) {
        super(view);
        this.mTag = tag;
    }

    @Override
    public void getPictureList(String page, String rows, int id) {
        PictureListModel.getPictureList(page, rows, id, mTag);
    }
}
