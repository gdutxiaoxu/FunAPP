package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.beans.NewsContentlistEntity;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.common.network.HttpException;
import com.xujun.funapp.common.network.IRequestListener;
import com.xujun.funapp.common.network.NetUtils;
import com.xujun.funapp.common.util.UIUtils;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.model.YYNewsListModel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:42
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListPresenter
        extends DefaultContract.Presenter<YYNewsListContract.View>
        implements YYNewsListContract.Presenter {

    private final YYNewsListModel mYYNewsListModel;

    private static final String TAG = "YYNewsListPresenter";

    public YYNewsListPresenter(YYNewsListContract.View view) {
        super(view);
        mYYNewsListModel = new YYNewsListModel();
    }

    @Override
    public void getNews(String channelId, String channelName, final int page, int maxResult) {
        handleLocalData(page);


        IRequestListener iRequestListener = new IRequestListener() {

            @Override
            public void onResponse(String response) {
                YYNewsListContract.View view = getView();
                mYYNewsListModel.saveData(page, response);
                if (view != null) {
                    view.onReceiveNews(response);
                }
            }

            @Override
            public void onFail(HttpException httpException) {
                Throwable e = httpException.e;
                e.printStackTrace();
                YYNewsListContract.View view = getView();
                if (view != null) {
                    view.onReceiveNewsError(e);
                }
            }
        };
        mYYNewsListModel.getNews(channelId, channelName, page, maxResult, iRequestListener);
    }

    private void handleLocalData(final int page) {
        if (!NetUtils.isFirstPage(page)) {
            return;
        }
        Observable.create(new Observable.OnSubscribe<List<NewsContentlistEntity>>() {

            @Override
            public void call(Subscriber<? super List<NewsContentlistEntity>> subscriber) {

                boolean mainThread = UIUtils.isMainThread();
                WriteLogUtil.i(TAG, " mainThread=" + mainThread);
                List<NewsContentlistEntity> loacalData = mYYNewsListModel.getLoacalData(page);
                WriteLogUtil.i(TAG, " loacalData=" + loacalData.get(0));
                WriteLogUtil.i(TAG, " loacalData=" + loacalData.get(1));
                subscriber.onNext(loacalData);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<NewsContentlistEntity>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NewsContentlistEntity> channelListEntityList) {

                YYNewsListContract.View view = getView();
                if (view != null) {
                    view.onReceiveLocal(page, channelListEntityList);
                }
            }
        });
    }


}
