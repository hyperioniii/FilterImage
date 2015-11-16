package com.example.linhnh.myapplication.adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.linhnh.myapplication.BaseApplication;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.activity.BaseActivity;
import com.example.linhnh.myapplication.callback.OnRecyclerViewItemClick;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LinhNguyen on 11/12/2015.
 */
public class FilterViewHolder extends OnClickViewHolder {

    OnRecyclerViewItemClick onRecyclerViewItemClick;

    public FilterViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    public static int LAYOUT_FILTER_IMG = R.layout.item_filter_img ;

    @InjectView(R.id.item_filter)
    ImageView ImgFilter;


    public void setimg(int i){
        Glide.with(BaseApplication.getInstance()).load(i).centerCrop().into(ImgFilter);
    }

    @OnClick(R.id.item_filter)
    public void ImgFilter(){
        if(onRecyclerViewItemClick!=null){
            onRecyclerViewItemClick.onItemClick(ImgFilter,getAdapterPosition());
        }
    }


}
