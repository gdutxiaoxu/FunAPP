package com.xujun.funapp.view.wechat;

import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.BaseListActivity;
import com.xujun.funapp.presenter.WeChatContract;
import com.xujun.funapp.presenter.WeChatPresenter;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WechatActivity extends BaseListActivity<WeChatPresenter> implements WeChatContract.View {
    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    protected WeChatPresenter setPresenter() {
        return new WeChatPresenter(this);
    }
}
