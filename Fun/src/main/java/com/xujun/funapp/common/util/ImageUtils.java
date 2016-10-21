package com.xujun.funapp.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xujun.funapp.R;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:36
 * @ email：gdutxiaoxu@163.com
 */
public class ImageUtils {

    public static void display(Context context, ImageView imageView,String url,Object tag){
        Picasso.with(context).load(url).tag(tag).
                error(R.drawable.ic_error).placeholder(R.drawable.ic_progress).into(imageView);
    }

    public static void display(Context context, ImageView imageView,String url){
        Picasso.with(context).load(url).
                error(R.drawable.ic_error).placeholder(R.drawable.ic_progress).into(imageView);
    }

    public static void cancel(Context context){
        Picasso picasso = Picasso.with(context);


    }
}
