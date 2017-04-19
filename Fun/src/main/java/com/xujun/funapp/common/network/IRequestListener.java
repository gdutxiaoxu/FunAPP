package com.xujun.funapp.common.network;

/**
 * @author meitu.xujun  on 2017/3/22 16:10
 * @version 0.1
 */

public interface IRequestListener {

    void onResponse(String response);
    void onFail(HttpException httpException);
}
