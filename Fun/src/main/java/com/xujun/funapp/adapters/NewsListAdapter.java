package com.xujun.funapp.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.News;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.util.GlideUtils;
import com.xujun.funapp.common.util.StringUtils;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/28 19:26
 * @ email：gdutxiaoxu@163.com
 */
public class NewsListAdapter extends BaseRecyclerAdapter<News.NewslistBean> {
    Object pictureTag;

    public NewsListAdapter(Context context,  List<News.NewslistBean> datas,Object pictureTag) {
        super(context, R.layout.item_news_list, datas);
        this.pictureTag=pictureTag;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, News.NewslistBean item, int position) {
        TextView tvTime=holder.getView(R.id.tv_time);
        TextView tvTitle=holder.getView(R.id.tv_title);
        TextView  tvSource=holder.getView(R.id.tv_source);
        ImageView iv=holder.getView(R.id.iv);

        String title = StringUtils.getStr(item.title);
        String picUrl = StringUtils.getStr(item.picUrl);
        String time = StringUtils.getStr(item.ctime);
        String source = StringUtils.getStr(item.description);
        tvTime.setText(time);
        tvSource.setText(source);
        tvTitle.setText(title);
//        WriteLogUtil.i("mContext"+(mContext==null));
//        WriteLogUtil.i("mContext"+(pictureTag==null));
//        WriteLogUtil.i("picUrl"+picUrl);
//        ImageUtils.display(mContext,iv,picUr,pictureTag);
        GlideUtils.display(mContext,iv,picUrl);

    }
}
