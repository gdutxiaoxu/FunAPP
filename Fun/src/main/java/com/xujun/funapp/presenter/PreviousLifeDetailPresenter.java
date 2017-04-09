package com.xujun.funapp.presenter;

import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.model.PreviousLifeDetailModel;

import java.util.HashMap;

import rx.Subscriber;

/**
 * @author xujun  on 2017/1/2.
 * @email gdutxiaoxu@163.com
 */

public class PreviousLifeDetailPresenter extends DefaultContract.Presenter<PreviousLifeDetailContract.View>
        implements PreviousLifeDetailContract.Presenter {

    private final PreviousLifeDetailModel mPreviousLifeModel;

    public PreviousLifeDetailPresenter(PreviousLifeDetailContract.View view) {
        super(view);
        mPreviousLifeModel = new PreviousLifeDetailModel();
    }

    @Override
    public void getData(String url, String e_id ) {
        HashMap<String, Object> map = getMap();
        map.put("e_id", e_id);
        mPreviousLifeModel.getData(url, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                WriteLogUtil.e("e =" + e.getMessage());
                PreviousLifeDetailContract.View view = getView();
                if (view != null) {
                    view.onError(e);
                }
            }

            @Override
            public void onNext(String s) {
                PreviousLifeDetailContract.View view = getView();
                if (view != null) {
                    view.onReveive(s);
                }
            }
        });
    }

    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "cd2fc6ee44f7d05b4b8799b40992776b");
        return map;
    }
}
