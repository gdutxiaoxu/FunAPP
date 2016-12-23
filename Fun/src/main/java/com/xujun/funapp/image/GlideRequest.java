package com.xujun.funapp.image;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.xujun.funapp.R;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public class GlideRequest implements IimageListener {

    static GlideRequest mInstance;

    private GlideRequest(){

    }

    public static GlideRequest getInstance(){
        if(mInstance==null){
            mInstance=new GlideRequest();
        }
        return mInstance;
    }

    @Override
    public void display(Context context, ImageView imageView, String url, int progressId, int
            errorId, Object tag) {
        DrawableTypeRequest<String> load = Glide.with(context).load(url);
        if (progressId != -1) {
            load.placeholder(progressId);
        } else {
            load.placeholder(new ColorDrawable(Color.GRAY));
        }
        if (errorId != -1) {
            load.error(errorId);
        }else{
            load.error(R.drawable.ic_error);
        }

        load.into(imageView);
    }

    @Override
    public void display(Context context, ImageView imageView, String url, int progressId, int
            errorId) {
        display(context, imageView, url, progressId, errorId, null);
    }

    @Override
    public void display(Context context, ImageView imageView, String url, int progressId) {
        display(context, imageView, url, progressId, -1, null);
    }

    @Override
    public void display(Context context, ImageView imageView, String url) {
        display(context, imageView, url, -1, -1, null);
    }
}
