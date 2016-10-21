package com.xujun.funapp.view.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.ImageUtils;
import com.xujun.funapp.databinding.FragmentImageBinding;

public class ImageFragment extends BindingBaseFragment<FragmentImageBinding,BasePresenter> {

    private ImageView mImage;
    private TextView mTvPage;

    PictureDetailBean.ListBean mListBean;

    public static ImageFragment  newInstance(PictureDetailBean.ListBean listBean){
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentConstants.DEFAULT_PARCEABLE_NAME,listBean);
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_image;
    }



    @Override
    protected void initView(FragmentImageBinding binding) {
        Bundle arguments = getArguments();
        if(arguments!=null){
            mListBean= arguments.getParcelable(Constants.IntentConstants.DEFAULT_PARCEABLE_NAME);
            checkNotNull(mListBean);
        }
        mImage = binding.image;
        mTvPage = binding.tvPage;
        String  imageURl= Constants.URLConstants.URL_IMAGE_BASE+mListBean.src;
        ImageUtils.display(mContext,mImage,imageURl);

    }

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
