package com.xujun.commonlibrary.common.util;

import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 指定算法为MD5的MessageDigest
     */
    private static MessageDigest messageDigest = null;

    /** * 初始化messageDigest的加密算法为MD5 */
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的MD5
     * @param path 文件的绝对路径
     * @return MD5
     */
    public static String getMd5ByFile(String path) {
        String value = null;
        FileInputStream in = null;

        try {
            in = new FileInputStream(path);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            BigInteger bi = new BigInteger(1, messageDigest.digest());
            value = bi.toString(16);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 校验密码与其MD5是否一致
     * @param pwd 密码字符串
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(String pwd, String md5) {
        return getMD5String(pwd).equalsIgnoreCase(md5);
    }

    /**
     * MD5加密字符串
     * @param str 目标字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5String(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        messageDigest.update(str.getBytes());
        BigInteger bi = new BigInteger(1, messageDigest.digest());
        return bi.toString(16);
    }
}
