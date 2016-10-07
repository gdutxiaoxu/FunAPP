package com.xujun.funapp.view.main.fragment.picture;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureClassify;
import com.xujun.funapp.beans.Test;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.mvp.DefaultContract;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.common.util.MD5;
import com.xujun.funapp.common.util.UIUtils;
import com.xujun.funapp.databinding.FragmentPictureBinding;
import com.xujun.funapp.model.PictureListModel;
import com.xujun.funapp.widget.TabPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 19:46
 * @ email：gdutxiaoxu@163.com
 */
public class PictureFragment extends BindingBaseFragment<FragmentPictureBinding,PicturePresenter>
        implements DefaultContract.View<PictureClassify>{

    private ViewPager mViewPager;
    private TabPagerIndicator mIndicator;

    private static final String[] mTitles= UIUtils.getStringArray(R.array.picture_titles);
    List<Fragment>  mFragments;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initView(FragmentPictureBinding binding) {
        mViewPager = binding.viewPager;
        mIndicator = binding.indicator;

    }

    @Override
    protected void initData() {
        super.initData();
        mFragments=new ArrayList<>();
        for(int i=0;i<mTitles.length;i++){
            PictureListFragment pictureListFragment = PictureListFragment.newInstance(mTitles[0],i+1);
            mFragments.add(pictureListFragment);

        }
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager
                (), mFragments,mTitles);
        mViewPager.setAdapter(baseFragmentAdapter);
        mIndicator.setViewPager(mViewPager);

        mPresenter.getPictureClassify();
        Test.Text2 text2 = new Test.Text2();
        text2.action="getVideo";
        String timeStap = System.currentTimeMillis() + "";
        text2.timestamp= timeStap;
        String encode = MD5.encode("hison" + timeStap);
        text2.token=encode;


        List<Test.Text2> text2s = new ArrayList<Test.Text2>();
        Test test = new Test();
        test.para=text2s;
        PictureListModel.test(test);

    }

    @Override
    protected PicturePresenter setPresenter() {
        return new PicturePresenter(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(PictureClassify pictureClassify) {
        ListUtils.print(pictureClassify.tngou);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onLocal(PictureClassify pictureClassify) {

    }
}
