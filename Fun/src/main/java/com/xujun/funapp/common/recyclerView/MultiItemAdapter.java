package com.xujun.funapp.common.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author xujun  on 2016/12/29.
 * @email gdutxiaoxu@163.com
 */

public abstract class MultiItemAdapter<T> extends BaseRecyclerAdapter<T> {

    protected MultiItemTypeSupport<T> mTMultiItemTypeSupport;


    public MultiItemAdapter(Context context, List<T> datas, MultiItemTypeSupport<T>
            multiItemTypeSupport) {
        super(context, -1, datas);
        mTMultiItemTypeSupport = multiItemTypeSupport;

    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return getHeaderTypeFromPosition(position);
        }
        position=position-getHeaderViewCounts();
        T t = mDatas.get(position);
        int itemType = mTMultiItemTypeSupport.getItemType(t, position);
        return itemType;
    }



    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getHeaderViewCounts() > 0 && isHeaderType(viewType)) {
            int headerPosition = getHeaderPositionFromType(viewType);
            View view = mHeaderView.get(headerPosition);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            BaseRecyclerHolder holder = new BaseRecyclerHolder(view, mContext);
            return holder;

        }
        int layoutId = mTMultiItemTypeSupport.getLayoutId(viewType);
        BaseRecyclerHolder viewHolder = BaseRecyclerHolder.createViewHolder(mContext, parent,
                layoutId);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }
}
