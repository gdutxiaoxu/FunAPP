package com.xujun.funapp.model;

import com.xujun.funapp.common.network.juhe.JuHeApiManger;

import java.util.Map;

import rx.Subscriber;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class PreviousLifeDetailModel {

    public void getData(String url, Map<String, Object> paramsMap, Subscriber<String> subscriber) {
        JuHeApiManger.getInstance().excutePushString(url,paramsMap,subscriber);
    }
}
