package com.xujun.funapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xujun.funapp.R;
import com.xujun.funapp.common.util.StringUtils;

/**
 * @ explain:
 * @ author：xujun on 2016/11/20 09:45
 * @ email：gdutxiaoxu@163.com
 */
public class SettingClickItem extends RelativeLayout {

    private ImageView icon;
    private TextView tvDes;
    private ImageView iconRight;
    private TextView tvContent;




    private Context mContext;
    private int mIconId;
    private String mContent;
    private String mDes;

    public SettingClickItem(Context context) {
        this(context, null);
    }

    public SettingClickItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingClickItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        initView();
    }

    private void initView() {
        View.inflate(mContext,R.layout.view_setting_click,this);
        icon = (ImageView) findViewById(R.id.icon);
        tvDes = (TextView) findViewById(R.id.tv_des);
        iconRight = (ImageView) findViewById(R.id.icon_right);
        tvContent = (TextView) findViewById(R.id.tv_content);
        if(mIconId!=-1){
            icon.setImageResource(mIconId);
        }
        mContent= StringUtils.getStr(mContent);
        tvContent.setText(mContent);
        mDes=StringUtils.getStr(mDes,"");
        tvDes.setText(mDes);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SettingClickItem);
        mIconId = typedArray.getResourceId(R.styleable.SettingClickItem_icon, -1);
        mContent = typedArray.getString(R.styleable.SettingClickItem_content);
        typedArray.recycle();

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable
                .SettingSwitchItem);
        mDes=a.getString(R.styleable.SettingSwitchItem_des);
        a.recycle();
    }

    public void setDes(String des) {
        mDes = des;
        tvDes.setText(des);
    }

    public void setContent(String content) {
        mContent = content;
        tvContent.setText(content);
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
        icon.setImageResource(iconId);
    }
}
