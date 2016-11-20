package com.xujun.funapp.view.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xujun.funapp.R;
import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.ActivityMainBinding;
import com.xujun.funapp.network.NetStateUtils;

import java.util.List;

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
                showFragment(mCurrentFragemnt,fragment,position);
                mCurrentFragemnt=fragment;

            }
        });
    }

    @Override
    protected void initData() {
      /*  if(false==NetStateUtils.isMobileConnected(this) &&false==NetStateUtils.isWifi(this)){
            APP.getInstance().showWifiDlg(this);
        }*/
        if(false== NetStateUtils.isNetworkConnected(this)){
            APP.getInstance().showWifiDlg(this);
        }

        mCurrentFragemnt=MainFragmentFactory.getInstance().get(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,mCurrentFragemnt).commit();




    }


    private void showFragment(Fragment from, Fragment to, int position) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_content, to,String.valueOf(position)).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }

    /**
     * 重写 这个方法，将 回传回来的结果传递给Fragemnt的onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = MainFragmentFactory.getInstance().getFragmentList();
        for(int i=0;i<fragmentList.size();i++){
            fragmentList.get(position).onActivityResult(requestCode,resultCode,data);
        }
    }
}
