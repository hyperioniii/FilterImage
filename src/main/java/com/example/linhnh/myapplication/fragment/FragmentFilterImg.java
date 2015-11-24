package com.example.linhnh.myapplication.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.linhnh.myapplication.CustomView.ImageAutoScale;
import com.example.linhnh.myapplication.CustomView.MetaballMenu;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.activity.MainActivity;
import com.example.linhnh.myapplication.adapter.DividerItemDecoration;
import com.example.linhnh.myapplication.adapter.FilterAdapter;
import com.example.linhnh.myapplication.callback.OnHeaderIconClickListener;
import com.example.linhnh.myapplication.callback.OnRecyclerViewItemClick;
import com.example.linhnh.myapplication.constant.HeaderIconOption;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.util.FileUtils;
import com.example.linhnh.myapplication.util.ImagePickerHelper;
import com.example.linhnh.myapplication.util.ListFilter;
import com.example.linhnh.myapplication.util.UiUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by LinhNguyen on 11/12/2015.
 */
public class FragmentFilterImg extends BaseFragment implements OnHeaderIconClickListener, MetaballMenu.MetaballMenuClickListener, OnRecyclerViewItemClick {

    @InjectView(R.id.review_filter_img)
    ImageView imgFilter;

    @InjectView(R.id.line_demo_filter)
    RecyclerView mRecyclerView;

    @InjectView(R.id.img_main_add)
    ImageAutoScale imgAdd;

    @InjectView(R.id.metaball_menu)
    MetaballMenu metaballMenu;

    FilterAdapter adapter;
    public Bitmap bitmap;
    String path;
    private ImagePickerHelper imagePickerHelper;

    public static FragmentFilterImg newIntance() {
        FragmentFilterImg fm = new FragmentFilterImg();
        return fm;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_filter_img;
    }

    @Override
    protected void initView(View root) {
        ((MainActivity) getActivity()).toolBar.setOnHeaderIconClickListener(this);
        MainScreenSettingEvent mainScreenSettingEvent = new MainScreenSettingEvent("FilterImg", HeaderIconOption.RIGHT_NONE, HeaderIconOption.LEFT_BACK);
        EventBus.getDefault().post(mainScreenSettingEvent);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
    }

    @Override
    protected void getArgument(Bundle bundle) {
    }

    @Override
    protected void initData() {

        UiUtil.hideView(metaballMenu, true);
        metaballMenu.setMenuClickListener(this);
        imagePickerHelper = new ImagePickerHelper(this, new ImagePickerHelper.OnPickerSuccess() {
            @Override
            public void onFinish(Uri uri) {
                 path = FileUtils.getPath(getActivity(), uri);
                if (path == null) {
                    return;
                }
                File imageFile = new File(path);

                Glide.with(getActivity()).load("file://" + imageFile).into(imgFilter);
                UiUtil.hideView(imgAdd, true);
                UiUtil.showView(metaballMenu);
                UiUtil.showView(imgFilter);
            }
        });

        adapter = new FilterAdapter();
        adapter.setOnRecyclerViewItemClick(this);
        mRecyclerView.setAdapter(adapter);

        imgFilter.setOnTouchListener(onTouchListener);

    }

    @OnClick(R.id.img_main_add)
    public void choseImage() {
        imagePickerHelper.openImageIntent(ImagePickerHelper.NORMAL_TRIM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePickerHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onHeaderBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onHeaderClose() {

    }

    @Override
    public void onHeaderSetting() {

    }

    @Override
    public void onHeaderEdit() {

    }

    @Override
    public void onHeaderDelete() {

    }

    public int rotation = 0 ;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setting1:
                UiUtil.showView(mRecyclerView);
                break;
            case R.id.btn_setting2:
                imagePickerHelper.openImageIntent(ImagePickerHelper.CAMERA_PICKER_REQUEST_CODE);
                break;
            case R.id.btn_setting3:
                imgFilter.setRotation(rotation);
                rotation = rotation + 90;
                break;
            case R.id.btn_setting4:
                UiUtil.hideView(mRecyclerView, true);
                UiUtil.hideView(metaballMenu, true);
                UiUtil.hideView(imgFilter, true);
                UiUtil.showView(imgAdd);
                imagePickerHelper.openImageIntent(ImagePickerHelper.NORMAL_TRIM_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        imgFilter.setImageBitmap(ListFilter.get(path, position,mProgressDialog));
        Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();

    }

    /*
    *   Mutil touch
    */

    // these matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;

    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;


    protected View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // handle touch events here
            ImageView view = (ImageView) v;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    mode = DRAG;
                    lastEvent = null;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                    }
                    lastEvent = new float[4];
                    lastEvent[0] = event.getX(0);
                    lastEvent[1] = event.getX(1);
                    lastEvent[2] = event.getY(0);
                    lastEvent[3] = event.getY(1);
                    d = rotation(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    lastEvent = null;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        matrix.set(savedMatrix);
                        float dx = event.getX() - start.x;
                        float dy = event.getY() - start.y;
                        matrix.postTranslate(dx, dy);
                    } else if (mode == ZOOM) {
                        float newDist = spacing(event);
                        if (newDist > 10f) {
                            matrix.set(savedMatrix);
                            float scale = (newDist / oldDist);
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                        if (lastEvent != null && event.getPointerCount() == 3) {
                            newRot = rotation(event);
                            float r = newRot - d;
                            float[] values = new float[9];
                            matrix.getValues(values);
                            float tx = values[2];
                            float ty = values[5];
                            float sx = values[0];
                            float xc = (view.getWidth() / 2) * sx;
                            float yc = (view.getHeight() / 2) * sx;
                            matrix.postRotate(r, tx + xc, ty + yc);
                        }
                    }
                    break;

            }

            view.setImageMatrix(matrix);
            return true;
        }
    };

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
