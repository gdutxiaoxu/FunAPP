package com.xujun.funapp.view.main.fragment.news;

import com.xujun.funapp.beans.YiYuanNewsClassify;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.model.NewsModel;

import java.util.Map;

import rx.Subscriber;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:32
 * @ email：gdutxiaoxu@163.com
 */
public class NewsPresenter extends DefaultContract.Presenter<DefaultContract.View>
   implements NewsContract.Presenter{

    private final NewsModel mNewsModel;

    public NewsPresenter(DefaultContract.View view) {
        super(view);
        mNewsModel = new NewsModel();
    }

    @Override
    public void getNewsClassify(String url, Map paramsMap) {
        Subscriber<YiYuanNewsClassify> subscriber = new Subscriber<YiYuanNewsClassify>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DefaultContract.View view = getView();
                WriteLogUtil.i(" e="+e.getMessage());
                if(view!=null){
                    view.onError(e);
                }
            }

            @Override
            public void onNext(YiYuanNewsClassify yiYuanNewsClassify) {
                DefaultContract.View view = getView();
                WriteLogUtil.i(" 请求成功=");
                if(view!=null){
                    view.onSuccess(yiYuanNewsClassify);
                }
            }

        };
        mNewsModel.getNewsClassify(url,paramsMap,subscriber);

    }
}
