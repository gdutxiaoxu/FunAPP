package com.xujun.funapp.model;

import com.xujun.funapp.beans.News;
import com.xujun.funapp.network.BaiDuApi;
import com.xujun.funapp.network.BaiDuNet;
import com.xujun.funapp.network.RequestListener;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class NewsModel {

    public  void getNews(String type, int page, int num, final RequestListener<News>
            requestListener) {
        BaiDuApi api = BaiDuNet.getInstance().getApi();
        Observable<News> observable = api.getNews(type, num, page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        if (requestListener != null) {
                            requestListener.onSuccess(news);
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (requestListener != null) {
                            requestListener.onError(throwable);
                        }
                    }
                });

    }
}
