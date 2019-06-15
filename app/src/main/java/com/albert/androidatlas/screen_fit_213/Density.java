package com.albert.androidatlas.screen_fit_213;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Filename : Density.java
 *
 * @author : Mihawk
 * @since : 2019-06-13
 */

public class Density {

    private static final float WIDTH = 360;//参考设备，单位是dp

    private static float appDensity;//表示屏幕密度
    private static float appScaleDensity;//字体缩放比例，默认appDensity

    public static void setDensity(final Application application, Activity activity) {
        //获取当前app的屏幕显示信息
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (appDensity == 0) {
            //初始化赋值操作
            appDensity = displayMetrics.density;
            appScaleDensity = displayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体发生更改，重新对scaleDensisty进行赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //计算目标值density,scaleDensity,densityDpi
        float targetDensity = displayMetrics.widthPixels / WIDTH;// 1080/360 = 3.0
        float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = (int) (targetDensity * 160);

        //替换Activity的density，scaleDensity，densityDpi
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        dm.density = targetDensity;
        dm.scaledDensity = targetScaleDensity;
        dm.densityDpi = targetDensityDpi;

    }
}
