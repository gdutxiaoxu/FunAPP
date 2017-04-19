package com.xujun.funapp.view.previouslife;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.QsjsDetail;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.databinding.ActivityPreviousLifeDetailBinding;
import com.xujun.funapp.image.ImageRequestManager;
import com.xujun.funapp.presenter.PreviousLifeDetailContract;
import com.xujun.funapp.presenter.PreviousLifeDetailPresenter;
import com.xujun.funapp.common.network.GsonManger;

import java.lang.reflect.Type;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class PreviousLifeDetailActivity extends
        BaseMVPActivity<ActivityPreviousLifeDetailBinding, PreviousLifeDetailPresenter>
        implements PreviousLifeDetailContract.View {

    String url = "http://v.juhe.cn/todayOnhistory/queryDetail.php";
    private String e_id = "66";
    private ImageView mIvPic;
    private TextView mTvContent;
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_previous_life_detail;
    }

    @Override
    protected PreviousLifeDetailPresenter setPresenter() {
        return new PreviousLifeDetailPresenter(this);
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        e_id = intent.getStringExtra(Constants.IntentConstants.DEFAULT_STRING_NAME);
    }

    @Override
    protected void initView(ActivityPreviousLifeDetailBinding bind) {
        mIvPic = bind.ivPic;
        mTvContent = bind.tvContent;
        mRefreshLayout = bind.BGARefreshLayout;

        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder
                (mContext, true);
        refreshViewHolder.setOriginalImage(R.mipmap.bga_refresh_moooc);
        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        refreshViewHolder.setSpringDistanceScale(0.2f);
//        refreshViewHolder.setLoadingMoreText("正在加载更多");
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                refresh();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
        mBind.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void refresh() {
        mPresenter.getData(url, e_id);
    }

    @Override
    protected void initData() {
        super.initData();
        mRefreshLayout.beginRefreshing();


    }

    @Override
    public void onReveive(String s) {
        WriteLogUtil.i(" s="+s);
        refreshComplete();
        Type type = new com.google.gson.reflect.TypeToken<List<QsjsDetail>>() {
        }.getType();
        List<QsjsDetail> data = GsonManger.getInstance().fromJson(s, type);
        QsjsDetail qsjsDetail = data.get(0);
        String content = qsjsDetail.content;
        List<QsjsDetail.PicUrl> picUrl = qsjsDetail.picUrl;
        if (!picUrl.isEmpty()) {
            String url = picUrl.get(0).url;
            ImageRequestManager.getRequest().display(mContext, mIvPic, url);
        }
        mTvContent.setText(content);


    }

    @Override
    public void onError(Throwable throwable) {
        WriteLogUtil.e(" throwable=" + throwable.getMessage());
        refreshComplete();

    }

    private void refreshComplete() {
        mRefreshLayout.endRefreshing();
    }
}
