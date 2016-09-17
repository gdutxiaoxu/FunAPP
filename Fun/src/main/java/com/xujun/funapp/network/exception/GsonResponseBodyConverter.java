package com.xujun.funapp.network.exception;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @ explain:chu
 * @ author：xujun on 2016-7-19 11:57
 * @ email：gdutxiaoxu@163.com
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            // 打印出返回的信息，不管是成功还是失败
            //            Logger.json(response);
            Logger.d(response);
            //ResultResponse 只解析result字段，Response为我们定义的基类
          /*  Response resultResponse = gson.fromJson(response, Response.class);
            //成功的时候
            if (resultResponse.isSuccess()) {
                //result==0表示成功返回，继续用本来的Model类解析
                return gson.fromJson(response, type);
                //
            } else {
                //ErrResponse 将msg解析为异常消息文本
                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);
                throw new ResultException(resultResponse.getStatus_code(), errResponse.getMsg());
            }*/
        } finally {
        }
        return gson.fromJson(response, type);
    }
}