package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.beans.News;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.NewsModel;
import com.xujun.funapp.network.RequestListener;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:42
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListPresenter
        extends DefaultContract.DefaultPresenter<NewsListContract.View>
        implements NewsListContract.Presenter {

    private final NewsModel mNewsModel;

    public NewsListPresenter(NewsListContract.View view) {
        super(view);
        mNewsModel = new NewsModel();
    }

    @Override
    public void getNews(String type, int page, int num) {
        mNewsModel.getNews(type, page, num, new RequestListener<News>() {
            @Override
            public void onSuccess(News news) {
                NewsListContract.View view = getView();
                if(view!=null){
                    view.onReceiveNews(news);
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
