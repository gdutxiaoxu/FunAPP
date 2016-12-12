package com.xujun.funapp.view.main.fragment.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xujun.funapp.adapters.PictureListAdapter;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.BaseListFragment;
import com.xujun.funapp.common.Constants.IntentConstants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.util.LUtils;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.model.PictureListModel;
import com.xujun.funapp.view.detail.PictureDetailActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 20:17
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListFragment extends BaseListFragment<PictureListPresenter>
        implements PictureListContract.View<PictureListBean> {

    private static final String TITLE = "title";
    private static final String ID = "id";

    private static String[] tags = PictureListModel.tags;
    private ArrayList<PictureListBean.TngouBean> mDatas;
    private PictureListAdapter mAdapter;

    protected int mId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LUtils.d(this.getClass().getSimpleName()+"onCreate  mId"+mId);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LUtils.d(this.getClass().getSimpleName()+"onCreateView  mId"+mId);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LUtils.d(this.getClass().getSimpleName()+"onDestroyView  mId"+mId);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        LUtils.d(this.getClass().getSimpleName()+"onDestroyView  mId"+mId);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LUtils.d(this.getClass().getSimpleName()+"onDestroy  mId"+mId);
        super.onDestroy();
    }

    public static PictureListFragment newInstance(String title, int id) {
        PictureListFragment pictureListFragment = new PictureListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putInt(ID, id);
        pictureListFragment.setArguments(bundle);
        return pictureListFragment;
    }

    @Override
    protected void initAru() {
        super.initAru();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mId = arguments.getInt(ID);
//            WriteLogUtil.i("mId=" + mId);
        }

    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        mDatas = new ArrayList<>();
        mAdapter = new PictureListAdapter(mContext, mDatas, this);
        return mAdapter;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RecyclerView.ViewHolder holder, int position) {
                PictureListBean.TngouBean tngouBean = mDatas.get(position);
                readyGo(PictureDetailActivity2.class,
                        IntentConstants.DEFAULT_PARCEABLE_NAME, tngouBean);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        //  如果是第0个Item，初始化的时候主动去刷新，不是第0个Item，等到界面的时候会调用fetech方法手动去刷新
        if(isFirstItem()){
            beginRefresh();
        }
    }

    @Override
    protected PictureListPresenter setPresenter() {
        return new PictureListPresenter(this);
    }

    protected void getNextPageData() {
        mPresenter.getPictureList(String.valueOf(++mPage), String.valueOf(mRows), mId);
    }

    protected void getFirstPageData() {
        mPage = 1;
        mPresenter.getPictureList(String.valueOf(mPage), String.valueOf(mRows), mId);
    }

    protected boolean isFirstItem(){
        return mId==1;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccess(PictureListBean o) {
        List<PictureListBean.TngouBean> tngou = o.tngou;
        if (false == ListUtils.isEmpty(tngou)) {
            handleResult(tngou, RequestResult.success);
        } else {
            handleResult(tngou, RequestResult.empty);
        }


    }

    @Override
    public void onError(Throwable throwable) {

        handleResult(null, RequestResult.error);
    }

    @Override
    public void onLocal(PictureListBean o) {

    }
}
