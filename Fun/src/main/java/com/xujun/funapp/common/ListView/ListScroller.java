package com.xujun.funapp.common.ListView;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 17:49
 * @ email：gdutxiaoxu@163.com
 */

import android.content.Context;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

/**
 * 滚动监听
 */
public class ListScroller implements AbsListView.OnScrollListener {

    private Context tag;

    public ListScroller(Context tag){
        this.tag=tag;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(tag);
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            //重置
            picasso.resumeTag(tag);
        } else {
            //暂停
            picasso.pauseTag(tag);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}