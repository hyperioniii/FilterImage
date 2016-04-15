package com.example.linhnh.myapplication.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.camera.CameraPreview;
import com.example.linhnh.myapplication.camera.filter.FilterManager;
import com.example.linhnh.myapplication.camera.video.TextureMovieEncoder;
import com.example.linhnh.myapplication.constant.AppConstant;
import com.example.linhnh.myapplication.util.DebugLog;
import com.example.linhnh.myapplication.util.FileUtils;
import com.example.linhnh.myapplication.util.ImagePickerHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        preview.setAspectRatio(4,4);
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
                updateRecordButton();
            }
        } else {
             mIsRecordEnabled = TextureMovieEncoder.getInstance().isRecording();
            updateRecordButton();
        }

        buttonClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                preview.changeFilter(FilterManager.FilterType.Normal);
                imagePickerHelper = new ImagePickerHelper(this, new ImagePickerHelper.OnPickerSuccess() {
                    @Override
                    public void onFinish(Uri uri) {
                        path = FileUtils.getPath(getActivity(), uri);
                        if (path == null) {
                            return;
                        }
                        File imageFile = new File(path);
                        DebugLog.d("-----path img: ======================: " + path);

                    }
                });
            }
        });

    }  String path;
    private ImagePickerHelper imagePickerHelper;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private Uri fileUri;
    /*
 * Capturing Camera Image will lauch camera app requrest image capture
 */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "imgaeheheheh");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("imgaeheheheh", "Oops! Failed create "
                        + "imgaeheheheh" + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    public void updateRecordButton() {
    }
int a = 0 ;
    @OnClick(R.id.cam_filter)
    public void btnCamFilter(){
        a++;
        preview.changeFilter(FilterManager.FilterType.ToneCurve);
        Toast.makeText(getActivity(),"next Filter" +a,Toast.LENGTH_LONG).show();
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
