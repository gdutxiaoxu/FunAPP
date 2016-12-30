package com.xujun.funapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.AnimationUtil;
import com.xujun.funapp.databinding.ActivityBaseListBinding;
import com.xujun.funapp.widget.MutiLayout;
import com.xujun.funapp.widget.MutiLayout.LoadResult;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public abstract class BaseListActivity<P extends BasePresenter> extends
        BaseMVPActivity<ActivityBaseListBinding, P> {

    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private BaseRecyclerAdapter mBaseAdapter;
    private boolean mEnableLoadMore = true;

    protected int mPage = 1;
    private BaseListFragment.RequestResult mRequestResult;
    private MutiLayout mMultiLayout;
    private FloatingActionButton mFloatActionButton;
    private LinearLayoutManager mLinearLayoutManager;

    public static  final String TAG="xujun";

    @Override
    protected void initView(ActivityBaseListBinding bind) {
        mRecyclerView = bind.recyclerView;
        mRefreshLayout = bind.BGARefreshLayout;
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBaseAdapter = getBaseAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);
        mMultiLayout = bind.multiLayout;
        mFloatActionButton = bind.floatActionButton;

        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder
                (mContext, true);
        refreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        refreshViewHolder.setSpringDistanceScale(0.2f);
        refreshViewHolder.setLoadingMoreText("正在加载更多");
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        mFloatActionButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            @Override
            public void onGlobalLayout() {
                mFloatActionButton.setTranslationX(200);
                mFloatActionButton.setVisibility(View.GONE);
                mFloatActionButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        mFloatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 没有动画效果
                // mRecyclerView.scrollToPosition(0);
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
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
        mMultiLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirstPageData();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = mLinearLayoutManager
                        .findFirstVisibleItemPosition();
                if(dy>0){
                    if(firstVisibleItemPosition>=15  && mFloatActionButton.getVisibility()!=View.VISIBLE){
                        AnimationUtil.translateXInt(mFloatActionButton, (int) mFloatActionButton.getTranslationX(),0);
                    }
                }else{
                    if(firstVisibleItemPosition<12&&mFloatActionButton.getVisibility()==View.VISIBLE){
                        AnimationUtil.translateXOut(mFloatActionButton,0,200);
                    }
                }
            }
        });
        mBind.titleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void getNextPageData() {
    }

    protected void getFirstPageData() {
        mPage = 1;
    }

    public void setEnableLoadMore(Boolean enableLoadMore) {
        mEnableLoadMore = enableLoadMore;
    }

    protected abstract BaseRecyclerAdapter getBaseAdapter();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_list;
    }

    protected <V> void handleResult(List<V> data, BaseListFragment.RequestResult requestResult) {
        mRequestResult = requestResult;

        // 请求成功的时候
        if (requestResult == BaseListFragment.RequestResult.success) {
            if (isFirstPage()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.noone);
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
            mMultiLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mBaseAdapter.addDates(data);
        } else if (requestResult == BaseListFragment.RequestResult.error) {

            if (isFirstPage()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                mMultiLayout.setVisibility(View.VISIBLE);
                show(LoadResult.error);
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
                show(LoadResult.empty);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.endLoadingMore();
                //                设置不能加载更多了
                setEnableLoadMore(false);
            }
            mPage--;
        }


    }

    private void show(LoadResult loadResult) {
        mMultiLayout.show(loadResult);
    }

    private boolean isFirstPage() {
        return mPage <= 1;
    }
}
