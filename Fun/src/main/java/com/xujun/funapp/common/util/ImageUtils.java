package com.xujun.funapp.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:36
 * @ email：gdutxiaoxu@163.com
 */
public class ImageUtils {

    public static void display(Context context, ImageView imageView,String url){
        Picasso.with(context).load(url).into(imageView);
    }
}
