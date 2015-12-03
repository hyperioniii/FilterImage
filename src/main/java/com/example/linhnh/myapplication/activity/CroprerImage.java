package com.example.linhnh.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.edmodo.cropper.CropImageView;
import com.example.linhnh.myapplication.CustomView.CropImageViewAware;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.util.DebugLog;
import com.example.linhnh.myapplication.util.FileUtils;
import com.example.linhnh.myapplication.util.Memory;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LinhNguyen on 1/17/2015.
 */
public class CroprerImage extends BaseActivity {
    @InjectView(R.id.cropImageView)
    CropImageView cropImageView;

    // Instance variables
    private int ratioX;
    private int ratioY;
    String link;

    @Override
    public int setContentViewId() {
        return R.layout.crop_image;
    }

    @Override
    public void initView() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheSizePercentage(20)
                .denyCacheImageMultipleSizesInMemory().writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    @OnClick(R.id.crop_cancel)
    public void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.crop_ok)
    public void cropOk() {
        link = FileUtils.saveIMG(cropImageView.getCroppedImage());
        DebugLog.d("abc" + link);
        Intent i = new Intent(CroprerImage.this, MainActivity.class);
        i.putExtra("String_IMG_CROP", link);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void initData() {
        String linkIMG = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ratioX = bundle.getInt("RATIO_X");
            ratioY = bundle.getInt("RATIO_Y");
            linkIMG = bundle.getString("PATH_IMAGE_CROP");
        }
        cropImageView.setAspectRatio(ratioX, ratioY);
        cropImageView.setFixedAspectRatio(true);

        File imgFile = new File(linkIMG);

        if (imgFile.exists()) {
            ImageLoader.getInstance().displayImage("file://" + imgFile.getPath(), new CropImageViewAware(cropImageView), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);

                }
            });
        }
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(MainScreenSettingEvent event) {

    }
/*
* Giải phóng memory
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Memory.unbindDrawable(cropImageView);
    }
}
