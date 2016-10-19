package com.xujun.commonlibrary.common.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/4/19 17:00
 * @ email：gdutxiaoxu@163.com
 */
public class SDCardUtils {

    private static String Tag = "SDCardUtils";
    public static final String root = "/storage";
    public static final String root_sdcard = "sdcard";

    private SDCardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final String FILE_CACHE_ROOT;
    public static final String PACKNAME_NAME = "hobby";

    static {

        FILE_CACHE_ROOT = getSDCardPath() + PACKNAME_NAME;

    }

    public static String getExternStoragePath(Context context) {
        return getStoragePath(context, true);
    }

    public static List<File> getRootPath(Context context) {
        File file = new File(root);
        List<File> fileArrayList = new ArrayList<>();
        if (!file.exists()) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            fileArrayList.add(externalStorageDirectory);
            return fileArrayList;
        }
        String storagePath = getStoragePath(context, true);
        LUtils.i(" storagePath= " + storagePath);
        if (storagePath != null) {
            fileArrayList.add(new File(storagePath));
        }
        fileArrayList.add(Environment.getExternalStorageDirectory());

/*
        for(int i=0;i<files.length;i++){
            File child = files[i];
             if(child.exists()){
                 if(child.getName().contains(root_sdcard)){
                     fileArrayList.add(child);
                 }
             }

        }*/
        return fileArrayList;
    }

    private static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    public static File getCacheFile(Context context, String fileName) {
        File file = getExternalCacheDirFile(context);
        if (file == null) {
            file = context.getCacheDir();
        }
        return new File(file, fileName);
    }

    public static String getCachePath(Context context, String fileName) {
        return getCacheFile(context, fileName).getAbsolutePath().toString();
    }


    /**
     * 获取缓存的路径
     *
     * @param context
     * @return
     */
    private static File getExternalCacheDirFile(Context context) {
        if (!isSDCardEnable()) {//当sd卡不可用的时候，返回空
            return null;
        }
        File dataDir = new File(new File(Environment.getExternalStorageDirectory().toString(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w(Tag, "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.w(Tag, "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


}
