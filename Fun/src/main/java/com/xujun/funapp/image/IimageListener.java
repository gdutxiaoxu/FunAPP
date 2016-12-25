package com.xujun.funapp.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public interface IimageListener {

    public  void display(Context context, ImageView imageView, String url, int progressId,int errorId,Object tag);

    public  void display(Context context, ImageView imageView, String url, int progressId,int errorId);

    public  void display(Context context, ImageView imageView, String url,int progressId);

    public  void display(Context context, ImageView imageView, String url);
    public  void display(Context context, ImageView imageView, Uri uri);
}
