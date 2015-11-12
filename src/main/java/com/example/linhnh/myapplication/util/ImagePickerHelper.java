package com.example.linhnh.myapplication.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import com.example.linhnh.myapplication.constant.AppConstant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hex0r on 7/8/15.
 */
public class ImagePickerHelper {

    public static final int AVATAR_PICKER_REQUEST_CODE = 6969;
    public static final int NORMAL_PICKER_REQUEST_CODE = 9696;
    public static final int AVATAR_TRIM_REQUEST_CODE = 678;
    public static final int NORMAL_TRIM_REQUEST_CODE = 789;

    private Fragment baseFragment;
    private Activity baseActivity;
    private boolean isFromFragment;

    private OnPickerSuccess onPickerSuccess;

    public ImagePickerHelper(Fragment baseFragment, OnPickerSuccess onPickerSuccess) {
        this.baseFragment = baseFragment;
        isFromFragment = true;
        this.onPickerSuccess = onPickerSuccess;
    }

    public ImagePickerHelper(Activity baseActivity, OnPickerSuccess onPickerSuccess) {
        this.baseActivity = baseActivity;
        isFromFragment = false;
        this.onPickerSuccess = onPickerSuccess;
    }

    private Uri outputFileUri;
    private Uri selectedFileUri;

    public void openImageIntent(int requestCode) {

        if (isFromFragment && baseFragment == null) {
            return;
        }

        if (!isFromFragment && baseActivity == null) {
            return;
        }

        if (requestCode == AVATAR_PICKER_REQUEST_CODE) {
            outputFileUri = Uri.fromFile(createFileUri(true));
        } else {
            outputFileUri = Uri.fromFile(createFileUri(false));
        }
        DebugLog.i("outputFileUri: " + outputFileUri.toString());
        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = isFromFragment ? baseFragment.getActivity().getPackageManager() : baseActivity.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }
        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        if (isFromFragment) {
            baseFragment.startActivityForResult(chooserIntent, requestCode);
        } else {
            baseActivity.startActivityForResult(chooserIntent, requestCode);
        }
    }

    private File createFileUri(boolean isAvatar) {
        // Determine Uri of camera image to save.
        File[] externalFile = ContextCompat.getExternalFilesDirs(isFromFragment ? baseFragment.getActivity() : baseActivity, null);
        if (externalFile == null) {
            externalFile = new File[]{isFromFragment ? baseFragment.getActivity().getExternalFilesDir(null) : baseActivity.getExternalFilesDir(null)};
        }
        final File root = new File(externalFile[0] + File.separator + AppConstant.APP_DIRECTORY_NAME + File.separator);
        if (root.mkdirs() || root.isDirectory()) {
            final String fname = FileUtils.getUniqueImageFilename(isAvatar);
            final File sdImageMainDirectory = new File(root, fname);
            if (sdImageMainDirectory.exists()) {
                if (sdImageMainDirectory.delete()) {
                    DebugLog.i("File: " + sdImageMainDirectory.getAbsolutePath() + " Deleted");
                } else {
                    DebugLog.i("File: " + sdImageMainDirectory.getAbsolutePath() + " not existed");
                }
            }
            return sdImageMainDirectory;
        } else {
            return null;
        }
    }

    private void handleResultData(Intent data, final boolean isAvatar, boolean needsTrim) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        DebugLog.i("isCamera:" + isCamera);
        if (isCamera) {

            selectedFileUri = outputFileUri;
        } else {

            selectedFileUri = data.getData();
            if (selectedFileUri == null && outputFileUri != null) {
                selectedFileUri = outputFileUri;
            }
        }

        final Uri uri = selectedFileUri;
        if (uri != null) {
            if (needsTrim) {
                new AsyncTask<Void, Void, Intent>() {
                    @Override
                    protected Intent doInBackground(Void... voids) {
                        try {
                            outputFileUri = Uri.fromFile(ImageCropHelper.createTempFile(true));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return ImageCropHelper.getCropIntent(isAvatar ? 500 : AppConstant.IMAGE_MAX_WIDTH, isAvatar ? 500 : AppConstant.IMAGE_MAX_HEIGHT, uri, outputFileUri);
                    }

                    @Override
                    protected void onPostExecute(Intent intent) {
                        if (intent != null) {
                            if (isFromFragment) {
                                baseFragment.startActivityForResult(intent, isAvatar ? AVATAR_TRIM_REQUEST_CODE : NORMAL_TRIM_REQUEST_CODE);
                            } else {
                                baseActivity.startActivityForResult(intent, isAvatar ? AVATAR_TRIM_REQUEST_CODE : NORMAL_TRIM_REQUEST_CODE);
                            }
                        } else {
                            if (onPickerSuccess != null) {
                                onPickerSuccess.onFinish(uri);
                            }
                        }
                    }
                }.execute();
            } else {
                if (onPickerSuccess != null) {
                    onPickerSuccess.onFinish(uri);
                }
            }
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        DebugLog.i("requestCode:" + requestCode + "  |  resultCode: " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AVATAR_PICKER_REQUEST_CODE:
                case NORMAL_PICKER_REQUEST_CODE:
                    handleResultData(data, requestCode == AVATAR_PICKER_REQUEST_CODE, true);
                    break;
                case AVATAR_TRIM_REQUEST_CODE:
                case NORMAL_TRIM_REQUEST_CODE:
                    handleResultData(data, requestCode == AVATAR_TRIM_REQUEST_CODE, false);
            }
        }
    }

    public interface OnPickerSuccess {
        void onFinish(Uri uri);
    }
}
