package com.xujun.funapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xujun.commonlibrary.widget.MutiLayout;
import com.xujun.commonlibrary.widget.MutiLayout.LoadResult;
import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.recyclerView.RecyclerScroller;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.databinding.FragmentBaseListBinding;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 19:26
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseListFragment<P extends BasePresenter>
        extends BindingBaseFragment<FragmentBaseListBinding, P>
        implements OnRefreshListener {

    protected int mId = 1;
    protected int mPage = 1;
    protected int mRows = 20;

    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView.Adapter mAdapter;

    OnRefreshListener mOnRefreshListener;
    //    加载图片的tag
    protected Object mPictureTag;
    // 集成错误界面，空界面，加载中的界面在一起
    private MutiLayout mMutiLayout;
    private FrameLayout mFlRoot;

    protected void setOnRefreshListner(OnRefreshListener OnRefreshListener) {
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
        mMutiLayout = new MutiLayout(mContext);

        mFlRoot.addView(mMutiLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder
                (mContext, true);
        refreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        refreshViewHolder.setSpringDistanceScale(0.2f);
        refreshViewHolder.setLoadingMoreText("正在加载更多");

        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        RecyclerUtils.init(mRecyclerView);
        mPictureTag = this;

        mAdapter = getAdapter();
        if (mAdapter == null) {
            throw new IllegalStateException("You must init recycler adapter first");
        }

        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnScrollListener(new RecyclerScroller(mContext, mPictureTag));


    }

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    protected void initListener() {
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
                return true;
            }
        });
        setOnRefreshListner(this);

        /**
         * 当出现错误界面的时候，点击按钮，会调用下拉刷新，即重新获取第一页的数据
         */

        mMutiLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRefreshLayout.beginRefreshing();
            }
        });
    }

    protected void showError() {
        show(LoadResult.error);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mRefreshLayout.setVisibility(View.INVISIBLE);
    }

    protected void show(LoadResult loadResult) {
        mMutiLayout.show(loadResult);
    }

    protected void endRefresh() {
        if (isRefresh()) {
            /**
             * 在第一页刷新结束的要隐藏mMultiLayout
             */
            show(LoadResult.noone);
            mRefreshLayout.endRefreshing();
        } else {
            mRefreshLayout.endLoadingMore();
        }
    }

    @Override
    public void fetchData() {
        super.fetchData();

    }

    @Override
    protected void initData() {
        WriteLogUtil.i("fetchData()");
        show(LoadResult.loading);
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onRefresh(ViewGroup viewGroup) {
        getFirstPageData();

    }

    protected void getFirstPageData() {
    }

    protected void getNextPageData() {
    }

    @Override
    public void onLoadMore(ViewGroup viewGroup) {
        getNextPageData();
    }

    protected boolean isRefresh() {
        return mPage <= 1;
    }
}
