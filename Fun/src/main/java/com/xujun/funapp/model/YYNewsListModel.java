package com.xujun.funapp.model;

import com.xujun.funapp.beans.NewsContentlistEntity;
import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.common.ThreadManger;
import com.xujun.funapp.common.network.BaseFunc1;
import com.xujun.funapp.common.network.GsonManger;
import com.xujun.funapp.common.network.IRequestListener;
import com.xujun.funapp.common.network.NetUtils;
import com.xujun.funapp.common.network.yiyuan.YYHttpManger;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.xujun.funapp.common.network.yiyuan.YiYuanApi.API_ID;
import static com.xujun.funapp.common.network.yiyuan.YiYuanApi.API_ID_KEY;
import static com.xujun.funapp.common.network.yiyuan.YiYuanApi.API_SIGN;
import static com.xujun.funapp.common.network.yiyuan.YiYuanApi.API_SIGN_KEY;
import static com.xujun.funapp.common.network.yiyuan.YiYuanApi.mBaseUrl;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListModel {

    private static final String TAG = "YYNewsListModel";

    public  List<NewsContentlistEntity> getLoacalData(int page) {
        if(!NetUtils.isFirstPage(page)){
            return null;
        }
        List<NewsContentlistEntity> all = DataSupport.findAll(NewsContentlistEntity.class);
        return all;
    }

    public void getNews(String channelId, String channelName, int page,
                        int maxResult, IRequestListener iRequestListener) {
        HashMap<java.lang.String, Object> map = new HashMap<>();
        map.put(API_ID_KEY, API_ID);
        map.put(API_SIGN_KEY, API_SIGN);
        map.put("channelId", channelId);
        map.put("channelName", channelName);
        map.put("page", page);
        map.put("maxResult", maxResult);
        String url = mBaseUrl + "/109-35/";
        YYHttpManger.getInstance().push(url, map, iRequestListener);

    }

    public void saveData(int page, final String result) {
        if (!NetUtils.isFirstPage(page)) {
            return;
        }
        ThreadManger.getInstance().getShortPool().excute(new Runnable() {
            @Override
            public void run() {
                YYNews yyNews = GsonManger.getInstance().fromJson(result, YYNews.class);
                List<NewsContentlistEntity> contentlist = yyNews.pagebean
                        .contentlist;
                DataSupport.deleteAll(NewsContentlistEntity.class);
                DataSupport.saveAll(contentlist);
            }
        });

    }

    public void getNews(java.lang.String url, java.lang.String channelId, java.lang.String
            channelName, int page,
                        int maxResult, BaseFunc1 baseFunc1) {
        HashMap<java.lang.String, Object> map = new HashMap<>();
        map.put(API_ID_KEY, API_ID);
        map.put(API_SIGN_KEY, API_SIGN);
        map.put("channelId", channelId);
        map.put("channelName", channelName);
        map.put("page", page);
        map.put("maxResult", maxResult);
        //        YYHttpManger.getRequest().testRequestBody(map,url,baseFunc1);
    }

    public void getNews(java.lang.String url, java.lang.String channelId, java.lang.String
            channelName, int page,
                        int maxResult, Subscriber<ResponseBody> subscriber) {
        HashMap<java.lang.String, Object> map = new HashMap<>();
        map.put(API_ID_KEY, API_ID);
        map.put(API_SIGN_KEY, API_SIGN);
        map.put("channelId", channelId);
        map.put("channelName", channelName);
        map.put("page", page);
        map.put("maxResult", maxResult);
        YYHttpManger.getInstance().excutePush(map, url, subscriber);
    }


}
