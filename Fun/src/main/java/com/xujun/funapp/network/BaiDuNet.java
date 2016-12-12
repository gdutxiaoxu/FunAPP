package com.xujun.funapp.network;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.common.util.WriteLogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xujun on 2016/4/20.
 */
public class BaiDuNet {

    public static final String mBaseImageUrl = "http://tnfs.tngou.net/image";

    public static final String apikey = "apikey";
    public static final String apikeyValue = "81bf9da930c7f9825a3c3383f1d8d766";
    public static final String ContentType = "Content-Type";
    public static final String ContentTypeValue = "application/json";

    List<Object> mList = new ArrayList<>();
    private Interceptor mInterceptor;
    private String mBaseUrl = "http://apis.baidu.com/";

    private BaiDuNet() {
    }

    private static class NetworkHelper {
        private static final BaiDuNet INSTANCE = new BaiDuNet();
    }

    /**
     * 通过静态内部类实现单例
     */
    public static BaiDuNet getInstance() {
        return NetworkHelper.INSTANCE;
    }

    /**
     * 正式环境
     */
    private final static INetwork mINetwork = NetworkConfig.create(NetworkConfig.Normal.class);

    private BaiDuApi mBaiDuApi;

    /**
     * 注意证书是在第一次验证的时候验证的。
     */
    private OkHttpClient mOkHttpClient;
    //    测试的时候使用下面这个
    private Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();

    //    统一处理，正式上线的时候使用
    //    private Converter.Factory mGsonConverterFactory = ResponseConverterFactory.create();

    private CallAdapter.Factory mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /**
     * 有统一格式的时候的处理的retrofit的单例
     *
     * @return
     */
    public BaiDuApi getApi() {
        if (mBaiDuApi == null) {
            synchronized (BaiDuNet.class) {
                if (mBaiDuApi == null) {
                    if (mOkHttpClient == null) {
                        Interceptor interceptor = getInterceptor();
                        mOkHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .build();
                    }
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(mBaseUrl)
                            //使用自定义的mGsonConverterFactory
                            .addConverterFactory(mGsonConverterFactory)
                            .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                            .client(mOkHttpClient)
                            .build();
                    mBaiDuApi = retrofit.create(BaiDuApi.class);


                }
            }


        }
        return mBaiDuApi;
    }

    public Interceptor getInterceptor() {
        if (mInterceptor != null) {
            return mInterceptor;
        }

        //  增加自己的apikey
        mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 增加自己的apikey
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();

                request = builder.header(apikey, apikeyValue).
                        header(ContentType, ContentTypeValue).build();

                Response response = chain.proceed(request);

                WriteLogUtil.i("请求的url是" + request.url());

                long startNs = System.nanoTime();

                long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
                ResponseBody responseBody = response.body();
                long contentLength = responseBody.contentLength();
                Logger.w("TnGouNet ,intercept: 168  =" + "<-- " + response.code() + ' ' +
                        response
                                .message() + " (" + tookMs + "ms" +
                        ')');

                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = responseBody.contentType();
                if (contentType != null)
                    charset = contentType.charset(Charset.forName("UTF-8"));
                if (contentLength != 0) {
//                    WriteLogUtil.json(buffer.clone().readString(charset));
                }

                return response;
            }

        };


        return mInterceptor;
    }


}
