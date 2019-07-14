package com.albert.androidatlas.actual_322;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.albert.androidatlas.R;

/**
 * Filename : MaterialDesign322Activity.java
 *
 * @author : Mihawk
 * @since : 2019-06-30
 */

public class MaterialDesign322Activity extends AppCompatActivity {
    MyRecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design322);
        myRecyclerView = findViewById(R.id.table);
        myRecyclerView.setAdapter(new MyRecyclerView.Adapter() {
            @Override
            public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
                TextView textView = convertView.findViewById(R.id.text1);
                textView.setText("第" + position + "行");

                return convertView;
            }

            @Override
            public View onBinderViewHolder(int position, View convertView, ViewGroup parent) {
                TextView textView = convertView.findViewById(R.id.text1);
                textView.setText("网易课堂" + position);
                return convertView;
            }


            @Override
            public int getItemViewType(int row) {
                return 0;
            }

            @Override
            public int getViewtypeCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return 50;
            }

            @Override
            public int getHeight(int index) {
                return 100;
            }
        });
    }
}
