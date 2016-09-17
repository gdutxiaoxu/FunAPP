package com.xujun.funapp.beans;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 19:42
 * @ email：gdutxiaoxu@163.com
 */
public class PictureClassify {

    /**
     * status : true
     * tngou : [{"description":"性感美女","id":1,"keywords":"性感美女","name":"性感美女","seq":1,
     * "title":"性感美女"},{"description":"韩日美女","id":2,"keywords":"韩日美女","name":"韩日美女","seq":2,
     * "title":"韩日美女"},{"description":"丝袜美腿","id":3,"keywords":"丝袜美腿","name":"丝袜美腿","seq":3,
     * "title":"丝袜美腿"},{"description":"美女照片","id":4,"keywords":"美女照片","name":"美女照片","seq":4,
     * "title":"美女照片"},{"description":"美女写真","id":5,"keywords":"美女写真","name":"美女写真","seq":5,
     * "title":"美女写真"},{"description":"清纯美女","id":6,"keywords":"清纯美女","name":"清纯美女","seq":6,
     * "title":"清纯美女"},{"description":"性感车模","id":7,"keywords":"性感车模","name":"性感车模","seq":7,
     * "title":"性感车模"}]
     */

    public boolean status;
    /**
     * description : 性感美女
     * id : 1
     * keywords : 性感美女
     * name : 性感美女
     * seq : 1
     * title : 性感美女
     */

    public List<TngouBean> tngou;

    public static class TngouBean {
        public String description;
        public int id;
        public String keywords;
        public String name;
        public int seq;
        public String title;
    }
}
