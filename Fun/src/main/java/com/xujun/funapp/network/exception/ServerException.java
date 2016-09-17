package com.xujun.funapp.network.exception;

/**
 * @ explain:
 * @ author：xujun on 2016-7-19 14:54
 * @ email：gdutxiaoxu@163.com
 */
public class ServerException extends RuntimeException {
    public int code;
    public String message;
}