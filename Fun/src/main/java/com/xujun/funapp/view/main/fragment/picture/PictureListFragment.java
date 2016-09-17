package com.xujun.funapp.view.main.fragment.picture;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.PictureListAdapter;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.UIUtils;
import com.xujun.funapp.databinding.FragmentPictureListBinding;
import com.xujun.funapp.widget.divider.DividerItemDecoration;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListFragment extends BindingBaseFragment<FragmentPictureListBinding,BasePresenter> {

    private  static  final String TITLE="title";
    private String mTitle="";
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private PictureListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public static PictureListFragment newInstance(String title){
        PictureListFragment pictureListFragment = new PictureListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
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
        if(arguments!=null){
            mTitle = arguments.getString(TITLE);
        }
        mRecyclerView = binding.recyclerView;
        mRefreshLayout = binding.refreshLayout;

        ArrayList<String> mDatas = new ArrayList<>();
        for(int i=0;i<20;i++){
            mDatas.add("图片"+i);
        }
        mAdapter = new PictureListAdapter(mContext, mDatas);
        mLayoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,
                mLayoutManager.getOrientation());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter);

        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder(mContext, true);
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
                UIUtils.showShortText("refresh");
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endRefreshing();
                    }
                },1500);

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                UIUtils.showShortText("loadMore");
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endLoadingMore();
                    }
                },1500);
                return true;
            }
        });
    }

    @Override
    protected void initData() {







    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
