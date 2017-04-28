package com.jarek.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author meitu.xujun on 2017/4/28 9:44
 * @version 0.1
 */
public class TitleView extends RelativeLayout implements View.OnClickListener {

    private String textColor = "#ff655f";

    /**
     * default leftMargin with dp unit
     */
    int leftMargin = 8;
    /**
     * default rightMargin with dp unit
     */
    int rightMargin = 12;

    /**
     * default TextView drawable-padding with dp unit
     */
    private int drawablePadding = 3;
    /**
     * min view width with dp unit
     */
    private int minViewWidth = 35;
    /**
     * the size of smaller text with sp unit
     */
    private int defaultSmallTextSize = 16;
    /**
     * the size of title text with sp unit
     */
    private int defaultTitleSize = 20;

    /*left button, if this means back,two options:1.image button;2:a textView which has drawable
    and text*/
    /**
     * back to last page--TextView
     */
    private TextView mLeftBackTextTv;
    /**
     * back to last page--ImageView
     */
    private ImageView mLeftBackImageTv;

    /**
     * title
     */
    private TextView mTitleTv;

    /*right button, ImageView or TextView ,only show one of them*/
    /**
     * right ImageView
     */
    private ImageView mRightImageIv;
    /**
     * right TextView
     */
    private TextView mRightTextTv;

