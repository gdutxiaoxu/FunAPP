package com.xujun.commonlibrary.common.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * @ explain:
 * @ author：xujun on 2016/6/14 16:03
 * @ email：gdutxiaoxu@163.com
 */
public class ViewUtils {

    public static void removeParent(View view){
        ViewGroup parent =(ViewGroup) view.getParent();
        if(parent!=null){
            parent.removeView(view);
        }

    }



}
