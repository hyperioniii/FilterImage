package com.example.linhnh.myapplication.util;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;


import com.example.linhnh.myapplication.BaseApplication;
import com.example.linhnh.myapplication.constant.AppConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageCropHelper {

    public static final String CROP_ACTION = "com.android.camera.action.CROP";

    /// Should be called from background thread
    public static Intent getCropIntent(int width, int height, Uri uri, Uri uriTemp) {
        Uri uriToUse = getUriToUse(uri);
        if (uriToUse == null) {
            return null;
        }

        DebugLog.i("width height " + width + " " + height);
        Intent intent = new Intent(CROP_ACTION);
        intent.setDataAndType(uriToUse, "image/*");
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTemp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
        return intent;
    }

    public static File createTempFile(boolean external) throws IOException {
        return File.createTempFile(AppConstant.APP_PHOTO_FOLDER_NAME, ".jpg", external
                ? BaseApplication.getInstance().getExternalCacheDir() : BaseApplication.getInstance().getCacheDir());
    }

    private static Uri getUriToUse(final Uri uri) {
        if (uri.getScheme().equals("content")) {
            final ContentResolver contentResolver = BaseApplication.getInstance().getContentResolver();
            final Cursor cursor = contentResolver.query(uri, null, null, null, null);
            cursor.moveToFirst();
            try {
                final String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                return Uri.fromFile(new File(path));
            } catch (IllegalArgumentException e) {
                return getCopyUri(uri, contentResolver);
            } finally {
                cursor.close();
            }
        } else if (uri.getScheme().equals("file")) {
            return uri;
        }
        return null;
    }

    private static Uri getCopyUri(Uri uri, ContentResolver contentResolver) {
        File tmpFile;
        InputStream iStream = null;
        FileOutputStream oStream = null;
        try {
            tmpFile = createTempFile(true);
            iStream = contentResolver.openInputStream(uri);
            oStream = new FileOutputStream(tmpFile);

            byte[] buffer = new byte[1024 * 64]; //64KiB????
            int size;
            while (-1 != (size = iStream.read(buffer))) {
                oStream.write(buffer, 0, size);
            }

            return Uri.fromFile(tmpFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (iStream != null) {
                try {
                    iStream.close();
                } catch (IOException ignored) {
                }
            }
            if (oStream != null) {
                try {
                    oStream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

}
