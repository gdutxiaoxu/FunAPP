package com.xujun.funapp.view.detail;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xujun.funapp.beans.PictureDetailBean;
import com.xujun.funapp.common.util.GlideUtils;
import com.xujun.funapp.network.TnGouNet;

/**
 * @author xujun  on 2016/12/12.
 * @email gdutxiaoxu@163.com
 */

public class PictureDetailFragment extends Fragment {

    Context mContext;

    public static final  String Key="key";
    private Parcelable mParcelable;
    private ImageView mImageView;

    public static PictureDetailFragment newInstance(Parcelable parcelable){
        PictureDetailFragment pictureDetailFragment = new PictureDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Key,parcelable);
        pictureDetailFragment.setArguments(bundle);
        return pictureDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mImageView = new ImageView(mContext);
        mImageView.setLayoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return mImageView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null){
            mParcelable = arguments.getParcelable(Key);
        }
        PictureDetailBean.ListBean listBean = (PictureDetailBean.ListBean) mParcelable;
        String url = TnGouNet.mBaseImageUrl+ listBean.src;
        GlideUtils.display(mContext,mImageView,url);


    }
}
