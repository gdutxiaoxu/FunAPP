package com.xujun.funapp.view.main.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.MultiYYNewsListAdapter;
import com.xujun.funapp.beans.NewsContentlistEntity;
import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.common.BaseTopListFragment;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.RequestResult;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.recyclerView.MultiItemTypeSupport;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.view.detail.WebViewActivity;
import com.xujun.funapp.view.main.fragment.news.YYNewsListContract;
import com.xujun.funapp.view.main.fragment.news.YYNewsListPresenter;
import com.xujun.funapp.view.previouslife.PreviousLifeActivity;
import com.xujun.funapp.view.wechat.WechatActivity;
import com.xujun.funapp.widget.ImageButtonWithText;
import com.xujun.mylibrary.utils.ListUtils;
import com.xujun.funapp.common.network.GsonManger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class HomeFragment extends BaseTopListFragment<YYNewsListPresenter>
        implements YYNewsListContract.View, View.OnClickListener {

    List<NewsContentlistEntity> mData = new ArrayList<>();
    private MultiYYNewsListAdapter mMultiYYNewsListAdapter;
    private java.lang.String mChannelId = "5572a108b3cdc86cf39001d9";
    private java.lang.String mChannelName = "科技焦点";

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
                MultiItemTypeSupport<NewsContentlistEntity>() {

                    @Override
                    public int getItemType(NewsContentlistEntity newsContentlistEntity, int
                            position) {
                        if (newsContentlistEntity.havePic) {
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
    protected void initListener() {
        super.initListener();
        mMultiYYNewsListAdapter.setOnItemClickListener(new BaseRecyclerAdapter
                .OnItemClickListener() {

            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                //  将newslistBean传递过去
                NewsContentlistEntity newslistBean = mData.get(position);
                java.lang.String link = newslistBean.link;
                BaseRecyclerHolder recyclerHolder = (BaseRecyclerHolder) holder;
                View title = recyclerHolder.getView(R.id.tv_title);

                if (android.os.Build.VERSION.SDK_INT >= 22) {
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation
                            (getActivity(),
                                    title, mContext.getString(R.string.share_view)).toBundle();

                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(Constants.IntentConstants.DEFAULT_STRING_NAME, link);
                    intent.putExtra(Constants.IntentConstants.TITLE_NAME, newslistBean.title);
                    mContext.startActivity(intent, bundle);
                } else {
                    readyGo(WebViewActivity.class, Constants.IntentConstants.DEFAULT_STRING_NAME,
                            link);
                }


            }
        });
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
    public void onReceiveNews(String result) {
        YYNews yyNews = GsonManger.getInstance().fromJson(result, YYNews.class);
        YYNews.PagebeanEntity pagebean = yyNews.pagebean;
        if(pagebean.allNum>=mPage){
            List<NewsContentlistEntity> contentlist = pagebean
                    .contentlist;
            if (false == ListUtils.isEmpty(contentlist)) {
                handleResult(contentlist, RequestResult.success);
            } else {
                handleResult(contentlist, RequestResult.empty);
            }
        }else{
           handleResult(null,RequestResult.empty);
        }

    }

    @Override
    public void onReceiveNewsError(Throwable error) {
        handleResult(null, RequestResult.error);
        WriteLogUtil.e(" =" + error.getMessage());

    }

    @Override
    public void onReceiveLocal(int page, List list) {

        handleResult(list, RequestResult.success);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat:
                readyGo(WechatActivity.class);
                break;
            case R.id.past_life:
                readyGo(PreviousLifeActivity.class);
                break;
            case R.id.joke:

            default:
                break;
        }

    }
}
