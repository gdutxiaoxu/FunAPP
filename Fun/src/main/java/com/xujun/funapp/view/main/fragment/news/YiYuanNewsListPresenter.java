package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.beans.YiYuanNews;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.YYNewsListModel;

import rx.Subscriber;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:42
 * @ email：gdutxiaoxu@163.com
 */
public class YiYuanNewsListPresenter
        extends DefaultContract.DefaultPresenter<YiYuanNewsListContract.View>
        implements YiYuanNewsListContract.Presenter {

    private final YYNewsListModel mYYNewsListModel;


    public YiYuanNewsListPresenter(YiYuanNewsListContract.View view) {
        super(view);
        mYYNewsListModel = new YYNewsListModel();
    }

    @Override
    public void getNews(String channelId, String channelName, int page, int maxResult) {
        Subscriber<YiYuanNews> subscriber = new Subscriber<YiYuanNews>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                YiYuanNewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNewsError(e);
                }
            }

            @Override
            public void onNext(YiYuanNews yiYuanNews) {
                YiYuanNewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNews(yiYuanNews);
                }
            }


        };
        mYYNewsListModel.getNews(channelId,channelName,page,maxResult,subscriber);
    }



}
