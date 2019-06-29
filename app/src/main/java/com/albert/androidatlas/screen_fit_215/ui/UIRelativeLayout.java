package com.albert.androidatlas.screen_fit_215.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Filename : UIRelativeLayout.java
 *
 * @author : Mihawk
 * @since : 2019-06-29
 */

public class UIRelativeLayout extends RelativeLayout {

    private boolean flag = true;

    public UIRelativeLayout(Context context) {
        super(context);
    }

    public UIRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UIRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UIRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (flag) {
            UIUtils.initInstance(getContext());

            float scaleX = UIUtils.getInstance().getHorizontalScaleValue();
            float scaleY = UIUtils.getInstance().getVerticalScaleValue();

            int childCount = this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                params.width = (int) (params.width * scaleX);
                params.height = (int) (params.height * scaleY);
                params.leftMargin = (int) (params.leftMargin * scaleX);
                params.rightMargin = (int) (params.rightMargin * scaleX);
                params.topMargin = (int) (params.topMargin * scaleY);
                params.bottomMargin = (int) (params.bottomMargin * scaleY);

                child.setPadding((int) (child.getLeft() * scaleX),
                        (int) (child.getTop() * scaleY),
                        (int) (child.getRight() * scaleX),
                        (int) (child.getBottom() * scaleY));
            }
            flag = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
