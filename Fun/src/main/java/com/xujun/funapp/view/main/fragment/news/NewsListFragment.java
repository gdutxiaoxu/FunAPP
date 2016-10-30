package com.xujun.funapp.view.main.fragment.news;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xujun.funapp.adapters.NewsListAdapter;
import com.xujun.funapp.beans.News;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.view.detail.NewsDetailActivity;

import java.util.ArrayList;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:46
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListFragment extends BaseListFragment<NewsListPresenter>
        implements NewsListContract.View {

    static final String type = "id";
    private String mType = "world";
    private ArrayList<News.NewslistBean> mDatas;
    private NewsListAdapter mAdapter;

    public static NewsListFragment newInstance(String type) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NewsListFragment.type, type);
        newsListFragment.setArguments(bundle);
        return newsListFragment;

    }

    @Override
    protected void initAru() {
        super.initAru();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getString(type);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {

                //  将newslistBean传递过去
                News.NewslistBean newslistBean = mDatas.get(position);
                readyGo(NewsDetailActivity.class, newslistBean);
            }
        });

    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
        mPage = 1;
        mPresenter.getNews(mType, mPage, mRows);
    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();
        mPage++;
        mPresenter.getNews(mType, mPage, mRows);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        mDatas = new ArrayList<>();
        mAdapter = new NewsListAdapter(mContext, mDatas, mPictureTag);
        return mAdapter;
    }

    @Override
    protected NewsListPresenter setPresenter() {
        return new NewsListPresenter(this);
    }

    @Override
    public void onReceiveNews(News news) {
        if (false == ListUtils.isEmpty(news.newslist)) {
            handleResult(news.newslist, RequestResult.success);
        } else {
            handleResult(news.newslist, RequestResult.empty);
        }


    }

    @Override
    public void onReceiveNewsError(Throwable error) {
        handleResult(null, RequestResult.error);

    }
}
