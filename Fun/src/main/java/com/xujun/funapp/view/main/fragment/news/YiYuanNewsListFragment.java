package com.xujun.funapp.view.main.fragment.news;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.YYNewsListAdapter;
import com.xujun.funapp.beans.YiYuanNews;
import com.xujun.funapp.beans.YiYuanNews.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.common.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:46
 * @ email：gdutxiaoxu@163.com
 */
public class YiYuanNewsListFragment extends BaseListFragment<YiYuanNewsListPresenter> implements
        YiYuanNewsListContract.View {

    static final String KEY = "id";
    static final String KEY_POSITION = "KEY_POSITION";
    private YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity mChannelListEntity = null;
    private ArrayList<ContentlistEntity> mDatas;
    private YYNewsListAdapter mAdapter;
    //    保存上一次的可见的第一个位置
    private int mLastPosition;
    private int mItemPosition = -1;
    private String mChannelId;
    private String mName;

    public static YiYuanNewsListFragment newInstance(Parcelable parcelable, int postion) {
        YiYuanNewsListFragment newsListFragment = new YiYuanNewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(YiYuanNewsListFragment.KEY, parcelable);
        bundle.putInt(YiYuanNewsListFragment.KEY_POSITION, postion);
        newsListFragment.setArguments(bundle);
        return newsListFragment;

    }

    //    判断是否是第一个Item
    protected boolean isFirstItem() {
        return mItemPosition == 0;
    }

    @Override
    protected void initAru() {
        super.initAru();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mChannelListEntity = arguments.getParcelable(KEY);
            mItemPosition = arguments.getInt(KEY_POSITION);
            mChannelId = mChannelListEntity.channelId;
            mName = mChannelListEntity.name;

        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {

              /*  //  将newslistBean传递过去
                TxNews.NewslistBean newslistBean = mDatas.get(position);
                readyGo(NewsDetailActivity.class, newslistBean);*/
            }
        });

        MenuItemListener menuItemListener = new MenuItemListener();
        mMenuItemLinear.setOnClickListener(menuItemListener);
        mMenuItemGrid.setOnClickListener(menuItemListener);
        mMenuItemStrag.setOnClickListener(menuItemListener);

    }

    @Override
    protected void initData() {
        super.initData();
        //  如果是第0个Item，初始化的时候主动去刷新，不是第0个Item，等到界面的时候会调用fetech方法手动去刷新
        if (isFirstItem()) {
            beginRefresh();
        }
    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
        mPage = 1;
        mPresenter.getNews(mChannelId, mName, mPage, mRows);
    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();
        mPage++;
        mPresenter.getNews(mChannelId, mName, mPage, mRows);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        mDatas = new ArrayList<>();
        mAdapter = new YYNewsListAdapter(mContext, mDatas, mPictureTag, LayoutMangerType.Linear);
        return mAdapter;
    }

    @Override
    protected YiYuanNewsListPresenter setPresenter() {
        return new YiYuanNewsListPresenter(this);
    }

    @Override
    public void onReceiveNews(YiYuanNews news) {
        List<ContentlistEntity> contentlist = news.showapi_res_body.pagebean.contentlist;
        if (false == ListUtils.isEmpty(contentlist)) {
            handleResult(contentlist, RequestResult.success);
        } else {
            handleResult(contentlist, RequestResult.empty);
        }
    }

    @Override
    public void onReceiveNewsError(Throwable error) {
        handleResult(null, RequestResult.error);

    }

    private class MenuItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.menu_item_linear:

                    closeMenu();
                    break;
                case R.id.menu_item_grid:

                    closeMenu();
                    break;
                case R.id.menu_item_strag:

                    closeMenu();
                    break;

                default:
                    break;
            }
        }
    }


}
