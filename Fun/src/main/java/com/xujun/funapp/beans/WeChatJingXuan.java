package com.xujun.funapp.beans;

import java.util.List;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WeChatJingXuan {


    public int showapi_res_code;
    public String showapi_res_error;
    public ShowapiResBodyEntity showapi_res_body;

    public static class ShowapiResBodyEntity {


        public int ret_code;
        public PagebeanEntity pagebean;

        public static class PagebeanEntity {

            public int allPages;
            public int currentPage;
            public int allNum;
            public int maxResult;
            public List<ContentlistEntity> contentlist;

            public static class ContentlistEntity {
                /**
                 * date : 2016-12-29 06:27:12
                 * weixinNum : kongfuf
                 * url : http://mp.weixin.qq
                 * .com/s?__biz=MzIzOTA3NTA5Mg==&mid=2652437780&idx=1&sn
                 * =01e5a21d1b0137e0b7aeff210c6bd358&chksm
                 * =f2c3f76fc5b47e7998154d1762bb414a750ba65b8fe9d75e39cf8af75873dfeeaff790392bbd#rd
                 * ct : 2016-12-29 11:30:36.756
                 * id : 5864835c6e36a8dec72c2e05
                 * typeName : 推荐
                 * title : 时寒冰：德国黄金飞速“回家”，欧元区解体来临？
                 * contentImg : http://mmbiz.qpic
                 * .cn/mmbiz_png/KXUjtX5DukVARcubolvRF9SOWm5x2wtCEuksoib7MIuxMw9Q3EC4QDibLQggbFN6me7NwolTib0C6xFZWZoxSWBgg/0?wx_fmt=png
                 * userLogo : http://mmbiz.qpic.cn/mmbiz_png/KXUjtX5DukX2xl4V7eycOxrywhMoER09ckxdEPdbrlTTp0NTmK0o5EWrQBAnU2TXodT0BVW5H5AYLfKXhn47yg/0?wx_fmt=png
                 * userName : 功夫财经
                 * read_num : 64369
                 * like_num : 935
                 * typeId : 1
                 * userLogo_code : http://open.weixin.qq.com/qr/code/?username=kongfuf
                 */

                public String date;
                public String weixinNum;
                public String url;
                public String ct;
                public String id;
                public String typeName;
                public String title;
                public String contentImg;
                public String userLogo;
                public String userName;
                public int read_num;
                public int like_num;
                public String typeId;
                public String userLogo_code;
            }
        }
    }
}
