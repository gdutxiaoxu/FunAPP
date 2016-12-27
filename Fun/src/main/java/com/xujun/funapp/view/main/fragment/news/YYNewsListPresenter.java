package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.beans.YYNews;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.YYNewsListModel;

import rx.Subscriber;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:42
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListPresenter
        extends DefaultContract.Presenter<YYNewsListContract.View>
        implements YYNewsListContract.Presenter {

    private final YYNewsListModel mYYNewsListModel;


    public YYNewsListPresenter(YYNewsListContract.View view) {
        super(view);
        mYYNewsListModel = new YYNewsListModel();
    }

    @Override
    public void getNews(String channelId, String channelName, int page, int maxResult) {
        Subscriber<YYNews> subscriber = new Subscriber<YYNews>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                YYNewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNewsError(e);
                }
            }

            @Override
            public void onNext(YYNews yiYuanNews) {
                YYNewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNews(yiYuanNews);
                }
            }


        };
        mYYNewsListModel.getNews(channelId,channelName,page,maxResult,subscriber);
    }



}
