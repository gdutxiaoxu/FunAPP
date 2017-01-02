package com.xujun.funapp.view.main.fragment.home;

import android.view.View;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.MultiYYNewsListAdapter;
import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.beans.YYNews.ShowapiResBodyEntity.PagebeanEntity;
import com.xujun.funapp.common.BaseTopListFragment;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.MultiItemTypeSupport;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.view.main.fragment.news.YYNewsListContract;
import com.xujun.funapp.view.main.fragment.news.YYNewsListPresenter;
import com.xujun.funapp.view.wechat.WechatActivity;
import com.xujun.funapp.widget.ImageButtonWithText;
import com.xujun.mylibrary.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class HomeFragment extends BaseTopListFragment<YYNewsListPresenter> implements
        YYNewsListContract.View, View.OnClickListener {

    List<PagebeanEntity.ContentlistEntity> mData = new ArrayList<>();
    private MultiYYNewsListAdapter mMultiYYNewsListAdapter;
    private String mChannelId = "5572a108b3cdc86cf39001d9";
    private String mChannelName = "科技焦点";

    private View mHeaderView;

    private ImageButtonWithText mWechat;
    private ImageButtonWithText mJoke;
    private ImageButtonWithText mFortune;
    private ImageButtonWithText mPastLife;
    private ImageButtonWithText mSport;
    private ImageButtonWithText mMovie;

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        mMultiYYNewsListAdapter = new MultiYYNewsListAdapter(mContext, mData, new
                MultiItemTypeSupport<PagebeanEntity.ContentlistEntity>() {

            @Override
            public int getItemType(PagebeanEntity.ContentlistEntity contentlistEntity, int
                    position) {
                if (contentlistEntity.havePic) {
                    return MultiYYNewsListAdapter.TYPE_ONE;
                }
                return MultiYYNewsListAdapter.TYPE_TWO;
            }

            @Override
            public int getLayoutId(int itemType) {
                if (itemType == MultiYYNewsListAdapter.TYPE_ONE) {
                    return R.layout.item_yy_news_list_one;
                }
                return R.layout.item_yy_news_list_two;
            }
        });
        initHeadView();

        return mMultiYYNewsListAdapter;
    }

    private void initHeadView() {
        if (mHeaderView == null) {
            mHeaderView = View.inflate(mContext, R.layout.header_view_home, null);
            mWechat = (ImageButtonWithText) mHeaderView.findViewById(R.id.wechat);
            mJoke = (ImageButtonWithText) mHeaderView.findViewById(R.id.joke);
            mFortune = (ImageButtonWithText) mHeaderView.findViewById(R.id.fortune);
            mPastLife = (ImageButtonWithText) mHeaderView.findViewById(R.id.past_life);
            mSport = (ImageButtonWithText) mHeaderView.findViewById(R.id.sport);
            mMovie = (ImageButtonWithText) mHeaderView.findViewById(R.id.movie);
            mWechat.setOnClickListener(this);
            mJoke.setOnClickListener(this);
            mFortune.setOnClickListener(this);
            mPastLife.setOnClickListener(this);
            mSport.setOnClickListener(this);
            mMovie.setOnClickListener(this);
            mMultiYYNewsListAdapter.addHeaderView(mHeaderView);
        }

    }

    @Override
    protected YYNewsListPresenter setPresenter() {
        return new YYNewsListPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
      /*  "channelId": "5572a108b3cdc86cf39001d9",
                "name": "科技焦点"*/


    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
        mPage = 1;
        mPresenter.getNews(mChannelId, mChannelName, mPage, mRows);
    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();
        mPresenter.getNews(mChannelId, mChannelName, ++mPage, mRows);
    }

    @Override
    public void onReceiveNews(YYNews news) {
        List<PagebeanEntity.ContentlistEntity> contentlist = news.showapi_res_body.pagebean
                .contentlist;
        if (false == ListUtils.isEmpty(contentlist)) {
            handleResult(contentlist, BaseTopListFragment.RequestResult.success);
        } else {
            handleResult(contentlist, BaseTopListFragment.RequestResult.empty);
        }
    }

    @Override
    public void onReceiveNewsError(Throwable error) {
        handleResult(null, BaseTopListFragment.RequestResult.error);
        WriteLogUtil.e(" =" + error.getMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat:
                readyGo(WechatActivity.class);
                break;
            default:
                break;
        }

    }
}
