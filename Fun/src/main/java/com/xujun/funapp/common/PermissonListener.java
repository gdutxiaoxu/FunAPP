package com.xujun.funapp.common;

import java.util.List;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public interface PermissonListener {

    void onGranted();

    void onDenied(List<String> permisons);
}
