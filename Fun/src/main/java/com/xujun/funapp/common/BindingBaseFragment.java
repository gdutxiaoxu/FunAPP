package com.xujun.funapp.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xujun.funapp.common.mvp.BasePresenter;
import com.xujun.funapp.common.util.LUtils;

import org.simple.eventbus.EventBus;

import static com.xujun.funapp.common.Constants.IntentConstants.DEFAULT_PARCEABLE_NAME;

/**
 * Created by xujun、on 2016/4/26.
 */
public abstract class BindingBaseFragment<V extends ViewDataBinding, P extends BasePresenter>
        extends Fragment {

    protected V mBinding;
    protected P mPresenter;
    protected Context mContext;
    public static final String TAG = "tag";
    protected boolean mIsVisiableToUser;
    protected boolean mIsViewInitiated;
    protected boolean mIsDataInitiated;
    protected View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = setPresenter();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        Log.i(TAG, this.getClass().getSimpleName()+" onCreateView" );
        if (hasEventBus()) {
            EventBus.getDefault().register(this);
        }

        mBinding = DataBindingUtil.inflate(inflater, getContentViewLayoutID(), container, false);
        initView(mBinding);
        LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>onCreateView()");
        mView=mBinding.getRoot();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mPresenter != null) {
            mPresenter.start();
        }
        mIsViewInitiated=true;
        initListener();
        initData();
        LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>onActivityCreated()");
    }

    protected void initData() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisiableToUser=true;
        LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>setUserVisibleHint()"+isVisibleToUser);
        prepareFetchData();

    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    /**
     * 如果想等到界面可见的时候在加载网络数据，可以在这个方法里面执行
     */
    public  void fetchData(){

    }

    /***
     * 传true可以强制刷新
     * @param forceUpdate
     * @return
     */
    public boolean prepareFetchData(boolean forceUpdate) {
        LUtils.d(this.getClass().getSimpleName()+">>>>>>>>>>>prepareFetchData()"+forceUpdate);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.i(TAG, this.getClass().getSimpleName() +" onDestroyView" );
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        if (mPresenter != null)
            mPresenter.stop();
    }


    protected void showToast(int resID) {
        Toast.makeText(getActivity(), resID, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    protected void replace(Fragment target,int id) {
        if (getActivity() == null)
            return;
        if (getActivity().isFinishing())
            return;
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(id, target).commit();
    }

    public void readyGo(Class<?> clazz, Parcelable parcelable) {
        this.readyGo(clazz, DEFAULT_PARCEABLE_NAME,parcelable);
    }




    public void readyGo(Class<?> clazz, String name, Parcelable parcelable) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != parcelable) {
            intent = intent.putExtra(name, parcelable);
        }
        startActivity(intent);
    }

    protected  <T>  T  checkNotNull(T t) {
        if(t==null){
            throw new NullPointerException();
        }
        return t;
    }



    protected abstract int getContentViewLayoutID();

    protected abstract void initView(V binding);

    protected abstract P setPresenter();


}
