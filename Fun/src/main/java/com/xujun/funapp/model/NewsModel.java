package com.xujun.funapp.model;

import com.xujun.funapp.beans.TxNews;
import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.beans.YiYuanResponse;
import com.xujun.funapp.network.ApiManger;
import com.xujun.funapp.network.HttpManger;
import com.xujun.funapp.network.YiYuanApi;
import com.xujun.funapp.network.retrofitclient.NetworkApi;
import com.xujun.funapp.network.RequestListener;
import com.xujun.funapp.network.retrofit.ApiNet;
import com.xujun.funapp.network.retrofit.TxApi;
import com.xujun.funapp.network.retrofit.TxNet;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class NewsModel {

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

    public void getNewsClassify(String url, Map<String, Object> paramsMap,
                                Subscriber<YiYuanNewsClassify> subscriber) {
        ApiManger instance = ApiManger.getInstance();
        YiYuanApi api = instance.getApi(YiYuanApi.class, YiYuanApi.mBaseUrl);
        Observable<YiYuanNewsClassify> observable = api.getNewsClassify(paramsMap);

        HttpManger.getInstance().doHttpDeal(observable,subscriber);


    }

    public void testUrl(String url, Map<String, String> paramsMap,
                        final RequestListener<YiYuanNewsClassify.ShowapiResBodyEntity> requetListener) {
        ApiNet baseApi = ApiNet.getInstance().createBaseApi();
        NetworkApi networkApi = baseApi.getNetworkApi();

        Observable<YiYuanResponse<YiYuanNewsClassify.ShowapiResBodyEntity>> observable = networkApi.testUrl
                (url, paramsMap);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<YiYuanResponse<YiYuanNewsClassify.ShowapiResBodyEntity>>() {
                    @Override
                    public void call(YiYuanResponse<YiYuanNewsClassify.ShowapiResBodyEntity>
                                             showapiResBodyEntityYiYuanResponse) {
                        YiYuanNewsClassify.ShowapiResBodyEntity showapi_res_body =
                                showapiResBodyEntityYiYuanResponse.showapi_res_body;
                        requetListener.onSuccess(showapi_res_body);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        requetListener.onError(throwable);
                    }
                });
    }

    public void testPath(String url, Map<String, String> paramsMap,
                    final RequestListener<YiYuanNewsClassify.ShowapiResBodyEntity> requetListener) {
        ApiNet baseApi = ApiNet.getInstance().createBaseApi();
        NetworkApi networkApi = baseApi.getNetworkApi();
        Observable<Object> observable = networkApi.testPath(url, paramsMap);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<Object>() {


            @Override
            public void call(Object o) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });


    }
}
