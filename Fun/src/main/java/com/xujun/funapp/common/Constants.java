package com.xujun.funapp.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by xujun、on 2016/5/5.
 */
public class Constants {

    public final static String CONFIG = "config";

    public final static String BaseCachePath = Environment.getExternalStoragePublicDirectory
            ("Download") + File.separator + APP.getInstance().getPackageName() + File.separator;



    public static class IntentConstants {
        public static final String DEFAULT_PARCEABLE_NAME = "default_parceable_name";
        public static final String DEFAULT_STRING_NAME = "default_string_name";
        public static final String TITLE_NAME = "title_name";
        public static final int RESULT_CODE_PICK_CITY = 2333;
        public static final String KEY_PICKED_CITY = "picked_city";
    }

    public static class URLConstants {
        public static final String URL_IMAGE_BASE = "http://tnfs.tngou.net/image";
    }

    public static class SPConstants {
        //表示是否在WiFi的情况下 才加载图片，true表示在WiFi的情况下才加载图片 ，false而表示不是
        public static final String isIntelligentNoPic = "isIntelligentNoPic";
        public static final String isNightMode = "isNightMode";
//        表示当前城市
        public static final String city = "city";
    }
}
