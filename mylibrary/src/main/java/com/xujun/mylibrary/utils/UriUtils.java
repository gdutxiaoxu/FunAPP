package com.xujun.mylibrary.utils;

import android.content.Context;
import android.net.Uri;

/**
 * @author xujun  on 2016/12/25.
 * @email gdutxiaoxu@163.com
 */

public class UriUtils {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
