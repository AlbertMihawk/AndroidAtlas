package com.albert.androidatlas.ui_core_141.MyAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Filename : VSyncManager.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class VSyncManager {

    private static final VSyncManager sInstence = new VSyncManager();
    private final List<AnimationFrameCallBack> list = new ArrayList<>();

    private Runnable runnable = () -> {
        while (true) {
            try {
                Thread.sleep(16);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (AnimationFrameCallBack animationFrameCallBack : list) {
                animationFrameCallBack.doAnimationFrame(System.currentTimeMillis());
            }
        }
    };

    private VSyncManager() {
        new Thread(runnable).start();
    }

    public static VSyncManager getInstance() {
        return sInstence;
    }

    public void add(AnimationFrameCallBack animationFrameCallBack) {
        list.add(animationFrameCallBack);
    }

}
