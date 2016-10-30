package com.xujun.funapp.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.Constants;

/**
 * Created by Domen、on 2016/5/5.
 */
public class SPUtils {
    private static final String DEFAULT_NAME = Constants.CONFIG;

    /**
     * 保存数据
     *
     * @param fileName 文件名,传空则用默认
     * @param key      key
     * @param obj      value
     */
    public static boolean put(String fileName, String key, Object obj) {
        if (TextUtils.isEmpty(fileName))
            fileName = DEFAULT_NAME;

        SharedPreferences.Editor editor = APP.getInstance().getSharedPreferences(fileName,
                Context.MODE_PRIVATE).edit();

        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        }
        return editor.commit();
    }

    public static String getString(String key) {
        return getString(DEFAULT_NAME, key);
    }

    public static String getString(String fileName, String key) {
        return (String) get(fileName, key, "");
    }

    public static boolean getBoolean(String key) {
        return (boolean) get(DEFAULT_NAME, key, false);
    }

    public static boolean put(String key, Object obj) {
        return put(DEFAULT_NAME, key, obj);
    }

    public static Object get(String key, Object defaultObj) {
        return get(null, key, defaultObj);
    }

    /**
     * 获取指定的数据
     *
     * @param key        key
     * @param defaultObj 不存在时候的默认值
     * @return 返回的数据
     */
    public static Object get(String fileName, String key, Object defaultObj) {
        if (TextUtils.isEmpty(fileName))
            fileName = DEFAULT_NAME;

        SharedPreferences sp = APP.getInstance().getSharedPreferences(fileName, Context
                .MODE_PRIVATE);

        if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String... keys) {
        SharedPreferences sp = APP.getInstance().getSharedPreferences(DEFAULT_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String key : keys)
            editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear(String fileName) {
        SharedPreferences sp = APP.getInstance().getSharedPreferences(fileName, Context
                .MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
