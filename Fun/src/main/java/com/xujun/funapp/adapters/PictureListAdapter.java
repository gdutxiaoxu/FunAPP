package com.xujun.funapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.APP;
import com.xujun.funapp.common.Constants;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.util.GlideUtils;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.common.util.StringUtils;
import com.xujun.funapp.network.TnGouNet;

import java.util.List;

import static com.xujun.funapp.R.id.iv;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 22:23
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListAdapter extends BaseRecyclerAdapter<PictureListBean.TngouBean> {

    //    加载图片的tag
    protected Object mPictureTag;

    public PictureListAdapter(Context context, List<PictureListBean.TngouBean> datas, Object
            pictureTag) {
        super(context, R.layout.item_picture_list, datas);
        this.mPictureTag = pictureTag;
    }

    @Override
    public void convert(BaseRecyclerHolder holder, PictureListBean.TngouBean item, int position) {
        ImageView imageView = (ImageView) holder.getView(iv);
        TextView tvDes = holder.getView(R.id.tv_des);
        TextView tvNums = holder.getView(R.id.tv_image_nums);
        //        ImageLoaderUtils.loadImageView(mContext, TnGouNet.mBaseImageUrl+item.img,
        // imageView);

        int size = item.size;
        String nums = String.format("图片数量：%d", size);
        String title = StringUtils.getStr(item.title);

        tvDes.setText(title);
        tvNums.setText(nums);


        String imageUrl = TnGouNet.mBaseImageUrl + item.img;
        boolean isIntelligentNoPic = SPUtils.getBoolean(Constants.SPConstants.isIntelligentNoPic);
//        WriteLogUtil.i("isIntelligentNoPic="+isIntelligentNoPic);
        if (isIntelligentNoPic) {
            if (APP.getInstance().isWifi()) {
                GlideUtils.display(mContext, imageView, imageUrl);
            } else {
                imageView.setImageDrawable(new ColorDrawable(Color.GRAY));
            }

        } else {
            GlideUtils.display(mContext, imageView, imageUrl);
        }

        //        ImageUtils.display(mContext,imageView,imageUrl,mPictureTag);
        //        Picasso.with(mContext).load(TnGouNet.mBaseImageUrl + item.img).resizeDimen(
        //                R.dimen.iv_width_pic_list, R.dimen.iv_height_pic_list).into(imageView);

    }
}
