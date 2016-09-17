package com.xujun.funapp.common.util;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by Domen、on 2016/4/28.
 */
public class AnimationUtil {
    private static AnimationUtil ourInstance = new AnimationUtil();

    public static AnimationUtil getInstance() {
        return ourInstance;
    }

    private AnimationUtil() {
    }

    /**
     * 隐藏或显示logo的动画
     *
     * @param target 动画目标
     * @param start  开始时候的值
     * @param end    结束之后的值
     */
    public void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();

                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;

                //整型估值器IntEvaluator
                target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(300).start();
    }
}
