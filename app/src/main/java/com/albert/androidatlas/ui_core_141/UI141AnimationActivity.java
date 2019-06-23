package com.albert.androidatlas.ui_core_141;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.albert.androidatlas.R;
import com.albert.androidatlas.ui_core_141.MyAnimation.LinearInterpolator;
import com.albert.androidatlas.ui_core_141.MyAnimation.MyObjectAnimator;

/**
 * Filename : UI141AnimationActivity.java
 *
 * @author : Mihawk
 * @since : 2019-06-22
 */

public class UI141AnimationActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui141_animation);

        textView = (TextView) findViewById(R.id.textView);
        MyObjectAnimator animator = MyObjectAnimator.ofFloat(textView, "scaleX", 1.0f, 4.0f, 1f);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
