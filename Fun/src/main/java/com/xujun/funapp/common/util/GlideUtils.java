package com.xujun.funapp.common.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.xujun.funapp.R;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:36
 * @ email：gdutxiaoxu@163.com
 */
public class GlideUtils {

    public static void display(Context context, ImageView imageView, String url, Object tag) {
        Glide.with(context).load(url).
                error(R.drawable.ic_error).placeholder(R.drawable.ic_progress).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url, int progressId) {
        RequestManager manager = Glide.with(context);
        DrawableTypeRequest<String> load = manager.load(url);
        load.error(R.drawable.ic_error).placeholder(new ColorDrawable(Color.GRAY)).into
                (imageView);
        if (progressId != -1) {
            load.placeholder(progressId);
        }
    }

    public static void display(Context context, ImageView imageView, String url) {
        display(context, imageView, url, -1);
    }

    public static void cancel(Context context) {


    }
}
