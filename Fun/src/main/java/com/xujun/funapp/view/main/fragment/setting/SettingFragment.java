package com.xujun.funapp.view.main.fragment.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.xujun.funapp.R;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.Constants.IntentConstants;
import com.xujun.funapp.common.Constants.SPConstants;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.common.util.WriteLogUtil;
import com.xujun.funapp.databinding.FragmentSettingBinding;
import com.xujun.funapp.view.location.CityPickerActivity;
import com.xujun.funapp.view.location.GPSLocationActivity;
import com.xujun.funapp.widget.SettingClickItem;
import com.xujun.funapp.widget.SettingSwitchItem;

import static com.xujun.funapp.common.Constants.SPConstants.city;

/**
 * @ explain:
 * @ author：xujun on 2016/11/2 10:30
 * @ email：gdutxiaoxu@163.com
 */
public class SettingFragment extends BindingBaseFragment<FragmentSettingBinding, BasePresenter> {

    private SettingSwitchItem mSsiIsIntelligentNoPic;
    private SettingSwitchItem mSsiNightMode;

    private SettingClickItem mSciLocation;
    private String mCity;
    private SettingClickItem mSciGPSlocation;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(FragmentSettingBinding binding) {
        mSsiIsIntelligentNoPic = binding.ssiIsIntelligentNoPic;
        mSsiNightMode = binding.ssiNightMode;
        mSciLocation = binding.sciLocation;
        mSciGPSlocation = binding.sciGPSlocation;

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSsiIsIntelligentNoPic.setOnChangedListenr(new SettingSwitchItem.onChangeListener() {
            @Override
            public void onChange(boolean checked) {
                SPUtils.put(SPConstants.isIntelligentNoPic, checked);
            }
        });
        mSsiNightMode.setOnChangedListenr(new SettingSwitchItem.onChangeListener() {
            @Override
            public void onChange(boolean checked) {
                SPUtils.put(SPConstants.isNightMode, checked);
                getActivity().recreate();
            }
        });

        mSciLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CityPickerActivity.class);
                intent.putExtra(IntentConstants.KEY_PICKED_CITY,mCity);
                getActivity().startActivityForResult(intent,0);
            }
        });
        mSciGPSlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(GPSLocationActivity.class);
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
        mCity = SPUtils.getString(city);
        mSciLocation.setContent(mCity);

    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    /**
     * 接收Activity回传回来的result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch (resultCode) {
            case IntentConstants.RESULT_CODE_PICK_CITY:
                String city = data.getStringExtra(IntentConstants.KEY_PICKED_CITY);
                if(!TextUtils.isEmpty(city)){
                    mSciLocation.setContent(city);
                    SPUtils.put(IntentConstants.KEY_PICKED_CITY,city);
                }

                break;
        }
    }

    @Override
    public void fetchData() {
        super.fetchData();
        WriteLogUtil.i(" fetchData=");
    }
}
