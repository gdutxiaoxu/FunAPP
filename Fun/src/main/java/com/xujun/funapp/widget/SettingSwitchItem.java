package com.xujun.funapp.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.util.WriteLogUtil;

/**
 * @ explain:
 * @ author：xujun on 2016/11/4 18:25
 * @ email：gdutxiaoxu@163.com
 */
public class SettingSwitchItem extends RelativeLayout {

    private TextView mTvDes;
    private Switch mSwitch;
    Context mContext;

    onChangeListener mOnChangeListener;

    int[] track = new int[]{android.R.attr.track};
    int[] thumb = new int[]

            {
                    android.R.attr.thumb
            };
    private String mDes;
    private String mOnDes;
    private String mOffDes;
    private int mTrackId;
    private int mThumbId;

    public interface onChangeListener {
        void onChange(boolean checked);
    }

    public void setOnChangedListenr(onChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }

    public SettingSwitchItem(Context context) {
        this(context, null);
    }

    public SettingSwitchItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingSwitchItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        initView();
        initListener();
    }

    private void initListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mSwitch.isChecked();
                mSwitch.setChecked(!checked);
                if (mOnChangeListener != null) {
                    mOnChangeListener.onChange(!checked);
                }

                WriteLogUtil.d("!checked=" + !checked);
            }
        });
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable
                .SettingSwitchItem);
        mDes = typedArray.getString(R.styleable.SettingSwitchItem_des);
        mOnDes = typedArray.getString(R.styleable.SettingSwitchItem_on_des);
        mOffDes = typedArray.getString(R.styleable.SettingSwitchItem_off_des);

        typedArray.recycle();
        TypedArray a = mContext.obtainStyledAttributes(this.track);
        mTrackId = a.getResourceId(0, -1);
        a.recycle();
        TypedArray b = mContext.obtainStyledAttributes(this.thumb);
        mThumbId = b.getResourceId(0, -1);
        b.recycle();


    }

    private void initView() {
        View.inflate(mContext, R.layout.view_switch_setting, this);
        mTvDes = (TextView) findViewById(R.id.tv_des);
        mSwitch = (Switch) findViewById(R.id.sw);
        mSwitch.setClickable(false);
        mSwitch.setFocusable(false);
        mSwitch.setFocusableInTouchMode(false);
        if (TextUtils.isEmpty(mDes) == false) {
            mTvDes.setText(mDes);
        }
        if (mTrackId != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mSwitch.setTrackResource(mThumbId);
            }
        }

        if (mThumbId != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mSwitch.setThumbResource(mThumbId);
            }
        }

    }

    public void setChecked(boolean checked) {
        mSwitch.setChecked(checked);
    }

    @TargetApi(21)
    public SettingSwitchItem(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
