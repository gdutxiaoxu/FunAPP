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
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.common.recyclerView.MultiItemAdapter;
import com.xujun.funapp.common.recyclerView.MultiItemTypeSupport;
import com.xujun.funapp.common.util.SPUtils;
import com.xujun.funapp.image.ImageRequestManager;
import com.xujun.funapp.network.retrofit.TnGouNet;
import com.xujun.mylibrary.utils.StringUtils;

import java.util.List;

import static com.xujun.funapp.R.id.iv;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 22:23
 * @ email：gdutxiaoxu@163.com
 */
public class MultiPictureListAdapter extends MultiItemAdapter<PictureListBean.TngouBean> {

    public static int TYPE_ONE = 100;
    public static int TYPE_TWO = 101;

    //    加载图片的tag
    protected Object mPictureTag;

    public MultiPictureListAdapter(Context context, List<PictureListBean.TngouBean> datas,
                                   MultiItemTypeSupport<PictureListBean.TngouBean>
                                           multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, PictureListBean.TngouBean item, int position) {
        PictureListBean.TngouBean tngouBean = mDatas.get(position);
        if (mTMultiItemTypeSupport.getItemType(item,position) == TYPE_ONE) {//第一种Item类型
            setData(holder, item);
        } else {
            setData(holder, item);
        }


    }

    private void setData(BaseRecyclerHolder holder, PictureListBean.TngouBean item) {
        ImageView imageView = (ImageView) holder.getView(iv);
        TextView tvDes = holder.getView(R.id.tv_des);
        TextView tvNums = holder.getView(R.id.tv_image_nums);
        int size = item.size;
        String nums = String.format("图片数量：%d", size);
        String title = StringUtils.getStr(item.title);
        tvDes.setText(title);
        tvNums.setText(nums);
        String imageUrl = TnGouNet.mBaseImageUrl + item.img;
        boolean isIntelligentNoPic = SPUtils.getBoolean(Constants.SPConstants
                .isIntelligentNoPic);

        if (isIntelligentNoPic) {
            if (APP.getInstance().isWifi()) {
                ImageRequestManager.getInstance().display(mContext, imageView, imageUrl);
            } else {
                imageView.setImageDrawable(new ColorDrawable(Color.GRAY));
            }

        } else {
            ImageRequestManager.getInstance().display(mContext, imageView, imageUrl);
        }
    }
}
