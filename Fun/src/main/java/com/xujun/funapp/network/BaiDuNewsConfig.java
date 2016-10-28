package com.xujun.funapp.network;

import com.xujun.funapp.beans.NewsClassify;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/28 16:03
 * @ email：gdutxiaoxu@163.com
 */
public class BaiDuNewsConfig {

    static BaiDuNewsConfig mInstance;

    List<NewsClassify> mList;

    public static BaiDuNewsConfig getInstance(){
        if(mInstance==null){
            mInstance=new BaiDuNewsConfig();
        }
        return mInstance;

    }

    public  static final String[] mTitles=new String[]{
            "世界","科技","体育"
    };

    public  static final String[] mTypes=new String[]{
            "world","keji","tiyu"
    };

    public List<NewsClassify> getList(){
        if(mList==null){
            mList=new ArrayList<>();
            for(int i=0;i<mTitles.length;i++){
                NewsClassify newsClassify = new NewsClassify(mTypes[i], mTitles[i]);
                mList.add(newsClassify);
            }

        }
        return  mList;

    }
}
