package com.xujun.funapp.view.main.fragment.news;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xujun.funapp.R;
import com.xujun.funapp.adapters.YYNewsListAdapter;
import com.xujun.funapp.beans.YiYuanNews;
import com.xujun.funapp.beans.YiYuanNews.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.network.YiYuanApi;
import com.xujun.funapp.network.retrofitclient.CustomIntercept;
import com.xujun.mylibrary.widget.CarouselView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:46
 * @ email：gdutxiaoxu@163.com
 */
public class YiYuanNewsListFragment extends BaseListFragment<YiYuanNewsListPresenter> implements
        YiYuanNewsListContract.View {

    private int[] mImagesSrc = {
            R.mipmap.tangyang7,
            R.mipmap.tangyang7,
            R.mipmap.tangyang7,
            R.mipmap.tangyang7,
            R.mipmap.tangyang7
    };

    static final String KEY = "id";
    static final String KEY_POSITION = "KEY_POSITION";
    private YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity mChannelListEntity = null;
    private ArrayList<ContentlistEntity> mDatas = new ArrayList<>();;
    private YYNewsListAdapter mAdapter;
    //    保存上一次的可见的第一个位置
    private int mLastPosition;
    private int mItemPosition = -1;
    private String mChannelId;
    private String mName;

    public static  final String TAG="YiYuanNewsListFragment";

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
        if (isFirstItem()&& isFirstPage()) {
            beginRefresh();
        }
    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
        mPage = 1;
        mPresenter.getNews(mChannelId, mName, mPage, mRows);
//        testResponeBody();



    }

    private void testResponeBody() {
        final String url = "https://route.showapi.com/109-35/";
        Retrofit retrofit = getRetrofit(url);
        YiYuanApi yiYuanApi = retrofit.create(YiYuanApi.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put(YiYuanApi.API_ID_KEY, YiYuanApi.API_ID);
        map.put(YiYuanApi.API_SIGN_KEY, YiYuanApi.API_SIGN);
        map.put("channelId",mChannelId);
        map.put("channelName",mName);
        map.put("page",mPage);
        map.put("maxResult",mRecyclerView);

        Observable<ResponseBody> observable = yiYuanApi.testResponse(url, map);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        WriteLogUtil.e(" e="+e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            WriteLogUtil.i(" requestBody="+responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @NonNull
    private Retrofit getRetrofit(String url) {
        Interceptor interceptor = new CustomIntercept();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                //使用自定义的mGsonConverterFactory
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .build();
    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();
        mPage++;
        mPresenter.getNews(mChannelId, mName, mPage, mRows);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        mAdapter = new YYNewsListAdapter(mContext, mDatas, mPictureTag, LayoutMangerType.Linear);

        View headerView = initHeaderView();

        mAdapter.addHeaderView(headerView);
        return mAdapter;
    }

    private View initHeaderView() {
        View view = View.inflate(mContext, R.layout.header_view_yiyuan_news, null);
        CarouselView carouselView= (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setAdapter(new CarouselView.Adapter() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public View getView(int position) {
                View view = View.inflate(mContext,R.layout.item,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                imageView.setImageResource(mImagesSrc[position]);
                return view;
            }

            @Override
            public int getCount() {
                return mImagesSrc.length;
            }
        });
        return view;
    }

    @Override
    protected YiYuanNewsListPresenter setPresenter() {
        return new YiYuanNewsListPresenter(this);
    }

    @Override
    public void onReceiveNews(YiYuanNews news) {
        Log.i(TAG, "onReceiveNews: mItemPosition="+mItemPosition );
        List<ContentlistEntity> contentlist = news.showapi_res_body.pagebean.contentlist;
        if(isFirstPage()){

        }

        if (false == ListUtils.isEmpty(contentlist)) {
            handleResult(contentlist, RequestResult.success);
        } else {
            handleResult(contentlist, RequestResult.empty);
        }
    }

    @Override
    public void onReceiveNewsError(Throwable error) {
        Log.i(TAG, "onReceiveNewsError: +="+ mItemPosition);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: mItemPosition=" +mItemPosition);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: mItemPosition=" +mItemPosition);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: mItemPosition=" +mItemPosition);
        super.onDestroyView();
    }
}
