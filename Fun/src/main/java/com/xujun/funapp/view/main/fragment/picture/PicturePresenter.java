package com.xujun.funapp.view.main.fragment.picture;

import com.xujun.funapp.beans.PictureClassify;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.PictureModel;

import de.greenrobot.event.Subscribe;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:32
 * @ email：gdutxiaoxu@163.com
 */
public class PicturePresenter extends DefaultContract.DefaultPresenter<DefaultContract.View> {

    public PicturePresenter(DefaultContract.View view) {
        super(view);
    }


    @Subscribe
    public void onReceive(PictureClassify  pictureClassify){
        DefaultContract.View view = getView();
        if(view!=null){
            view.onSuccess(pictureClassify);
        }
    }

    public static void getPictureClassify(){
        PictureModel.getPictureClassify();
    }
}
