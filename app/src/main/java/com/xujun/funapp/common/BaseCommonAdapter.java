package com.xujun.funapp.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szl.mobileoa.R;
import com.szl.mobileoa.common.recyclerView.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的通用Adapter，需重写convert(Helper helper, T item)方法
 * Created by Domen、on 2016/5/5.
 */
public abstract class BaseCommonAdapter<T> extends RecyclerView.Adapter {
    private final static int FOOTER = 1;

    protected final Context mContext;
    protected final int mItemLayoutId;
    private int mLoadingFooterId;
    private boolean showFooter;
    protected List<T> mDates;

    protected int mScreenWidth;

    /**
     * @param context      context
     * @param itemLayoutId 布局的layout的Id
     */
    public BaseCommonAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mLoadingFooterId = R.layout.layout_load_more;
        mDates = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= mDates.size())
            return;
        if (holder instanceof BaseRecyclerHolder) {
//            setListener((BaseRecyclerHolder) holder, mDatas.get(position));
            convert((BaseRecyclerHolder) holder, mDates.get(position));
        }
    }

    /**
     * @param helper 自定义的ViewHolder对象，可以getView获取控件
     * @param item   对应的数据
     */
    public abstract void convert(BaseRecyclerHolder helper, T item);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER)
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(mLoadingFooterId,
                    parent, false));
        return new BaseRecyclerHolder(LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false));
    }

    @Override
    public int getItemCount() {
        return showFooter ? mDates.size() + 1 : mDates.size();
    }

    public void showFooter(boolean showFooter) {
        this.showFooter = showFooter;
        if (showFooter)
            notifyItemInserted(mDates.size());
        else
            notifyItemRemoved(mDates.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mDates.size())
            return FOOTER;
        return super.getItemViewType(position);
    }

    /**
     * 设置列表中的数据
     */
    public void setDates(List<T> dates) {
        this.mDates = dates;
        notifyDataSetChanged();
    }

    /**
     * 将单个数据添加到列表中
     */
    public void addSingleDate(T t) {
        this.mDates.add(t);
        notifyItemInserted(mDates.size() - 1);
    }

    /**
     * 将一个List添加到列表中
     */
    public void addDates(List<T> dates) {
        this.mDates.addAll(dates);
        notifyDataSetChanged();
    }

    public void addDates(List<T> dates, int position) {
        if (dates == null || dates.size() == 0)
            return;
        mDates.addAll(position, dates);
        notifyItemRangeInserted(position, dates.size());
    }

    public void removeDatas(List<T> dates, int position) {
        if (dates == null || dates.size() == 0)
            return;
        mDates.removeAll(dates);
        notifyItemRangeRemoved(position, dates.size());
    }

    public T get(int position) {
        return position > -1 && position < mDates.size() ? mDates.get(position) : null;
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
