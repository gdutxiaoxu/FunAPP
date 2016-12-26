package com.xujun.funapp.network.retrofitclient;

import com.xujun.funapp.common.util.WriteLogUtil;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public class CustomIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        Request request = null;
        try {
            request = chain.request();
            response=chain.proceed(request);
            WriteLogUtil.i("请求的url是" + request.url());
        } catch (IOException e) {
            throw new HttpIOException(request, e);
        }


        return response;
    }

    private static class HttpIOException extends IOException {

        HttpIOException(final Request request, final IOException e) {
            super(String.format(Locale.US, "IOException during http request. Request= %s",
                    request), e);
        }
    }
}
