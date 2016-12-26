package com.xujun.funapp.view.main.fragment.news;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.beans.YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.BaseViewPagerFragemnt;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.common.util.GsonManger;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.common.util.ResourceUtils;
import com.xujun.funapp.common.util.WriteLogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/9/16 23:47
 * @ email：gdutxiaoxu@163.com
 */
public class YiYuanNewsFragment extends BaseViewPagerFragemnt<YYNewsPresenter> implements
        DefaultContract.View<YiYuanNewsClassify> {

    public static final String YIYUAN_NEWS_CLASSIFY_JSON = "yiyuan_newsClassify.json";
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles = new ArrayList<>();
    private BaseFragmentAdapter mFragmentAdapter;

    public static final String API_ID = "29571";
    private static final String API_SECRET = "5bf00910e04a46998f6979f6da400f1e";
    private static final String API_SIGN =API_SECRET ;

    @Override
    protected BaseFragmentAdapter getViewPagerAdapter() {
        mFragments = new ArrayList<>();
        mFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments,
                ListUtils.listToArr(mTitles));
        return mFragmentAdapter;
    }

    @Override
    protected void initData() {
        super.initData();
        //        https://route.showapi.com/109-34?showapi_appid=29457
        // &showapi_timestamp=20161223143804&showapi_sign=c2b86cae09320f5b0c5e2a2590450b8d
        String url = "https://route.showapi.com/109-34/";
        HashMap<String, String> map = new HashMap<>();
        map.put("showapi_appid", API_ID);

        map.put("showapi_sign", API_SIGN);
        Log.i(TAG, "initData: API_SIGN=" + API_SIGN);
        mPresenter.getNewsClassify(url, map);


        Observable.create(new Observable.OnSubscribe<List<ChannelListEntity>>() {

            @Override
            public void call(Subscriber<? super List<ChannelListEntity>> subscriber) {
                String result = ResourceUtils.getFromAssets(YIYUAN_NEWS_CLASSIFY_JSON);
                //        WriteLogUtil.e(" result="+result);
                YiYuanNewsClassify classify = GsonManger.getInstance().fromJson(result,
                        YiYuanNewsClassify.class);
                subscriber.onNext(classify.showapi_res_body.channelList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<ChannelListEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ChannelListEntity> channelList) {
                int size = channelList.size();
                if (size > 4) {
                    size = 4;
                }
                for (int i = 0; i < size; i++) {
                    ChannelListEntity channelListEntity = channelList.get(i);
                    mTitles.add(channelListEntity.name.substring(0, 2));
                    YiYuanNewsListFragment fragment = YiYuanNewsListFragment.newInstance
                            (channelListEntity, i);
                    mFragments.add(fragment);
                }
                mFragmentAdapter.setData(mFragments, ListUtils.listToArr(mTitles));
                mFragmentAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected YYNewsPresenter setPresenter() {
        return new YYNewsPresenter(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(YiYuanNewsClassify yiYuanNewsClassify) {
        List<ChannelListEntity> channelList = yiYuanNewsClassify.showapi_res_body.channelList;
        ListUtils.print(channelList);

    }

    @Override
    public void onError(Throwable throwable) {
        WriteLogUtil.i(" throwable=" + throwable.getMessage());

    }

    @Override
    public void onLocal(YiYuanNewsClassify yiYuanNewsClassify) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
