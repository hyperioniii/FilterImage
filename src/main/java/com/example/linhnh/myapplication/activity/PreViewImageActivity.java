package com.example.linhnh.myapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.linhnh.myapplication.CustomView.ImageAutoScale;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.util.DebugLog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LinhNH on 12/1/2015.
 */
public class PreViewImageActivity extends BaseActivity {
    @Override
    public int setContentViewId() {
        return R.layout.include_preview_layout;
    }

    public static final String PREVIEW_IMAGE = "preview_image";

    private Bitmap imgLink;

    @Bind(R.id.img_preview_ac)
    ImageAutoScale imgPreview;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        byte[] bytes = getIntent().getByteArrayExtra(PREVIEW_IMAGE);
        imgLink = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        imgLink = getIntent().getExtras().getParcelable(PREVIEW_IMAGE);
        DebugLog.d("---------- show image -------------------");
        imgPreview.setImageBitmap(imgLink);
    }

    @OnClick(R.id.btn_close)
    public void closePreview(){
        finish();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(MainScreenSettingEvent event) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgLink.recycle();
    }
}
