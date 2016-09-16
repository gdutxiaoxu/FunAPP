package com.xujun.funapp.common.util;

import org.litepal.crud.DataSupport;

/**
 * @ explain:
 * @ author：xujun on 2016/5/19 15:48
 * @ email：gdutxiaoxu@163.com
 */
public class DbUtills {


    public static void saveAll(String user,String readType,Class clz){
        DataSupport.deleteAll(clz,"user=?and readType=?",user,readType);
    }
}
