package com.xujun.funapp.common.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016-8-9 15:52
 * @ email：gdutxiaoxu@163.com
 */
public abstract class DefaultAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mDatas;
    protected OnItemClickListener mOnItemClickListener;
    protected OnLongItemClickListener mOnLongItemClickListener;

    public boolean isEmpty() {
        return mDatas == null || mDatas.size() == 0;
    }

    /**
     * 设置列表中的数据
     */
    public void setDatas(List<T> datas) {
        if (datas == null) {
            return;
        }
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 将单个数据添加到列表中
     */
    public void addSingleDate(T t) {
        if (t == null) {
            return;
        }
        this.mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void addDates(List<T> dates, int position) {
        if (dates == null || dates.size() == 0)
            return;
        mDatas.addAll(position, dates);
        notifyItemRangeInserted(position, dates.size());
    }

    public void removeDatas(List<T> dates, int position) {
        if (dates == null || dates.size() == 0)
            return;
        mDatas.removeAll(dates);
        notifyItemRangeRemoved(position, dates.size());
    }

    public void addSingleDate(T t, int position) {
        mDatas.add(position, t);
        notifyItemInserted(position);
        // notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        // notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData(T t) {
        int index = mDatas.indexOf(t);
        notifyItemRemoved(index);
        // notifyItemRangeChanged(index, mDatas.size());
    }

    /**
     * 将一个List添加到列表中
     */
    public void addDates(List<T> dates,boolean isNotify) {
        if (dates == null || dates.size() == 0) {
            return;
        }
//        int oldSize = this.mDatas.size();
//        int newSize = dates.size();
//        this.mDatas.addAll(dates);
//        notifyItemRangeInserted(oldSize, newSize);
        this.mDatas.addAll(dates);
        if(true){
            notifyDataSetChanged();
        }



    }


    public void addDates(List<T> dates) {
      this.addDates(dates,false);


    }

    public void clearDates() {
        if (!isEmpty()) {
            this.mDatas.clear();
        }

    }

    public interface OnItemClickListener<T> {
        void onClick(View view, RecyclerView.ViewHolder holder, T o, int position);

    }

    public interface OnLongItemClickListener<T> {
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, T o, int position);
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按点击事件
     *
     * @param onLongItemClickListener
     */
    public void setonLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.mOnLongItemClickListener = onLongItemClickListener;
    }
}
