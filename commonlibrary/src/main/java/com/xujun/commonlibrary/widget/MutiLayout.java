package com.xujun.commonlibrary.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xujun.commonlibrary.R;

import static com.xujun.commonlibrary.R.id.page_bt;

/**
 * @ explain:
 * @ author：xujun on 2016/10/27 20:07
 * @ email：gdutxiaoxu@163.com
 */
public class MutiLayout extends FrameLayout {

    public static final int STATE_NOONE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;

    private ImageView mIvEmpty;
    private TextView mTvEmpty;

    private Button mBtnError;
    private TextView mTvError;
    private ImageView mIvError;

    private int mEmptyIconId;
    private String mEmptyText;
    private Context mContext;
    private String mErrorText;
    private int mErrorIconId;

    private View loadingView;// 加载中的界面
    private View errorView;// 错误界面
    private View emptyView;// 空界面
    private View successView;// 加载成功的界面

    int state = STATE_NOONE;

    OnClickListener mRetryListener;

    public MutiLayout(Context context) {
        this(context, null, 0);
    }

    public MutiLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MutiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs);
        initView();

    }

    private void initView() {
        emptyView = createEmptyView();
        errorView = createErrorView();
        loadingView = createLoadingView();
        removeView(emptyView);
        removeView(errorView);
        removeView(loadingView);
        add(emptyView);
        add(errorView);
        add(loadingView);
        show(LoadResult.loading);

    }

    private void add(View view) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, LayoutParams
                .MATCH_PARENT);
        addView(view, layoutParams);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.empty);
        mEmptyText = typedArray.getString(R.styleable.empty_emptyText);
        mEmptyIconId = typedArray.getResourceId(R.styleable.empty_emptyIcon, -1);
        mErrorText = typedArray.getString(R.styleable.error_errorIcon);
        mErrorIconId = typedArray.getResourceId(R.styleable.error_errorIcon, -1);
        typedArray.recycle();

    }

    @TargetApi(21)
    public MutiLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public enum LoadResult {
        noone(0), loading(1), error(2), empty(3);

        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public void setOnRetryListener(OnClickListener onClickListener) {
        mRetryListener = onClickListener;
    }

    public void hide() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setVisibility(View.INVISIBLE);
        }
    }

    // 根据不同的状态显示不同的界面
    public void show(LoadResult loadResult) {
        state = loadResult.getValue();
        if (state == STATE_NOONE) {
            hide();
        } else {
            loadingView.setVisibility(state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);

            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);

            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }

    }

    /* 创建了空的界面 */
    private View createEmptyView() {
        View view = View.inflate(mContext, R.layout.loadpage_empty,
                null);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        mIvEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        if (TextUtils.isEmpty(mEmptyText)) {
            mTvEmpty.setText(mEmptyText);
        }

        if (mEmptyIconId != -1) {
            mIvEmpty.setImageResource(mEmptyIconId);
        }
        return view;
    }

    /* 创建了错误界面 */
    private View createErrorView() {
        View view = View.inflate(mContext, R.layout.loadpage_error,
                null);
        mBtnError = (Button) view.findViewById(page_bt);
        mIvError = (ImageView) view.findViewById(R.id.page_iv);
        if (!TextUtils.isEmpty(mEmptyText)) {
            mBtnError.setText(mErrorText);
        }

        if (mErrorIconId != -1) {
            mIvError.setImageResource(mErrorIconId);
        }

        mBtnError.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mRetryListener != null) {
                    mRetryListener.onClick(v);
                }
            }
        });
        return view;
    }

    /* 创建加载中的界面 */
    private View createLoadingView() {
        View view = View.inflate(mContext,
                R.layout.loadpage_loading, null);
        return view;
    }

    public void removeParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

    }


}
