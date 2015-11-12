package com.example.linhnh.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Envy 15T on 10/5/2015.
 */
public class SaveCaptureTask extends AsyncTask<Integer, Integer, Boolean> {

    public static final String TAG = "SaveCaptureTask";

    File pictureFile;
    byte[] data;

    OnSaveCaptureListener onSaveCaptureListener;

    public void setOnSaveCaptureListener(OnSaveCaptureListener onSaveCaptureListener) {
        this.onSaveCaptureListener = onSaveCaptureListener;
    }

    public SaveCaptureTask(File pictureFile, byte[] data) {
        this.pictureFile = pictureFile;
        this.data = data;
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        return saveBitmap();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (onSaveCaptureListener != null) {
            onSaveCaptureListener.onSaveCaptureListener(aBoolean, pictureFile);
        }
    }

    private boolean saveBitmap() {
        Bitmap captureBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap savedBitmap = Bitmap.createBitmap(captureBitmap.getHeight(), captureBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(savedBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        Matrix matrix = new Matrix();
        matrix.postTranslate(-captureBitmap.getWidth() / 2, -captureBitmap.getHeight() / 2); // Centers image
        matrix.postRotate(90);
        matrix.postTranslate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawBitmap(captureBitmap, matrix, paint);
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            savedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            savedBitmap.recycle();
            captureBitmap.recycle();
            matrix = null;
            paint = null;
            canvas = null;
            return true;
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
            return false;
        }
    }


    public interface OnSaveCaptureListener {
        void onSaveCaptureListener(boolean result, File pictureFile);
    }
}
