package com.xujun.funapp.view.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.xujun.funapp.R;
import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.ActivityCollector;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.ActivityMainBinding;
import com.xujun.funapp.network.NetStateUtils;
import com.xujun.mylibrary.utils.ToastUtils;

import java.util.List;

public class MainActivity extends BaseMVPActivity<ActivityMainBinding, BasePresenter> {

    private RadioGroup mRg;

    int position = 0;

    private Fragment mCurrentFragemnt;

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

        mRg = bind.rg;


    }

    @Override
    protected void initListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_news:
                        position = 1;

                        break;
                    case R.id.rb_picture:
                        position = 2;

                        break;
                    case R.id.rb_setting:
                        position = 3;
                        break;

                    default:
                        position = 0;

                }
                Fragment fragment = MainFragmentFactory.getInstance().get(position);
                showFragment(R.id.fl_content, mCurrentFragemnt, fragment, String.valueOf(position));
                mCurrentFragemnt = fragment;

            }
        });
    }

    @Override
    protected void initData() {
      /*  if(false==NetStateUtils.isMobileConnected(this) &&false==NetStateUtils.isWifi(this)){
            APP.getInstance().showWifiDlg(this);
        }*/
        if (false == NetStateUtils.isNetworkConnected(this)) {
            APP.getInstance().showWifiDlg(this);
        }

        mCurrentFragemnt = MainFragmentFactory.getInstance().get(0);
        replaceFragment(R.id.fl_content, mCurrentFragemnt);


    }

    private boolean finishFlag = true;
    private long oldTime;

    @Override
    public void onBackPressed() {
        if (finishFlag) {
            finishFlag = !finishFlag;
            oldTime = System.currentTimeMillis();
            ToastUtils.show("再按一次退出");
        } else {
            finishFlag = !finishFlag;
            long newTime = System.currentTimeMillis();
            if ((newTime - oldTime) < 2000) {
                ActivityCollector.quit();
                // 强制杀死当前进程
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

    /**
     * 重写 这个方法，将 回传回来的结果传递给Fragemnt的onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = MainFragmentFactory.getInstance().getFragmentList();
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentList.get(position).onActivityResult(requestCode, resultCode, data);
        }
    }
}
