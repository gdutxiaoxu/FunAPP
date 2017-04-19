package com.xujun.funapp.view.main.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.MultiYYNewsListAdapter;
import com.xujun.funapp.adapters.YYNewsListGridAdapter;
import com.xujun.funapp.adapters.YYNewsListStargAdapter;
import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.beans.NewsContentlistEntity;
import com.xujun.funapp.beans.YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.RequestResult;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.common.recyclerView.MultiItemTypeSupport;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.image.ImageRequestManager;
import com.xujun.funapp.view.detail.WebViewActivity;
import com.xujun.funapp.widget.CarouselView;
import com.xujun.mylibrary.utils.ListUtils;
import com.xujun.funapp.common.network.GsonManger;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:46
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListFragment extends BaseListFragment<YYNewsListPresenter> implements
        YYNewsListContract.View {

    private int[] mImagesSrc = {R.mipmap.tangyang7, R.mipmap.tangyang7, R.mipmap.tangyang7, R
            .mipmap.tangyang7, R.mipmap.tangyang7};

    static final java.lang.String KEY = "id";
    static final java.lang.String KEY_POSITION = "KEY_POSITION";
    private ChannelListEntity mChannelListEntity = null;

    private YYNewsListGridAdapter mYYNewsListGridAdapter;
    private YYNewsListStargAdapter mYYNewsListStargAdapter;
    //  保存上一次的可见的第一个位置
    private int mLastPosition;
    private int mItemPosition = -1;
    private java.lang.String mChannelId;
    private java.lang.String mName;

    public static final java.lang.String TAG = "YYNewsListFragment";
    private ArrayList<NewsContentlistEntity> mHeaderDatas = new ArrayList<>();
    private ArrayList<NewsContentlistEntity> mDatas = new ArrayList<>();
    ;
    private CarouselView mCarouselView;
    private View mHeaderView;
    private MultiYYNewsListAdapter mMultiListAdapter;

    public static YYNewsListFragment newInstance(Parcelable parcelable, int postion) {
        YYNewsListFragment newsListFragment = new YYNewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(YYNewsListFragment.KEY, parcelable);
        bundle.putInt(YYNewsListFragment.KEY_POSITION, postion);
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
            checkNotNull(mChannelListEntity);
            mItemPosition = arguments.getInt(KEY_POSITION);
            mChannelId = mChannelListEntity.channelId;
            mName = mChannelListEntity.name;

        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mMultiListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                jump(holder, position);
            }
        });

        MenuItemListener menuItemListener = new MenuItemListener();
        mMenuItemLinear.setOnClickListener(menuItemListener);
        mMenuItemGrid.setOnClickListener(menuItemListener);
        mMenuItemStrag.setOnClickListener(menuItemListener);

    }

    private void jump(RecyclerView.ViewHolder holder, int position) {
        BaseRecyclerHolder recyclerHolder = (BaseRecyclerHolder) holder;
        View titleView = recyclerHolder.getView(R.id.tv_title);
        NewsContentlistEntity newsContentlistEntity = mDatas.get(position);
        java.lang.String title = newsContentlistEntity.title;
        java.lang.String link = newsContentlistEntity.link;
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(Constants.IntentConstants.DEFAULT_STRING_NAME, link);
        intent.putExtra(Constants.IntentConstants.TITLE_NAME, title);
        if (android.os.Build.VERSION.SDK_INT >= 22) {
            if(titleView!=null){
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        titleView, mContext.getString(R.string.share_view)).toBundle();
                mContext.startActivity(intent, bundle);
            }
            mContext.startActivity(intent);

        } else {
            mContext.startActivity(intent);
        }
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
        mMultiListAdapter = new MultiYYNewsListAdapter(mContext, mDatas,
                new MultiItemTypeSupport<NewsContentlistEntity>() {
                    @Override
                    public int getItemType(NewsContentlistEntity newsContentlistEntity, int position) {
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
        View headerView = initHeaderView();
        mMultiListAdapter.addHeaderView(headerView);
        return mMultiListAdapter;
    }

    private View initHeaderView() {
        if (mHeaderView == null) {
            mHeaderView = View.inflate(mContext, R.layout.header_view_yiyuan_news, null);
            mCarouselView = (CarouselView) mHeaderView.findViewById(R.id.carouselView);
            setHeaderData(mHeaderDatas);
        }

        return mHeaderView;
    }

    @Override
    protected YYNewsListPresenter setPresenter() {
        return new YYNewsListPresenter(this);
    }

    @Override
    public void onReceiveNews(String result) {
        Log.i(TAG, "onReceiveNews: mItemPosition=" + mItemPosition);
        YYNews yyNews = GsonManger.getInstance().fromJson(result, YYNews.class);

        List<NewsContentlistEntity> contentlist = yyNews.pagebean.contentlist;

        if (isFirstPage() && !ListUtils.isEmpty(contentlist)) {
            setHeaderData(contentlist);
        }

        if (false == ListUtils.isEmpty(contentlist)) {
            handleResult(contentlist, RequestResult.success);
        } else {
            handleResult(contentlist, RequestResult.empty);
        }
    }

    @Override
    public void onReceiveNewsError(Throwable error) {
        Log.i(TAG, "onReceiveNewsError: +=" + mItemPosition);
        handleResult(null, RequestResult.error);
        WriteLogUtil.e(" =" + error.getMessage());

    }

    @Override
    public void onReceiveLocal(int page, List list) {

        handleResult(list, RequestResult.success);

    }

    private void setHeaderData(List<NewsContentlistEntity> contentlist) {
        int size = Math.min(4, contentlist.size());
        mHeaderDatas.clear();
        if (contentlist != null && contentlist.size() > 0) {
            for (int i = 0; i < size; i++) {
                mHeaderDatas.add(contentlist.get(i));
            }
        }


        mCarouselView.setCarouseAdapter(new CarouselView.CarouseAdapter() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public View getView(int position) {
                View view = View.inflate(mContext, R.layout.item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                if (!ListUtils.isEmpty(mHeaderDatas)) {
                    NewsContentlistEntity newsContentlistEntity = mHeaderDatas.get(position);
                    if (newsContentlistEntity.havePic) {
                        NewsContentlistEntity.ImageurlsEntity imageurlsEntity = newsContentlistEntity
                                .imageurls.get(0);
                        ImageRequestManager.getRequest().display(mContext, imageView,
                                imageurlsEntity.url);

                    } else {
                        imageView.setImageResource(R.mipmap.tangyang7);
                    }
                } else {
                    imageView.setImageResource(R.mipmap.tangyang7);
                }


                return view;
            }

            @Override
            public int getCount() {
                return ListUtils.isEmpty(mHeaderDatas) ? 0 : mHeaderDatas.size();
            }
        });
    }

    private class MenuItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_item_linear:
                    switchRecyclerAdapter(LayoutMangerType.Linear, mMultiListAdapter);
                    closeMenu();
                    break;
                case R.id.menu_item_grid:
                    initGridAdapter();
                    switchRecyclerAdapter(LayoutMangerType.Grid, mYYNewsListGridAdapter);
                    closeMenu();
                    break;
                case R.id.menu_item_strag:
                    initStragAdapter();
                    switchRecyclerAdapter(LayoutMangerType.Strag, mYYNewsListStargAdapter);
                    closeMenu();
                    break;

                default:
                    break;
            }
        }
    }

    private void initGridAdapter() {
        if (mYYNewsListGridAdapter == null) {
            mYYNewsListGridAdapter = new YYNewsListGridAdapter(mContext, mDatas,
                    mPictureTag);
            mYYNewsListGridAdapter.addHeaderView(mHeaderView);
            mYYNewsListGridAdapter.setOnItemClickListener(new BaseRecyclerAdapter
                    .OnItemClickListener() {

                @Override
                public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                    jump(holder, position);
                }
            });
        }
    }

    private void initStragAdapter() {
        if (mYYNewsListStargAdapter == null) {
            mYYNewsListStargAdapter = new YYNewsListStargAdapter(mContext, mDatas,
                    mPictureTag);
            mYYNewsListStargAdapter.addHeaderView(mHeaderView);
            mYYNewsListStargAdapter.setOnItemClickListener(new BaseRecyclerAdapter
                    .OnItemClickListener() {

                @Override
                public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                    jump(holder, position);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: mItemPosition=" + mItemPosition);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: mItemPosition=" + mItemPosition);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: mItemPosition=" + mItemPosition);
        super.onDestroyView();
    }
}
