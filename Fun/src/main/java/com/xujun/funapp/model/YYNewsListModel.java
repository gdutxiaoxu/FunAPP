package com.xujun.funapp.model;

import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.network.ApiManger;
import com.xujun.funapp.network.YiYuanApi;
import com.xujun.myrxretrofitlibrary.BaseFunc1;
import com.xujun.myrxretrofitlibrary.YYHttpManger;

import java.util.HashMap;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListModel {

    public void getNews(String channelId, String channelName, int page,
                        int maxResult, Subscriber<YYNews> subscriber) {
        YiYuanApi api = ApiManger.getInstance().getApi(YiYuanApi.class, YiYuanApi.mBaseUrl);
        HashMap<String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY,YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY,YiYuanApi.API_SIGN);
        map.put("channelId",channelId);
        map.put("channelName",channelName);
        map.put("page",page);
        map.put("maxResult",maxResult);
        Observable<YYNews> observable = api.getNews(map);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
//        observable.compose(new HttpManger.LiftAllTransformer<YYNews,YYNews>())
//                .subscribe(subscriber);

    }

    public void getNews(String url,String channelId, String channelName, int page,
                        int maxResult, BaseFunc1 baseFunc1) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY,YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY,YiYuanApi.API_SIGN);
        map.put("channelId",channelId);
        map.put("channelName",channelName);
        map.put("page",page);
        map.put("maxResult",maxResult);
//        YYHttpManger.getInstance().testRequestBody(map,url,baseFunc1);
    }

    public void getNews(String url,String channelId, String channelName, int page,
                        int maxResult, Subscriber<ResponseBody> subscriber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY,YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY,YiYuanApi.API_SIGN);
        map.put("channelId",channelId);
        map.put("channelName",channelName);
        map.put("page",page);
        map.put("maxResult",maxResult);
        YYHttpManger.getInstance().excutePush(map,url,subscriber);
    }





}
