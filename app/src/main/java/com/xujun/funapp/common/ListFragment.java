package com.xujun.funapp.common;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.szl.mobileoa.R;
import com.szl.mobileoa.common.mvp.BasePresenter;
import com.szl.mobileoa.common.recyclerView.BaseRecyclerAdapter;
import com.szl.mobileoa.common.recyclerView.EmptyWrapper;
import com.szl.mobileoa.common.recyclerView.EndlessRecyclerOnScrollListener;
import com.szl.mobileoa.common.recyclerView.LoadMoreWrapper;
import com.szl.mobileoa.common.recyclerView.OnRefreshListener;
import com.szl.mobileoa.databinding.FragmentListBinding;

import java.util.List;

/**
 * @ explain:带下拉刷新，加载更多的fragment，支持不同的LayoutManger
 * 还存在一些bug，带修复
 * @ author：xujun on 2016-8-9 11:33
 * @ email：gdutxiaoxu@163.com
 */
public abstract class ListFragment<P extends BasePresenter, T> extends
        BindingBaseFragment<FragmentListBinding, P> {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EndlessRecyclerOnScrollListener mScrollListener;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;

    OnRefreshListener mOnRefreshListener;
    private RefreshState mCurRefreshSate = RefreshState.NORMAL;

    LoadMoreWrapper<T> mLoadMoreWrapper;

    private long minTime = 3000;
    private long startTime = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(FragmentListBinding binding) {
        mRecyclerView = binding.recyclerView;
        mSwipeRefreshLayout = binding.swipeRefreshLayout;
        final RecyclerView.LayoutManager layoutManager = getDefaultLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);

        // 初始化各个监听器
        mScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {

                loadMore();
            }
        };

        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCurRefreshSate == RefreshState.NORMAL) {
                    refresh();

                }
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener);
        mRecyclerView.addOnScrollListener(mScrollListener);

    }

    private void refresh() {
        mCurRefreshSate = RefreshState.Refresh;
        mLoadMoreWrapper.setEnableLoadMore(false);
        if (mOnRefreshListener != null) {
            startTime = System.currentTimeMillis();
            mOnRefreshListener.onRefresh(mRecyclerView);

        }
    }

    private void loadMore() {
        if (mCurRefreshSate == RefreshState.NORMAL) {
            mCurRefreshSate = RefreshState.loadMore;
            mSwipeRefreshLayout.setEnabled(false);
            mLoadMoreWrapper.setEnableLoadMore(true);
            mLoadMoreWrapper.showLoadMore(true);


            if (mOnRefreshListener != null) {

                startTime = System.currentTimeMillis();
                mOnRefreshListener.onLoadMore(mRecyclerView);

            }


        }
    }

    /**
     * 重写这个方法可以支持不同的LayoutManger
     *
     * @return
     */
    @NonNull
    protected RecyclerView.LayoutManager getDefaultLayoutManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    }

    public void setLayoutManger(RecyclerView.LayoutManager layoutManger) {
        mRecyclerView.setLayoutManager(layoutManger);
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        mLoadMoreWrapper.notifyDataSetChanged();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof LoadMoreWrapper)) {
            mLoadMoreWrapper = new LoadMoreWrapper<>(adapter);
            mLoadMoreWrapper.setLoadMoreView(R.layout.item_load_more);

        } else {
            mLoadMoreWrapper = (LoadMoreWrapper<T>) adapter;
        }


        mRecyclerView.setAdapter(mLoadMoreWrapper);

    }

    public <V> void setData(List<V> datas) {
        RecyclerView.Adapter adapter = mLoadMoreWrapper.getInnerAdapter();
        if (adapter instanceof BaseRecyclerAdapter) {
            ((BaseRecyclerAdapter) adapter).setDatas(datas);
        }

        notifyDataChanged();
    }

    public <V> void addData(List<V> datas) {
        RecyclerView.Adapter adapter = mLoadMoreWrapper.getInnerAdapter();
        if (adapter instanceof BaseRecyclerAdapter) {
            ((BaseRecyclerAdapter) adapter).addDates(datas);
        }
        mLoadMoreWrapper.notifyDataSetChanged();

    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public enum RefreshState {

        NORMAL(1),

        Refresh(2),

        loadMore(3);

        private int value;

        RefreshState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public void complete() {
        long distance = startTime - System.currentTimeMillis();
        if (distance < minTime) {
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setComplete();
                }
            }, minTime - distance);
        } else {
            setComplete();
        }


    }

    private void setComplete() {
        if (mCurRefreshSate == RefreshState.Refresh) {
            mLoadMoreWrapper.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        } else if (mCurRefreshSate == RefreshState.loadMore) {
            mSwipeRefreshLayout.setEnabled(true);
        }
        //        mLoadMoreWrapper.showLoadMore(false);
        mCurRefreshSate = RefreshState.NORMAL;
    }

    protected void showEmpty() {
        showEmpty(R.layout.loadpage_empty);
    }

    public void showEmpty(int layoutId) {
        EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(mLoadMoreWrapper);
        emptyWrapper.setEmptyView(layoutId);
        mRecyclerView.setAdapter(emptyWrapper);

    }


}
