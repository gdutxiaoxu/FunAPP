package com.xujun.funapp.common.util;

import com.szl.mobileoa.common.Constants;
import com.szl.mobileoa.widget.LockPatternView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LockPatternUtils {

    /**
     * Deserialize a pattern.
     *
     * @param string The pattern serialized with {@link #patternToString}
     * @return The pattern.
     */
    public static List<LockPatternView.Cell> stringToPattern(String string) {
        List<LockPatternView.Cell> result = new ArrayList<>();

        final byte[] bytes = string.getBytes();
        for (byte b : bytes) {
            result.add(LockPatternView.Cell.of(b / 3, b % 3));
        }
        return result;
    }

    /**
     * Serialize a pattern.
     *
     * @param pattern The pattern.
     * @return The pattern in string form.
     */
    public static String patternToString(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return "";
        }
        final int patternSize = pattern.size();

        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return Arrays.toString(res);
    }

    /**
     * 保存手势密码
     *
     * @param pattern
     */
    public static void saveLockPattern(List<LockPatternView.Cell> pattern) {
        SPUtils.put(Constants.KEY_LOCK_PWD, MD5Util.getMD5String(patternToString(pattern)));
    }

    /**
     * 获取手势密码
     *
     * @return
     */
    public static String getLockPatternString() {
        return SPUtils.getString(Constants.KEY_LOCK_PWD);
    }

    public static int checkPattern(List<LockPatternView.Cell> pattern) {
        String stored = getLockPatternString();
        if (!stored.equals("")) {
            return stored.equals(MD5Util.getMD5String(patternToString(pattern))) ? 1 : 0;
        }
        return -1;
    }

    public static void clearLock() {
        saveLockPattern(null);
    }
}
