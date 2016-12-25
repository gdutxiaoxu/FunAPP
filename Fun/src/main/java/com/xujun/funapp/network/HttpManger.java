package com.xujun.funapp.network;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author xujun  on 2016/12/24.
 * @email gdutxiaoxu@163.com
 */

public class HttpManger {

    private HttpManger() {

    }

    public static HttpManger getInstance() {
        return Holder.mInstance;
    }

    private static class Holder {
        private static final HttpManger mInstance = new HttpManger();
    }

    public <T> Subscription doHttpDeal(Observable<T> observable, Subscriber<T> subscriber) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io()).subscribe(subscriber);
    }

    public static class LiftAllTransformer<T,R> implements Observable.Transformer<T, R> {


        @Override
        public Observable<R> call(Observable<T> observable) {
            return (Observable<R>)observable.subscribeOn(Schedulers.io()).// 指定在IO线程执行耗时操作
                    unsubscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread());//指定Subscrible在主线程回调
        }
    }

    public <T,R> LiftAllTransformer<T,R> getTransformer(){
        return new LiftAllTransformer<T,R>();
    }
}
