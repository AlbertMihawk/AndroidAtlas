package com.albert.androidatlas.actual_321_2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.albert.androidatlas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Filename : MaterialDesign3212Activity.java
 *
 * @author : Mihawk
 * @since : 2019-06-30
 */

public class MaterialDesign3212Activity extends AppCompatActivity {
    FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design3212);
        flowLayout = findViewById(R.id.flow);
        List<String> tags = new ArrayList<>();
        tags.add("网易");
        tags.add("这是一个超长的标题，可能一行装不下，这是一个超长的标题，可能一行装不下");
        tags.add("网易课堂");
        tags.add("网易云音乐");
        tags.add("有道云");
        tags.add("高级UI自定义控件");
        tags.add("继承控件");
        tags.add("今天天气真的好好");
        tags.add("杭州天气也不错~");
        tags.add("好好学习   天天向上");
        tags.add("你是最棒的");
        tags.add("加油");
        flowLayout.addTag(tags);
    }
}
