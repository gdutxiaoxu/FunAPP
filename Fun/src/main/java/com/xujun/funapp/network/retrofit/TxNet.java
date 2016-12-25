package com.xujun.funapp.network.retrofit;

import com.xujun.funapp.network.INetwork;
import com.xujun.funapp.network.NetworkConfig;
import com.xujun.funapp.network.retrofitclient.CustomIntercept;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xujun on 2016/4/20.
 */
public class TxNet {

    public static final String mBaseImageUrl = "http://tnfs.tngou.net/image";

    public static final String apikey = "apikey";
    public static final String apikeyValue = "81bf9da930c7f9825a3c3383f1d8d766";
    public static final String ContentType = "Content-Type";
    public static final String ContentTypeValue = "application/json";

    private String mBaseUrl = "http://apis.baidu.com/";

    private TxNet() {
    }

    private static class NetworkHelper {
        private static final TxNet INSTANCE = new TxNet();
    }

    /**
     * 通过静态内部类实现单例
     */
    public static TxNet getInstance() {
        return NetworkHelper.INSTANCE;
    }

    /**
     * 正式环境
     */
    private final static INetwork mINetwork = NetworkConfig.create(NetworkConfig.Normal.class);

    private TxApi mTxApi;

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
    public TxApi getApi() {
        if (mTxApi == null) {
            synchronized (TxNet.class) {
                if (mTxApi == null) {
                    if (mOkHttpClient == null) {
                        Interceptor interceptor = new CustomIntercept();
                        mOkHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                                .build();
                    }
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
                            //使用自定义的mGsonConverterFactory
                            .addConverterFactory(mGsonConverterFactory).addCallAdapterFactory
                                    (mRxJavaCallAdapterFactory).client(mOkHttpClient).build();
                    mTxApi = retrofit.create(TxApi.class);


                }
            }


        }
        return mTxApi;
    }


}
