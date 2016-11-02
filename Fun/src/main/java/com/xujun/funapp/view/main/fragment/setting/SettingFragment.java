package com.xujun.funapp.view.main.fragment.setting;

import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.Constants.SPConstants;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.databinding.FragmentSettingBinding;

/**
 * @ explain:
 * @ author：xujun on 2016/11/2 10:30
 * @ email：gdutxiaoxu@163.com
 */
public class SettingFragment extends BindingBaseFragment<FragmentSettingBinding, BasePresenter> {

    private Switch mSwitchWifiPic;
    private TextView mTvWifiPic;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(FragmentSettingBinding binding) {
        mSwitchWifiPic = binding.switchWifiPic;
        mTvWifiPic = binding.tvWifiPic;

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwitchWifiPic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(SPConstants.isIntelligentNoPic,isChecked);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        boolean isIntelligentNoPic = SPUtils.getBoolean(SPConstants.isIntelligentNoPic);
        mSwitchWifiPic.setChecked(isIntelligentNoPic);
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
