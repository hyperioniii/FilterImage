package com.example.linhnh.myapplication.adapter;

import android.view.ViewGroup;

import com.example.linhnh.myapplication.adapter.ViewHolder.FilterViewHolder;

/**
 * Created by LinhNguyen on 11/12/2015.
 */
public class FilterAdapter extends AdapterWithItemClick<FilterViewHolder> {


    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.setimg();
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
