package com.albert.androidatlas.material_design_322;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.albert.androidatlas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Filename : MyRecyclerView.java
 *
 * @author : Mihawk
 * @since : 2019-06-30
 */

public class MyRecyclerView extends ViewGroup {

    List<View> viewList;
    private int currentY; //Y方向滑动距离
    private int rowCount;//加载的总数据，条数
    private int firstRow;//在屏幕中第一个显示的View的位置
    private int scrollY;//第一个view的左上顶点离屏幕的距离

    private MyRecycler myRecycler;

    private boolean needRelayout;
    private int width;
    private int height;
    private int[] heights;

    private Adapter adapter;

    //非用户主动触摸意愿的移动
    private int touchSlop;


    public MyRecyclerView(Context context) {
        super(context);
    }


    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            myRecycler = new MyRecycler(adapter.getViewtypeCount());
            scrollY = 0;
            firstRow = 0;
            needRelayout = true;
            requestLayout();//1.onMeasure 2onLayout
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h = 0;
        if (adapter != null) {
            this.rowCount = adapter.getCount();
            heights = new int[this.rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = adapter.getHeight(i);
            }
        }
        //数据的高度
        h = sumListHeight(heights, 0, heights.length);
        //比较数据的高度和设置的固定高度
        h = Math.min(heightSize, h);
        setMeasuredDimension(widthSize, h);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //初始化
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;

            viewList.clear();
            removeAllViews();
            if (adapter != null) {
                width = r - l;
                height = b - t;
                int left, top = 0, right, bottom;
                for (int i = 0; i < rowCount && top < height; i++) {
                    right = width;
                    bottom = top + heights[i];
                    View view = makeAndStep(i, 0, top, right, bottom);
                    viewList.add(view);
                    top = bottom;

                }

            }
        }
    }

    private View makeAndStep(int row, int left, int top, int right, int bottom) {
        View view = obtainView(row, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    private View obtainView(int row, int width, int viewHeight) {
        //key type
        int itemViewType = adapter.getItemViewType(row);
        //从回收池中取
        View recyclerView = myRecycler.get(itemViewType);
        View view;
        if (recyclerView == null) {
            view = adapter.onCreateViewHolder(row, recyclerView, this);
            if (view == null) {
                throw new RuntimeException("onCreateViewHolder");
            }
        } else {
            view = adapter.onBinderViewHolder(row, recyclerView, this);

        }
        view.setTag(R.id.tag_type_view, itemViewType);
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        addView(view, 0);
        return view;

    }

    /**
     * @param array      item的高度数组
     * @param firstIndex 第一个可见数组为角标
     * @param count      可见数据的总数
     * @return
     */
    private int sumListHeight(int[] array, int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y2 = Math.abs(currentY - (int) ev.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
                break;
        }
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //移动的距离，y方向
                int y2 = (int) event.getRawY();
                int diffY = currentY - y2;
                //画布移动，并不影响子控件的位置
                scrollBy(0, diffY);
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void scrollBy(int x, int y) {
        scrollY += y;
        scrollY = scrollBounds(scrollY);
        if (scrollY > 0) {
            //上滑正，下滑负
            while (scrollY > heights[firstRow]) {
                //上滑移除
                removeView(viewList.remove(0));
                scrollY -= heights[firstRow];
                firstRow++;

            }
            //上滑加载
            while (getFillHeight() < height) {
                int addLast = firstRow + viewList.size();
                View view = obtainView(addLast, width, heights[addLast]);
                viewList.add(viewList.size(), view);
            }

        } else if (scrollY < 0) {

            //下滑加载
            while (scrollY < 0) {
                int firstAddRow = firstRow - 1;
                View view = obtainView(firstAddRow, width, heights[firstAddRow]);
                viewList.add(0, view);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
            //下滑移除
            while (sumListHeight(heights, firstRow, viewList.size())
                    - scrollY - heights[firstRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }
        } else {

        }
        repositionViews();

    }

    private int scrollBounds(int scrollY) {
        if (scrollY > 0) {
            //上滑
            scrollY = Math.min(scrollY, sumListHeight(heights, firstRow, heights.length - firstRow) - height);

        } else if (scrollY < 0) {
            //下滑 极限值，会取零
            scrollY = Math.max(scrollY, -sumListHeight(heights, 0, firstRow));
        }
        return scrollY;
    }

    private void repositionViews() {
        int left, top, right, bottom, i;
        top = -scrollY;
        i = firstRow;
        for (View view : viewList) {
            bottom = top + heights[i++];
            view.layout(0, top, width, bottom);
            top = bottom;
        }
    }

    private int getFillHeight() {
        //数据的高度 - scrollY
        return sumListHeight(heights, firstRow, viewList.size()) - scrollY;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        int key = (int) view.getTag(R.id.tag_type_view);
        myRecycler.put(view, key);
    }

    interface Adapter {
        View onCreateViewHolder(int position, View convertView, ViewGroup parent);

        View onBinderViewHolder(int position, View convertView, ViewGroup parent);

        //item的类型
        int getItemViewType(int row);

        //item的类型数量
        int getViewtypeCount();

        int getCount();

        int getHeight(int index);
    }
}
