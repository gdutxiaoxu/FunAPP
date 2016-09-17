package com.xujun.funapp.adapters;

import android.content.Context;

import com.xujun.funapp.R;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 22:23
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListAdapter extends BaseRecyclerAdapter<String> {

    public PictureListAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_picture_list, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {

    }
}
