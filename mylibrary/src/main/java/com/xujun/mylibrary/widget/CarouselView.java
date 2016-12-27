package com.xujun.mylibrary.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xujun.mylibrary.ConvertUtils;
import com.xujun.mylibrary.R;

/**
 * user: yangqiangyu on 1/18/16 10:32
 * csdn: http://blog.csdn.net/yissan
 */
public class CarouselView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private Context context;

    private int currentPosition = 0;
    private ViewPager mViewPager;
    private LinearLayout mCarouselLayout;
    private CarouseAdapter mCarouseAdapter;
    private int pageItemWidth;
    private boolean isUserTouched = false;

    private ViewPagerAdapter mAdapter;
    private long mDelayMillis = 5000;
    private boolean isCanceld = false;

    public static final String TAG = "xujun";

    public void notifyDataChange() {
        mAdapter.notifyDataSetChanged();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!isUserTouched) {//当前用户是否正在触摸
                int currentItem=getRealCurrentItem();
                int realCount=getRealCount();
                if (currentItem == realCount - 1) {
                    mViewPager.setCurrentItem(0, false);
                } else {
                    mViewPager.setCurrentItem(currentItem + 1);
                }
            }

            if (!isCanceld) {
                handler.sendEmptyMessageDelayed(0, mDelayMillis);
            }

        }
    };

    public int getRealCurrentItem(){
        int realCount = getRealCount();
        int currentItem = mViewPager.getCurrentItem();
        if(realCount!=0){
            currentItem=currentItem%realCount;
        }
        return currentItem;
    }

    private int getRealCount() {
        return mCarouseAdapter.getCount();
    }

    public CarouselView(Context context) {
        super(context);
        this.context = context;
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void init() {
        mViewPager.setAdapter(null);
        mCarouselLayout.removeAllViews();
        if (mCarouseAdapter.isEmpty()) {
            return;
        }
        int count = getRealCount();

        for (int i = 0; i < count; i++) {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.carousel_layout_page);
            setIndicatorView(i, view);

            mCarouselLayout.addView(view);
        }
        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        this.mViewPager.setOnTouchListener(new OnTouchListener() {
            float lastX = 0;
            float lastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //    这个方法是请求父亲不要拦截触摸事件
                float y = event.getRawY();
                float x = event.getRawX();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        float xDistance = x - lastX;
                        float yDistance = y - lastY;
                        if (Math.abs(xDistance) > Math.abs(yDistance)) {
                            mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            mViewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        isUserTouched = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isUserTouched = false;
                        break;
                }
                lastX = x;
                lastY = y;
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(0, 0);

    }

    private void setIndicatorView(int i, View view) {
        int realCurrentItem = getRealCurrentItem();
        view.setBackgroundResource(R.drawable.carousel_layout_page);
        if (realCurrentItem == i) {
            view.setPressed(true);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pageItemWidth +
                    ConvertUtils.dip2px(context, 5), pageItemWidth + ConvertUtils.dip2px
                    (context, 5));
            params.setMargins(pageItemWidth, 0, 0, 0);
            view.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pageItemWidth,
                    pageItemWidth);
            params.setMargins(pageItemWidth, 0, 0, 0);
            view.setLayoutParams(params);
            view.setPressed(false);
        }
    }

    public void setCarouseAdapter(CarouseAdapter carouseAdapter) {
        this.mCarouseAdapter = carouseAdapter;
        if (carouseAdapter != null) {
            init();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_layout, null);
        this.mViewPager = (ViewPager) view.findViewById(R.id.gallery);
        this.mCarouselLayout = (LinearLayout) view.findViewById(R.id.CarouselLayoutPage);
        pageItemWidth = ConvertUtils.dip2px(context, 5);
        this.mViewPager.addOnPageChangeListener(this);
        addView(view);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //        Log.d("CarouselView", "onPageScrolled was invoke()");
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = getRealCurrentItem();
        int count = mCarouselLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = mCarouselLayout.getChildAt(i);
            setIndicatorView(i,view);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //        Log.d("CarouselView", "onPageScrollStateChanged was invoke()");
    }

    private int getRealPosition(int position) {
        int realCount = getRealCount();
        if(realCount==0){
            return position;
        }
        return position%realCount;
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return getRealCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=getRealPosition(position);
            View view = mCarouseAdapter.getView(position);
            container.addView(view);
            return view;
        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }
    }

    public interface CarouseAdapter {
        boolean isEmpty();

        View getView(int position);

        int getCount();
    }
}
