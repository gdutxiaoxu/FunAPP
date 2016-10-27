package com.xujun.funapp.view.main.fragment.news;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.common.BaseListFragment;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:46
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListFragment extends BaseListFragment<NewsListPresenter>
        implements NewsListContract.View {

    static final String ID="id";


    public static NewsListFragment newInstance(int id){
        NewsListFragment newsListFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID,id);
        newsListFragment.setArguments(bundle);
        return newsListFragment;

    }
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    protected NewsListPresenter setPresenter() {
        return new NewsListPresenter(this);
    }
}
