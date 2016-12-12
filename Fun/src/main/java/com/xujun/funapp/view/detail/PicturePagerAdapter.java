package com.xujun.funapp.view.detail;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.util.GlideUtils;

import java.util.List;

import ru.xujun.touchgallery.GalleryWidget.BasePagerAdapter;
import ru.xujun.touchgallery.GalleryWidget.GalleryViewPager;
import ru.xujun.touchgallery.TouchView.TouchImageView;

/**
 * @author xujun  on 2016/12/4.
 * @email gdutxiaoxu@163.com
 */

public class PicturePagerAdapter extends BasePagerAdapter {

    public static  final String TAG="xujun";

    public PicturePagerAdapter(Context context, List<String> resources) {
        super(context, resources);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager)container).mCurrentView = (TouchImageView)object;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position){
        final TouchImageView iv = new TouchImageView(mContext);
//        iv.setUrl(mResources.get(position));

        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = mResources.get(position);
        Log.i(TAG, "instantiateItem: url="+url);
        GlideUtils.display(mContext,iv, url, R.drawable.ic_progress);

        collection.addView(iv, 0);
        return iv;
    }
}
