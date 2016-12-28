package com.xujun.funapp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.ActivityBaseListBinding;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public abstract class BaseListActivity<P extends BasePresenter> extends
        BaseMVPActivity<ActivityBaseListBinding, P> {

    private RecyclerView mRecyclerView;
    private BGARefreshLayout mBGARefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private boolean  mEnableLoadMore = true;

    protected int mPage=1;

    @Override
    protected void initView(ActivityBaseListBinding bind) {
        mRecyclerView = bind.recyclerView;
        mBGARefreshLayout = bind.BGARefreshLayout;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = getAdapter();
        mRecyclerView.setAdapter(mAdapter);

        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder
                (mContext, true);
        refreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        refreshViewHolder.setSpringDistanceScale(0.2f);
        refreshViewHolder.setLoadingMoreText("正在加载更多");
        mBGARefreshLayout.setRefreshViewHolder(refreshViewHolder);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getFirstPageData();

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getNextPageData();
                return mEnableLoadMore;
            }
        });
    }

    protected void getNextPageData() {
    }

    protected void getFirstPageData() {
        mPage=1;
    }

    public void setEnableLoadMore(Boolean enableLoadMore){
        mEnableLoadMore=enableLoadMore;
    }

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_list;
    }
}
