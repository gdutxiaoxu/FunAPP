package com.xujun.funapp.view.main.fragment.picture;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xujun.funapp.beans.PictureClassify;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.BaseViewPagerFragemnt;
import com.xujun.funapp.common.mvp.DefaultContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/17 21:48
 * @ email：gdutxiaoxu@163.com
 */
public class PictureFragment extends BaseViewPagerFragemnt<PicturePresenter>
        implements DefaultContract.View<PictureClassify> {



    private  final String[] mTitles= new String[]{
            "性感美女","韩日美女","丝袜美腿","美女照片","美女写真","清纯美女","性感车模"
    };
    List<Fragment> mFragments;

    @Override
    protected BaseFragmentAdapter getViewPagerAdapter() {
        mFragments=new ArrayList<>();
        for(int i=0;i<mTitles.length;i++){
//            传递过去分别是 1-7
            PictureListFragment pictureListFragment = PictureListFragment.newInstance(mTitles[0],i+1);
            mFragments.add(pictureListFragment);

        }
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager
                (), mFragments,mTitles);
        return baseFragmentAdapter;
    }

    @Override
    protected PicturePresenter setPresenter() {
        return new PicturePresenter(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
       /* mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LUtils.i(this.getClass().getSimpleName()+"   position=      "+position);
            }
        });*/
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getPictureClassify();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(PictureClassify pictureClassify) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onLocal(PictureClassify pictureClassify) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(int  i=0;i<mFragments.size();i++){
            Fragment fragment = mFragments.get(i);
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
