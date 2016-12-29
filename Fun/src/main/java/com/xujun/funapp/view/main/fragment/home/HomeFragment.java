package com.xujun.funapp.view.main.fragment.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.YiYuanNewsClassify.ShowapiResBodyEntity.ChannelListEntity;
import com.xujun.funapp.common.BaseFragmentAdapter;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.databinding.FragmentHomeBinding;
import com.xujun.funapp.presenter.HomeContract;
import com.xujun.funapp.view.main.fragment.news.YYNewsListFragment;
import com.xujun.funapp.view.wechat.WechatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class HomeFragment extends BindingBaseFragment<FragmentHomeBinding, HomeContract.Presenter>
        implements HomeContract.View{

    private ViewPager mViewpager;
    private List<ChannelListEntity> mData;
    private ArrayList<Fragment> mFragments;

    String[] mTitles;
    private TabLayout mTablayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(FragmentHomeBinding binding) {
        mViewpager = binding.viewpager;
        mTablayout = binding.tablayout;

    }

    @Override
    protected void initListener() {
        super.initListener();
        mBinding.wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(WechatActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mData = new Info().getData();
        mFragments = new ArrayList<>();
        int size = mData.size();
        mTitles=new String[size];
        for(int i = 0; i< size; i++){
            ChannelListEntity channelListEntity = mData.get(i);
            YYNewsListFragment yyNewsListFragment = YYNewsListFragment.newInstance(channelListEntity, i);
            mFragments.add(yyNewsListFragment);
            mTitles[i]=channelListEntity.name;
        }
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager
                (), mFragments, mTitles);
        mViewpager.setAdapter(baseFragmentAdapter);
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected HomeContract.Presenter setPresenter() {
        return new HomeContract.Presenter(this);
    }


}
