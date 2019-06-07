package com.albert.androidatlas.ui_123;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * FileName : UI123Canvas.java
 *
 * @author : Mihawk
 * @since : 2019-06-07
 */

public class UI123Canvas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new SplitView(this));
    }

}
