package com.xujun.funapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.News;
import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.recyclerView.LayoutMangerType;
import com.xujun.funapp.common.util.GlideUtils;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.common.util.StringUtils;
import com.xujun.funapp.common.util.WriteLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/28 19:26
 * @ email：gdutxiaoxu@163.com
 */
public class NewsGridAdapter extends BaseRecyclerAdapter<News.NewslistBean> {
    Object pictureTag;
    LayoutMangerType mType;
    private ArrayList<Integer> mHeights;

    public void getRandomHeight(List<News.NewslistBean> mList){
        if(mHeights==null){
            mHeights = new ArrayList<>();
        }else{
            mHeights.clear();
        }

        for(int i=0; i < mList.size();i++){
            //随机的获取一个范围为200-600直接的高度
            mHeights.add((int) (200+Math.random()*400));
        }
    }

    public void setType(LayoutMangerType mangerType) {
        initType(mangerType);

    }

    private void initType(LayoutMangerType mangerType) {
        this.mType = mangerType;
        if (mType == LayoutMangerType.Linear) {
            mItemLayoutId = R.layout.item_news_list;
        } else if (mType == LayoutMangerType.Grid) {
            mItemLayoutId = R.layout.item_news_list_grid;
        } else {
            mItemLayoutId = R.layout.item_news_list_strag;
        }
    }

    public NewsGridAdapter(Context context, List<News.NewslistBean> datas, Object pictureTag,
                           LayoutMangerType type) {

        super(context, R.layout.item_news_list, datas);
        this.mType = type;
        this.pictureTag = pictureTag;
        initType(type);

    }

    @Override
    public void convert(BaseRecyclerHolder holder, News.NewslistBean item, int position) {
        if (mType == LayoutMangerType.Linear) {
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvTitle = holder.getView(R.id.tv_title);
            TextView tvSource = holder.getView(R.id.tv_source);
            ImageView iv = holder.getView(R.id.iv);

            String title = StringUtils.getStr(item.title);
            String picUrl = StringUtils.getStr(item.picUrl);
            String time = StringUtils.getStr(item.ctime);
            String source = StringUtils.getStr(item.description);
            tvTime.setText(time);
            tvSource.setText(source);
            tvTitle.setText(title);
            // 是否开启智能无图模式，true表示开启智能无图模式
            boolean isIntelligentNoPic = SPUtils.getBoolean(Constants.SPConstants
                    .isIntelligentNoPic);
            WriteLogUtil.i("isIntelligentNoPic=" + isIntelligentNoPic);
            if (isIntelligentNoPic) {
                if (APP.getInstance().isWifi()) {
                    GlideUtils.display(mContext, iv, picUrl);
                } else {
                    iv.setImageDrawable(new ColorDrawable(Color.GRAY));
                }

            } else {
                GlideUtils.display(mContext, iv, picUrl);
            }
        } else if (mType == LayoutMangerType.Grid) {


            TextView tvTitle = holder.getView(R.id.tv_title);

            ImageView iv = holder.getView(R.id.iv);

            String title = StringUtils.getStr(item.title);
            String picUrl = StringUtils.getStr(item.picUrl);


            tvTitle.setText(title);
            // 是否开启智能无图模式，true表示开启智能无图模式
            boolean isIntelligentNoPic = SPUtils.getBoolean(Constants.SPConstants
                    .isIntelligentNoPic);
            WriteLogUtil.i("isIntelligentNoPic=" + isIntelligentNoPic);
            if (isIntelligentNoPic) {
                if (APP.getInstance().isWifi()) {
                    GlideUtils.display(mContext, iv, picUrl);
                } else {
                    iv.setImageDrawable(new ColorDrawable(Color.GRAY));
                }

            } else {
                GlideUtils.display(mContext, iv, picUrl);
            }

        }else{
            View convertView = holder.getConvertView();
            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            layoutParams.height=mHeights.get(position);
            convertView.setLayoutParams(layoutParams);
            TextView tvTitle = holder.getView(R.id.tv_title);
            ImageView iv = holder.getView(R.id.iv);

            String title = StringUtils.getStr(item.title);
            String picUrl = StringUtils.getStr(item.picUrl);


            tvTitle.setText(title);
            // 是否开启智能无图模式，true表示开启智能无图模式
            boolean isIntelligentNoPic = SPUtils.getBoolean(Constants.SPConstants
                    .isIntelligentNoPic);
            WriteLogUtil.i("isIntelligentNoPic=" + isIntelligentNoPic);
            if (isIntelligentNoPic) {
                if (APP.getInstance().isWifi()) {
                    GlideUtils.display(mContext, iv, picUrl);
                } else {
                    iv.setImageDrawable(new ColorDrawable(Color.GRAY));
                }

            } else {
                GlideUtils.display(mContext, iv, picUrl);
            }

        }


    }
}
