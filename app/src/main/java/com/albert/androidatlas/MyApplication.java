package com.albert.androidatlas;

import android.app.Application;

import com.albert.actual325.ui.UIUtils;

/**
 * Filename : MyApplication.java
 *
 * @author : Mihawk
 * @since : 2019-07-13
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.getInstance(this);
    }
}
