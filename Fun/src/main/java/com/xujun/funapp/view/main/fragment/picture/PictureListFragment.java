package com.xujun.funapp.view.main.fragment.picture;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.xujun.funapp.adapters.PictureListAdapter;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.model.PictureListModel;

import java.util.ArrayList;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListFragment extends BaseListFragment<PictureListPresenter>
        implements PictureListContract.View<PictureListBean> {

    private static final String TITLE = "title";
    private static final String ID = "id";

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
    protected RecyclerView.Adapter getAdapter() {
        mDatas = new ArrayList<>();
        return new PictureListAdapter(mContext, mDatas);
    }

    @Override
    protected PictureListPresenter setPresenter() {
        return new PictureListPresenter(this, tags[mId]);
    }

    @Override
    public void onRefresh(ViewGroup viewGroup) {
        getFirstPageData();
    }

    @Override
    public void onLoadMore(ViewGroup viewGroup) {
        getNextPageData();
    }

    private void getNextPageData() {
        mPresenter.getPictureList(String.valueOf(++mPage), String.valueOf(mRows), mId);
    }

    private void getFirstPageData() {
        mPage = 1;
        mPresenter.getPictureList(String.valueOf(mPage), String.valueOf(mRows), mId);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(PictureListBean o) {
        if (isRefresh()) {
            mDatas.clear();
        }
        mDatas.addAll(o.tngou);
        mAdapter.notifyDataSetChanged();
        endRefresh();

    }

    @Override
    public void onError(Throwable throwable) {
        Logger.i(throwable.getMessage());
        mPage--;
        endRefresh();
    }

    @Override
    public void onLocal(PictureListBean o) {

    }
}
