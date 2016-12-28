package com.xujun.funapp.presenter;

import com.xujun.funapp.common.YiYuanConstants;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.model.WeChatModel;

import java.util.HashMap;

import rx.Subscriber;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WeChatPresenter   extends DefaultContract.Presenter<WeChatContract.View>
        implements WeChatContract.Presenter {

    private final WeChatModel mWeChatModel;

    public WeChatPresenter(WeChatContract.View view) {
        super(view);
        mWeChatModel = new WeChatModel();
    }



    @Override
    public void getData(String url, String typeId,String key,int page, int needContent) {
        HashMap<String, Object> map = YiYuanConstants.getParamsMap();
        map.put("typeId",typeId);
        map.put("key",key);
        map.put("page",page);
        map.put("needContent",needContent);
        mWeChatModel.getData(url, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                WeChatContract.View view = getView();
                if(view!=null){
                    view.onError(e);
                }
            }

            @Override
            public void onNext(String s) {
                WeChatContract.View view = getView();
                if(view!=null){
                    view.onReveive(s);
                }

            }
        });
    }

}
