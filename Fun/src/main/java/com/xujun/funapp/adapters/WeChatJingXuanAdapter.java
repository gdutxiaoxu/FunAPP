package com.xujun.funapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;

import java.util.List;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WeChatJingXuanAdapter extends BaseRecyclerAdapter {

    public WeChatJingXuanAdapter(Context context, List datas) {
        super(context, R.layout.item_wechat_jingxuan, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, Object item, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
