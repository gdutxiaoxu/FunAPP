package com.xujun.myrxretrofitlibrary.juhe;

import android.os.Looper;
import android.util.Log;

import com.xujun.myrxretrofitlibrary.CustomIntercept;
import com.xujun.myrxretrofitlibrary.GsonManger;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author xujun  on 2016/12/24.
 * @email gdutxiaoxu@163.com
 */

public class JuHeApiManger {

    private HashMap<String, Object> mMap = new HashMap<>();

    public static  final String TAG="xujun";

    public static JuHeApiManger getInstance() {
        return Holder.mInstance;
    }

    private JuHeApiManger() {
    }

    private static class Holder {
        private static final JuHeApiManger mInstance = new JuHeApiManger();
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
                .addConverterFactory(GsonConverterFactory.create())
                //使用自定义的mGsonConverterFactory
                .addCallAdapterFactory
                        (RxJavaCallAdapterFactory.create()).client(okHttpClient).build();
        return retrofit.create(clz);
    }

    public JuHeApi getJuheApi(){
        return getApi(JuHeApi.class, JuHeApi.mBaseUrl);
    }

    public <T> void excutePush(String url, HashMap<String,Object> map, Subscriber<T> subscriber){
        getJuheApi().push(url,map).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, T>() {
                    @Override
                    public T call(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string();
                            BaseJuheEntity baseJuheEntity = GsonManger.getInstance().fromJson
                                    (json, BaseJuheEntity.class);
                            boolean b = Looper.getMainLooper() == Looper.myLooper();
                            Log.i(TAG, "excutePush :是否是主线程 =" +b);
                            return (T)baseJuheEntity.result;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }


    public <T> void excutePushString(String url, Map<String,Object> map, Subscriber<String> subscriber){
        getJuheApi().push(url,map).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        try {
                            /*String json = responseBody.string();
                            BaseJuheEntity baseJuheEntity = GsonManger.getInstance().fromJson
                                    (json, BaseJuheEntity.class);
                            String s = GsonManger.getInstance().toJson(baseJuheEntity.result);*/

                            JSONTokener jsonTokener = new JSONTokener( responseBody.string());
                            JSONObject jsonObject= (JSONObject) jsonTokener.nextValue();
                            String result = jsonObject.getString("result");
                            boolean b = Looper.getMainLooper() == Looper.myLooper();
                            Log.i(TAG, "excutePushString :是否是主线程 =" +b);
                            return result;

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }





}
