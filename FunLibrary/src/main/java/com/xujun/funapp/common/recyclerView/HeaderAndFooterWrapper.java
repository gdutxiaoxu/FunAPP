package com.xujun.funapp.common.recyclerView;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 2016/7/7 17:29.
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private boolean mIsShowHeader = true;
    private boolean mIsShowFooter = true;
    public static final String TAG = "tag";

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    /***
     * 返回
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return getHeadersCount() + getRealItemCount() + getFootersCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getRealItemCount() - getHeadersCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            BaseRecyclerHolder holder = BaseRecyclerHolder.createViewHolder(mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null) {
            BaseRecyclerHolder holder = BaseRecyclerHolder.createViewHolder( mFootViews.get
                    (viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());

    }




    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils
                .SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager
                    .SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        if (mIsShowFooter) {
            return position < getHeadersCount();
        } else {
            return false;
        }

    }

    private boolean isFooterViewPos(int position) {

        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(getRealHeaderViewCounts() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(getRealFooterViewCounts() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {

        return mIsShowHeader ? getRealHeaderViewCounts() : 0;
    }

    public int getFootersCount() {
        return mIsShowFooter ? getRealFooterViewCounts() : 0;
    }

    public void showHeader(boolean isShowHeader) {
        mIsShowHeader = isShowHeader;
        //        notifyDataSetChanged();
        if (isShowHeader) {
            notifyItemRangeInserted(0, getRealHeaderViewCounts());
        } else {
            notifyItemRangeRemoved(0, getRealHeaderViewCounts());
        }
    }

    private int getRealHeaderViewCounts() {
        return mHeaderViews.size();
    }

    public void showFooter(boolean isShowFooter) {
        mIsShowFooter = isShowFooter;
        //        notifyDataSetChanged();
        Log.i(TAG, "HeaderAndFooterWrapper.class:showFooter(): 146:" + getItemCount());
        if (isShowFooter) {
            notifyItemRangeInserted(getItemCount() - 1, getRealFooterViewCounts());
        } else {
            notifyItemRangeRemoved(getItemCount() - 1, getRealFooterViewCounts());
        }

    }

    private int getRealFooterViewCounts() {
        return mFootViews.size();
    }


}
