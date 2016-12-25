package com.xujun.funapp.network.retrofitclient;

import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.beans.YiYuanResponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public interface NetworkApi {

    public final static String mBaseUrl = "https://route.showapi.com/109-34/";

    @GET
    Observable<ResponseBody> executeGet(@Url String url, @QueryMap Map<String, Object> paramsMap);

    @POST
    Observable<ResponseBody> executePost(@Url String url,
                                         //  @Header("") String authorization,
                                         @QueryMap Map<String, String> maps);

    @POST("./")
    Observable<ResponseBody> json(@Url String url, @Body RequestBody jsonStr);

    @GET("{url}")
    Observable<Object> testPath(@Path("url") String url, @QueryMap Map<String, String> paramsMap);

    @GET
    Observable<YiYuanResponse<YiYuanNewsClassify.ShowapiResBodyEntity>> testUrl(@Url String url,
                                                                                @QueryMap
                                                                                        Map<String, String> paramsMap);

}
