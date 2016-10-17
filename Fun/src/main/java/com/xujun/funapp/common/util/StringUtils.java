package com.xujun.funapp.common.util;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:54
 * @ email：gdutxiaoxu@163.com
 */
public class StringUtils {

    public static String getStr(String str,String defStr){
        if(str==null || str.length()==0){
            return defStr;
        }
        return str;
    }

    public static String getStr(String str){
       return getStr(str,"");
    }
}
