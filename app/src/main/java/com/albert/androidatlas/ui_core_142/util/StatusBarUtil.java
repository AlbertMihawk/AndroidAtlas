package com.albert.androidatlas.ui_core_142.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.albert.androidatlas.ui_core_142.ui.UIUtils;


/**
 * Filename : StatusBarUtil.java
 *
 * @author : Mihawk
 * @since : 2019-06-23
 */

//沉浸式状态栏
public class StatusBarUtil {
    public static void setTranslateStateBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void setStateBar(Activity activity, View toolBar) {
        setTranslateStateBar(activity);
        if (toolBar != null) {
            toolBar.setPadding(toolBar.getPaddingLeft(), UIUtils.getInstance().getSystemBarHeight(activity),
                    toolBar.getPaddingRight(), toolBar.getPaddingBottom());
        }

    }
}
