package com.xujun.funapp.view.wechat;

import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.BaseListActivity;
import com.xujun.funapp.common.YiYuanConstants;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.presenter.WeChatContract;
import com.xujun.funapp.presenter.WeChatPresenter;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WechatActivity extends BaseListActivity<WeChatPresenter> implements WeChatContract.View {

    public static final String url_jing_xuan= YiYuanConstants.WECHAT_JING_XUAN;
    private String typeId="";//分类
    private String key="";//关键词
    private int needCount=0;



    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }


    @Override
    protected WeChatPresenter setPresenter() {
        return new WeChatPresenter(this);
    }

    @Override
    protected void initData() {
//
        mPresenter.getData(url_jing_xuan,typeId,key,mPage,needCount);
    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();
    }

    @Override
    public void onReveive(String s) {
        WriteLogUtil.i("s ="+s);
    }

    @Override
    public void onError(Throwable throwable) {
         WriteLogUtil.e(" throwable="+throwable.getMessage());
    }
}
