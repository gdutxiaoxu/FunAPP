package com.xujun.funapp.view.main.fragment.picture;

import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.PictureListModel;
import com.xujun.funapp.network.RequestListener;

/**
 * @ explain:
 * @ author：xujun on 2016/9/18 21:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListPresenter extends DefaultContract.DefaultPresenter<PictureListContract.View>
        implements PictureListContract.Presenter {


    public static final String[] tags = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7"
    };
    private final PictureListModel mModel;


    public PictureListPresenter(PictureListContract.View view) {
        super(view);
        mModel = new PictureListModel();
    }

    @Override
    public void getPictureList(String page, String rows, int id) {
        mModel.getPictureList(page, rows, id, new RequestListener<PictureListBean>() {
            @Override
            public void onSuccess(PictureListBean pictureListBean) {
                PictureListContract.View view = getView();
                if (view != null) {
                    view.onSuccess(pictureListBean);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                PictureListContract.View view = getView();
                if (view != null) {
                    view.onError(throwable);

                }
            }
        });
    }
}
