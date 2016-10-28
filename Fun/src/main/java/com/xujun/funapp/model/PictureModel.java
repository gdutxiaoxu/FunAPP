package com.xujun.funapp.model;

import com.xujun.funapp.beans.PictureClassify;
import com.xujun.funapp.network.TnGouNet;
import com.xujun.funapp.network.TnGouAPi;

import org.simple.eventbus.EventBus;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class PictureModel {

    public static void getPictureClassify() {
        TnGouAPi tnGouAPi = TnGouNet.getInstance().getTnGouAPi();
        Observable<PictureClassify> observable = tnGouAPi.pictureClassify();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PictureClassify>() {
                    @Override
                    public void call(PictureClassify pictureClassify) {
                        List<PictureClassify.TngouBean> tngou = pictureClassify.tngou;
                        EventBus.getDefault().post(pictureClassify);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        EventBus.getDefault().post(throwable);
                    }
                });

    }
}
