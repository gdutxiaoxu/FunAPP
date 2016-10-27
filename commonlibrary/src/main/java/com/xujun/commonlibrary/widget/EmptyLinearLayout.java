package com.xujun.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xujun.commonlibrary.R;

/**
 * @ explain:
 * @ author：xujun on 2016/6/14 14:55
 * @ email：gdutxiaoxu@163.com
 */
public class EmptyLinearLayout extends LinearLayout {

    private ImageView mIvEmpty;
    private TextView mTvEmpty;
    private int mIconId;
    private String mText;
    public static final String TAG = "tag";

    public EmptyLinearLayout(Context context) {
        this(context, null);
    }

    public EmptyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initCustonAttrs(context, attrs);
        refresh();
    }

    private void refresh() {
        if (mText != null) {
            Log.i(TAG, "EmptyLinearLayout.class:refresh(): 43:" + mText);
            mTvEmpty.setText(mText);
        }

        if (mIconId != -1) {
            mIvEmpty.setImageResource(mIconId);
        }
    }

    private void initCustonAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.empty);
        mText = typedArray.getString(R.styleable.empty_emptyText);
        mIconId = typedArray.getResourceId(R.styleable.empty_emptyIcon, -1);
        typedArray.recycle();
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.loadpage_empty, this);
        mIvEmpty = findView(R.id.iv_empty);
        mTvEmpty = findView(R.id.tv_empty);
    }

    public void setText(String text) {
        if (text != null) {
            mTvEmpty.setText(text);
        }
    }

    public void setIconResource(int id) {
        mIvEmpty.setImageResource(id);
    }

    public ImageView getIvEmpty() {
        return mIvEmpty;
    }

    public TextView getTvEmpty() {
        return mTvEmpty;
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
