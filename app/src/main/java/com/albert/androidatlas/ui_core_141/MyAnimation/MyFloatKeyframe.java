package com.albert.androidatlas.ui_core_141.MyAnimation;

/**
 * Filename : MyFloatKeyframe.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class MyFloatKeyframe {

    float mFraction;//当前百分比 0-100%。0-1
    Class mValueType;
    float mValue;

    public MyFloatKeyframe(float mFraction, float mValue) {
        this.mFraction = mFraction;
        this.mValueType = float.class;
        this.mValue = mValue;//具体值
    }

    public float getFraction() {
        return mFraction;
    }

    public void setFraction(float mFraction) {
        this.mFraction = mFraction;
    }

    public Class getValueType() {
        return mValueType;
    }

    public void setValueType(Class mValueType) {
        this.mValueType = mValueType;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }
}
