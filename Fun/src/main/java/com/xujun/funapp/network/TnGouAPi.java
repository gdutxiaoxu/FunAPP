package com.xujun.funapp.network;

import com.xujun.funapp.beans.PictureClassify;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.beans.Test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xujun、on 2016/4/20.
 */
public interface TnGouAPi {
    //检验MD5
    @GET("checkImg")
    Observable<ResponseBody> checkMD5(@Query("md5") String MD5);

    // 获取图片列表
    @GET("tnfs/api/list")
    Observable<PictureListBean> getPictureList(@Query("page") String page,
                                               @Query("rows") String rows,
                                               @Query("id") int id);

    @FormUrlEncoded
    @POST("updateGestureCode")
    Observable<ResponseBody> updateGestureCode(@Field("staffNo") String staffNo,
                                               @Field("gestureCode") String gestureCode, @Field
                                                       ("state") int state);

    //获取图片分类
    @GET("tnfs/api/classify")
    Observable<PictureClassify> pictureClassify();

    @POST("/api.php")
    Call<ResponseBody> getTest(@Body Test test);

    //    http://www.tngou.net/tnfs/api/show?id=9

    // 获取图片列表
    @GET("tnfs/api/show")
    Observable<PictureDetailBean> getPictureDetail(@Query("id") int id);

}
