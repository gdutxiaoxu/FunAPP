package com.xujun.funapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xujun.mylibrary.R;

/**
 * @author xujun  on 2017/3/5.
 * @email gdutxiaoxu@163.com
 */

public class TitleView extends RelativeLayout {

    private Context mContext;

    int DEF_VALUE = 0;
    int DEF_RES = 0;
    int DEF_TEXT_SIZE = 14;
    int DEF_TEXT_COLOR = Color.BLACK;

    private ImageView mIvLeft;

    private TextView mTvLeft;
    private ImageView mIvRight;
    private TextView mTvRight;
    private TextView mTvTitle;

    private String mLeftText;
    private String mRightText;
    private String mTitle;
    private int mLeftTextColor;
    private int mRightTextColor;
    private int mTitleColor;
    private int mLeftResId;
    private int mRightResId;
    private int mLeftVisibility;
    private float mLeftTextSize;
    private float mRightTextSize;
    private float mTitleSize;
    private int mRightVisibility;
    private int mTitleVisibility;

    TitleViewClickListener mTitleViewClickListener;

    public TitleView(Context context) {
        this(context, null, 0);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        obtainAttrs(attrs);
        initEventAndData();
    }

    public void setTitleViewClickListener(TitleViewClickListener titleViewClickListener) {
        mTitleViewClickListener = titleViewClickListener;
    }

    private void initEventAndData() {
        View.inflate(mContext, R.layout.title_view, this);
        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        mTvLeft = (TextView) findViewById(R.id.tv_left);
        mIvRight = (ImageView) findViewById(R.id.iv_right);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mTvTitle = (TextView) findViewById(R.id.tv_title);


        mIvLeft.setVisibility(mLeftVisibility);
        mTvLeft.setVisibility(mLeftVisibility);

        mIvRight.setVisibility(mLeftVisibility);
        mTvRight.setVisibility(mLeftVisibility);
        analyzeTextAndImage(mTvRight, mIvRight, mRightText, mRightResId);

        analyzeTextAndImage(mTvLeft, mIvLeft, mLeftText, mLeftResId);

        initTextView(mTvLeft, mLeftText, mLeftTextColor, mLeftTextSize);
        initTextView(mTvRight, mRightText, mRightTextColor, mRightTextSize);
        initTextView(mTvTitle, mTitle, mTitleColor, mTitleSize);
        mTvTitle.setVisibility(mTitleVisibility);

        mIvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleViewClickListener != null) {
                    mTitleViewClickListener.onLeftClick(v);
                }
            }
        });

        mTvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleViewClickListener != null) {
                    mTitleViewClickListener.onLeftClick(v);
                }
            }
        });

        mIvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleViewClickListener != null) {
                    mTitleViewClickListener.onRightClick(v);
                }
            }
        });

        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleViewClickListener != null) {
                    mTitleViewClickListener.onRightClick(v);
                }
            }
        });

    }

    private void initTextView(TextView tv, String title, int textColor, float textSize) {
        if(title!=null){
            tv.setText(title);
        }

        tv.setTextSize(textSize);
        tv.setTextColor(textColor);
    }

    private void analyzeTextAndImage(TextView tv, ImageView iv, String text, int resid) {
        //        即如果有设置图片的话，文字是不可见的
        if (resid != DEF_RES) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(resid);
            tv.setVisibility(View.GONE);
        } else {
            iv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
    }

    private void obtainAttrs(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleView);
        mLeftText = ta.getString(R.styleable.TitleView_leftText);
        mRightText = ta.getString(R.styleable.TitleView_rightText);
        mTitle = ta.getString(R.styleable.TitleView_title);
        mLeftTextColor = ta.getColor(R.styleable.TitleView_leftTextColor, DEF_TEXT_COLOR);
        mRightTextColor = ta.getColor(R.styleable.TitleView_rightTextColor, DEF_TEXT_COLOR);
        mTitleColor = ta.getColor(R.styleable.TitleView_titleColor, DEF_TEXT_COLOR);
        mLeftResId = ta.getResourceId(R.styleable.TitleView_leftIcon, DEF_RES);
        mRightResId = ta.getResourceId(R.styleable.TitleView_rightIcon, DEF_RES);
        mLeftVisibility = ta.getInt(R.styleable.TitleView_leftVisibility, DEF_VALUE);
        mLeftTextSize = ta.getDimension(R.styleable.TitleView_leftTextSize, DEF_TEXT_SIZE);
        mRightTextSize = ta.getDimension(R.styleable.TitleView_rightTextSize, DEF_TEXT_SIZE);
        mTitleSize = ta.getDimension(R.styleable.TitleView_titleSize, DEF_TEXT_SIZE);
        mLeftVisibility = parseVisibility(mLeftVisibility);
        mRightVisibility = ta.getInt(R.styleable.TitleView_rightVisibility, DEF_VALUE);
        mRightVisibility = parseVisibility(mRightVisibility);
        mTitleVisibility = ta.getInt(R.styleable.TitleView_titleVisibility, DEF_VALUE);
        mTitleVisibility = parseVisibility(mTitleVisibility);


        ta.recycle();
    }

    private int parseVisibility(int visibility) {
        if (visibility == 0) {
            return View.VISIBLE;
        } else if (visibility == 1) {
            return View.INVISIBLE;
        } else {
            return View.GONE;
        }
    }

    public interface TitleViewClickListener {
        public void onLeftClick(View view);

        public void onRightClick(View view);
    }

    public void setTitle(String title) {
        mTitle = title;
        mTvTitle.setText(mTitle);
    }

    public void setRightText(String rightText) {
        mRightText = rightText;
        mTvRight.setText(mRightText);
    }

    public void setLeftText(String leftText) {
        mLeftText = leftText;
        mTvLeft.setText(leftText);
    }

    public int getLeftTextColor() {
        return mLeftTextColor;
    }

    public int getRightTextColor() {
        return mRightTextColor;
    }

    public String getTitle() {
        return mTitle;
    }
}
