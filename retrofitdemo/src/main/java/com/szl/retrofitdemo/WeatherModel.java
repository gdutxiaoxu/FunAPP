package com.szl.retrofitdemo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ explain:
 * @ author：xujun on 2016-8-25 15:17
 * @ email：gdutxiaoxu@163.com
 */
public class WeatherModel {

    public static void getNews(String number, String page) {
        APi api = Network.getInstance().getApi();
        Call<News> news = api.getNews(number, page);
        news.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

    }
}
