package com.xujun.funapp.network;

import java.util.Map;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public interface INetworkListener {

    public <T> void getData(String url, Map paramsMap ,final RequestListener<T> requestListener);
    public <T> void pushData(String url, Map paramsMap,final RequestListener<T> requestListener);
    public <T> void json(String url,String jsonStr,final RequestListener<T> requestListener);
}
