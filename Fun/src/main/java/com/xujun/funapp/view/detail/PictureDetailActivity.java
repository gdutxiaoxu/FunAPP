package com.xujun.funapp.view.detail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.beans.PictureListBean.TngouBean;
import com.xujun.funapp.common.Constants.IntentConstants;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.databinding.ActivityPictureDetailBinding;
import com.xujun.funapp.network.TnGouNet;
import com.xujun.funapp.presenter.PictureDetailContract;
import com.xujun.funapp.presenter.PictureDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.xujun.touchgallery.GalleryWidget.GalleryViewPager;

public class PictureDetailActivity extends
        BaseMVPActivity<ActivityPictureDetailBinding,PictureDetailPresenter>
        implements PictureDetailContract.View{


    private TngouBean mTngouBean;
    private ArrayList<Fragment> mFragments;
    private TextView mTvPageNum;
    private int mTotalPage;
    private GalleryViewPager mViewPager;
    int curPosition=0;

    @Override
    protected PictureDetailPresenter setPresenter() {
        return new PictureDetailPresenter(this);
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        mTngouBean = (TngouBean)intent.getParcelableExtra(IntentConstants.DEFAULT_PARCEABLE_NAME);
        mTotalPage = mTngouBean.size;
        checkNotNull(mTngouBean);
    }



    @Override
    protected void initView(ActivityPictureDetailBinding bind) {
        mViewPager = bind.viewPager;
        mTvPageNum = bind.tvPageNum;

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
        return R.layout.activity_picture_detail;
    }

    @Override
    public void onReceivePictureList(PictureDetailBean pictureDetailBean) {
        List<PictureDetailBean.ListBean> data = pictureDetailBean.list;
        mFragments = new ArrayList<>();
        ArrayList<String> source = new ArrayList<>();

        for(int i=0;i<data.size();i++){
            PictureDetailBean.ListBean listBean = data.get(i);
            String url = TnGouNet.mBaseImageUrl+ listBean.src;
            source.add(url);

        }


        PicturePagerAdapter picturePagerAdapter = new PicturePagerAdapter(mContext, source);

        mViewPager.setAdapter(picturePagerAdapter);

    }
}
