package com.albert.androidatlas.screen_fit_214;

import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.albert.androidatlas.R;

/**
 * Filename : DisplayCutoutActivity.java
 *
 * @author : Mihawk
 * @since : 2019-06-15
 */

public class DisplayCutoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //1.设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //华为，小米，Oppo
        //1.判断手机厂商，为了判断是否有刘海
        //2.判端是否有刘海
        //3.设置是否让内容区域延伸进刘海
        //4.设置内容控件是否需要下移
        //5.获取刘海的高度（一般状态栏的高度跟刘海相同）


        //判断手机是不是刘海屏幕
        boolean hasDisplayCutout = true;//模拟器假设为true

        DisplayCutout displayCutout;
        View rootView = window.getDecorView();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            WindowInsets insets = rootView.getRootWindowInsets();//理解为窗口下挫
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P && insets != null) {
                displayCutout = insets.getDisplayCutout();
                if (displayCutout.getBoundingRects() != null &&
                        displayCutout.getBoundingRects().size() > 0 &&
                        displayCutout.getSafeInsetTop() > 0) {
                    hasDisplayCutout = true;
                }
            }
        }

        if (hasDisplayCutout) {

            //2.让内容区域延伸进刘海区
            WindowManager.LayoutParams params = window.getAttributes();
            /**
             * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式，内容下移，非全屏不受影响
             * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容区延伸进刘海区
             * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸进刘海区
             */
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                window.setAttributes(params);
            }

            //3.设置成沉浸式
            int flags = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= flags;//追加沉浸式设置
            window.getDecorView().setSystemUiVisibility(visibility);

            //可设置顶部状态栏颜色
//            window.getDecorView().setBackgroundColor(Color.BLUE);
        }

        getSupportActionBar().hide();
        setContentView(R.layout.activity_display_cutout);

        //使用计算的刘海区域下移内部控件
        ImageView iv_bg = (ImageView) findViewById(R.id.iv_bg);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_bg.getLayoutParams();
        layoutParams.topMargin = heightForDisplayCutout();
        iv_bg.setLayoutParams(layoutParams);

        //通过父容器设置下移Padding
        RelativeLayout layout = findViewById(R.id.container);
        layout.setPadding(0, heightForDisplayCutout(), 0, 0);


    }


    //获取状态栏高度
    // 通常情况下，刘海的高就是状态栏的高
    public int heightForDisplayCutout() {
        int resId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resId > 0) {
            return getResources().getDimensionPixelSize(resId);
        }
        return 96;
    }
}
