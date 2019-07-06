package com.albert.materialdesign324;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

/**
 * Filename : BaseDelegateAdapter.java
 *
 * @author : Mihawk
 * @since : 2019-07-06
 */

public class BaseDelegateAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private Context context;
    private LayoutHelper mLayoutHelper;
    private int mCount = -1;
    private int mLayoutId = -1;


    public BaseDelegateAdapter(Context context, LayoutHelper mLayoutHelper, int mLayoutId, int mCount) {
        this.context = context;
        this.mLayoutHelper = mLayoutHelper;
        this.mLayoutId = mLayoutId;
        this.mCount = mCount;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(context).inflate(mLayoutId, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return mCount;
    }
}
