package com.xujun.funapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @ explain:百度新闻api返回的字段
 * @ author：xujun on 2016/10/28 15:20
 * @ email：gdutxiaoxu@163.com
 */
public class News {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-10-28 12:24","title":"齐布娃两盘全胜哈勒普 携手科贝尔进总决赛四强",
     * "description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p0.ifengimg
     * .com/cmpp/2016/10/28/0854e4ec0c5233e40dd9e14081f1716a_size334_w500_h350.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50170284_0.shtml"},{"ctime":"2016-10-28 12:00",
     * "title":"五大新秀出战中国拳王争霸赛 谁说赢家不会是他们","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p3.ifengimg.com/a/2016_44/ab53b8f13baac02_size27_w570_h196.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169636_0.shtml"},{"ctime":"2016-10-28 10:23",
     * "title":"中国短道速滑主教练：世界格局改变 对手不只有韩国","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p2.ifengimg.com/a/2016_44/b22c0253e262402_size14_w491_h280.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169683_0.shtml"},{"ctime":"2016-10-28 12:00",
     * "title":"女排联赛揭幕战上海硬碰江苏 鹰眼将成新焦点","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p1.ifengimg.com/a/2016_44/3179d659f7dbcfc_size61_w640_h412.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169761_0.shtml"},{"ctime":"2016-10-28 12:00",
     * "title":"女排热？04雅典夺冠联赛也没火 球员腰包如何鼓起来","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p0.ifengimg.com/a/2016_44/e66d54212a4dfcb_size229_w640_h426.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169801_0.shtml"},{"ctime":"2016-10-28 10:27",
     * "title":"羽联排名：谌龙次席林丹第3 混双小将首进前十","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p0.ifengimg.com/a/2016_44/f88bab09a358963_size39_w569_h380.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169821_0.shtml"},{"ctime":"2016-10-28 10:00",
     * "title":"郭川自述：不要被安逸生活所困 执着的人是幸福的","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p2.ifengimg
     * .com/cmpp/2016/10/28/ef59a0606713d8f7d76e58773a6de869_size317_w500_h350.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169384_0.shtml"},{"ctime":"2016-10-28 10:00",
     * "title":"张继科爬上高墙避\u201c追捕\u201d 粉丝大喊：科科别想不开","description":"凤凰体育","picUrl":"http://d
     * .ifengimg.com/w145_h103/p1.ifengimg.com/a/2016_44/3c95a566749dfb1_size75_w640_h708.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169461_0.shtml"},{"ctime":"2016-10-28 09:07",
     * "title":"3名伦敦奥运冠军涉药金牌被剥夺 2人原籍中国","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p3.ifengimg.com/a/2016_44/e1955abbc600939_size78_w900_h451.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50169490_0.shtml"},{"ctime":"2016-10-28 07:25",
     * "title":"专业帆船手：单人航行必须系保险绳 盼奇迹出现","description":"凤凰体育","picUrl":"http://d.ifengimg
     * .com/w145_h103/p0.ifengimg.com/a/2016_44/a7f076ce082bf3f_size17_w387_h241.jpg",
     * "url":"http://sports.ifeng.com/a/20161028/50168476_0.shtml"}]
     */

    public int code;
    public String msg;
    /**
     * ctime : 2016-10-28 12:24
     * title : 齐布娃两盘全胜哈勒普 携手科贝尔进总决赛四强
     * description : 凤凰体育
     * picUrl : http://d.ifengimg.com/w145_h103/p0.ifengimg
     * .com/cmpp/2016/10/28/0854e4ec0c5233e40dd9e14081f1716a_size334_w500_h350.jpg
     * url : http://sports.ifeng.com/a/20161028/50170284_0.shtml
     */

    public List<NewslistBean> newslist;

    public static class NewslistBean implements Parcelable {
        public String ctime;
        public String title;
        public String description;
        public String picUrl;
        public String url;

        protected NewslistBean(Parcel in) {
            ctime = in.readString();
            title = in.readString();
            description = in.readString();
            picUrl = in.readString();
            url = in.readString();
        }

        public static final Creator<NewslistBean> CREATOR = new Creator<NewslistBean>() {
            @Override
            public NewslistBean createFromParcel(Parcel in) {
                return new NewslistBean(in);
            }

            @Override
            public NewslistBean[] newArray(int size) {
                return new NewslistBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ctime);
            dest.writeString(title);
            dest.writeString(description);
            dest.writeString(picUrl);
            dest.writeString(url);
        }
    }
}
