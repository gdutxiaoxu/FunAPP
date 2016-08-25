package com.xujun.funapp.common.recyclerView;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 博客地址：http://blog.csdn.net/gdutxiaoxu
 *
 * @author xujun
 * @time 2016/7/7 17:29.
 */
public class FooterWrapper<T> extends DefaultAdapter<T> {

    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private boolean mIsShowFooter = false;

    public static final String TAG = "tag";

    private SparseArrayCompat<ViewGroup> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;
    private View mFoot;

    public FooterWrapper(RecyclerView.Adapter adapter) {
        super();
        mInnerAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterPos(position)) {
            return mFooterViews.keyAt(position - getRealItemCount());
        }

        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterViews.get(viewType) != null) {
            BaseRecyclerHolder holder = BaseRecyclerHolder.createViewHolder(mFoot);
            return holder;

        }
        return (BaseRecyclerHolder) mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        /***
         * 如果是footerView的话直接返回
         */
        if (isFooterPos(position)) {
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position);


    }

    @Override
    public int getItemCount() {
        return getRealItemCount() + getFootViewCounts();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils
                .SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager
                    .SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);

                if (mFooterViews.get(viewType) != null) {
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
        if (isFooterPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isFooterPos(int position) {

        return position >= getRealItemCount();
    }

    public int getFootViewCounts() {
        if (mIsShowFooter) {
            return getRealFooterViewCounts();
        } else {
            return 0;
        }

    }

    private int getRealFooterViewCounts() {
        return mFooterViews.size();
    }

    public void addFootView(View view) {
        if (!(view instanceof ViewGroup)) {
            throw new IllegalStateException("the view must be instanceOf ViewGroup");
        }
        mFoot = view;
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, (ViewGroup) view);
    }



    public void showFooter(boolean isShowFooter) {
        mIsShowFooter = isShowFooter;
        //        notifyDataSetChanged();
        //        if (isShowFooter) {
        //            notifyItemRangeInserted(getRealItemCount(), getRealFooterViewCounts());
        //        } else {
        //            notifyItemRangeRemoved(getRealItemCount(), getRealFooterViewCounts());
        //        }
        if (isShowFooter) {
            notifyItemInserted(getRealItemCount());
        } else {
            notifyItemRemoved(getRealItemCount());
        }
    }

    public void showFooter() {
        mIsShowFooter = true;
        //        notifyDataSetChanged();
        notifyItemRangeInserted(getRealItemCount(), getFootViewCounts());

    }

    public void hideFooterView() {
        mIsShowFooter = false;
        notifyItemRangeRemoved(getRealItemCount(), getFootViewCounts());
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }


}
