package com.xujun.funapp.network.retrofitclient;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.xujun.funapp.common.APP;
import com.xujun.funapp.network.INetworkListener;
import com.xujun.funapp.network.RequestListener;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public class RetrofitClient implements INetworkListener {

    private static final int DEFAULT_TIMEOUT = 20;

    private static OkHttpClient okHttpClient;
    public static String baseUrl = NetworkApi.mBaseUrl;
    private static Context mContext;
    private static RetrofitClient mInstance;
    private static NetworkApi apiService;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;

    private static Retrofit.Builder builder = new Retrofit.Builder().addConverterFactory
            (GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create
            ()).baseUrl(baseUrl);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor
                    .Level.HEADERS)).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

    public static RetrofitClient getInstance() {
        return Holder.mInstace;
    }

    private RetrofitClient() {
        this(null, null, null);
    }

    private RetrofitClient(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        if (mContext == null) {
            mContext = APP.getInstance();
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "tamic_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new CustomIntercept())
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder().client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(url).build();

        apiService = create(NetworkApi.class);

    }

    public NetworkApi getApiService() {
        return apiService;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    private static class Holder {
        private static final RetrofitClient mInstace = new RetrofitClient();
    }

    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io()).unsubscribeOn
                        (Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }

          /*  @Override
            public Observable call(Observable observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }*/
        };
    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer();
    }

    @Override
    public <T> void excuteGet(String url, Map paramsMap, final RequestListener<T> requestListener) {
        //        Observable<RequestBody> observable1 = apiService.executeGet(url, paramsMap);
        Observable<ResponseBody> o = apiService.executeGet(url, paramsMap);
        Observable<T> observable = o.map(new Func1<ResponseBody, T>() {
            @Override
            public T call(ResponseBody responseBody) {


                return (T)responseBody;
            }
        });

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<T>() {
            @Override
            public void call(T t) {
                requestListener.onSuccess(t);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                requestListener.onError(throwable);
            }
        });

    }

    @Override
    public <T> void excutePush(String url, Map paramsMap, RequestListener<T> requestListener) {

    }

    @Override
    public <T> void json(String url, String jsonStr, RequestListener<T> requestListener) {

    }


}
