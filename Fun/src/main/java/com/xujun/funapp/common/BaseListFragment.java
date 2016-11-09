package com.xujun.funapp.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.xujun.commonlibrary.widget.MutiLayout;
import com.xujun.commonlibrary.widget.MutiLayout.LoadResult;
import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.UIUtils;
import com.xujun.funapp.databinding.FragmentBaseListBinding;

import java.util.List;

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


    protected int mPage = 1;
    protected int mRows = 20;
    //记录请求结果的状态，有三种类型，success，error，empty
    protected RequestResult mRequestResult;

    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    //    recyclerView 的Adapter
    private BaseRecyclerAdapter mBaseAdapter;
    //下拉刷新，上拉加载更多的接口
    OnRefreshListener mOnRefreshListener;
    //    加载图片的tag
    protected Object mPictureTag;
    // 集成错误界面，空界面，加载中的界面在一起
    private MutiLayout mMutiLayout;
    //    根布局
    private FrameLayout mFlRoot;
    private FloatingActionButton mMenuItemLinear;
    private FloatingActionButton mMenuItemGrid;
    private FloatingActionButton mMenuItemStrag;

    //记录请求结果的状态，有三种类型，success，error，empty
    public enum RequestResult {
        success, error, empty;
    }

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
        mMenuItemLinear = binding.menuItemLinear;
        mMenuItemGrid = binding.menuItemGrid;
        mMenuItemStrag = binding.menuItemStrag;


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

        mBaseAdapter = getAdapter();
        if (mBaseAdapter == null) {
            throw new IllegalStateException("You must init recycler adapter first");
        }

        mRecyclerView.setAdapter(mBaseAdapter);


        // mRecyclerView.addOnScrollListener(new RecyclerScroller(mContext, mPictureTag));


    }

    protected abstract <V> BaseRecyclerAdapter<V> getAdapter();

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
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.beginRefreshing();
                show(LoadResult.loading);
            }
        });
        MenuItemListener menuItemListener = new MenuItemListener();
        mMenuItemLinear.setOnClickListener(menuItemListener);
        mMenuItemGrid.setOnClickListener(menuItemListener);
        mMenuItemStrag.setOnClickListener(menuItemListener);


    }

    protected <V> void handleResult(List<V> data, RequestResult requestResult) {
        mRequestResult = requestResult;

        // 请求成功的时候
        if (requestResult == RequestResult.success) {
            if (isRefresh()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.noone);
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
            mRecyclerView.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mBaseAdapter.addDates(data);
        } else if (requestResult == RequestResult.error) {

            if (isRefresh()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.error);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
                mBaseAdapter.clearDates();
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.endLoadingMore();
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            }

            mPage--;
        } else {

            if (isRefresh()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.empty);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.endLoadingMore();
            }
            mPage--;
        }


    }

    protected void show(LoadResult loadResult) {
        mMutiLayout.show(loadResult);
    }

    // 这个方法处理下拉刷新后界面的变化，但是并没有处理数据的变化，准备整改删除
    protected void endRefresh(RequestResult requestResult) {
        // 请求成功的时候
        if (requestResult == RequestResult.success) {
            if (isRefresh()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.noone);
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
        } else if (requestResult == RequestResult.error) {
            mPage--;
            if (isRefresh()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.error);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
        } else {
            mPage--;
            if (isRefresh()) {
                /**
                 * 在第一页刷新结束的要隐藏mMultiLayout
                 */
                show(LoadResult.empty);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mRefreshLayout.setVisibility(View.INVISIBLE);
                mRefreshLayout.endRefreshing();
            } else {
                mRefreshLayout.endLoadingMore();
            }
        }

    }

    @Override
    protected void initData() {
        //        显示加载中的界面
        show(LoadResult.loading);
        //        获取第一页数据
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

    //  判断当前是否正在第一页
    protected boolean isRefresh() {
        return mPage <= 1;
    }


    private class  MenuItemListener  implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case  R.id.menu_item_linear:
                    UIUtils.showShortText("menu_item_linear");
                    RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

                    if(layoutManager instanceof LinearLayoutManager &&
                            false==(layoutManager instanceof GridLayoutManager)){
                        break;
                    }
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    RecyclerUtils.init(mRecyclerView);
                    mBaseAdapter.notifyDataSetChanged();
                    break;
                case  R.id.menu_item_grid:
                    UIUtils.showShortText("menu_item_grid");
                    if(mRecyclerView.getLayoutManager() instanceof GridLayoutManager){
                        break;
                    }
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
                    RecyclerUtils.init(mRecyclerView);
                    mBaseAdapter.notifyDataSetChanged();
                    break;
                case  R.id.menu_item_strag:
                    UIUtils.showShortText("menu_item_strag");

                    if(mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                        break;
                    }
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                            LinearLayoutManager.VERTICAL));
                    RecyclerUtils.init(mRecyclerView);
                    mBaseAdapter.notifyDataSetChanged();
                    break;

                default:break;
            }
        }
    }
}
