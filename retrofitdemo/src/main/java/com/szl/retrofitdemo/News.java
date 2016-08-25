package com.szl.retrofitdemo;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016-8-25 14:54
 * @ email：gdutxiaoxu@163.com
 */
public class News {



    public int code;
    public String msg;
    /**
     * ctime : 2016-08-25 12:04
     * title : 外媒：阿富汗美国大学遭袭 12人遇难44人受伤
     * description : 搜狐国际
     * picUrl :
     * url : http://news.sohu.com/20160825/n465928226.shtml
     */

    public List<NewslistBean> newslist;

    public static class NewslistBean {
        public String ctime;
        public String title;
        public String description;
        public String picUrl;
        public String url;
    }
}
