package com.xujun.funapp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szl.mobileoa.R;
import com.szl.mobileoa.common.recyclerView.BaseRecyclerHolder;
import com.szl.mobileoa.util.LUtils;
import com.szl.mobileoa.widget.LoadingRecycler;

import org.simple.eventbus.EventBus;

/**
 * Created by Domen、on 2016/5/11.
 */
public abstract class BaseListFragment<T> extends Fragment implements LoadingRecycler
        .LoadingListener {

    private LoadingRecycler mRecycler;
    protected BaseCommonAdapter<T> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getItemLayoutRes() == 0) {
            throw new NullPointerException("You must set itemLayoutRes");
        }

        init();
        mAdapter = new BaseCommonAdapter<T>(getActivity(), getItemLayoutRes()) {
            @Override
            public void convert(BaseRecyclerHolder helper, T item) {
                initAdapter(helper, item);
            }
        };
    }

    protected void init() {

    }

    public void setRefreshing() {
        mRecycler.getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                mRecycler.getSwipeRefreshLayout().setRefreshing(true);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        if (mAdapter == null)
            return null;
        mRecycler = (LoadingRecycler) inflater.inflate(R.layout.fragment_base_list, container,
                false);
        mRecycler.setLayoutManager(getLayoutManager());
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLoadingListener(this);
        mRecycler.setOnItemClickListener(new OnItemClickListener(mRecycler.getRecyclerView()) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getItemViewType() == 0)
                    BaseListFragment.this.onItemClick(viewHolder.itemView, viewHolder
                            .getAdapterPosition());
            }
        });
        if (hasEventBus())
            EventBus.getDefault().register(this);
        return mRecycler;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hasEventBus())
            EventBus.getDefault().unregister(this);
    }

    protected void onItemClick(View view, int position) {
        LUtils.e("position:" + position);
    }

    @Override
    public void onRefresh() {
        complete();
    }

    @Override
    public void onLoadMore() {
        complete();
    }

    protected void setLoadMoreEnable(boolean loadMoreEnable) {
        mRecycler.setLoadMoreEnabled(loadMoreEnable);
    }

    protected void setPullEnable(boolean pullEnable) {
        mRecycler.setPullToRefreshEnabled(pullEnable);
    }

    protected void complete() {
        mRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecycler.onComplete();
            }
        }, 500);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected boolean hasEventBus() {
        return false;
    }

    public abstract void initAdapter(BaseRecyclerHolder helper, T item);

    public abstract int getItemLayoutRes();

    public abstract void loadDatas();
}
