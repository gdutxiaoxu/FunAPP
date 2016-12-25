package com.xujun.funapp.common.util;

import com.xujun.mylibrary.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.xujun.funapp.common.util.UIUtils.getResources;

/**
 * @author xujun  on 2016/12/24.
 * @email gdutxiaoxu@163.com
 */

public class ResourceUtils {

    public static String getFromAssets(String fileName) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader inputReader=null;
        try {
            inputReader  = new InputStreamReader(getResources().getAssets().open
                    (fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) sb.append(line);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(inputReader);
        }
        return sb.toString();
    }
}