    /*right another button, ImageView or TextView ,only show one of them*/
    /**
     * right another ImageView
     */
    private ImageView mRightImageTwoIv;
    /**
     * right another TextView
     */
    private TextView mRightTextTwoTv;
    private ITitleViewListener mTitleViewListener;
    private Context mContext;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initView(context, attrs);
    }

    public void setOnTitleListener(ITitleViewListener titleViewListener) {
        mTitleViewListener = titleViewListener;
    }

    /**
     * initview
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    private void initView(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        /*init text and image widget*/
        initLeftTextView(context, typeArray);
        initLeftImageView(context, typeArray);
        initTitleView(context, typeArray);
        initRightTextView(context, typeArray);
        initRightImageView(context, typeArray);
        initRightAnotherTextView(context, typeArray);
        initRightAnotherImageView(context, typeArray);
        initListener();
        typeArray.recycle();

    }

    private void initListener() {
        setViewListener(mLeftBackImageTv);
        setViewListener(mLeftBackTextTv);
        setViewListener(mTitleTv);
        setViewListener(mRightImageIv);
        setViewListener(mRightTextTv);
        setViewListener(mRightTextTwoTv);
        setViewListener(mRightImageTwoIv);

    }

    private void setViewListener(View view) {
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    /**
     * init the left TextView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initLeftTextView(Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleView_left_text, 0);
        CharSequence charSequence = leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable
                .TitleView_left_text);

        LayoutParams params = initLayoutParams();
        params.leftMargin = dip2px(mContext, leftMargin);

        /*init TextView*/
        mLeftBackTextTv = createTextView(context, R.id.tv_left_text, charSequence, params);
        setTextViewDrawable(typeArray, R.styleable.TitleView_left_text_drawable_left,
                R.styleable.TitleView_left_text_drawable_right, mLeftBackTextTv);

        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleView_small_text_size,
                defaultSmallTextSize);
        mLeftBackTextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mLeftBackTextTv.setTextColor(getTextColorFromAttr(typeArray));
        addView(mLeftBackTextTv);
    }

    /**
     * init the left ImageView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initLeftImageView(Context context, TypedArray typeArray) {
        int leftImgAttr = typeArray.getResourceId(R.styleable.TitleView_left_image, 0);

        if (leftImgAttr != 0) {
            LayoutParams params = initLayoutParams();
            params.leftMargin = dip2px(mContext, leftMargin);
            mLeftBackImageTv = createImageView(context, R.id.iv_left_image, leftImgAttr, params);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            addView(mLeftBackImageTv);
        }

    }

    /**
     * init the title TextView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initTitleView(Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleView_title_name, 0);
        CharSequence charSequence = leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable
                .TitleView_title_name);

        LayoutParams params = initLayoutParams();
        /*init TextView*/
        mTitleTv = createTextView(context, R.id.tv_title_name, charSequence, params);
        //   设置 标题 TextView 的最大行数为1
        mTitleTv.setMaxLines(1);
        mTitleTv.setEllipsize(TextUtils.TruncateAt.END);
        //   防止在标题字数过长的情况下，占领其他空间
        mTitleTv.setMaxWidth(getDisplayMetrics(mContext).widthPixels - 200);
        setTextViewDrawable(typeArray, R.styleable.TitleView_title_drawable_left, R.styleable
                .TitleView_title_drawable_right, mTitleTv);


        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleView_title_text_size,
                defaultTitleSize);
        mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTitleTv.setTextColor((getTextColorFromAttr(typeArray)));

        int gravity = typeArray.getInt(R.styleable.TitleView_title_gravity, 0);
        if (gravity > 0) {
            if (gravity == Gravity.LEFT) {
                params.addRule(RelativeLayout.RIGHT_OF, mLeftBackImageTv == null ?
                        mLeftBackTextTv.getId() : mLeftBackImageTv.getId());
            }
        } else { //default:center
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
        }

        addView(mTitleTv);
    }

    /**
     * init the right TextView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initRightTextView(Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleView_right_text, 0);
        CharSequence charSequence = leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable
                .TitleView_right_text);

        LayoutParams params = initLayoutParams();
        params.rightMargin = dip2px(mContext, rightMargin);
        /*init TextView*/
        mRightTextTv = createTextView(context, R.id.tv_right_text, charSequence, params);
        setTextViewDrawable(typeArray, R.styleable.TitleView_right_text_drawable_left, R
                .styleable.TitleView_right_text_drawable_right, mRightTextTv);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleView_small_text_size,
                defaultSmallTextSize);
        mRightTextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mRightTextTv.setTextColor(getTextColorFromAttr(typeArray));

        addView(mRightTextTv);
    }

    /**
     * init the right another TextView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initRightAnotherTextView(Context context, TypedArray typeArray) {
        int leftText = typeArray.getResourceId(R.styleable.TitleView_right_text_two, 0);
        CharSequence charSequence = leftText > 0 ?
                typeArray.getResources().getText(leftText) : typeArray.getString(R.styleable
                .TitleView_right_text_two);

        LayoutParams params = initLayoutParams();
        /*init TextView*/
        mRightTextTwoTv = createTextView(context, R.id.tv_right_text_two, charSequence, params);
        setTextViewDrawable(typeArray, R.styleable.TitleView_right_text_two_drawable_left, R
                .styleable.TitleView_right_text_two_drawable_right, mRightTextTwoTv);
        if (mRightImageIv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightImageIv.getId());
        } else if (mRightTextTv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightTextTv.getId());
        }

        float textSize = getDimensionPixelSize(typeArray, R.styleable.TitleView_small_text_size,
                defaultSmallTextSize);
        mRightTextTwoTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mRightTextTwoTv.setTextColor(getTextColorFromAttr(typeArray));

        addView(mRightTextTwoTv);
    }

    /**
     * init the right ImageView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initRightImageView(Context context, TypedArray typeArray) {
        int rightImgAttr = typeArray.getResourceId(R.styleable.TitleView_right_image, 0);

        if (rightImgAttr == 0) {
            return;
        }
        LayoutParams params = initLayoutParams();
        params.rightMargin = dip2px(mContext, rightMargin);
        mRightImageIv = createImageView(context, R.id.iv_right_image, rightImgAttr, params);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(mRightImageIv);

    }

    /**
     * init the right ImageView
     *
     * @param context   Context
     * @param typeArray TypedArray
     */
    private void initRightAnotherImageView(Context context, TypedArray typeArray) {
        int rightImgAttr = typeArray.getResourceId(R.styleable.TitleView_right_image_two, 0);

        if (rightImgAttr == 0) {
            return;
        }
        LayoutParams params = initLayoutParams();
        mRightImageTwoIv = createImageView(context, R.id.iv_right_image_two, rightImgAttr, params);

        if (mRightImageIv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightImageIv.getId());
        } else if (mRightTextTv != null) {
            params.addRule(RelativeLayout.LEFT_OF, mRightTextTv.getId());
        }
        addView(mRightImageTwoIv);

    }

    /**
     * layout params of RelativeLayout
     *
     * @return LayoutParams
     */
    private LayoutParams initLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * drawable of TextView
     *
     * @param typeArray              TypedArray
     * @param leftDrawableStyleable  leftDrawableStyleable
     * @param rightDrawableStyleable rightDrawableStyleable
     * @param textView               which TextView to set
     */
    private void setTextViewDrawable(TypedArray typeArray, int leftDrawableStyleable, int
            rightDrawableStyleable, TextView textView) {
        int leftDrawable = typeArray.getResourceId(leftDrawableStyleable, 0);
        int rightDrawable = typeArray.getResourceId(rightDrawableStyleable, 0);
        textView.setCompoundDrawablePadding((int) getPixelSizeByDp(drawablePadding));
        textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, rightDrawable, 0);

    }

    /**
     * create TextView
     *
     * @param context      Context
     * @param id           the id of TextView
     * @param charSequence text to show
     * @param params       relative params
     * @return the TextView which is inited
     */
    @NonNull
    private TextView createTextView(Context context, int id, CharSequence charSequence,
                                    LayoutParams params) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setId(id);
        textView.setMinWidth((int) getPixelSizeByDp(minViewWidth));
        textView.setText(charSequence);
        return textView;
    }

    /**
     * create ImageView
     *
     * @param context  Context
     * @param id       the id of ImageView
     * @param drawable the resource of imageView
     * @param params   relative params
     * @return ImageView
     */
    @NonNull
    private ImageView createImageView(Context context, int id, int drawable, LayoutParams params) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setId(id);
        imageView.setMinimumWidth((int) getPixelSizeByDp(minViewWidth));
        imageView.setImageResource(drawable);
        return imageView;
    }

    /**
     * get Pixel size by dp
     *
     * @param dp dp
     * @return the value of px
     */
    private float getPixelSizeByDp(int dp) {
        Resources res = this.getResources();
        final float scale = res.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    /**
     * get Pixel size by sp
     *
     * @param sp sp
     * @return the value of px
     */
    private float getPiselSizeBySp(int sp) {
        Resources res = this.getResources();
        final float scale = res.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * get the dimension pixel size from typeArray which is defined in attr
     *
     * @param typeArray   TypedArray
     * @param stylable    stylable
     * @param defaultSize defaultSize
     * @return the dimension pixel size
     */
    private float getDimensionPixelSize(TypedArray typeArray, int stylable, int defaultSize) {
        int sizeStyleable = typeArray.getResourceId(stylable, 0);
        return sizeStyleable > 0 ? typeArray.getResources().getDimensionPixelSize(sizeStyleable)
                : typeArray.getDimensionPixelSize(stylable, (int) getPiselSizeBySp(defaultSize));
    }

    /**
     * get textColor
     *
     * @param typeArray TypedArray
     * @return textColor
     */
    private int getTextColorFromAttr(TypedArray typeArray) {
        int textColorStyleable = typeArray.getResourceId(R.styleable.TitleView_title_text_color, 0);
        if (textColorStyleable > 0) {
            return typeArray.getResources().getColor(textColorStyleable);
        } else {
            return Color.parseColor(textColor);
        }

    }

    /**
     * set TextView's drawable padding
     *
     * @param drawablePadding drawablePadding
     */
    public void setTextViewDrawablePadding(int drawablePadding) {
        this.drawablePadding = drawablePadding;
    }

    public TextView getLeftBackTextTv() {
        return mLeftBackTextTv;
    }

    public ImageView getLeftBackImageTv() {
        return mLeftBackImageTv;
    }

    public TextView getTitleTv() {
        return mTitleTv;
    }

    public ImageView getRightImageIv() {
        return mRightImageIv;
    }

    public TextView getRightTextTv() {
        return mRightTextTv;
    }

    public ImageView getRightImageTwoIv() {
        return mRightImageTwoIv;
    }

    public TextView getRightTextTwoTv() {
        return mRightTextTwoTv;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (mTitleViewListener == null) {
            return;
        }
        if (id == R.id.iv_left_image) {
            mTitleViewListener.onLeftImageClick();
        } else if (id == R.id.tv_left_text) {
            mTitleViewListener.onLeftTextClick();
        } else if (id == R.id.tv_title_name) {
            mTitleViewListener.onTitleClick();
        } else if (id == R.id.iv_right_image) {
            mTitleViewListener.onRightImageClick();
        } else if (id == R.id.tv_right_text) {
            mTitleViewListener.onRightTextClick();
        } else if (id == R.id.iv_right_image_two) {
            mTitleViewListener.onRightImageTwoClick();
        } else if (id == R.id.tv_right_text_two) {
            mTitleViewListener.onRightTextTwoClick();
        }

    }

    public static DisplayMetrics getDisplayMetrics(Context context) {

        return context.getResources().getDisplayMetrics();
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public interface ITitleViewListener {
        void onLeftTextClick();

        void onLeftImageClick();

        void onTitleClick();

        void onRightTextClick();

        void onRightImageClick();

        void onRightTextTwoClick();

        void onRightImageTwoClick();
    }
}
