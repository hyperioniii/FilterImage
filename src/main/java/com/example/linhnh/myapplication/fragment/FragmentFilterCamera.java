package com.example.linhnh.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.camera.CameraPreview;
import com.example.linhnh.myapplication.camera.CameraRecordRenderer;
import com.example.linhnh.myapplication.camera.CameraUtils;
import com.example.linhnh.myapplication.camera.filter.FilterManager;
import com.example.linhnh.myapplication.camera.video.EncoderConfig;
import com.example.linhnh.myapplication.camera.video.TextureMovieEncoder;
import com.example.linhnh.myapplication.constant.AppConstant;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LinhNguyen on 12/4/2015.
 * http://capycoding.blogspot.com/2012/06/custom-camera-application.html
 */
public class FragmentFilterCamera extends BaseFragment {
    private static final String TAG = "CameraDemo";

    @Bind(R.id.camera_preview)
    CameraPreview preview;

    @Bind(R.id.picture_cam)
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
        preview.setAspectRatio(4, 4);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions(
                        getActivity(), new String[]{Manifest.permission.CAMERA},
                        AppConstant.REQUEST_CODE_CAMERA);
            } else {
                // permission has been granted, continue as usual
                mIsRecordEnabled = TextureMovieEncoder.getInstance().isRecording();

            }
        } else {
            mIsRecordEnabled = TextureMovieEncoder.getInstance().isRecording();
        }
        buttonClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                preview.changeFilter(FilterManager.FilterType.Normal);
                if (!mIsRecordEnabled) {
                    preview.queueEvent(new Runnable() {
                        @Override public void run() {
                            CameraRecordRenderer renderer = preview.getRenderer();
                            renderer.setEncoderConfig(new EncoderConfig(new File(
                                    CameraUtils.getCacheDirectory(getActivity(), true),
                                    "video-" + System.currentTimeMillis() + ".mp4"), 480, 640,
                                    1024 * 1024 /* 1 Mb/s */));
                        }
                    });
                }
                mIsRecordEnabled = !mIsRecordEnabled;
                preview.queueEvent(new Runnable() {
                    @Override public void run() {
                        preview.getRenderer().setRecordingEnabled(mIsRecordEnabled);
                    }
                });
                updateRecordButton();
            }
        });
    }

    public void updateRecordButton() {
        buttonClick.setBackgroundDrawable(
                mIsRecordEnabled ? getActivity().getResources().getDrawable(R.drawable.btn_stop_top_header)
                        : getActivity().getResources().getDrawable(R.drawable.btn_start_top_header));
    }

    int a = 0;

    @OnClick(R.id.cam_filter)
    public void btnCamFilter() {
        a++;
        preview.changeFilter(FilterManager.FilterType.ToneCurve);
        Toast.makeText(getActivity(), "next Filter" + a, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        preview.onResume();
        updateRecordButton();
    }

    @Override
    public void onPause() {
        preview.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        preview.onDestroy();
        super.onDestroy();
    }
}
