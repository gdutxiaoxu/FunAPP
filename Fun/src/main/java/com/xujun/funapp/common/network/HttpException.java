package com.xujun.funapp.common.network;

/**
 * @author meitu.xujun  on 2017/4/8 17:26
 * @version 0.1
 */

public class HttpException {

    public static final int CUSTOM_ERROR_CODE=100;

    public int code;
    public Throwable e;
    public String errMsg;

    public HttpException(int code, Throwable e, String errMsg) {
        this.code = code;
        this.e = e;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "HttpException{" +
                "code=" + code +
                ", e=" + e +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
