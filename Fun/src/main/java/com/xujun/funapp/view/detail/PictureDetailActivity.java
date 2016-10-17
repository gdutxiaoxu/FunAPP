package com.xujun.funapp.view.detail;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.ActivityPictureDetailBinding;

public class PictureDetailActivity extends
        BaseMVPActivity<ActivityPictureDetailBinding,BasePresenter> {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initView(ActivityPictureDetailBinding bind) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_picture_detail;
    }
}
