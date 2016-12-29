package com.xujun.funapp.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.WeChatJingXuan;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.image.ImageRequestManager;
import com.xujun.mylibrary.utils.StringUtils;

import java.util.List;

/**
 * @author xujun  on 2016/12/28.
 * @email gdutxiaoxu@163.com
 */

public class WeChatJingXuanAdapter extends BaseRecyclerAdapter<WeChatJingXuan.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity> {

    public WeChatJingXuanAdapter(Context context, List datas) {
        super(context, R.layout.item_wechat_jingxuan, datas);
    }


    @Override
    public void convert(BaseRecyclerHolder holder, WeChatJingXuan.ShowapiResBodyEntity.PagebeanEntity
            .ContentlistEntity item, int position) {
        ImageView imageView=holder.getView(R.id.iv);
        TextView tvTitle=holder.getView(R.id.tv_title);
        TextView tvTime=holder.getView(R.id.tv_time);
        TextView tvSource=holder.getView(R.id.tv_source);

        String title = StringUtils.getStr(item.title);
        String time = StringUtils.getStr(item.date);
        int length = time.length();
        if(length >5){
            time=time.substring(5,length);
        }
        String imageUrl = StringUtils.getStr(item.contentImg);
        String source = StringUtils.getStr(item.userName);
        String readNum = StringUtils.getStr(item.read_num+"");
        String likeNum = StringUtils.getStr(item.like_num+"");

        ImageRequestManager.getInstance().display(mContext,imageView,imageUrl);
        tvTime.setText(time);
        tvTitle.setText(title);
        tvSource.setText(source);

    }
}
