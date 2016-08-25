package com.xujun.funapp.common.util;

import android.os.CountDownTimer;

/**
 * Created by chenzhihui on 2016/06/02 .
 * 倒计时工具类
 */
public class CountDownHelper {
    private final String TAG=this.getClass().getSimpleName();
    private static CountDownHelper sCountDownHelper;
    private final int DEFAULT_COUNTDOWN = 60000;
    private CountDownTimer mCountDownTimer;
    private long mMillisUntilFinished = 0;
    private CountDownListener mCountDownListener;

    //几时标识
    private String mTag = "Count_0";

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public boolean isSameTag(String tag) {
        return mTag.equals(tag);
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        mCountDownListener = countDownListener;
    }

    public interface CountDownListener {

        public void onTick(long millisUntilFinished);

        public void onFinish();
    }

    private CountDownHelper() {

    }

    public static CountDownHelper getInstance() {
        if (sCountDownHelper == null) {
            synchronized (CountDownHelper.class) {
                if (sCountDownHelper == null) {
                    sCountDownHelper = new CountDownHelper();
                }
            }
        }
        return sCountDownHelper;
    }

    /**
     * 设置倒计时
     *
     * @param time
     */
    public CountDownHelper resetCountDown(long time) {
        if (time < 0) {
            time = DEFAULT_COUNTDOWN;
        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mMillisUntilFinished = 0;
        mCountDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LUtils.d("onTick:"+millisUntilFinished);
                mMillisUntilFinished = millisUntilFinished;
                if (mCountDownListener != null) {
                    mCountDownListener.onTick(mMillisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                LUtils.d("onFinish");
                mMillisUntilFinished = 0;
                if (mCountDownListener != null) {
                    mCountDownListener.onFinish();
                }
            }
        };
        return this;
    }

    /**
     * 获取倒计时
     *
     * @return
     */
    public long getMillisUntilFinished() {
        return mMillisUntilFinished;
    }

    /**
     * 开始计时
     */
    public void startTick() {
        if (mCountDownTimer == null) {
            throw new IllegalStateException("resetCountDown first!!!");
        }
        mCountDownTimer.cancel();
        mCountDownTimer.start();
    }

    /**
     * 取消计时
     */
    public void cancleTick() {
        if (mCountDownTimer == null) {
            return;
        }
        mCountDownTimer.cancel();
        mMillisUntilFinished = 0;
    }


}
