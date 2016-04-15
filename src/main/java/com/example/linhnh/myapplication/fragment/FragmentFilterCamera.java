package com.example.linhnh.myapplication.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.camera.CameraPreview;
import com.example.linhnh.myapplication.camera.filter.FilterManager;
import com.example.linhnh.myapplication.camera.video.TextureMovieEncoder;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LinhNguyen on 12/4/2015.
 * http://capycoding.blogspot.com/2012/06/custom-camera-application.html
 */
public class FragmentFilterCamera extends BaseFragment {
    private static final String TAG = "CameraDemo";

    @InjectView(R.id.camera_preview)
    CameraPreview preview;

    @InjectView(R.id.picture_cam)
    ImageView buttonClick;


    private boolean mIsRecordEnabled;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_camera;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    public static FragmentFilterCamera newIntance() {
        return new FragmentFilterCamera();
    }


    @Override
    protected void initData() {
        preview.setAspectRatio(3,4);

        mIsRecordEnabled = TextureMovieEncoder.getInstance().isRecording();
        updateRecordButton();

        buttonClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preview.changeFilter(FilterManager.FilterType.Normal);
            }
        });

    }

    public void updateRecordButton() {
    }

    @OnClick(R.id.cam_filter)
    public void btnCamFilter(){
        preview.changeFilter(FilterManager.FilterType.ToneCurve);
    };

    @Override public void onResume() {
        super.onResume();
        preview.onResume();
        updateRecordButton();
    }

    @Override public void onPause() {
        preview.onPause();
        super.onPause();
    }

    @Override public void onDestroy() {
        preview.onDestroy();
        super.onDestroy();
    }
}
