package com.albert.androidatlas.screen_fit_213;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Filename : ScreenFit213App.java
 *
 * @author : Mihawk
 * @since : 2019-06-15
 */

public class ScreenFit213App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                //通过Appplication统一控制activity
                Density.setDensity(ScreenFit213App.this, activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
