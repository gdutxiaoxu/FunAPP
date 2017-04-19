package com.xujun.funapp.view.previouslife;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.xujun.funapp.BaseListActivity;
import com.xujun.funapp.adapters.QsjsAdapter;
import com.xujun.funapp.beans.Qsjs;
import com.xujun.funapp.common.RequestResult;
import com.xujun.funapp.common.YiYuanConstants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.presenter.PreviousLifeContract;
import com.xujun.funapp.presenter.PreviousLifePresenter;
import com.xujun.mylibrary.utils.DateUtils;
import com.xujun.mylibrary.utils.ListUtils;
import com.xujun.funapp.common.network.GsonManger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class PreviousLifeActivity extends BaseListActivity<PreviousLifePresenter> implements
        PreviousLifeContract.View {

    public static final String url_jing_xuan = YiYuanConstants.WECHAT_JING_XUAN;
    String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php";
    private String date = "1/2";

    List<Qsjs> mData = new ArrayList<>();
    private QsjsAdapter mAdapter;

    @Override
    protected BaseRecyclerAdapter getBaseAdapter() {
        mAdapter = new QsjsAdapter(this, mData);
        return mAdapter;
    }

    @Override
    protected PreviousLifePresenter setPresenter() {
        return new PreviousLifePresenter(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                Qsjs qsjs = mData.get(position);

                readyGo(PreviousLifeDetailActivity.class, qsjs.e_id);

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mBind.tvTitle.setText("前世今生");
        // 设置不能加载更多
        setEnableLoadMore(false);

    }

    @Override
    protected void getFirstPageData() {
        super.getFirstPageData();
        date = DateUtils.getJuheDate();
        //  WriteLogUtil.i("date =" + date);
        mPresenter.getData(url, date);

    }

    @Override
    protected void getNextPageData() {
        super.getNextPageData();

    }

    @Override
    public void onReveive(String s) {
        //     WriteLogUtil.i("s =" + s);
        Type type = new TypeToken<List<Qsjs>>() {
        }.getType();
        List<Qsjs> contentlist = GsonManger.getInstance().fromJson(s, type);
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
