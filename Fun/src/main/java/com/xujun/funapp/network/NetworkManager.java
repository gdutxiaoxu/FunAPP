package com.xujun.funapp.network;

import com.xujun.funapp.network.retrofitclient.RetrofitClient;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public class NetworkManager {

    public static INetworkListener getInstance(){
        return RetrofitClient.getInstance();
    }
}
