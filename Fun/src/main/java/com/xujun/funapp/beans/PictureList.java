package com.xujun.funapp.beans;

import java.util.List;

/**
 * @ explain:图片列表
 * @ author：xujun on 2016/9/18 21:09
 * @ email：gdutxiaoxu@163.com
 */
public class PictureList {


    public boolean status;
    public int total;
    /**
     * count : 3567
     * fcount : 0
     * galleryclass : 1
     * id : 937
     * img : /ext/160902/4710af037ca4f1e00c42e2b65a1fbaee.jpg
     * rcount : 0
     * size : 7
     * time : 1472815816000
     * title : 性感嫩模吊带裙酥胸抢眼撩人
     */

    public List<TngouBean> tngou;

    public static class TngouBean {
        public int count;
        public int fcount;
        public int galleryclass;
        public int id;
        public String img;
        public int rcount;
        public int size;
        public long time;
        public String title;
    }
}
