package com.xujun.funapp.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public interface IimageListener {



    void display(Context context, ImageView imageView, String url, int progressId, int errorId,
                 Object tag);

    void display(Context context, ImageView imageView, String url, int progressId, int errorId);

    void display(Context context, ImageView imageView, String url, int progressId);

    void display(Context context, ImageView imageView, String url);

    void display(Context context, ImageView imageView, Uri uri);
}
