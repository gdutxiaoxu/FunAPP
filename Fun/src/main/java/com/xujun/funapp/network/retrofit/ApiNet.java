package com.xujun.funapp.network.retrofit;

import com.xujun.funapp.network.retrofitclient.NetworkApi;
import com.xujun.funapp.network.retrofitclient.CustomIntercept;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xujun on 2016/4/20.
 */
public class ApiNet {

    public static final String mBaseImageUrl = "http://tnfs.tngou.net/image";

    private String mBaseUrl = NetworkApi.mBaseUrl;

    NetworkApi mNetworkApi;

    private OkHttpClient mOkHttpClient;

    private Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();

    //    统一处理，正式上线的时候使用
    //    private Converter.Factory mGsonConverterFactory = ResponseConverterFactory.create();

    private CallAdapter.Factory mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private final Retrofit mRetrofit;

    private ApiNet() {
        mOkHttpClient=new OkHttpClient().newBuilder().
                addInterceptor(new CustomIntercept()).build();

        //使用自定义的mGsonConverterFactory
        mRetrofit = new Retrofit.Builder().baseUrl(mBaseUrl)

                //使用自定义的mGsonConverterFactory
                .addConverterFactory(mGsonConverterFactory)
                .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                .client(mOkHttpClient)
                .build();
    }

    private static class NetworkHelper {
        private static final ApiNet INSTANCE = new ApiNet();
    }

    /**
     * 通过静态内部类实现单例
     */
    public static ApiNet getInstance() {
        return NetworkHelper.INSTANCE;
    }


    public ApiNet createBaseApi(){
        mNetworkApi=createApi(NetworkApi.class);
        return this;
    }

    public NetworkApi getNetworkApi(){
        return mNetworkApi;
    }



    public <T> T createApi(Class<T> clz) {
        return mRetrofit.create(clz);
    }


}
