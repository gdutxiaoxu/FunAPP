package com.xujun.funapp.common;

/**
 * @ explain:
 * @ author：xujun on 2016/6/12 09:13
 * @ email：gdutxiaoxu@163.com
 */
public class DownloadConstants {

    //定义下载状态常量
    public static final int NONE = 0;         //无状态  --> 等待
    public static final int WAITING = 1;      //等待    --> 下载，暂停
    public static final int DOWNLOADING = 2;  //下载中  --> 暂停，完成，错误
    public static final int PAUSE = 3;        //暂停    --> 等待，下载
    public static final int FINISH = 4;       //完成    --> 重新下载
    public static final int ERROR = 5;        //错误    --> 等待
}
