package com.xujun.funapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.common.recyclerView.RecyclerUtils;
import com.xujun.funapp.databinding.FragmentBaseListBinding;
import com.xujun.funapp.widget.MutiLayout;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:26
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseListFragment<P extends BasePresenter> extends
        BindingBaseFragment<FragmentBaseListBinding, P> implements onRefreshListener {
    //代表当前是第几页
    protected int mPage = 1;
    protected int mRows = 20;
    //记录请求结果的状态，有三种类型，success，onError，empty
    protected RequestResult mRequestResult;

    protected RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    //    recyclerView 的Adapter
    private BaseRecyclerAdapter mBaseAdapter;
    //下拉刷新，上拉加载更多的接口
    onRefreshListener mOnRefreshListener;
    //    加载图片的tag
    protected Object mPictureTag;

    //    根布局
    private FrameLayout mFlRoot;
    protected FloatingActionButton mMenuItemLinear;
    protected FloatingActionButton mMenuItemGrid;
    protected FloatingActionButton mMenuItemStrag;
    protected FloatingActionMenu mMenu;
    private MutiLayout mMultiLayout;

    protected LayoutMangerType mLayoutMangerType = LayoutMangerType.Linear;
    private Boolean mEnableLoadMore=true;



    protected void setOnRefreshListner(onRefreshListener OnRefreshListener) {
        this.mOnRefreshListener = OnRefreshListener;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initView(FragmentBaseListBinding binding) {

        mRecyclerView = binding.recyclerView;
        mRefreshLayout = binding.refreshLayout;
        mFlRoot = binding.flRoot;
        mMenuItemLinear = binding.menuItemLinear;
        mMenuItemGrid = binding.menuItemGrid;
        mMenuItemStrag = binding.menuItemStrag;
        mMenu = binding.menu;
        mMultiLayout = binding.multiLayout;

        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder
                (mContext, true);
        refreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        refreshViewHolder.setSpringDistanceScale(0.2f);
        refreshViewHolder.setLoadingMoreText("正在加载更多");
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        RecyclerUtils.init(mRecyclerView);
        mPictureTag = this;
        mBaseAdapter = getAdapter();
        if (mBaseAdapter == null) {
            throw new IllegalStateException("You must init recycler adapter first");
        }

        mRecyclerView.setAdapter(mBaseAdapter);
    }

    protected abstract <V> BaseRecyclerAdapter<V> getAdapter();

    @Override
    protected void initListener() {

        mMultiLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefreshLayout.beginRefreshing();
            }
        });
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh(refreshLayout);
                }

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onLoadMore(refreshLayout);
                }
                return mEnableLoadMore;
            }
        });
        setOnRefreshListner(this);


    }

    protected <V> void handleResult(List<V> data, RequestResult requestResult) {
        mRequestResult = requestResult;

        // 请求成功的时候
        if (requestResult == RequestResult.success) {
            if (isFirstPage()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                mBaseAdapter.clearDates();
                show(MutiLayout.LoadResult.noone);
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
            mMultiLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mBaseAdapter.addDates(data);
        } else if (requestResult == RequestResult.error) {

            if (isFirstPage()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                mMultiLayout.setVisibility(View.VISIBLE);
                show(MutiLayout.LoadResult.error);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
                mBaseAdapter.clearDates();
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.endLoadingMore();
                Toast.makeText(mContext, "网络加载错误", Toast.LENGTH_SHORT).show();
            }

            mPage--;
        } else {

            if (isFirstPage()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                mMultiLayout.setVisibility(View.VISIBLE);
                show(MutiLayout.LoadResult.empty);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.endLoadingMore();
                // 设置不能加载更多了
                setEnableLoadMore(false);
            }
            mPage--;
        }


    }
    public void setEnableLoadMore(Boolean enableLoadMore) {
        mEnableLoadMore = enableLoadMore;
    }

    public void show(MutiLayout.LoadResult loadResult) {
        mMultiLayout.show(loadResult);
    }

    @Override
    protected void initData() {
        //        显示加载中的界面
        //        show(LoadResult.loading);

    }

    // 等待界面可见的时候,并且当前页面是第一页的时候，在去加载第一页数据
    @Override
    public void fetchData() {
        super.fetchData();
        if (isFirstPage()) {
            beginRefresh();
        }
    }

    //    开始刷新，获取第一页的数据
    protected void beginRefresh() {
        //    获取第一页数据
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onRefresh(ViewGroup viewGroup) {
        getFirstPageData();

    }

    protected void getFirstPageData() {
        mPage = 1;
    }

    protected void getNextPageData() {
    }

    @Override
    public void onLoadMore(ViewGroup viewGroup) {
        getNextPageData();
    }

    //  判断当前是否正在第一页
    protected boolean isFirstPage() {
        return mPage <= 1;
    }

    protected void closeMenu() {
        if (mMenu.isOpened()) {
            mMenu.close(true);
        }
    }

    public void switchRecyclerAdapter(LayoutMangerType type, BaseRecyclerAdapter adapter) {
        if (mLayoutMangerType == type) {
            return;
        }
        mLayoutMangerType = type;
        this.mBaseAdapter = adapter;
        RecyclerUtils.init(mRecyclerView, type);
        mRecyclerView.setAdapter(adapter);


    }
}
