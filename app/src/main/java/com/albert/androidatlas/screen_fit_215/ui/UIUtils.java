package com.albert.androidatlas.screen_fit_215.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Filename : UIUtils.java
 *
 * @author : Mihawk
 * @since : 2019-06-29
 */

public class UIUtils {

    //标准值
    public static final int STANDARD_WIDTH = 1080;
    public static final int STANDARD_HEIGHT = 1920;

    //获取屏幕实际宽高
    private static float displayMetricsWidth;
    private static float displayMetricsHeight;

    private static float statusBarHeight;

    //工具类
    private static volatile UIUtils sInstance;


    private UIUtils(Context context) {
        //计算缩放比例
        if (displayMetricsWidth == 0f || displayMetricsHeight == 0f) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();

            //注意
            //方法一，包含状态栏，不包含虚拟按键
//            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            //方法二，整个屏幕的宽高，真实宽高
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

            statusBarHeight = getSystemBarHeight(context);

            //横竖屏问题
            if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                //横屏
                this.displayMetricsWidth = displayMetrics.heightPixels;
                this.displayMetricsHeight = displayMetrics.widthPixels;
            } else {
                //竖屏
                this.displayMetricsWidth = displayMetrics.widthPixels;
                this.displayMetricsHeight = displayMetrics.heightPixels;
            }

        }
    }

    //application
    public static void initInstance(Context context) {
        if (sInstance == null) {
            synchronized (UIUtils.class) {
                if (sInstance == null) {
                    sInstance = new UIUtils(context);
                }
            }
        }
    }

    public static UIUtils getInstance() {
        return sInstance;
    }

    public static UIUtils notifyInstance(Context context) {
        sInstance = new UIUtils(context);
        return sInstance;
    }

    public static int getValue(Context context, String dimenClass, String system_bar_height, int defaultValue) {
        //com.android.internal.R$dimen   status_bar_height 状态栏的高度
        try {
            Class<?> cls = Class.forName(dimenClass);
            Object object = cls.newInstance();
            Field field = cls.getField(system_bar_height);
            int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelSize(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public int getWidth(int width) {
        return Math.round((float) width * this.displayMetricsWidth / STANDARD_WIDTH);
    }

    public int getHeight(int height) {
        return Math.round((float) height * this.displayMetricsHeight / STANDARD_HEIGHT);
    }

    public float getHorizontalScaleValue() {
        return displayMetricsWidth / STANDARD_WIDTH;
    }

    public float getVerticalScaleValue() {
        return displayMetricsHeight / STANDARD_HEIGHT;
    }

    //system_bar_height 和 status_bar_height
    private int getSystemBarHeight(Context context) {
        return getValue(context, "com.android.internal.R$dimen", "status_bar_height", 48);
    }
}
