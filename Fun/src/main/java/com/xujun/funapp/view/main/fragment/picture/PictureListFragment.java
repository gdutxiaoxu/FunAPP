package com.xujun.funapp.view.main.fragment.picture;

import android.os.Bundle;

import com.xujun.funapp.R;
import com.xujun.funapp.common.BindingBaseFragment;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.databinding.FragmentPictureListBinding;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListFragment extends BindingBaseFragment<FragmentPictureListBinding,BasePresenter> {

    private  static  final String TITLE="title";
    private String mTitle="";

    public static PictureListFragment newInstance(String title){
        PictureListFragment pictureListFragment = new PictureListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        pictureListFragment.setArguments(bundle);
        return pictureListFragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_picture_list;
    }

    @Override
    protected void initView(FragmentPictureListBinding binding) {
        Bundle arguments = getArguments();
        if(arguments!=null){
            mTitle = arguments.getString(TITLE);
        }

    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
