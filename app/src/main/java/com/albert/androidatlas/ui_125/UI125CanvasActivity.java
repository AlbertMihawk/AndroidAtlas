package com.albert.androidatlas.ui_125;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.albert.androidatlas.R;

/**
 * Filename : UI125CanvasActivity.java
 *
 * @author : Mihawk
 * @since : 2019-06-08
 */

public class UI125CanvasActivity extends AppCompatActivity {

    private DragBubbleView mDragBubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui125_canvas);
        mDragBubbleView = findViewById(R.id.dragBubbleView);

        //TODO 1.3.1事件分发机制
        mDragBubbleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDragBubbleView.onTouchEvent(event);
                return true;
            }
        });
    }

    public void onClick(View view) {
        mDragBubbleView.reset();
    }
}
