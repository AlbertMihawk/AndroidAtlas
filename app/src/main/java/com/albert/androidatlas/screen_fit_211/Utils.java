package com.albert.androidatlas.screen_fit_211;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Filename : Utils.java
 *
 * @author : Mihawk
 * @since : 2019-06-10
 */

public class Utils {
    private static Utils utils;

    //参考的宽高
    private static final float STANDARD_WIDTH = 720;
    private static final float STANDARD_HEIGHT = 1280;

    //屏幕显示的宽高
    private int mDisplayWidth;
    private int mDisplayHeight;

    private Utils(Context context) {
        //获取屏幕的宽高
        if (mDisplayWidth == 0 || mDisplayHeight == 0) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);

                if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    mDisplayWidth = displayMetrics.heightPixels;
                    mDisplayHeight = displayMetrics.widthPixels;
                } else {
                    mDisplayHeight = displayMetrics.heightPixels - getStatusBarHeight(context);
                    mDisplayWidth = displayMetrics.widthPixels;
                }
            }
        }
    }

    public static Utils getInstance(Context context) {
        if (utils == null) {
            utils = new Utils(context.getApplicationContext());
        }
        return utils;
    }

    public int getStatusBarHeight(Context context) {
        //系统环境提供的资源文件
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }


    public float getHorizontalScale() {
        return mDisplayWidth / STANDARD_WIDTH ;
    }

    public float getVerticalScale(){
        return mDisplayHeight / STANDARD_HEIGHT;
    }
}


