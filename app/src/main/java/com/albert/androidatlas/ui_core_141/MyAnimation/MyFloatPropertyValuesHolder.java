package com.albert.androidatlas.ui_core_141.MyAnimation;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Filename : MyFloatPropertyValuesHolder.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class MyFloatPropertyValuesHolder {
    //属性名
    String mPropertyName;
    //设置的属性 float
    Class mValueType;
    //反射
    Method mSetter = null;

    //关键帧管理类
    MyKeyframeSet mKeyframes;

    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;

        mKeyframes = MyKeyframeSet.ofFloat(values);
    }

    public void setupSetter() {
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;

        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public void setAnimatedValue(View view, float fraction) {
        Object value = mKeyframes.getValue(fraction);
        //反射
        try {
            mSetter.invoke(view, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
