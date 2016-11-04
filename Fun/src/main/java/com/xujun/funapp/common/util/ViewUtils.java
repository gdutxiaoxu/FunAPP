package com.xujun.funapp.common.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * @ explain:
 * @ author：xujun on 2016/6/14 16:03
 * @ email：gdutxiaoxu@163.com
 */
public class ViewUtils {

    public static  void removeParent(View view){
        ViewGroup viewGroup= (ViewGroup) view.getParent();
        if(viewGroup!=null){
            viewGroup.removeView(view);
        }

    }



}
