package com.xujun.funapp.beans;

import java.util.List;

/**
 * @author xujun  on 2017/1/3.
 * @email gdutxiaoxu@163.com
 */

public class QsjsDetail {

    public String e_id;
    public String title;
    public String content;
    public String picNo;
    public List<PicUrl> picUrl;

    public static  class PicUrl{
        public String pic_title;
        public String id;
        public String url;
    }


}
