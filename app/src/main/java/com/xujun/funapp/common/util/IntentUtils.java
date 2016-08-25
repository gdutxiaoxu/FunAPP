package com.xujun.funapp.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * @ explain:
 * @ author：xujun on 2016/6/14 09:19
 * @ email：gdutxiaoxu@163.com
 */
public class IntentUtils {

    /**
     * 打开各种文件的Intent
     */
    public static void openDifferentFile(String path, Context context, String fileType) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        if(!(context instanceof Activity)){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }else{
            Logger.i("Activity=" );
        }

        try {
            if (fileType.equals("pdf")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "application/pdf");
            } else if (fileType.equals("txt")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "text/plain");
            } else if (fileType.equals("apk")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            } else if (fileType.equals("doc") || fileType.equals("docx")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "application/msword");
            } else if (fileType.equals("xls") || fileType.equals("xlsx")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (fileType.equals("ppt") || fileType.equals("pptx")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("JPG")) {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri, "image/*");
            } else {
                Uri uri = Uri.fromFile(new File(path));
                intent.setDataAndType(uri,"*/*");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "没有可以打开此文件的应用",Toast.LENGTH_SHORT).show();
            return;
        }




    }
}
