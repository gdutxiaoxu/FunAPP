package com.xujun.funapp.network;

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
public class TnGouNet {

    public static final String mBaseImageUrl="http://tnfs.tngou.net/image";

    private TnGouNet() {
    }

    private static class NetworkHelper {
        private static final TnGouNet INSTANCE = new TnGouNet();
    }

    /**
     * 通过静态内部类实现单例
     */
    public static TnGouNet getInstance() {
        return NetworkHelper.INSTANCE;
    }

    /**
     * 正式环境
     */
    private final static INetwork mINetwork = NetworkConfig.create(NetworkConfig.Normal.class);

    /**
     * 测试环境使用下面这一句
     */
    // private final static INetwork mINetwork= NetworkConfig.create(NetworkConfig.Test.class);

    public static final String mCurrentUrl = mINetwork.getBaseUrl();
    public static final String mCurrentDownLoadUrl = mINetwork.getDownloadUrl();
    public static final String mCurrentUploapUrlBase = mINetwork.getUploadUrl();
    private static String mHostName = mINetwork.getHostName();

    private TnGouAPi mTnGouAPi;
    private TnGouAPi mDifferentTnGouAPi;
    private DownLoadApi mDownLoadApi;

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
    public TnGouAPi getTnGouAPi() {
        if (mTnGouAPi == null) {
            synchronized (TnGouNet.class) {
                if (mTnGouAPi == null) {
                    if (mOkHttpClient == null) {
                        Interceptor interceptor = OkHttpUtils.getInstance().getInterceptor();
                        mOkHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .build();
                    }
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(mCurrentUrl)
                            //使用自定义的mGsonConverterFactory
                            .addConverterFactory(mGsonConverterFactory)
                            .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                            .client(mOkHttpClient)
                            .build();
                    mTnGouAPi = retrofit.create(TnGouAPi.class);
                }
            }


        }
        return mTnGouAPi;
    }

    /**
     * 没有统一格式的时候请使用这个retrofit的单例
     *
     * @return
     */
    public TnGouAPi getDifferentTnGouAPi() {
        if (mDifferentTnGouAPi == null) {
            synchronized (TnGouNet.class) {
                if (mDifferentTnGouAPi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(mCurrentUrl)
                            .addConverterFactory(GsonConverterFactory.create())//这里没有在转化gson的时候进行拦截
                            .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                            .client(mOkHttpClient)
                            .build();
                    mDifferentTnGouAPi = retrofit.create(TnGouAPi.class);
                }
            }
        }
        return mDifferentTnGouAPi;
    }

    public static String getDonwLoadUrl() {
        return mCurrentDownLoadUrl;
    }

    public DownLoadApi getDownLoadApi() {
        if (mDownLoadApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mCurrentDownLoadUrl)
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .build();
            mDownLoadApi = retrofit.create(DownLoadApi.class);
        }
        return mDownLoadApi;
    }


}
