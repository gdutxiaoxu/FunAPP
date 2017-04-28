package com.xujun.funapp.view.wechat;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xujun.funapp.BaseListActivity;
import com.xujun.funapp.adapters.WeChatJingXuanAdapter;
import com.xujun.funapp.beans.WeChatJingXuan;
import com.xujun.funapp.beans.WeChatJingXuan.PagebeanEntity;
import com.xujun.funapp.common.RequestResult;
import com.xujun.funapp.common.YiYuanConstants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.presenter.WeChatContract;
import com.xujun.funapp.presenter.WeChatPresenter;
import com.xujun.funapp.view.detail.UrlDetailActivity;
import com.xujun.funapp.view.search.SearchWeChat;
import com.xujun.mylibrary.utils.ListUtils;
import com.xujun.funapp.common.network.GsonManger;

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
    protected void initListener() {
        super.initListener();
        mBind.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(SearchWeChat.class);
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                PagebeanEntity.ContentlistEntity contentlistEntity = mData.get(position);
                String url = contentlistEntity.url;
                readyGo(UrlDetailActivity.class, url);

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mBind.tvTitle.setText("微信精选");
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
        List<PagebeanEntity.ContentlistEntity> contentlist = weChatJingXuan
                .pagebean.contentlist;


        if (false == ListUtils.isEmpty(contentlist)) {
            handleResult(contentlist, RequestResult.success);
        } else {
            handleResult(contentlist, RequestResult.empty);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        WriteLogUtil.e(" throwable=" + throwable.getMessage());
        handleResult(null, RequestResult.success);
    }
}
