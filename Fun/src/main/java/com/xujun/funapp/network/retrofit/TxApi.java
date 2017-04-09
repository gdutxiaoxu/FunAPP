package com.xujun.funapp.network.retrofit;

import com.xujun.funapp.beans.TxNews;

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
public interface TxApi {

   /* http://apis.baidu.com/txapi/social/social*/


    @GET("txapi/{type_default}/{type_default}")
    Observable<TxNews> getNews(@Path("type_default") String type, @Query("num") int num,
                               @Query("page") int page);



    @GET("txapi/tiYu/tiYu")
    Observable<TxNews> tiYu(@Query("num") int num, @Query("page") int page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("txapi/world/world")
    Observable<TxNews> world(@Query("num") int num, @Query("page") int page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("txapi/keji/keji")
    Observable<TxNews> keji(@Query("num") int num, @Query("page") int page);

    @Headers({"apikey:81bf9da930c7f9825a3c3383f1d8d766" ,"Content-Type:application/json"})
    @GET("txapi/social/social")
    Observable<TxNews> social(@Query("num") int num, @Query("page") int page);


}
