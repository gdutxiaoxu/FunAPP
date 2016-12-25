package com.xujun.funapp.beans;

import java.util.List;

/**
 * @author xujun  on 2016/12/23.
 * @email gdutxiaoxu@163.com
 * <p>
 * 易源新闻
 */

public class YiYuanNews {

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
                 * pubDate : 2016-12-24 21:33:39
                 * havePic : true
                 * title : 泰达退役老将：球队缺死拼气势 没预感会退役
                 * channelName : 国内足球最新
                 * imageurls : [{"height":350,"width":554,"url":"http://img1.gtimg
                 * .com/sports/pics/hv1/189/218/2171/141225054.jpg"}]
                 * desc : 当泰达队在澳大利亚的阳光下苦练体能的时候，已经正式告别球队的宗磊在忙什么呢？面对记者的询问，这位已经35
                 * 岁的老将笑着表示：“我还没有找到退役的感觉呢，每天接送孩子之外，还要忙着健身、苦练英语和电脑，每天过得实在是太充实了。”退役球员宗磊踢球的兴奋点没了周三，当记者敲开宗磊家大门的时候，发现一家三口都
                 * source : 国内足球新闻
                 * channelId : 5572a10ab3cdc86cf39001e8
                 * link : http://sports.qq.com/a/20161224/020641.htm
                 */

                public String pubDate;
                public boolean havePic;
                public String title;
                public String channelName;
                public String desc;
                public String source;
                public String channelId;
                public String link;
                public List<ImageurlsEntity> imageurls;

                public static class ImageurlsEntity {
                    /**
                     * height : 350
                     * width : 554
                     * url : http://img1.gtimg.com/sports/pics/hv1/189/218/2171/141225054.jpg
                     */

                    public int height;
                    public int width;
                    public String url;
                }
            }
        }
    }
}
