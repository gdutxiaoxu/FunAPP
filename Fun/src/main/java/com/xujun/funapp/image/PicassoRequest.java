package com.xujun.funapp.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * @author xujun  on 2017/1/13.
 * @email gdutxiaoxu@163.com
 */

public class PicassoRequest implements IimageListener {

    @Override
    public void display(Context context, ImageView imageView, String url, int progressId, int
            errorId, Object tag) {
//        Picasso.with(context).load(url).placeholder(progressId).error(errorId).tag(tag).into(imageView);
    }

    @Override
    public void display(Context context, ImageView imageView, String url, int progressId, int
            errorId) {
//        Picasso.with(context).load(url).placeholder(progressId).error(errorId).into(imageView);
    }

    @Override
    public void display(Context context, ImageView imageView, String url, int progressId) {
//       Picasso.with(context).load(url).placeholder(progressId).into(imageView);
    }

    @Override
    public void display(Context context, ImageView imageView, String url) {
//        Picasso.with(context).load(url).into(imageView);
    }

    @Override
    public void display(Context context, ImageView imageView, Uri uri) {
//           Picasso.with(context).load(uri).into(imageView);
    }
}
