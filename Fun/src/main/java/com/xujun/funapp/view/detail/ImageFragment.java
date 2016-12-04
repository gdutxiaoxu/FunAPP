package com.xujun.funapp.view.detail;

import android.os.Bundle;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.GlideUtils;
import com.xujun.funapp.databinding.FragmentImageBinding;

import ru.xujun.touchgallery.TouchView.TouchImageView;

public class ImageFragment extends BindingBaseFragment<FragmentImageBinding,BasePresenter> {



    PictureDetailBean.ListBean mListBean;
    private TouchImageView mImage;

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

        String  imageURl= Constants.URLConstants.URL_IMAGE_BASE+mListBean.src;
//        ImageUtils.display(mContext,mImage,imageURl);
        GlideUtils.display(mContext,mImage,imageURl);

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
