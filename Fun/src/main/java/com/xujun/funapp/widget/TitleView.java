package com.xujun.funapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xujun.funapp.R;

/**
 * @author xujun  on 2016/12/30.
 * @email gdutxiaoxu@163.com
 */

public class TitleView extends RelativeLayout {

    private Context mContext;

    private ImageView mIvBack;
    private TextView mTvTitle;
    private OnClickListener mOnClickListener;
    private String mTitle;
    private int mBackId;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        initView();
        initData();

    }

    private void initData() {
        setTitle(mTitle);
        setBackId(mBackId);
    }

    private void initView() {
        View.inflate(mContext, R.layout.view_title, this);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener!=null){
                    mOnClickListener.onClick(v);
                }
            }
        });

    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleView);
        mTitle = typedArray.getString(R.styleable.TitleView_title);
        mBackId = typedArray.getResourceId(R.styleable.TitleView_backId, -1);
        typedArray.recycle();
    }

    public void setOnBackListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        if(title!=null){
            mTvTitle.setText(title);
        }

    }

    public int getBackId() {
        return mBackId;
    }

    public void setBackId(int backId) {
        mBackId = backId;
        if(backId!=-1){
            mIvBack.setImageResource(backId);
        }
    }
}
