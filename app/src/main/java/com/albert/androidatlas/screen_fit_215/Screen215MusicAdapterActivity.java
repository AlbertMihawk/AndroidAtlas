package com.albert.androidatlas.screen_fit_215;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.albert.androidatlas.R;
import com.albert.androidatlas.screen_fit_215.ui.UIUtils;
import com.albert.androidatlas.screen_fit_215.ui.ViewCalculateUtil;


/**
 * Filename : Screen215MusicAdapterActivity.java
 *
 * @author : Mihawk
 * @since : 2019-06-23
 */

public class Screen215MusicAdapterActivity extends AppCompatActivity {

    private TextView tvText3;
    private TextView tvText5;
    private TextView tvText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.initInstance(this.getApplicationContext());
        getSupportActionBar().hide();
        setContentView(R.layout.activity_screen215_music_adapter);
        tvText3 = findViewById(R.id.tvText3);
        tvText5 = findViewById(R.id.tvText5);
        tvText4 = findViewById(R.id.tvText4);
        ViewCalculateUtil.setViewLinearLayoutParam(tvText3, 540, 100, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(tvText5, 810, 130, 0, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(tvText4, 1080, 100, 0, 0, 0, 0);
        ViewCalculateUtil.setTextSize(tvText3, 100);
        ViewCalculateUtil.setTextSize(tvText5, 100);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UIUtils.initInstance(this);

    }
}
