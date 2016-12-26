package com.xujun.funapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.YYNews.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.util.ListUtils;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.common.util.StringUtils;
import com.xujun.funapp.image.ImageRequestManager;
import com.xujun.mylibrary.UriUtils;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/28 19:26
 * @ email：gdutxiaoxu@163.com
 */
public class YYNewsListGridAdapter extends BaseRecyclerAdapter<ContentlistEntity> {
    Object pictureTag;

    public YYNewsListGridAdapter(Context context, List<ContentlistEntity> datas, Object
            pictureTag) {
        super(context, R.layout.item_yy_news_list_grid, datas);
        this.pictureTag = pictureTag;


    }

    @Override
    public void convert(BaseRecyclerHolder holder, ContentlistEntity item, int position) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        ImageView iv = holder.getView(R.id.iv);

        String title = StringUtils.getStr(item.title);
        List<ContentlistEntity.ImageurlsEntity> imageurls = item.imageurls;
        boolean havePic = item.havePic;
        String picUrl = null;
        if (!ListUtils.isEmpty(imageurls)) {//有图片
            picUrl = StringUtils.getStr(imageurls.get(0).url);
        } else {//没有图片

        }

        tvTitle.setText(title);
        // 是否开启智能无图模式，true表示开启智能无图模式
        boolean isIntelligentNoPic = SPUtils.getBoolean(Constants.SPConstants.isIntelligentNoPic);
        //            WriteLogUtil.i("isIntelligentNoPic=" + isIntelligentNoPic);
        if (isIntelligentNoPic) {
            if (APP.getInstance().isWifi()) {
                display(iv, havePic, picUrl);

            } else {
                iv.setImageDrawable(new ColorDrawable(Color.GRAY));
            }

        } else {
            display(iv, havePic, picUrl);
        }
    }

    private void display(ImageView iv, boolean havePic, String picUrl) {
        if (havePic) {
            ImageRequestManager.getInstance().display(mContext, iv, picUrl);
        } else {
            ImageRequestManager.getInstance().display(mContext, iv, UriUtils.resourceIdToUri
                    (mContext, R.mipmap.tangyang11));
        }
    }
}
