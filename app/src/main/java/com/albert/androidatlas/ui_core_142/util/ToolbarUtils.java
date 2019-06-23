package com.albert.androidatlas.ui_core_142.util;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Field;

/**
 * Filename : ToolbarUtils.java
 *
 * @author : Mihawk
 * @since : 2019-06-23
 */

public class ToolbarUtils {
    public static TextView getToolbarTitleView(Toolbar toolbar) {

        try {
            Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
            field.setAccessible(true);

            Object object = field.get(toolbar);
            if (object != null) {
                TextView mTitleTextView = (TextView) object;
                return mTitleTextView;
            }
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static void setMarqueeForToolbarTitleView(final Toolbar toolbar) {
        //用post方式才能获取到控件的宽高
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                TextView mTitleTextView = getToolbarTitleView(toolbar);
                if (mTitleTextView == null) {
                    return;
                }
                mTitleTextView.setHorizontallyScrolling(true);
                mTitleTextView.setMarqueeRepeatLimit(-1);
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTitleTextView.setSelected(true);
            }
        });
    }
}
