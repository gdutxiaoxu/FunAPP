package com.xujun.funapp.common.util;

/**
 * @ explain:
 * @ author：xujun on 2016/10/30 14:50
 * @ email：gdutxiaoxu@163.com
 */
public class StatusCodeUtils {

           /* 400 (Bad Request/错误请求)
            400 (SC_BAD_REQUEST)指出客户端请求中的语法错误。

            401 (Unauthorized/未授权)
            401 (SC_UNAUTHORIZED)表示客户端在授权头信息中没有有效的身份信
            息时访问受到密码保护的页面。这个响应必须包含一个WWW-Authenticate
            的授权信息头。例如，在本书4.5部分中的“Restricting Access to Web Pages./限制访问Web页。”

            403 (Forbidden/禁止)
            403 (SC_FORBIDDEN)的意思是除非拥有授权否则服务器拒绝提供所请求的资源。
            这个状态经常会由于服务器上的损坏文件或目录许可而引起。

            404 (Not Found/未找到)*/

    public String handle(int stateCode) {
        String msg = "";
        String strCode=String.valueOf(stateCode);
        if(strCode.startsWith("4")){
            switch (stateCode) {
                case 400:
                    msg="Bad Request";
                    break;
                case 401:
                    msg="Unauthorized";
                    break;

                case 402:
                    msg="Bad Request";
                    break;

                case 403:
                    msg="Forbidden";
                    break;

                case 404:
                    msg="Page Not Found";
                    break;

            }
        }


        return msg;
    }
}
