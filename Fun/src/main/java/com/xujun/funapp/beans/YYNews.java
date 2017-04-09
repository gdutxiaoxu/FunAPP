package com.xujun.funapp.beans;

import java.util.List;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 * <p>
 * 易源新闻
 */

public class YYNews {

    public int ret_code;
    public PagebeanEntity pagebean;

    public static class PagebeanEntity {

        public int allPages;
        public int currentPage;
        public int allNum;
        public int maxResult;
        public List<NewsContentlistEntity> contentlist;


    }
    
}
