package com.albert.ui132distribution;

import com.albert.ui132distribution.listener.OnClickListener;
import com.albert.ui132distribution.listener.OnTouchListener;

/**
 * Filename : View.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class View {

    protected String name;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public View() {
    }

    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    protected boolean isContainer(int x, int y) {
        if (x > left && x < right && y > top && y < bottom) {
            return true;
        }
        return false;
    }

    //接受事件分发
    protected boolean dispatchTouchEvent(MotionEvent event) {

        System.out.println(this.name + " View::dispatchTouchEvent");
        //消费
        boolean result = false;
        if (onTouchListener != null && onTouchListener.onTouch(this, event)) {
            result = true;
        }
        if (!result && onTouchEvent(event)) {
            result = true;
        }
        return result;
    }

    private boolean onTouchEvent(MotionEvent event) {
        if (onClickListener != null) {
            onClickListener.onClick(this);
            return true;
        }
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
