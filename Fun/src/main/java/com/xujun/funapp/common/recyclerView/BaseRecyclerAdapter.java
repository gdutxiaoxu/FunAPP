package com.xujun.funapp.common.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/5/13 15:45
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected Context mContext;
    protected  int mItemLayoutId;
    protected List<T> mDatas;
    protected BaseRecyclerAdapter.OnItemClickListener mOnItemClickListener;
    protected BaseRecyclerAdapter.OnLongItemClickListener mOnLongItemClickListener;

    public boolean isEmpty() {
        return mDatas == null || mDatas.size() == 0;
    }

    public BaseRecyclerAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = new ArrayList<>();
    }

    public BaseRecyclerAdapter(Context context, int itemLayoutId, List<T> datas) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = datas;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        BaseRecyclerHolder holder = new BaseRecyclerHolder(LayoutInflater.from
                (mContext).
                inflate(mItemLayoutId, parent, false));
        setListener(parent, holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerHolder holder, int position) {
        BaseRecyclerHolder baseHolder = (BaseRecyclerHolder) holder;

        convert(baseHolder, (T) mDatas.get(position), position);
    }

    /**
     * @param holder   自定义的ViewHolder对象，可以getView获取控件
     * @param item     对应的数据
     * @param position
     */
    public abstract void convert(BaseRecyclerHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return isEmpty() ? 0 : mDatas.size();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    public void setClickListener(BaseRecyclerHolder holder, int id, View.OnClickListener
            onClickListener) {
        holder.getView(id).setOnClickListener(onClickListener);
    }

    protected void setListener(final ViewGroup parent, final BaseRecyclerHolder viewHolder, int
            viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //这个方法是获取在holder里面真正的位置，而不是对应list的位置
                    int position = viewHolder.getAdapterPosition();
                    T t = mDatas.get(position);
                    mOnItemClickListener.onClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnLongItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });

    }

    public interface OnItemClickListener {
        void onClick(View view, RecyclerView.ViewHolder holder, int position);

    }

    public interface OnLongItemClickListener {
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener
                                               onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按点击事件
     *
     * @param onLongItemClickListener
     */
    public void setonLongItemClickListener(BaseRecyclerAdapter.OnLongItemClickListener
                                                   onLongItemClickListener) {
        this.mOnLongItemClickListener = onLongItemClickListener;
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
    public void addDates(List<T> dates, boolean isNotify) {
        if (dates == null || dates.size() == 0) {
            return;
        }
        //        int oldSize = this.mDatas.size();
        //        int newSize = dates.size();
        //        this.mDatas.addAll(dates);
        //        notifyItemRangeInserted(oldSize, newSize);
        this.mDatas.addAll(dates);
        if (true) {
            notifyDataSetChanged();
        }


    }

    public void addDates(List<T> dates) {
        this.addDates(dates, false);
        notifyDataSetChanged();


    }

    public void clearDates() {
        if (!isEmpty()) {
            this.mDatas.clear();
            notifyDataSetChanged();
        }

    }


}

