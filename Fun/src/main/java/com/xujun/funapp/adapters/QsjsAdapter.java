package com.xujun.funapp.adapters;

import android.content.Context;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.Qsjs;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.mylibrary.utils.StringUtils;

import java.util.List;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class QsjsAdapter extends BaseRecyclerAdapter<Qsjs> {

    public QsjsAdapter(Context context, List datas) {
        super(context, R.layout.item_qsjs, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, Qsjs item, int position) {

        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvDate = holder.getView(R.id.tv_date);
        String title = StringUtils.getStr(item.title);

        String date = StringUtils.getStr(item.date);

        tvTitle.setText(title);
        tvDate.setText(date);

    }
}
