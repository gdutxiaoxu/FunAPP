package com.xujun.myrxretrofitlibrary;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author xujun  on 2016/12/24.
 * @email gdutxiaoxu@163.com
 */

public interface YiYuanApi {

    String API_ID = "29571";
    String API_ID_KEY = "showapi_appid";
    String API_SECRET = "5bf00910e04a46998f6979f6da400f1e";
    String API_SIGN_KEY = "showapi_sign";
    //    String API_SIGN = MD5.encode(API_SECRET);
    String API_SIGN = API_SECRET;

    String mBaseUrl = "https://route.showapi.com/";

    //    http://route.showapi
    // .com/109-34?showapi_appid=29571&showapi_sign=5bf00910e04a46998f6979f6da400f1e


    //    https://route.showapi.com/109-35?channelId=&channelName=&maxResult=20&needAllList=0
    // &needContent=0&needHtml=0&page=1&showapi_appid=29457&showapi_timestamp=20161225101701
    // &title=足球&showapi_sign=99af1a0e6ad027c261b8965972b4e42b

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> excutePush(@Url String url, @FieldMap Map<String, Object> paramsMap);




}
