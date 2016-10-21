package com.xujun.funapp.common.recyclerView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 17:49
 * @ email：gdutxiaoxu@163.com
 */

public class RecyclerScroller extends RecyclerView.OnScrollListener {

    private Object tag;
    Context mContext;

    public RecyclerScroller(Context context, Object tag){
        this.tag=tag;
        this.mContext=context;

    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final  Picasso picasso=Picasso.with(mContext);
        if (newState == RecyclerView.SCROLL_STATE_IDLE)
        {
            Picasso.with(mContext).resumeTag(tag);
        }
        else
        {
            Picasso.with(mContext).pauseTag(tag);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}