package com.xujun.funapp.network;

import com.xujun.funapp.beans.News;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @ explain:
 * @ author：xujun on 2016/10/28 15:19
 * @ email：gdutxiaoxu@163.com
 */
public interface BaiDuApi {

   /* http://apis.baidu.com/txapi/social/social*/


    @GET("txapi/{type}/{type}")
    Observable<News> getNews(@Path("type") String type, @Query("num") int num,
                             @Query("page") int page);



    @GET("txapi/tiYu/tiYu")
    Observable<News> tiYu( @Query("num") int num, @Query("page") int page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("txapi/world/world")
    Observable<News> world( @Query("num") int num, @Query("page") int page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("txapi/keji/keji")
    Observable<News> keji( @Query("num") int num, @Query("page") int page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("txapi/social/social")
    Observable<News> social( @Query("num") int num, @Query("page") int page);


}
