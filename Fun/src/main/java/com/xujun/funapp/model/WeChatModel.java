package com.xujun.funapp.model;

import com.xujun.myrxretrofitlibrary.yiyuan.YYHttpManger;

import java.util.Map;

import rx.Subscriber;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WeChatModel {

    public void getData(String url, Map<String, Object> paramsMap, Subscriber<String> subscriber) {
        YYHttpManger.getInstance().push(url,paramsMap,subscriber);

    }
}
