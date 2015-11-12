package com.example.linhnh.myapplication.adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.linhnh.myapplication.callback.OnRecyclerViewItemClick;

import butterknife.ButterKnife;

/**
 * Created by Envy 15T on 8/14/2015.
 */
public class OnClickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnRecyclerViewItemClick onRecyclerViewItemClick;

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    public OnClickViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (onRecyclerViewItemClick != null) {
            onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
        }
    }
}
