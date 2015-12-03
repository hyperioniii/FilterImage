package com.example.linhnh.myapplication.fragment;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.activity.MainActivity;
import com.example.linhnh.myapplication.callback.OnHeaderIconClickListener;
import com.example.linhnh.myapplication.constant.HeaderIconOption;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.util.DebugLog;
import com.example.linhnh.myapplication.util.FragmentUtil;
import com.example.linhnh.myapplication.util.ListFilter;
import com.example.linhnh.view.ProgessSlideIndicator;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.adapters.BitmapColorAdapter;
import com.larswerkman.lobsterpicker.sliders.LobsterOpacitySlider;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by LinhNguyen on 11/6/2015.
 * https://android-arsenal.com/details/1/2683
 * https://github.com/okoteogl/Photogeneia
 * https://github.com/AmineChikhaoui/PhotoEdit
 * https://github.com/heyjii/PhotoFrames
 * https://github.com/retryu/PhotoEditorView
 * https://github.com/huydx/vlcamera
 * https://github.com/AndrewShidel/Green-Screen-Photography
 * https://github.com/finebyte/android-jhlabs
 * https://android-arsenal.com/details/1/216
 */
public class FragmentEditImage extends BaseFragment implements OnHeaderIconClickListener {

    @InjectView(R.id.opacityslider)
    LobsterOpacitySlider opacitySlider;

    @InjectView(R.id.shadeslider)
    LobsterShadeSlider shadeSlider;

    @InjectView(R.id.img_edit)
    ImageView editImg;

    @InjectView(R.id.fab_save)
    FloatingActionButton fabSave;

    @InjectView(R.id.progressIndicator)
    ProgessSlideIndicator progressIndicator;

    @InjectView(R.id.filter_R)
    ImageView imgFilterR;
    @InjectView(R.id.filter_B)
    ImageView imgFilterB;
    @InjectView(R.id.filter_G)
    ImageView imgFilterG;


    public static FragmentEditImage intantce() {
        FragmentEditImage fm = new FragmentEditImage();
        return fm;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_edit_image;
    }

    @Override
    protected void initView(View root) {
        ((MainActivity) getActivity()).toolBar.setOnHeaderIconClickListener(this);
        MainScreenSettingEvent mainScreenSettingEvent = new MainScreenSettingEvent("My title ", HeaderIconOption.RIGHT_EDIT, HeaderIconOption.LEFT_BACK);
        EventBus.getDefault().post(mainScreenSettingEvent);
        shadeSlider.addDecorator(opacitySlider);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DebugLog.d("====Save edit=====");
                saveImage();

            }
        });

//        fabSave.setRippleColor(getResources().getColor(R.color.colorPrimary));
//        fabSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        progressIndicator.setValue(20);
        shadeSlider.setColorAdapter(new BitmapColorAdapter(getActivity(), R.drawable.default_shader_pallete));
        setColor();


    }

    Canvas canvas;
    Paint paint = new Paint();
    Drawable myIcon;
    Bitmap src, bm1, r, b, g, bm2;

    public void setColor() {

        Glide.with(getActivity()).load(R.drawable.images).into(editImg);


        opacitySlider.setOpacity(0);
        myIcon = getResources().getDrawable(R.drawable.images);
        src = ((BitmapDrawable) myIcon).getBitmap();
        bm1 = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bm1);

        setRBG();
        shadeSlider.addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(int color) {
                DebugLog.d("====color=====" + color);
                fabSave.setBackgroundTintList(ColorStateList.valueOf(color));
                if (color != 0) {
                    //  trenparent color
//                    paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.OVERLAY));
//                    paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.LIGHTEN));
                    // nhe nhang hon overllay
                    paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SCREEN));
                    // alpha img
//                    paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.XOR));
                    canvas.drawBitmap(src, 0, 0, paint);
                    editImg.setImageBitmap(bm1);
                }
            }

            @Override
            public void onColorSelected(int color) {
            }
        });
        progressIndicator.setOnValueChangedListener(new ProgessSlideIndicator.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                qualityImg = value;
            }
        });
    }

    public void setRBG() {
       bm2 = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sc000_avatar_a);
        try {
            r = ListFilter.applyFilter_channelMixR(bm2);
            b = ListFilter.applyFilter_channelMixB(bm2);
            g = ListFilter.applyFilter_channelMixG(bm2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgFilterR.setImageBitmap(bm2);
        imgFilterB.setImageBitmap(bm2);
        imgFilterG.setImageBitmap(bm2);
    }

    @OnClick(R.id.filter_R)
    public void imgFilterR() {
        imgFilterR.setImageBitmap(r);
    }
    @OnClick(R.id.filter_B)
    public void imgFilterB() {
        imgFilterB.setImageBitmap(b);
    }
    @OnClick(R.id.filter_G)
    public void imgFilterG() {
        imgFilterG.setImageBitmap(g);
    }

    @Override
    public void onHeaderBack() {
        getActivity().getSupportFragmentManager().popBackStack();
//        DebugLog.i("imgLeftBack:FM");
    }

    @Override
    public void onHeaderClose() {

    }

    @Override
    public void onHeaderSetting() {

    }

    @Override
    public void onHeaderEdit() {
       FragmentUtil.pushFragment(getActivity(),FragmentFilterImg.newIntance(),null);
    }

    @Override
    public void onHeaderDelete() {

    }

    public int qualityImg = 0;

    public void saveImage() {
        new AsyncTask<Void, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                int imageNum = 0;
                File imagesFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM", getString(R.string.app_name));
                imagesFolder.mkdirs();
                String fileName = "image_" + String.valueOf(imageNum) + ".jpg";
                File output = new File(imagesFolder, fileName);
                while (output.exists()) {
                    imageNum++;
                    fileName = "image_" + String.valueOf(imageNum) + ".jpg";
                    output = new File(imagesFolder, fileName);
                }
                ;
                try {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bm1.compress(Bitmap.CompressFormat.JPEG, qualityImg, bytes);
                    FileOutputStream fo = new FileOutputStream(output);
                    fo.write(bytes.toByteArray());
                    fo.flush();
                    fo.close();
                    MediaScannerConnection.scanFile(getActivity(), new String[]{output.getAbsolutePath()}, null, null);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                DebugLog.d("++++++++++++++++++++++++++++++++++++++++");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }.execute();
    }
}
