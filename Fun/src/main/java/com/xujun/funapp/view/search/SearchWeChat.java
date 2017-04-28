package com.xujun.funapp.view.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.databinding.ActivitySearchWeChatBinding;
import com.xujun.funapp.widget.divider.DividerGridItemDecoration;

import java.util.ArrayList;

public class SearchWeChat extends BaseMVPActivity<ActivitySearchWeChatBinding,BasePresenter> {

    private SearchView mSearchView;
    RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
    private SearchAdapter mAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search_we_chat;
    }

    @Override
    protected void initView(ActivitySearchWeChatBinding bind) {
        mSearchView = bind.searchView;
        mRecyclerView=bind.recyclerView;

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                WriteLogUtil.i(TAG," newText="+newText);
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new SearchAdapter(this, mDatas);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
