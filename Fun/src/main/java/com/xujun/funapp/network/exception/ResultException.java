package com.xujun.funapp.network.exception;

/**
 * @ explain:
 * @ author：xujun on 2016-7-19 11:54
 * @ email：gdutxiaoxu@163.com
 */
public class ResultException extends RuntimeException {

    private int errCode = 0;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}