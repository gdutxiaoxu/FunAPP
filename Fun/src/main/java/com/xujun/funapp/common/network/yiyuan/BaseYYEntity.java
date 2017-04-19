package com.xujun.funapp.common.network.yiyuan;

/**
 * @author xujun  on 2016/12/25.
 * @email gdutxiaoxu@163.com
 */

public class BaseYYEntity {

    public int showapi_res_code;
    public String showapi_res_error;
    public Object showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public String getShowapi_res_body() {
        return showapi_res_body.toString();
    }

    public void setShowapi_res_body(String showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}
