package com.xujun.funapp.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.ActivityMainBinding;

public class MainActivity extends BaseMVPActivity<ActivityMainBinding,BasePresenter> {

    private FrameLayout mFlContent;
    private RadioGroup mRg;
    static final int[] checkIds=new int[]{
            R.id.rb_home,R.id.rb_news,R.id.rb_picture,R.id.rb_setting
    };

    int position=0;

    private Fragment mCurrentFragemnt;
    private RadioButton mRbHome;
    private RadioButton mRbNews;
    private RadioButton mRbPicture;
    private RadioButton mRbSetting;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(ActivityMainBinding bind) {
        mFlContent = bind.flContent;
        mRg = bind.rg;
        mRbHome = bind.rbHome;
        mRbNews = bind.rbNews;
        mRbPicture = bind.rbPicture;
        mRbSetting = bind.rbSetting;

    }

    @Override
    protected void initListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        position=0;
                        break;
                      case R.id.rb_news:
                        position=1;

                        break;
                      case R.id.rb_picture:
                        position=2;

                        break;
                      case R.id.rb_setting:
                        position=3;
                        break;

                      default:
                          position=0;

                }
                Fragment fragment = MainFragmentFactory.getInstance().get(position);
                showFragment(mCurrentFragemnt,fragment);
                mCurrentFragemnt=fragment;

            }
        });
    }

    @Override
    protected void initData() {



        mCurrentFragemnt=MainFragmentFactory.getInstance().get(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,mCurrentFragemnt).commit();

    }


    private void showFragment(Fragment from, Fragment to) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }


}
