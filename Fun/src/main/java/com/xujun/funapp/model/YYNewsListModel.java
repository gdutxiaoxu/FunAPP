package com.xujun.funapp.model;

import com.xujun.myrxretrofitlibrary.BaseFunc1;
import com.xujun.myrxretrofitlibrary.yiyuan.YYHttpManger;
import com.xujun.myrxretrofitlibrary.yiyuan.YiYuanApi;

import java.util.HashMap;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListModel {

    public void getNews(String channelId, String channelName, int page,
                        int maxResult, Subscriber<String> subscriber) {
        if(page<=1){


        }

        HashMap<java.lang.String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY,YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY,YiYuanApi.API_SIGN);
        map.put("channelId",channelId);
        map.put("channelName",channelName);
        map.put("page",page);
        map.put("maxResult",maxResult);
        String url= com.xujun.myrxretrofitlibrary.yiyuan.YiYuanApi.mBaseUrl+"/109-35/";
        YYHttpManger.getInstance().push(url,map,subscriber);

    }

    public void getNews(java.lang.String url, java.lang.String channelId, java.lang.String channelName, int page,
                        int maxResult, BaseFunc1 baseFunc1) {
        HashMap<java.lang.String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY,YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY,YiYuanApi.API_SIGN);
        map.put("channelId",channelId);
        map.put("channelName",channelName);
        map.put("page",page);
        map.put("maxResult",maxResult);
//        YYHttpManger.getRequest().testRequestBody(map,url,baseFunc1);
    }

    public void getNews(java.lang.String url, java.lang.String channelId, java.lang.String channelName, int page,
                        int maxResult, Subscriber<ResponseBody> subscriber) {
        HashMap<java.lang.String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY,YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY,YiYuanApi.API_SIGN);
        map.put("channelId",channelId);
        map.put("channelName",channelName);
        map.put("page",page);
        map.put("maxResult",maxResult);
        YYHttpManger.getInstance().excutePush(map,url,subscriber);
    }





}
