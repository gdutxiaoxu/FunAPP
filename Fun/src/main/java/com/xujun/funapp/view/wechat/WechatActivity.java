package com.xujun.funapp.view.wechat;

import com.xujun.funapp.BaseListActivity;
import com.xujun.funapp.adapters.WeChatJingXuanAdapter;
import com.xujun.funapp.beans.WeChatJingXuan;
import com.xujun.funapp.beans.WeChatJingXuan.ShowapiResBodyEntity.PagebeanEntity;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.YiYuanConstants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.GsonManger;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.presenter.WeChatContract;
import com.xujun.funapp.presenter.WeChatPresenter;
import com.xujun.mylibrary.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WechatActivity extends BaseListActivity<WeChatPresenter> implements WeChatContract
        .View {

    public static final String url_jing_xuan = YiYuanConstants.WECHAT_JING_XUAN;
    private String typeId = "";//分类
    private String key = "";//关键词
    private int needCount = 0;

    List<PagebeanEntity.ContentlistEntity> mData = new ArrayList<>();
    private WeChatJingXuanAdapter mAdapter;

    @Override
    protected BaseRecyclerAdapter<PagebeanEntity.ContentlistEntity> getBaseAdapter() {
        mAdapter = new WeChatJingXuanAdapter(this, mData);
        return mAdapter;
    }

    @Override
    protected WeChatPresenter setPresenter() {
        return new WeChatPresenter(this);
    }

    @Override
    protected void initData() {
        //
        mPresenter.getData(url_jing_xuan, typeId, key, mPage, needCount);
    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
        mPresenter.getData(url_jing_xuan, typeId, key, mPage, needCount);
    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();
        mPresenter.getData(url_jing_xuan, typeId, key, ++mPage, needCount);
    }

    @Override
    public void onReveive(String s) {
        WriteLogUtil.i("s =" + s);
        WeChatJingXuan weChatJingXuan = GsonManger.getInstance().fromJson(s, WeChatJingXuan.class);
        List<PagebeanEntity.ContentlistEntity> contentlist = weChatJingXuan.showapi_res_body
                .pagebean.contentlist;
        if (false == ListUtils.isEmpty(contentlist)) {
            handleResult(contentlist, BaseListFragment.RequestResult.success);
        } else {
            handleResult(contentlist, BaseListFragment.RequestResult.empty);
        }
      /*  handleResult(null, BaseListFragment.RequestResult.error);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handleResult(null, BaseListFragment.RequestResult.empty);
            }
        },1000);*/
    }

    @Override
    public void onError(Throwable throwable) {
        WriteLogUtil.e(" throwable=" + throwable.getMessage());
        handleResult(null, BaseListFragment.RequestResult.success);
    }
}
