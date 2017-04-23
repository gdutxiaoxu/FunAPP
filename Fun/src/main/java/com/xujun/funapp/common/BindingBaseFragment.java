package com.xujun.funapp.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xujun.funapp.BaseFragment;
import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.mylibrary.utils.ViewUtils;

import org.simple.eventbus.EventBus;

/**
 * Created by xujun、on 2016/4/26.
 */
public abstract class BindingBaseFragment<V extends ViewDataBinding, P extends BasePresenter>
        extends BaseFragment {

    protected V mBinding;
    protected P mPresenter;
    protected Context mContext;
    public static final String TAG = "tag";
    protected boolean mIsVisiableToUser = false;
    protected boolean mIsViewInitiated = false;
    protected boolean mIsDataInitiated = false;
    protected View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = setPresenter();
        initAru();

        if (hasEventBus()) {
            EventBus.getDefault().register(this);
        }

    }

    protected void initAru() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // LUtils.d(this.getClass().getSimpleName()+" onCreateView" );
        if (mView == null) {
            mBinding = DataBindingUtil.inflate(inflater, getContentViewLayoutID(), container,
                    false);
            mView = mBinding.getRoot();
            initView(mBinding);
        } else {
            ViewUtils.removeParent(mView);
        }
        initEvent();
        mIsViewInitiated = true;
        return mView;
    }

    protected void initEvent() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mPresenter != null) {
            mPresenter.start();
        }

        prepareFetchData();
        initListener();
        initData();
        // LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>onActivityCreated()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  LUtils.d( this.getClass().getSimpleName() +" onDestroy" );
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        if (mPresenter != null) mPresenter.stop();
    }

    protected void initData() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisiableToUser = isVisibleToUser;
       /* LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>setUserVisibleHint()
       "+isVisibleToUser);*/
        prepareFetchData();

    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    /**
     * 注意：
     * 这个方法是对ViewPager里面嵌套多个Fragment而言的
     * 如果想等到界面可见的时候在加载网络数据，可以在这个方法里面执行。
     * 同时如果是Fragment+ViewPager里面嵌套多个Fragment的话，ViewPager里面的第0个
     * Fragment必须 手动去调用fetchData（）方法
     * <p>
     * 这就是 人们常说的懒加载方法
     * <p>
     * 对于单纯用add，show，hide方法显示 的Fragemnt是不会调用的
     */
    public void fetchData() {

    }

    /***
     * 传true可以强制刷新
     *
     * @param forceUpdate
     * @return
     */
    public boolean prepareFetchData(boolean forceUpdate) {
      /*  LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>prepareFetchData()"+forceUpdate);
        LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>mIsViewInitiated"+mIsViewInitiated);
        LUtils.d(this.getClass().getSimpleName()
        +">>>>>>>>>>>mIsVisiableToUser"+mIsVisiableToUser);*/
        if (mIsVisiableToUser && mIsViewInitiated && (!mIsDataInitiated || forceUpdate)) {
            fetchData();
            mIsDataInitiated = true;
            return true;
        }
        return false;
    }

    protected boolean hasEventBus() {
        return false;
    }

    protected void initListener() {
    }




    protected abstract int getContentViewLayoutID();

    //    这个方法只有在View第一次创建的时候才会回调
    protected abstract void initView(V binding);

    protected abstract P setPresenter();


}
