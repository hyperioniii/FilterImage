//package com.example.linhnh.myapplication.activity;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffColorFilter;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.target.ImageViewTarget;
//import com.example.linhnh.myapplication.CustomView.ImageAutoScale;
//import com.example.linhnh.myapplication.R;
//import com.example.linhnh.myapplication.util.DebugLog;
//
//import butterknife.InjectView;
//import butterknife.OnClick;
//import uk.co.senab.photoview.PhotoViewAttacher;
//
///**
// * Created by hex0r on 8/11/15.
// */
//public class PreviewFullScreenActivity extends BaseActivity {
//
//    public static final String PREVIEW_IMAGE = "preview_image";
//
//    private Bitmap imgLink;
//    private PhotoViewAttacher photoViewAttacher;
//
//    @InjectView(R.id.img_preview)
//    ImageView imgPreview;
//
//    Paint paint = new Paint();
//
//    @Override
//    public void initData() {
////        imgLink = getIntent().getStringExtra(PREVIEW_IMAGE);
//        DebugLog.d("---------- show image -------------------");
////        imgLink = BitmapFactory.decodeByteArray(
////                getIntent().getByteArrayExtra(PREVIEW_IMAGE), 0, getIntent().getByteArrayExtra("byteArray").length);
//
//         imgLink = getIntent().getExtras().getParcelable(PREVIEW_IMAGE);
//        DebugLog.d("---------- show image -------------------");
//        if (imgLink != null) {
////            Glide.with(this).load(imgLink).into(new ImageViewTarget<GlideDrawable>(imgPreview) {
////                @Override
////                protected void setResource(GlideDrawable resource) {
////                    imgPreview.setImageDrawable(resource);
////                    photoViewAttacher = new PhotoViewAttacher(imgPreview);
////
////                    photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
////                        @Override
////                        public void onPhotoTap(View view, float v, float v1) {
////                            onCloseClick();
////                        }
////                    });
////                }
////            });
//            DebugLog.d("---------- show image -------------------");
//            imgPreview.setImageBitmap(imgLink);
//            photoViewAttacher = new PhotoViewAttacher(imgPreview);
//
//                    photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//                        @Override
//                        public void onPhotoTap(View view, float v, float v1) {
//                            onCloseClick();
//                        }
//                    });
//        } else {
//            finish();
//        }
//    }
//
//    @Override
//    public int setContentViewId() {
//        return R.layout.include_preview_layout;
//    }
//
//    @Override
//    public void initView() {
//
//    }
//
//    @OnClick(R.id.btn_close)
//    public void onCloseClick() {
//        if (photoViewAttacher != null) {
//            photoViewAttacher.cleanup();
//        }
//
//        finish();
//    }
//
//    @OnClick(R.id.img_preview)
//    public void onPreviewClick() {
//        if (photoViewAttacher != null) {
//            photoViewAttacher.cleanup();
//        }
//
//        finish();
//    }
//}
