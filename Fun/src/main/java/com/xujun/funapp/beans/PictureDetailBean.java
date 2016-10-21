package com.xujun.funapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/20 17:14
 * @ email：gdutxiaoxu@163.com
 */
public class PictureDetailBean {

    /**
     * count : 2000
     * fcount : 0
     * galleryclass : 1
     * id : 9
     * img : /ext/150714/b32d42d218ebcdb85d39e93ffff390c3.jpg
     * list : [{"gallery":9,"id":177,"src":"/ext/150714/b32d42d218ebcdb85d39e93ffff390c3.jpg"},
     * {"gallery":9,"id":178,"src":"/ext/150714/48badc38b9800305764882712155b0e3.jpg"},
     * {"gallery":9,"id":179,"src":"/ext/150714/f782657e2093fef9c56092251d71f691.jpg"},
     * {"gallery":9,"id":180,"src":"/ext/150714/107a59ef04b616927546d5543f3395cc.jpg"},
     * {"gallery":9,"id":181,"src":"/ext/150714/7dc0041d65f920dfbd047f994d23fd08.jpg"},
     * {"gallery":9,"id":182,"src":"/ext/150714/d9defc7d44804e18bf3bb3593361e0aa.jpg"},
     * {"gallery":9,"id":183,"src":"/ext/150714/7f74bc4107a5d291020782902fa44de9.jpg"},
     * {"gallery":9,"id":184,"src":"/ext/150714/533aa231bc3a37c2ebb3efb74cddbc7b.jpg"},
     * {"gallery":9,"id":185,"src":"/ext/150714/0333d05d8e12358d57a7a91d207c8dd3.jpg"},
     * {"gallery":9,"id":186,"src":"/ext/150714/f8c10ceb3ddd7e4387ff8fc19d85ae18.jpg"}]
     * rcount : 0
     * size : 10
     * status : true
     * time : 1436876053000
     * title : 办公室女郎的丝袜诱惑
     * url : http://www.tngou.net/tnfs/show/9
     */

    public int count;
    public int fcount;
    public int galleryclass;
    public int id;
    public String img;
    public int rcount;
    public int size;
    public boolean status;
    public long time;
    public String title;
    public String url;
    /**
     * gallery : 9
     * id : 177
     * src : /ext/150714/b32d42d218ebcdb85d39e93ffff390c3.jpg
     */

    public List<ListBean> list;

    public static class ListBean implements Parcelable {
        public int gallery;
        public int id;
        public String src;

        protected ListBean(Parcel in) {
            gallery = in.readInt();
            id = in.readInt();
            src = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel in) {
                return new ListBean(in);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(gallery);
            dest.writeInt(id);
            dest.writeString(src);
        }
    }
}
