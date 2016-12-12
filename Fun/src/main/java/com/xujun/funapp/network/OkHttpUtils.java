package com.xujun.funapp.network;

import com.xujun.funapp.common.util.WriteLogUtil;

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

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 23:24
 * @ email：gdutxiaoxu@163.com
 */
public class OkHttpUtils {

    private static volatile OkHttpUtils mInstance;

    private Interceptor mInterceptor;

    private OkHttpUtils() {

    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }

        }

        return mInstance;

    }

    /**
     * 证书校验
     *
     * @param certificates cer文件
     */
    public OkHttpClient setCertificates(String hostName, OkHttpClient okHttpClient,
                                        InputStream... certificates) {
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

            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(new MyHostnameVerifier(hostName))
                    .addInterceptor(mInterceptor)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return okHttpClient;
    }

    public Interceptor getInterceptor() {
        if (mInterceptor == null) {
            mInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    WriteLogUtil.i("请求的url是" + request.url());

                    long startNs = System.nanoTime();
                    Response response = chain.proceed(request);
                    long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
                    ResponseBody responseBody = response.body();
                    long contentLength = responseBody.contentLength();
                 /*   WriteLogUtil.w("TnGouNet ,intercept: 168  =" + "<-- " + response.code() + ' ' +
                            response
                                    .message() + " (" + tookMs + "ms" +
                            ')');*/

                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null)
                        charset = contentType.charset(Charset.forName("UTF-8"));
                    if (contentLength != 0) {

//                        WriteLogUtil.json(buffer.clone().readString(charset));
                    }

                    return response;
                }

            };


        }
        return mInterceptor;
    }

    static class MyHostnameVerifier implements HostnameVerifier {
        private String mHostName;

        public MyHostnameVerifier(String hostName) {
            mHostName = hostName;
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {

            return mHostName.equals(hostname);
        }
    }
}