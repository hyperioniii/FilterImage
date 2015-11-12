package com.example.linhnh.myapplication.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.linhnh.myapplication.callback.OnRecyclerViewItemClick;

public abstract class AdapterWithItemClick<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    OnRecyclerViewItemClick onRecyclerViewItemClick;

    public OnRecyclerViewItemClick getOnRecyclerViewItemClick() {
        return onRecyclerViewItemClick;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }
}
