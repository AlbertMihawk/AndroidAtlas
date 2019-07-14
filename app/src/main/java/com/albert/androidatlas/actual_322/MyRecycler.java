package com.albert.androidatlas.actual_322;

import android.view.View;

import java.util.Stack;

/**
 * Filename : MyRecycler.java
 *
 * @author : Mihawk
 * @since : 2019-06-30
 */

public class MyRecycler {

    private Stack<View>[] views;

    public MyRecycler(int typeNumber) {
        views = new Stack[typeNumber];
        for (int i = 0; i < typeNumber; i++) {
            views[i] = new Stack<>();
        }
    }

    public void put(View view, int type) {
        views[type].push(view);
    }

    public View get(int type) {
        try {
            return views[type].pop();
        } catch (Exception e) {
            return null;
        }
    }


}
