package com.example.linhnh.myapplication.adapter.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.model.CalenderEvent;
import com.example.linhnh.myapplication.util.DebugLog;

import butterknife.InjectView;

/**
 * Created by LinhNguyen on 11/4/2015.
 */
public class ListEventViewHolder extends OnClickViewHolder {

    @InjectView(R.id.img_item_img)
    ImageView imgEvent;
    @InjectView(R.id.tv_item_content)
    TextView tvContetnt;

    public ListEventViewHolder(View itemView) {
        super(itemView);
    }

    public void setup(CalenderEvent data) {
        DebugLog.d("=========" + data.uri);
//        Glide.with(BaseApplication.getInstance()).load(data.uri).override(350,500).into(imgEvent);
        tvContetnt.setText(data.content);
    }

    public void swipeLayout(int x, int offsetxx) {
        int height = imgEvent.getLayoutParams().height;
        int heighttv = tvContetnt.getLayoutParams().height;
        int total = height - heighttv;

        // Textview Up - scroll up
        if (offsetxx - x > 0) {
            delta = offsetxx - x;
        } else {
            delta = offsetxx - x;
        }
        tvContetnt.setY(tvContetnt.getY() + delta);
        DebugLog.d("============12============" + tvContetnt.getY() + "++" + delta);
    }

    int delta;

}
