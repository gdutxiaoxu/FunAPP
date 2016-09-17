package com.xujun.funapp.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Domen、on 2016/4/22.
 */
public interface DownLoadApi {

    @GET("getImg")
    Observable<ResponseBody> downLoadImage();

    @GET("getImg")
    Call<ResponseBody> downLoad(String url);
}
