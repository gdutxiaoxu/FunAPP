package com.xujun.funapp.view.detail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.beans.PictureListBean.TngouBean;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.Constants.IntentConstants;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.databinding.ActivityPictureDetailBinding;
import com.xujun.funapp.presenter.PictureDetailContract;
import com.xujun.funapp.presenter.PictureDetailPresenter;

import java.util.ArrayList;
import java.util.List;

public class PictureDetailActivity extends
        BaseMVPActivity<ActivityPictureDetailBinding,PictureDetailPresenter>
        implements PictureDetailContract.View{

    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private TngouBean mTngouBean;
    private ArrayList<Fragment> mFragments;

    @Override
    protected PictureDetailPresenter setPresenter() {
        return new PictureDetailPresenter(this);
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        mTngouBean = (TngouBean)intent.getParcelableExtra(IntentConstants.DEFAULT_PARCEABLE_NAME);
        checkNotNull(mTngouBean);
    }



    @Override
    protected void initView(ActivityPictureDetailBinding bind) {
        mRecyclerView = bind.recyclerView;
        mViewPager = bind.viewPager;

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getPictureList(mTngouBean.id);
        
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_picture_detail;
    }

    @Override
    public void onReceivePictureList(PictureDetailBean pictureDetailBean) {
        List<PictureDetailBean.ListBean> data = pictureDetailBean.list;
        mFragments = new ArrayList<>();

        for(int i=0;i<data.size();i++){
            PictureDetailBean.ListBean listBean = data.get(i);
            ImageFragment imageFragment = ImageFragment.newInstance(listBean);
            mFragments.add(imageFragment);
        }
        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter
                (getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(fragmentAdapter);

    }
}
