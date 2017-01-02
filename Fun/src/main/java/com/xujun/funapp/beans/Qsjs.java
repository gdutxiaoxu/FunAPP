package com.xujun.funapp.beans;

/**
 * @author xujun  on 2017/1/2.
 * @email gdutxiaoxu@163.com
 */

public class Qsjs {

    /**
     * _id : 18620102
     * title : 中华民国第一任总理唐绍仪诞生
     * pic : http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201001/3/A023309923.jpg
     * year : 1862
     * month : 1
     * day : 2
     * des : 在155年前的今天，1862年1月2日 (农历腊月初三)，中华民国第一任总理唐绍仪诞生。
     * lunar : 辛酉年腊月初三
     */

    public String _id;
    public String title;
    public String pic;
    public int year;
    public int month;
    public int day;
    public String des;
    public String lunar;

    @Override
    public String toString() {
        return "Qsjs{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", des='" + des + '\'' +
                ", lunar='" + lunar + '\'' +
                '}';
    }
}
