package com.example.linhnh.myapplication.CustomView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;

/**
 * Created by nguyenxuan on 2/12/2015.
 */
public class CropImageViewAware extends ViewAware {
    public CropImageViewAware(CropImageView view) {
        super(view);
    }

    public CropImageViewAware(CropImageView view, boolean checkActualViewSize) {
        super(view, checkActualViewSize);
    }

    @Override
    protected void setImageDrawableInto(Drawable drawable, View view) {
        view.setBackgroundDrawable(drawable);
    }

    @Override
    protected void setImageBitmapInto(Bitmap bitmap, View view) {
        ((CropImageView)view).setImageBitmap(bitmap);
    }
}