package com.xujun.funapp.image;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 */

public class ImageRequestManager {

   private ImageRequestManager(){

   }

    public static IimageListener getInstance(){
        return Holder.mIimageListener;
    }

    private static class Holder{
        private static final IimageListener mIimageListener=GlideRequest.getInstance();
    }
}
