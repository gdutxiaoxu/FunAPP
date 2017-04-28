package com.xujun.funapp.view.search;

import android.content.Context;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;

import java.util.List;

/**
 * @author meitu.xujun  on 2017/4/27 13:48
 * @version 0.1
 */

public class SearchAdapter extends BaseRecyclerAdapter<String> {

    public SearchAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_search, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        TextView tv=holder.getView(R.id.tv);
        String s = mDatas.get(position);
        tv.setText(s);

    }
}
