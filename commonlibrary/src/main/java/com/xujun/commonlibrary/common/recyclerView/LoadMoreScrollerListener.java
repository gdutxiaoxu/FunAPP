package com.xujun.commonlibrary.common.recyclerView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by xujun、on 2016/5/16.
 */
public class LoadMoreScrollerListener extends RecyclerView.OnScrollListener {

    private boolean mDown;
    private int[] mPositions;

    public LoadMoreScrollerListener(OnLoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mDown = dy >= 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState != RecyclerView.SCROLL_STATE_IDLE)
            return;
//        LUtils.w("down:" + mDown);
        if (mDown && mLoadingListener != null && isLastItem(recyclerView.getLayoutManager())) {
            mLoadingListener.onLoadMore();
//            Log.w("LoadMoreScrollerListener", "onScrollStateChanged:");
        }
    }

    private boolean isLastItem(RecyclerView.LayoutManager layoutManager) {
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager)
                    layoutManager;
            if (null == mPositions) {
                mPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(mPositions);
            return totalItemCount == getLast(mPositions) + 1;
        } else if (layoutManager instanceof LinearLayoutManager){
            return totalItemCount - 1 == ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        return false;
    }

    private int getLast(int[] mPositions) {
        int last = mPositions[0];
        for (int value : mPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }

    private OnLoadingListener mLoadingListener;

    public interface OnLoadingListener {
        void onLoadMore();
    }
}
