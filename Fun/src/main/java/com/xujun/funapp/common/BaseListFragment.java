package com.xujun.funapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.recyclerView.RecyclerScroller;
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

    protected void setOnRefreshListner(OnRefreshListener OnRefreshListener){
       this.mOnRefreshListener=OnRefreshListener;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initView(FragmentBaseListBinding binding) {
        mRecyclerView = binding.recyclerView;
        mRefreshLayout = binding.refreshLayout;
        RecyclerUtils.init(mRecyclerView);


        mAdapter=getAdapter();
        if(mAdapter==null){
            throw new IllegalStateException("You must init recycler adapter first");
        }

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerScroller(mContext));

        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder
                (mContext, true);
        refreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        refreshViewHolder.setSpringDistanceScale(0.2f);
        refreshViewHolder.setLoadingMoreText("正在加载更多");

        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }



    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    protected void initListener() {
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
               if(mOnRefreshListener!=null){
                   mOnRefreshListener.onRefresh(refreshLayout);
               }

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if(mOnRefreshListener!=null){
                    mOnRefreshListener.onLoadMore(refreshLayout);
                }
                return true;
            }
        });
        setOnRefreshListner(this);
    }

    protected void endRefresh(){
        if(isRefresh()){
            mRefreshLayout.endRefreshing();
        }else{
            mRefreshLayout.endLoadingMore();
        }
    }

    @Override
    protected void initData() {
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onRefresh(ViewGroup viewGroup) {

    }

    @Override
    public void onLoadMore(ViewGroup viewGroup) {

    }

    public boolean isRefresh(){
        return mPage<=1;
    }
}
