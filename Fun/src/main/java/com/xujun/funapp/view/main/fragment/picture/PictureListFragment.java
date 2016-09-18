package com.xujun.funapp.view.main.fragment.picture;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.R;
import com.xujun.funapp.adapters.PictureListAdapter;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.recyclerView.RecyclerScroller;
import com.xujun.funapp.databinding.FragmentPictureListBinding;
import com.xujun.funapp.model.PictureListModel;
import com.xujun.funapp.widget.divider.DividerItemDecoration;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListFragment extends BindingBaseFragment<FragmentPictureListBinding,
        PictureListPresenter> implements PictureListContract.View<PictureListBean> {

    private static final String TITLE = "title";
    private static final String ID = "id";
    private String mTitle = "";
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private PictureListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mId = 1;
    private int mPage = 1;
    private int mRows = 20;

    private static String[] tags = PictureListModel.tags;
    private ArrayList<PictureListBean.TngouBean> mDatas;

    public static PictureListFragment newInstance(String title, int id) {
        PictureListFragment pictureListFragment = new PictureListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(ID, id);
        pictureListFragment.setArguments(bundle);
        return pictureListFragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_picture_list;
    }

    @Override
    protected void initView(FragmentPictureListBinding binding) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(TITLE);
            mId = arguments.getInt(ID);
        }
        mRecyclerView = binding.recyclerView;
        mRefreshLayout = binding.refreshLayout;

        mDatas = new ArrayList<>();
        mAdapter = new PictureListAdapter(mContext, mDatas);
        mLayoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,
                mLayoutManager.getOrientation());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
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

    @Override
    protected void initListener() {
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getFirstPageData();

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getNextPageData();
                return true;
            }
        });
    }

    private void getNextPageData() {
        mPresenter.getPictureList(String.valueOf(++mPage), String.valueOf(mRows), mId);
    }

    private void getFirstPageData() {
        mPage = 1;
        mPresenter.getPictureList(String.valueOf(mPage), String.valueOf(mRows), mId);
    }

    @Override
    protected void initData() {
        mRefreshLayout.beginRefreshing();


    }

    @Override
    protected PictureListPresenter setPresenter() {
        return new PictureListPresenter(this, tags[mId]);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(PictureListBean o) {
        mDatas.addAll(o.tngou);
        mAdapter.notifyDataSetChanged();
        refreshStop();

    }

    private void refreshStop() {
        if(mPage<=1){
            mRefreshLayout.endRefreshing();
        }else{
            mRefreshLayout.endLoadingMore();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Logger.i(throwable.getMessage());
        refreshStop();
    }

    @Override
    public void onLocal(PictureListBean o) {

    }
}
