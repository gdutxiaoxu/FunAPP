package com.xujun.funapp.network;

import com.xujun.funapp.network.retrofitclient.CustomIntercept;

import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xujun  on 2016/12/24.
 * @email gdutxiaoxu@163.com
 */

public class ApiManger {

    private HashMap<String, Object> mMap = new HashMap<>();

    public static ApiManger getInstance() {
        return Holder.mInstance;
    }

    private ApiManger() {
    }

    private static class Holder {
        private static final ApiManger mInstance = new ApiManger();
    }

    public <T> T getApi(Class<T> clz,String baseUrl) {
        String name = clz.getName();
        Object value = mMap.get(name);
        if (value == null) {
            value = createApi(clz,baseUrl);
            mMap.put(name,value);
        }
        return (T) value;

    }

    private <T> T createApi(Class<T> clz, String baseUrl) {

        Interceptor interceptor = new CustomIntercept();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                //使用自定义的mGsonConverterFactory
                .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory
                        (RxJavaCallAdapterFactory.create()).client(okHttpClient).build();
        return retrofit.create(clz);
    }



}
