package com.xujun.funapp.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileUtil {

    public static long fileSize(String pathAndFile) {
        File file = new File(pathAndFile);
        return file.length();
    }

    public static boolean fileExist(String pathAndFile) {
        File file = new File(pathAndFile);
        if (file.exists())
            return true;
        else
            return false;
    }

    public static void fileRename(String fName, String nName) {
        File file = new File(fName);
        file.renameTo(new File(nName));
        file.delete();
    }

    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return value;
    }

}
