package com.xujun.funapp.common;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class YiYuanConstants {
    public static final String BASE_URL = "http://route.showapi.com/";

    public static final String API_ID = "29571";
    public static final String API_ID_KEY = "showapi_appid";
    public static final String API_SECRET = "5bf00910e04a46998f6979f6da400f1e";
    public static final String API_SIGN_KEY = "showapi_sign";
    public static final String API_SIGN = API_SECRET;
    //    String API_SIGN = MD5.encode(API_SECRET);

    public static final String WECHAT_JING_XUAN = BASE_URL + "582-2/";
    public static final String NEWS_GET = BASE_URL + "109-35/";
    public static final String NEWS_CLASSIFY = BASE_URL + "109-34/";

    @NonNull
    public static HashMap<String, Object> getParamsMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(YiYuanConstants.API_ID_KEY, YiYuanConstants.API_ID);
        map.put(YiYuanConstants.API_SIGN_KEY, YiYuanConstants.API_SIGN);
        return map;
    }

}