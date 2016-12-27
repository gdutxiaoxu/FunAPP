package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.beans.TxNews;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.NewsListModel;
import com.xujun.funapp.network.RequestListener;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:42
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListPresenter
        extends DefaultContract.Presenter<NewsListContract.View>
        implements NewsListContract.Presenter {

    private final NewsListModel mNewsListModel;

    public NewsListPresenter(NewsListContract.View view) {
        super(view);
        mNewsListModel = new NewsListModel();
    }

    @Override
    public void getNews(String type, int page, int num) {
        mNewsListModel.getNews(type, page, num, new RequestListener<TxNews>() {
            @Override
            public void onSuccess(TxNews txNews) {
                NewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNews(txNews);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                NewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNewsError(throwable);
                }
            }
        });
    }
}
