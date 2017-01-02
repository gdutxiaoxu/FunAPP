package com.xujun.myrxretrofitlibrary;

import com.google.gson.Gson;

/**
 * @author xujun  on 2016/12/25.
 * @email gdutxiaoxu@163.com
 */

public class GsonManger {

    private final Gson mGson;

    private GsonManger(){
        mGson = new Gson();
    }

    public static GsonManger getInstance(){
        return Holder.mInstance;
    }

    private static class Holder{
        private static final GsonManger mInstance=new GsonManger();
    }

    public String toJson(Object src){
        return mGson.toJson(src);
    }


    public  <T>  T  fromJson(String json,Class<T> clz){
        return mGson.fromJson(json,clz);
    }
}
