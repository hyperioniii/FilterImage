package com.example.linhnh.myapplication.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LinhNguyen on 12/3/2015.
 */
public class Memory {
    public static  void unbindDrawable(View view) {
        if(view == null ){
            return;
        }
        if(view instanceof ImageView){
            ((ImageView) view).setImageBitmap(null);
            ((ImageView) view).setImageDrawable(null);
        }
        if(view instanceof TextView){
            ((TextView) view).setTypeface(null);
        }
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        view.setBackgroundDrawable(null);
        if(view instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                unbindDrawable(child);
            }
            if (!(view instanceof AdapterView<?>))
                ((ViewGroup) view).removeAllViews();
            else {
                ((AdapterView) view).setAdapter(null);
            }
        }
    }
}
