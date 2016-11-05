package com.xujun.funapp.view.main.fragment.setting;

import com.xujun.funapp.R;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.Constants.SPConstants;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.databinding.FragmentSettingBinding;
import com.xujun.funapp.widget.SwitchSettingItem;

/**
 * @ explain:
 * @ author：xujun on 2016/11/2 10:30
 * @ email：gdutxiaoxu@163.com
 */
public class SettingFragment extends BindingBaseFragment<FragmentSettingBinding, BasePresenter> {

    private SwitchSettingItem mSsiIsIntelligentNoPic;
    private SwitchSettingItem mSsiNightMode;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(FragmentSettingBinding binding) {
        mSsiIsIntelligentNoPic = binding.ssiIsIntelligentNoPic;
        mSsiNightMode = binding.ssiNightMode;


    }

    @Override
    protected void initListener() {
        super.initListener();
        mSsiIsIntelligentNoPic.setOnChangedListenr(new SwitchSettingItem.onChangeListener() {
            @Override
            public void onChange(boolean checked) {
                SPUtils.put(SPConstants.isIntelligentNoPic, checked);
            }
        });
        mSsiNightMode.setOnChangedListenr(new SwitchSettingItem.onChangeListener() {
            @Override
            public void onChange(boolean checked) {
                SPUtils.put(SPConstants.isNightMode, checked);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        boolean isIntelligentNoPic = SPUtils.getBoolean(SPConstants.isIntelligentNoPic);
        mSsiIsIntelligentNoPic.setChecked(isIntelligentNoPic);
        boolean isNightMode = SPUtils.getBoolean(SPConstants.isNightMode);
        mSsiNightMode.setChecked(isNightMode);

    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
