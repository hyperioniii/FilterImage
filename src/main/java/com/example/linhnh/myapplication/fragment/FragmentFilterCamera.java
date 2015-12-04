package com.example.linhnh.myapplication.fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.camera.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;

/**
 * Created by LinhNguyen on 12/4/2015.
 * http://capycoding.blogspot.com/2012/06/custom-camera-application.html
 */
public class FragmentFilterCamera extends BaseFragment {
    private static final String TAG = "CameraDemo";
    Camera camera;

    @InjectView(R.id.camera_preview)
    CameraPreview preview;

    @InjectView(R.id.picture_cam)
    ImageView buttonClick;

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
        FragmentFilterCamera fm = new FragmentFilterCamera();
        return fm;
    }


    @Override
    protected void initData() {
        preview = new CameraPreview(getActivity());

        buttonClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
            }
        });

    }


    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };

    /** Handles data for raw picture */
    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    /** Handles data for jpeg picture */
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream outStream = null;
            long time = 0;
            try {
                // write to local sandbox file system
//                outStream = CameraDemo.this.openFileOutput(String.format("%d.jpg", System.currentTimeMillis()), 0);
                // Or write to sdcard
                time =  System.currentTimeMillis();
                outStream = new FileOutputStream(String.format("/sdcard/%d.jpg",time));
                outStream.write(data);
                outStream.close();
                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {



            }
            Log.d(TAG, "onPictureTaken - jpeg");
        }
    };


}
