package com.albert.androidatlas.ui_core_141.MyAnimation;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import java.util.Arrays;
import java.util.List;

/**
 * Filename : MyKeyframeSet.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class MyKeyframeSet {


    //类型故事器
    TypeEvaluator mEvaluator;
    MyFloatKeyframe mFirstKeyframe;

    List<MyFloatKeyframe> mKeyframes;

    public MyKeyframeSet(MyFloatKeyframe... keyframes) {
        mKeyframes = Arrays.asList(keyframes);
        mFirstKeyframe = keyframes[0];
        mEvaluator = new FloatEvaluator();
    }

    public static MyKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;
        //关键帧初始化
        MyFloatKeyframe keyframes[] = new MyFloatKeyframe[numKeyframes];
        keyframes[0] = new MyFloatKeyframe(0, values[0]);
        for (int i = 1; i < numKeyframes; i++) {
            //计算
            keyframes[i] = new MyFloatKeyframe(i / (numKeyframes - 1.0f), values[i]);


        }

        return new MyKeyframeSet(keyframes);
    }

    public Object getValue(float fraction) {
        MyFloatKeyframe prevKeyframe;// = mFirstKeyframe;
        for (int i = 1; i < mKeyframes.size(); i++) {
            prevKeyframe = mKeyframes.get(i - 1);
            MyFloatKeyframe nextKeyframe = mKeyframes.get(i);

            if (fraction < nextKeyframe.getFraction()) {
                Object evaluate = mEvaluator.evaluate(fraction, prevKeyframe.getValue(),
                        nextKeyframe.getValue());
                return evaluate;
            }
//            prevKeyframe = nextKeyframe;
        }
        return null;
    }

}
