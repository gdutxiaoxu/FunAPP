package com.xujun.funapp.model;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.network.TnGouNet;
import com.xujun.funapp.network.RequestListener;
import com.xujun.funapp.network.TnGouAPi;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/10/19 23:39
 * @ email：gdutxiaoxu@163.com
 */
public class PictureDetailModel {

    public  void  getPictureList(int id, final RequestListener listener){

        TnGouAPi tnGouAPi = TnGouNet.getInstance().getTnGouAPi();
        Observable<PictureDetailBean> observable = tnGouAPi.getPictureDetail(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PictureDetailBean>() {
                    @Override
                    public void call(PictureDetailBean pictureDetailBean) {
                             if(listener!=null){
                                 listener.onSuccess(pictureDetailBean);
                             }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.i(throwable.getMessage());
                        if(listener!=null){
                            listener.onError(throwable);
                        }

                    }
                });

    }
}
