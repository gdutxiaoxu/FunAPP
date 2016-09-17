package com.xujun.funapp.network;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

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
public class Network {

    private Network() {
    }

    private static class NetworkHelper {
        private static final Network INSTANCE = new Network();
    }

    /**
     * 通过静态内部类实现单例
     */
    public static Network getInstance() {
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
            synchronized (Network.class) {
                if (mTnGouAPi == null) {
                    if (mOkHttpClient == null) {
                        mOkHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(mInterceptor)
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
            synchronized (Network.class) {
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

    /**
     * 证书校验
     *
     * @param certificates cer文件
     */
    public OkHttpClient setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory
                        .generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            mOkHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(new MyHostnameVerifier())
                    .addInterceptor(mInterceptor)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return mOkHttpClient;
    }

    private Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Logger.i("请求的url是" + request.url());

            long startNs = System.nanoTime();
            Response response = chain.proceed(request);
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            Logger.w("Network ,intercept: 168  =" + "<-- " + response.code() + ' ' + response
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
                Logger.json(buffer.clone().readString(charset));
            }


            return response;
        }
    };


    private static class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {

            return mHostName.equals(hostname);
        }
    }
}
