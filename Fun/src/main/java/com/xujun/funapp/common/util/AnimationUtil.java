package com.xujun.funapp.common.util;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

/**
 * Created by Domen、on 2016/4/28.
 */
public class AnimationUtil {
    private static AnimationUtil ourInstance = new AnimationUtil();

    public static final String TAG = "xujun";

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
    public static void performAnimate(final View target, final int start, final int end) {
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

    /**
     * @param target 动画目标
     * @param start  开始时候的值
     * @param end    结束之后的值
     */
    public static void translateXOut(final View target, final int start, final int end) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        AnticipateInterpolator anticipateInterpolator = new AnticipateInterpolator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，start-end之间
                int currentValue = (Integer) animator.getAnimatedValue();
//                Log.i(TAG, "onAnimationUpdate:currentValue =" +currentValue);
                float animatedFraction = animator.getAnimatedFraction();
                if (animatedFraction == 1.0f) {
                    target.setVisibility(View.GONE);
                }
                target.setAlpha(1-animatedFraction);
                target.setTranslationX(currentValue);

            }


        });
        valueAnimator.setInterpolator(anticipateInterpolator);
        valueAnimator.setDuration(300).start();
    }

    /**
     * @param target 动画目标
     * @param start  开始时候的值
     * @param end    结束之后的值
     */
    public static void translateXInt(final View target, final int start, final int end) {
        target.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        AnticipateInterpolator anticipateInterpolator = new AnticipateInterpolator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，start-end之间
                int currentValue = (Integer) animator.getAnimatedValue();
//                Log.i(TAG, "onAnimationUpdate: currentValue=" + currentValue);
                float animatedFraction = animator.getAnimatedFraction();
                target.setAlpha(animatedFraction);
                target.setTranslationX(currentValue);

            }
        });
        valueAnimator.setInterpolator(anticipateInterpolator);
        valueAnimator.setDuration(300).start();
    }
}
