package com.xujun.funapp.network;

/**
 * @ explain:新增
 * @ author：xujun on 2016-7-19 17:54
 * @ email：gdutxiaoxu@163.com
 */
public interface INetwork {

    /**
     * 拿到BaseURl；
     * @return
     */

    String getBaseUrl();
    String getUploadUrl();
    String getDownloadUrl();
    String getHostName();

}
