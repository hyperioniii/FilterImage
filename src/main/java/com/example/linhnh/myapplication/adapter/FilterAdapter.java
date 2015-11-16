package com.example.linhnh.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.adapter.ViewHolder.FilterViewHolder;
import com.example.linhnh.myapplication.callback.OnRecyclerViewItemClick;

/**
 * Created by LinhNguyen on 11/12/2015.
 */
public class FilterAdapter extends AdapterWithItemClick<FilterViewHolder> implements OnRecyclerViewItemClick{

    public int[] list_filter = {R.drawable.sc000_avatar_a,R.drawable.sc000_avatar_a,R.drawable.sc000_avatar_a,
            R.drawable.sc000_avatar_a,R.drawable.sc000_avatar_a,R.drawable.sc000_avatar_a,R.drawable.sc000_avatar_a};

    public FilterAdapter() {
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(FilterViewHolder.LAYOUT_FILTER_IMG, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.setOnRecyclerViewItemClick(this);
        holder.setimg(list_filter[position]);
    }

    @Override
    public int getItemCount() {
       return list_filter.length;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
