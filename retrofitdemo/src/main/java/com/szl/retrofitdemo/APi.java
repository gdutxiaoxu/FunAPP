package com.szl.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @ explain:
 * @ author：xujun on 2016-8-25 15:06
 * @ email：gdutxiaoxu@163.com
 */
public interface APi {

    @Headers("apikey:81bf9da930c7f9825a3c3383f1d8d766")
    @GET
    Call<News> getNews(@Query("num") String num,@Query("page")String page);
}
