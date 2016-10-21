package com.xujun.funapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @ explain:图片列表
 * @ author：xujun on 2016/9/18 21:09
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListBean implements Parcelable {


    public boolean status;
    public int total;

    /**
     * 作为区分是哪一个PictureListFragment
     */
    public String tag;
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

    protected PictureListBean(Parcel in) {
        status = in.readByte() != 0;
        total = in.readInt();
        tag = in.readString();
    }

    public static final Creator<PictureListBean> CREATOR = new Creator<PictureListBean>() {
        @Override
        public PictureListBean createFromParcel(Parcel in) {
            return new PictureListBean(in);
        }

        @Override
        public PictureListBean[] newArray(int size) {
            return new PictureListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeInt(total);
        dest.writeString(tag);
    }

    public static class TngouBean  implements Parcelable {
        public int count;
        public int fcount;
        public int galleryclass;
        public int id;
        public String img;
        public int rcount;
        public int size;
        public long time;
        public String title;

        protected TngouBean(Parcel in) {
            count = in.readInt();
            fcount = in.readInt();
            galleryclass = in.readInt();
            id = in.readInt();
            img = in.readString();
            rcount = in.readInt();
            size = in.readInt();
            time = in.readLong();
            title = in.readString();
        }

        public static final Creator<TngouBean> CREATOR = new Creator<TngouBean>() {
            @Override
            public TngouBean createFromParcel(Parcel in) {
                return new TngouBean(in);
            }

            @Override
            public TngouBean[] newArray(int size) {
                return new TngouBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(count);
            dest.writeInt(fcount);
            dest.writeInt(galleryclass);
            dest.writeInt(id);
            dest.writeString(img);
            dest.writeInt(rcount);
            dest.writeInt(size);
            dest.writeLong(time);
            dest.writeString(title);
        }
    }
}
