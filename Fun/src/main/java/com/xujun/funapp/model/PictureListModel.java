package com.xujun.funapp.model;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.beans.Test;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.network.RequestListener;
import com.xujun.funapp.network.TnGouAPi;
import com.xujun.funapp.network.TnGouNet;

import java.io.IOException;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:24
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListModel {

    public static String[] tags = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7"
    };

    public static void test(Test test) {
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .baseUrl("http://120.76.120.173")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit build = mBuilder.build();
        TnGouAPi tnGouAPi = build.create(TnGouAPi.class);
        Call<ResponseBody> call = tnGouAPi.getTest(test);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                Headers headers = response.headers();
                Set<String> names = headers.names();
                for(String  key:names){
                    String value = headers.get(key);
                }


                try {
                    Logger.i("onResponse:   body=" + body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.i("onResponse:   t=" + t.getMessage());
            }
        });


    }

    public void getPictureList(String page, String rows, int id,
                               final RequestListener<PictureListBean> requestListener) {
        TnGouAPi tnGouAPi = TnGouNet.getInstance().getTnGouAPi();
        Observable<PictureListBean> observable = tnGouAPi.getPictureList(page, rows, id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PictureListBean>() {
                    @Override
                    public void call(PictureListBean pictureListBean) {
                        if (requestListener != null) {
                            requestListener.onSuccess(pictureListBean);
                        }
                    /*  pictureListBean.tag=tag;
                      EventBus.getDefault().post(pictureListBean);*/
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        WriteLogUtil.i(throwable.getMessage() + "\n" + throwable.getCause() +
                                "\n" + throwable.getStackTrace());
                      /*EventBus.getDefault().post(throwable);*/
                        if (requestListener != null) {
                            requestListener.onError(throwable);
                        }
                    }
                });

    }
}
