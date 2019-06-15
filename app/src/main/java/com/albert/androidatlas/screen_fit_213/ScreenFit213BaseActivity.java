package com.albert.androidatlas.screen_fit_213;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Filename : ScreenFit213BaseActivity.java
 *
 * @author : Mihawk
 * @since : 2019-06-13
 */

public class ScreenFit213BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.setDensity(getApplication(), this);
    }
}
