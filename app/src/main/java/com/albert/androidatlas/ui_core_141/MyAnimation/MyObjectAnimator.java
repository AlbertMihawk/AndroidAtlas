package com.albert.androidatlas.ui_core_141.MyAnimation;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Filename : MyObjectAnimator.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

/**
 * Filename : MyObjectAnimator.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class MyObjectAnimator implements AnimationFrameCallBack {

    private static final String LOG_TAG = "MyObjectAnimator";
    long mStartTime = -1;
    MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;
    private long mDuration = 0;
    private WeakReference<View> mTarget;

    private TimeInterpolator mInterpolator;

    private float mIndex = 0;

    private MyObjectAnimator(View target, String propertyName, float... values) {
        mTarget = new WeakReference<>(target);

        myFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }

    public static MyObjectAnimator ofFloat(View target, String propertyName, float... values) {
        MyObjectAnimator animator = new MyObjectAnimator(target, propertyName, values);
        return animator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    //每隔16ms回调 模拟系统调用行为
    @Override
    public boolean doAnimationFrame(long currentTime) {
        float total = mDuration / 16.0f;

        //执行百分比 index叠加
        float fraction = mIndex++ / total;
        if (mInterpolator != null) {
            fraction = mInterpolator.getInterpolation(fraction);
        }
        if (mIndex >= total) {
            mIndex = 0;
        }
        myFloatPropertyValuesHolder.setAnimatedValue(mTarget.get(), fraction);
        return false;
    }


    public void start() {
        myFloatPropertyValuesHolder.setupSetter();
        mStartTime = System.currentTimeMillis();
        VSyncManager.getInstance().add(this);
    }
}
