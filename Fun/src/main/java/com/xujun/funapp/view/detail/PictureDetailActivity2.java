package com.xujun.funapp.view.detail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.beans.PictureListBean.TngouBean;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.Constants.IntentConstants;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.databinding.ActivityPictureDetail2Binding;

import com.xujun.funapp.presenter.PictureDetailContract;
import com.xujun.funapp.presenter.PictureDetailPresenter;

import java.util.ArrayList;
import java.util.List;

public class PictureDetailActivity2 extends
        BaseMVPActivity<ActivityPictureDetail2Binding,PictureDetailPresenter>
        implements PictureDetailContract.View{


    private TngouBean mTngouBean;
    private ArrayList<Fragment> mFragments;
    private TextView mTvPageNum;
    private int mTotalPage;

    int curPosition=0;
    private ViewPager mViewPager;

    @Override
    protected PictureDetailPresenter setPresenter() {
        return new PictureDetailPresenter(this);
    }

    @Override
    protected void initView(ActivityPictureDetail2Binding bind) {
        mViewPager = bind.viewPager;
        mTvPageNum = bind.tvPageNum;
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        mTngouBean = (TngouBean)intent.getParcelableExtra(IntentConstants.DEFAULT_PARCEABLE_NAME);
        mTotalPage = mTngouBean.size;
        checkNotNull(mTngouBean);
    }





    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                String format = String.format("%d/" + mTotalPage, position + 1);
                mTvPageNum.setText(format);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        String format = String.format("%d/" + mTotalPage, curPosition + 1);
        mTvPageNum.setText(format);
        mPresenter.getPictureList(mTngouBean.id);
        
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_picture_detail2;
    }

    @Override
    public void onReceivePictureList(PictureDetailBean pictureDetailBean) {
        List<PictureDetailBean.ListBean> data = pictureDetailBean.list;
        mFragments = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            PictureDetailBean.ListBean listBean = data.get(i);
            PictureDetailFragment pictureDetailFragment = PictureDetailFragment.newInstance
                    (listBean);
            mFragments.add(pictureDetailFragment);


        }
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter
                (getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(baseFragmentAdapter);


      /*  ArrayList<String> source = new ArrayList<>();

        for(int i=0;i<data.size();i++){
            PictureDetailBean.ListBean listBean = data.get(i);
            String url = TnGouNet.mBaseImageUrl+ listBean.src;
            source.add(url);

        }


        PicturePagerAdapter picturePagerAdapter = new PicturePagerAdapter(mContext, source);

        mViewPager.setAdapter(picturePagerAdapter);*/

    }
}
