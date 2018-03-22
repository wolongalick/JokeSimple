package com.yyhd.joke.utils;

import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/9/10
 * 备注:
 */
public class DiggAnimUtils {
    public static void showPlus(final View view){
        view.setVisibility(View.VISIBLE);
        final ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(view,"translationY",0,-100);
        objectAnimator.setDuration(1000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                ObjectAnimator animator= ObjectAnimator.ofFloat(view,"translationY",100,0);
                animator.setDuration(0);
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }
}
