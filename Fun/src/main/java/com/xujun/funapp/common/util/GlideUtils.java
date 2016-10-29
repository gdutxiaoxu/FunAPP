package com.xujun.funapp.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xujun.funapp.R;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:36
 * @ email：gdutxiaoxu@163.com
 */
public class GlideUtils {

    public static void display(Context context, ImageView imageView,String url,Object tag){
        Glide.with(context).load(url).
                error(R.drawable.ic_error).placeholder(R.drawable.ic_progress).into(imageView);
    }

    public static void display(Context context, ImageView imageView,String url){
        Glide.with(context).load(url).
                error(R.drawable.ic_error).placeholder(R.drawable.ic_progress).into(imageView);
    }

    public static void cancel(Context context){



    }
}
