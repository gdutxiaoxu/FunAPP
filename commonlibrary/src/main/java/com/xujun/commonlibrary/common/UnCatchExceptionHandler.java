package com.xujun.commonlibrary.common;

import com.xujun.commonlibrary.common.util.LUtils;

/**
 * @ explain:
 * @ author：xujun on 2016-8-18 15:01
 * @ email：gdutxiaoxu@163.com
 */
public class UnCatchExceptionHandler implements Thread.UncaughtExceptionHandler{

    private static UnCatchExceptionHandler mInstance;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(ex!=null){
            LUtils.e(ex.toString());
        }

    }

    public static UnCatchExceptionHandler getInstance(){
        if(mInstance==null){
            mInstance=new UnCatchExceptionHandler();
        }
        return mInstance;

    }
}