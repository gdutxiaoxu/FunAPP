package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.common.mvp.DefaultContract;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 23:42
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListPresenter
        extends DefaultContract.DefaultPresenter<NewsListContract.View>
        implements NewsListContract.Presenter {

    public NewsListPresenter(NewsListContract.View view) {
        super(view);
    }
}
