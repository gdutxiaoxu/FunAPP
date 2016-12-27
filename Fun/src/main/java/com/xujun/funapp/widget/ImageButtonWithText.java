package com.xujun.funapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xujun.mylibrary.R;

/**
 * @author xujun  on 2016/12/27.
 * @email gdutxiaoxu@163.com
 */

public class ImageButtonWithText extends LinearLayout {
    private String mText;
    public ImageView imageView;
    public TextView textView;
    private  int mPicture_id;

    Context mContext;

    public ImageButtonWithText(Context context) {
        this(context,null);
    }

    public ImageButtonWithText(Context context, AttributeSet attrs) {
        this(context, attrs,-1);


    }

    public ImageButtonWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {
        mContext=context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageButtonWithText);
        mPicture_id = a.getResourceId(R.styleable.ImageButtonWithText_picture, -1);
        mText = a.getString(R.styleable.ImageButtonWithText_text);
        a.recycle();
        View.inflate(mContext,R.layout.view_image_text,this);
        imageView= (ImageView) findViewById(R.id.iv);
        textView=(TextView)findViewById(R.id.tv);

        if(TextUtils.isEmpty(mText)){
            mText="";
        }
        textView.setText(mText);
        if(mPicture_id!=-1){
            imageView.setImageResource(mPicture_id);
        }
    }



    public void setText(String text) {
        textView.setText(text);
    }

    public void setText(CharSequence buttonText) {
        textView.setText(buttonText);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }
}