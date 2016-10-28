package com.xujun.funapp.view.main.fragment.news;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xujun.funapp.beans.News;
import com.xujun.funapp.common.BaseListFragment;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:46
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListFragment extends BaseListFragment<NewsListPresenter>
        implements NewsListContract.View {

    static final String type = "id";
    private String mType="world";

    public static NewsListFragment newInstance(String type) {
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NewsListFragment.type, type);
        newsListFragment.setArguments(bundle);
        return newsListFragment;

    }

    @Override
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if(arguments!=null){
            mType = arguments.getString(type);
        }
    }

    @Override
    public void onRefresh(ViewGroup viewGroup) {
        super.onRefresh(viewGroup);


    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    protected NewsListPresenter setPresenter() {
        return new NewsListPresenter(this);
    }

    @Override
    public void onReceiveNews(News news) {

    }

    @Override
    public void onReceiveNewsError(Throwable error) {

    }
}
