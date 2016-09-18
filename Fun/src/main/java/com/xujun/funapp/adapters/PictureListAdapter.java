package com.xujun.funapp.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xujun.funapp.R;
import com.xujun.funapp.beans.PictureListBean;
import com.xujun.funapp.common.recyclerView.BaseRecyclerAdapter;
import com.xujun.funapp.common.recyclerView.BaseRecyclerHolder;
import com.xujun.funapp.network.Network;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/9/17 22:23
 * @ email：gdutxiaoxu@163.com
 */
public class PictureListAdapter extends BaseRecyclerAdapter<PictureListBean.TngouBean> {

    public PictureListAdapter(Context context, List<PictureListBean.TngouBean> datas) {
        super(context, R.layout.item_picture_list, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, PictureListBean.TngouBean item, int position) {
        ImageView imageView = (ImageView) holder.getView(R.id.iv);
        //        ImageLoaderUtils.loadImageView(mContext, Network.mBaseImageUrl+item.img,
        // imageView);
        Picasso.with(mContext).load(Network.mBaseImageUrl + item.img).resizeDimen(
                R.dimen.iv_width_pic_list, R.dimen.iv_height_pic_list).into(imageView);

    }
}
