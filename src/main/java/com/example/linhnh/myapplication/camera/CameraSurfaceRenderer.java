package com.example.linhnh.myapplication.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.linhnh.myapplication.camera.filter.FilterManager;
import com.example.linhnh.myapplication.camera.gles.FullFrameRect;
import com.example.linhnh.myapplication.camera.gles.GlUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraSurfaceRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    private final CameraPreview.CameraHandler mCameraHandler;
    private int mTextureId = GlUtil.NO_TEXTURE;
    private FullFrameRect mFullScreen;
    private SurfaceTexture mSurfaceTexture;
    private final float[] mSTMatrix = new float[16];

    private int mSurfaceWidth, mSurfaceHeight;
    private FilterManager.FilterType mCurrentFilterType;
    private FilterManager.FilterType mNewFilterType;

    public CameraSurfaceRenderer(Context context, CameraPreview.CameraHandler cameraHandler) {
        mContext = context;
        mCameraHandler = cameraHandler;
        mCurrentFilterType = mNewFilterType = FilterManager.FilterType.Normal;
    }

    public void setCameraPreviewSize(int width, int height) {

        float scaleHeight = mSurfaceWidth / (width * 1f / height * 1f);
        float surfaceHeight = mSurfaceHeight;

        if (mFullScreen != null) {
            mFullScreen.scaleMVPMatrix(1f, scaleHeight / surfaceHeight);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Matrix.setIdentityM(mSTMatrix, 0);
        mFullScreen =
                new FullFrameRect(FilterManager.getCameraFilterCam(mCurrentFilterType, mContext));
        mTextureId = mFullScreen.createTexture();
        mSurfaceTexture = new SurfaceTexture(mTextureId);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
        if (gl != null) {
            gl.glViewport(0, 0, width, height);
        }
        mCameraHandler.sendMessage(
                mCameraHandler.obtainMessage(CameraPreview.CameraHandler.SETUP_CAMERA, width,
                        height, mSurfaceTexture));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mSurfaceTexture.updateTexImage();

        if (mNewFilterType != mCurrentFilterType) {
            mFullScreen.changeProgram(FilterManager.getCameraFilterCam(mNewFilterType, mContext));
            mCurrentFilterType = mNewFilterType;
        }

        mSurfaceTexture.getTransformMatrix(mSTMatrix);
        mFullScreen.drawFrame(mTextureId, mSTMatrix);
    }

    public void notifyPausing() {

        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }

        if (mFullScreen != null) {
            mFullScreen.release(false);     // assume the GLSurfaceView EGL context is about
            mFullScreen = null;             // to be destroyed
        }

        //mIncomingWidth = mIncomingHeight = -1;
    }

    public void changeFilter(FilterManager.FilterType filterType) {
        mNewFilterType = filterType;
    }
}
