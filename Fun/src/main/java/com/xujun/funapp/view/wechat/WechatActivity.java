package com.xujun.funapp.view.wechat;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xujun.funapp.BaseListActivity;
import com.xujun.funapp.adapters.WeChatJingXuanAdapter;
import com.xujun.funapp.beans.Qsjs;
import com.xujun.funapp.beans.WeChatJingXuan;
import com.xujun.funapp.beans.WeChatJingXuan.ShowapiResBodyEntity.PagebeanEntity;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.YiYuanConstants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.GsonManger;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.presenter.WeChatContract;
import com.xujun.funapp.presenter.WeChatPresenter;
import com.xujun.funapp.view.detail.UrlDetailActivity;
import com.xujun.mylibrary.utils.ListUtils;
import com.xujun.myrxretrofitlibrary.JuHeApiManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

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
        String url = "/japi/toh";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("key", "cd2fc6ee44f7d05b4b8799b40992776b");
        map.put("v", "1.0");
        map.put("month", "1");
        map.put("day", "2");
        JuHeApiManger.getInstance().excutePush(url, map, new Subscriber<List<Qsjs>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                WriteLogUtil.i(" e=" + e.getMessage());
            }

            @Override
            public void onNext(List<Qsjs> qsjs) {
                WriteLogUtil.i(" =" + qsjs.toString());

            }
        });

        JuHeApiManger.getInstance().excutePushString(url, map, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                 WriteLogUtil.json(s);
            }
        });
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
