package io.github.cyning.mobilenews.widgets.bottomNavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.ViewPropertyAnimator;

/**
 * @author Cyning
 * @since 2016.03.29
 * Time    10:27 PM
 * Desc    <p>动画的监听事件</p>
 */

public class AnimatorListenerCompat {

    public  static  ViewPropertyAnimator withEnd(ViewPropertyAnimator animator ,final Runnable runable){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            animator.setListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    runable.run();
                }
            });
        } else {
            animator.withEndAction(runable) ;
        }
        return animator;
    }

}
