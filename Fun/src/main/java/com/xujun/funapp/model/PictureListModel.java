package com.xujun.funapp.model;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.network.Network;
import com.xujun.funapp.network.TnGouAPi;

import org.simple.eventbus.EventBus;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListModel {

    public static String[] tags=new String[]{
           "0", "1","2","3","4","5","6","7"
    };

    public static void getPictureList(String page, String rows, int id, final String tag) {
        TnGouAPi tnGouAPi = Network.getInstance().getTnGouAPi();
        Observable<PictureListBean> observable = tnGouAPi.getPictureList(page,rows,id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Action1<PictureListBean>() {
                  @Override
                  public void call(PictureListBean pictureListBean) {
                      pictureListBean.tag=tag;
                      EventBus.getDefault().post(pictureListBean);
                  }
              }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                      Logger.i(throwable.getMessage());
                      EventBus.getDefault().post(throwable);
                  }
              });

    }
}
