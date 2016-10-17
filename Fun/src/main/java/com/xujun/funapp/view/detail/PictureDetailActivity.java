package com.xujun.funapp.view.detail;

import android.webkit.WebView;
import android.widget.ImageView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.mvp.BaseMVPActivity;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.ActivityPictureDetailBinding;

public class PictureDetailActivity extends
        BaseMVPActivity<ActivityPictureDetailBinding,BasePresenter> {

    private ImageView mIvTop;
    private WebView mWebView;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initView(ActivityPictureDetailBinding bind) {
        mIvTop = bind.ivTop;
        mWebView = bind.webView;

    }

    @Override
    protected void initData() {
        super.initData();
        
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_picture_detail;
    }
}
