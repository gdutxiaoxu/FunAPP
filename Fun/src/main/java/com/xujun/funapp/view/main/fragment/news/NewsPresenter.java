package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.common.mvp.DefaultContract;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:32
 * @ email：gdutxiaoxu@163.com
 */
public class NewsPresenter extends DefaultContract.DefaultPresenter<DefaultContract.View>
   implements NewsContract.Presenter{

    public NewsPresenter(DefaultContract.View view) {
        super(view);
    }


}
