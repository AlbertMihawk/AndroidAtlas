package com.albert.ui132distribution;

import java.util.ArrayList;
import java.util.List;

/**
 * Filename : ViewGroup.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class ViewGroup extends View {

    //存放子控件
    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];
    private TouchTarget mFirstTouchTarget;


    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = childList.toArray(new View[childList.size()]);
    }

    //事件分发入口
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println(this.name + "  ViewGroup::dispatchTouchEvent");
        int actionMasked = event.getActionMasked();
        boolean intercepted = onInterceptTouchEvent(event);
        boolean handled = false;
        TouchTarget newTouchTarget;
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted) {
            //down事件
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                final View[] children = mChildren;
                //遍历倒序（布局后面有可能覆盖前面的控件）必须倒序
                for (int i = children.length - 1; i >= 0; i--) {
                    //View不能接收事件
                    View child = children[i];
                    if (!child.isContainer(event.getX(), event.getY())) {
                        continue;
                    }
                    //能够接受事件 分发给child
                    if (dispatchTransformedTouchEvent(event, child)) {
                        handled = true;
                        newTouchTarget = addTouchTarget(child);
                        break;
                    }
                }
            }
        }
        if (mFirstTouchTarget == null) {
            handled = dispatchTransformedTouchEvent(event, null);
        }
        return handled;
    }


    private TouchTarget addTouchTarget(View child) {
        final TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;
    }

    /**
     * 分发处理
     *
     * @param event
     * @param child
     * @return
     */
    private boolean dispatchTransformedTouchEvent(MotionEvent event, View child) {
        boolean handled = false;
        //child消费了事件
        if (child != null) {
            handled = child.dispatchTouchEvent(event);
        } else {
            handled = super.dispatchTouchEvent(event);
        }
        return handled;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    private static final class TouchTarget {
        private static final Object sRecycleLock = new Object[0];
        //回收池
        private static TouchTarget sRecycleBin;
        //size
        private static int sRecycledCount;
        public TouchTarget next;
        private View child;//当前缓存View

        public static TouchTarget obtain(View child) {
            TouchTarget target;
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecycleBin;
                }

                sRecycleBin = target.next;
                sRecycledCount--;
                target.next = null;
            }
            target.child = child;
            return target;
        }

        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("");
            }
            synchronized (sRecycleLock) {
                if (sRecycledCount < 32) {
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount++;
                }
            }
        }
    }
}
