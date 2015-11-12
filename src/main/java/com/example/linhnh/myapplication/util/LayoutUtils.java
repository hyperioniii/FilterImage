package com.example.linhnh.myapplication.util;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LinhNguyen on 9/22/2015.
 */
public class LayoutUtils {
    public static
    @NonNull
    View inflate(@NonNull ViewGroup viewGroup, int layoutId, boolean attachToRoot) {
        return LayoutInflater
                .from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, attachToRoot);
    }
}
