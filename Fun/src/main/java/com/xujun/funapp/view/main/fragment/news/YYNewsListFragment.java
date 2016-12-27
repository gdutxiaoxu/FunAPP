package com.xujun.funapp.view.main.fragment.news;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.YYNewsListAdapter;
import com.xujun.funapp.adapters.YYNewsListGridAdapter;
import com.xujun.funapp.adapters.YYNewsListStargAdapter;
import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.beans.YYNews.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.xujun.funapp.beans.YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.image.ImageRequestManager;
import com.xujun.funapp.view.detail.YYNewsDetailActivity;
import com.xujun.mylibrary.widget.CarouselView;

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

    static final String KEY = "id";
    static final String KEY_POSITION = "KEY_POSITION";
    private ChannelListEntity mChannelListEntity = null;


    private YYNewsListAdapter mAdapter;
    private YYNewsListGridAdapter mYYNewsListGridAdapter;
    private YYNewsListStargAdapter mYYNewsListStargAdapter;
    //  保存上一次的可见的第一个位置
    private int mLastPosition;
    private int mItemPosition = -1;
    private String mChannelId;
    private String mName;

    public static final String TAG = "YYNewsListFragment";
    private ArrayList<ContentlistEntity> mHeaderDatas = new ArrayList<>();
    private ArrayList<ContentlistEntity> mDatas = new ArrayList<>();
    ;
    private CarouselView mCarouselView;
    private View mHeaderView;

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
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                jump(position);
            }
        });

        MenuItemListener menuItemListener = new MenuItemListener();
        mMenuItemLinear.setOnClickListener(menuItemListener);
        mMenuItemGrid.setOnClickListener(menuItemListener);
        mMenuItemStrag.setOnClickListener(menuItemListener);

    }

    private void jump(int position) {
        ContentlistEntity contentlistEntity = mDatas.get(position);
        readyGo(YYNewsDetailActivity.class, contentlistEntity);
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
        mAdapter = new YYNewsListAdapter(mContext, mDatas, mPictureTag);
        View headerView = initHeaderView();
        mAdapter.addHeaderView(headerView);
        return mAdapter;
    }

    private View initHeaderView() {
        if(mHeaderView ==null){
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
    public void onReceiveNews(YYNews news) {
        Log.i(TAG, "onReceiveNews: mItemPosition=" + mItemPosition);

        List<ContentlistEntity> contentlist = news.showapi_res_body.pagebean.contentlist;

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

    private void setHeaderData(List<ContentlistEntity> contentlist) {
        int size = Math.min(4, contentlist.size());
        mHeaderDatas.clear();
        if(contentlist!=null && contentlist.size()>0){
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
                if(!ListUtils.isEmpty(mHeaderDatas)){
                    ContentlistEntity contentlistEntity = mHeaderDatas.get(position);
                    if (contentlistEntity.havePic) {
                        ContentlistEntity.ImageurlsEntity imageurlsEntity = contentlistEntity
                                .imageurls.get(0);
                        ImageRequestManager.getInstance().display(mContext, imageView,
                                imageurlsEntity.url);

                    } else {
                        imageView.setImageResource(R.mipmap.tangyang7);
                    }
                }else{
                    imageView.setImageResource(R.mipmap.tangyang7);
                }


                return view;
            }

            @Override
            public int getCount() {
                return ListUtils.isEmpty(mHeaderDatas)?0:mHeaderDatas.size();
            }
        });
    }

    private class MenuItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_item_linear:
                    switchRecyclerAdapter(LayoutMangerType.Linear, mAdapter);
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
            mYYNewsListGridAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {


                @Override
                public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                    jump(position);
                }
            });
        }
    }

    private void initStragAdapter() {
        if (mYYNewsListStargAdapter == null) {
            mYYNewsListStargAdapter = new YYNewsListStargAdapter(mContext, mDatas,
                    mPictureTag);
            mYYNewsListStargAdapter.addHeaderView(mHeaderView);
            mYYNewsListStargAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {


                @Override
                public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                    jump(position);
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
