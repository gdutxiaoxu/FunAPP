package com.xujun.funapp.model;

import com.xujun.funapp.beans.TxNews;
import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.network.NetworkManager;
import com.xujun.funapp.network.RequestListener;
import com.xujun.funapp.network.retrofit.TxApi;
import com.xujun.funapp.network.retrofit.TxNet;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListModel {

    public void getNews(String type, int page, int num, final RequestListener<TxNews>
            requestListener) {
        TxApi api = TxNet.getInstance().getApi();
        Observable<TxNews> observable = api.getNews(type, num, page);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TxNews>() {
            @Override
            public void call(TxNews txNews) {
                if (requestListener != null) {
                    requestListener.onSuccess(txNews);
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

    public void getNewsClassify(String url,Map<String, String> paramsMap,
                                final RequestListener<YiYuanNewsClassify> requetListener) {
        NetworkManager.getInstance().excuteGet(url,paramsMap,requetListener);

    }
}
