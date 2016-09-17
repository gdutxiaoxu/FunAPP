package com.xujun.funapp.network;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Domen、on 2016/4/20.
 */
public interface Api {
    //检验MD5
    @GET("checkImg")
    Observable<ResponseBody> checkMD5(@Query("md5") String MD5);

    //将待遇文件标记为已阅
    @GET("markRead")
    Observable<ResponseBody> markRead(@Query("PASSREAD_ID") String PASSREAD_ID,
                                      @Query("auth_token") String auth_token);

    @GET("download")
    Observable<ResponseBody> download(@Query("document_id") String document_id, @Query
            ("biz_type") String biz_type,
                                      @Query("file_type") String file_type, @Query("isDocPdf")
                                      String isDocPdf,
                                      @Query("is_doc") String is_doc, @Query("file_code") String
                                              file_code,
                                      @Query("auth_token") String auth_token);

    @FormUrlEncoded
    @POST("updateGestureCode")
    Observable<ResponseBody> updateGestureCode(@Field("staffNo") String staffNo,
                                               @Field("gestureCode") String gestureCode, @Field
                                                       ("state") int state);

}
