package com.albert.ui132distribution;

import com.albert.ui132distribution.listener.OnTouchListener;

/**
 * Filename : Activity.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class Activity {
    public static void main(String[] args) {
        MotionEvent motionEvent = new MotionEvent(100, 100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);

        ViewGroup group = new ViewGroup(0, 0, 1080, 1920);
        group.setName("第一层容器");
        ViewGroup group1 = new ViewGroup(0, 0, 600, 600);
        group1.setName("第二级容器");
        View view = new View(0, 0, 600, 600);
        view.setName("子控件");


        group.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                System.out.println("第一层容器 onTouch -> false");
                return false;
            }
        });
        group1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                System.out.println("第二层容器 onTouch -> false");
                return true;
            }
        });
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                System.out.println("子控件 onTouch -> false");
                return false;
            }
        });


        group.addView(group1);
        group1.addView(view);

        group.dispatchTouchEvent(motionEvent);

    }
}
