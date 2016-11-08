package com.xujun.funapp.view.detail;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ explain:
 * @ author：xujun on 2016/11/8 16:10
 * @ email：gdutxiaoxu@163.com
 */
public class HttpDownloadThread extends Thread {


    private String mUrl;
    private String mContentDisposition;
    private String mMimetype;
    private long mContentLength;

    public HttpDownloadThread(String url, String contentDisposition, String mimetype, long contentLength) {
        this.mUrl = url;
        this.mContentDisposition=contentDisposition;
        this.mContentDisposition=mimetype;
        this.mContentDisposition=contentDisposition;

    }

    @Override
    public void run() {
        URL url;
        try {
            url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            InputStream in = conn.getInputStream();

            File downloadFile;
            File sdFile;
            FileOutputStream out = null;
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED)){
                downloadFile = Environment.getExternalStorageDirectory();
                sdFile = new File(downloadFile, "test.file");
                out = new FileOutputStream(sdFile);
            }

            //buffer 4k
            byte[] buffer = new byte[1024 * 4];
            int len = 0;
            while((len = in.read(buffer)) != -1){
                if(out != null)
                    out.write(buffer, 0, len);
            }

            //close resource
            if(out != null)
                out.close();

            if(in != null){
                in.close();
            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}