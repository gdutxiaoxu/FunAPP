package com.xujun.funapp.common.network;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public class CustomIntercept implements Interceptor {

    public static  final String TAG="xujun";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i(TAG,"请求的url是" + request.url());

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
                 /*   WriteLogUtil.w("TnGouNet ,intercept: 168  =" + "<-- " + response.code() + '
                  ' +
                            response
                                    .message() + " (" + tookMs + "ms" +
                            ')');*/

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) charset = contentType.charset(Charset.forName("UTF-8"));
        if (contentLength != 0) {

            //                        WriteLogUtil.json(buffer.clone().readString(charset));
        }

        return response;
    }
}
