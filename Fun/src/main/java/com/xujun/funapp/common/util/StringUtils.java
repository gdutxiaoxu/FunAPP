package com.xujun.funapp.common.util;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:54
 * @ email：gdutxiaoxu@163.com
 */
public class StringUtils {
    /**
     * 提取出城市或者县
     * @param city
     * @param district
     * @return
     */
    public static String extractLocation(final String city, final String district){
        return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
    }


    public static String getStr(String str,String defStr){
        if(str==null || str.length()==0){
            return defStr;
        }
        return str;
    }

    public static String getStr(String str){
       return getStr(str,"null");
    }
}
