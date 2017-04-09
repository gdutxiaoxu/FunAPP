package com.xujun.funapp.view.main.fragment.news;

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
    public void getNews(String channelId, String channelName, final int page, int maxResult) {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                YYNewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNewsError(e);
                }
            }

            @Override
            public void onNext(String result) {
                YYNewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNews(result);
                }


            }


        };
        mYYNewsListModel.getNews(channelId,channelName,page,maxResult,subscriber);
    }



}
