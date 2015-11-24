package com.example.linhnh.myapplication.util;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.View;

import com.example.linhnh.myapplication.filter.BlockFilter;
import com.example.linhnh.myapplication.filter.ChannelMixFilter;
import com.example.linhnh.myapplication.filter.ContrastFilter;
import com.example.linhnh.myapplication.filter.EdgeFilter;
import com.example.linhnh.myapplication.filter.GlowFilter;
import com.example.linhnh.myapplication.filter.GrayscaleFilter;
import com.example.linhnh.myapplication.filter.HSBAdjustFilter;
import com.example.linhnh.myapplication.filter.SharpenFilter;
import com.example.linhnh.myapplication.filter.util.AndroidUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinhNguyen on 11/19/2015.
 */
public class ListFilter {


    public static Bitmap get(String path, int pos, ProgressDialog mProgess) {
        Bitmap bitmap = null;
        mProgess.show();
        final BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        // Calculate inSampleSize
        opt.inSampleSize = 2;
        // Decode bitmap with inSampleSize set
        opt.inJustDecodeBounds = false;

        Bitmap bim = BitmapFactory.decodeFile(path, opt);
        DebugLog.d("check bitmap-----" + bim);

        try {
            switch (pos) {
                case 0:
                    bitmap = applyFilter_contrast(bim);
                    break;
                case 1:
                    bitmap = applyFilter_channelMixB(bim);
                    break;
                case 2:
                    bitmap = applyFilter_glow(bim);
                    break;
                case 3:
                    bitmap = applyFilter_sharpen(bim);
                    break;
                case 4:
                    bitmap = applyFilter_grayscale(bim);
                    break;
                case 5:
                    bitmap = applyFilter_channelMixR(bim);
                    break;
                case 6:
                    bitmap = applyFilter_channelMixG(bim);
                    break;
                case 7:
                    bitmap = applyFilter_edge(bim);
                    break;
                case 8:
                    bitmap = applyFilter_block(bim);
                    break;
                case 9:
                    bitmap = applyFilter_HSBAdjustFilter(bim);
                    break;
                case 10:
                    bitmap = applyFilter_contrast(bim);
                    break;
                case 11:
                    bitmap = applyFilter_contrast(bim);
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
    * set contrast  : tương phản
    */
    public static Bitmap applyFilter_contrast(Bitmap bitmap) throws FileNotFoundException, IOException {

        ContrastFilter filter = new ContrastFilter();
//        filter.setBrightness(1.4f);
        filter.setContrast(1.4f);

        int[] src = AndroidUtils.bitmapToIntArray(bitmap);
        int[] src2 = filter.filter(src, bitmap.getWidth(), bitmap.getHeight());
        Bitmap bitmap_temp = Bitmap.createBitmap(src2, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return bitmap_temp;
    }

    public static Bitmap applyFilter_channelMixB(Bitmap bitmap) throws FileNotFoundException, IOException {

        Bitmap bitmap_temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        ChannelMixFilter filter = new ChannelMixFilter();
        filter.setIntoR(0);
        filter.setIntoG(0);
        filter.setIntoB(127);
        filter.setBlueGreen(0);
        filter.setGreenRed(0);
        filter.setRedBlue(0);
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter.filterRGB(x, y, bitmap.getPixel(x, y)));
            }
        }
        return bitmap_temp;
    }

    public static Bitmap applyFilter_glow(Bitmap bitmap) throws FileNotFoundException, IOException {

        GlowFilter filter2 = new GlowFilter();
        filter2.setAmount(0.03f);

        int[] src = AndroidUtils.bitmapToIntArray(bitmap);
        filter2.filter(src, bitmap.getWidth(), bitmap.getHeight());
        Bitmap bitmap_temp = Bitmap.createBitmap(src, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        return bitmap_temp;
    }

    /*
   *  sắc nét
   */
    public static Bitmap applyFilter_sharpen(Bitmap bitmap) throws FileNotFoundException, IOException {

        SharpenFilter filter = new SharpenFilter();
        int[] src = AndroidUtils.bitmapToIntArray(bitmap);
        for (int i = 0; i < 3; i++) {
            src = filter.filter(src, bitmap.getWidth(), bitmap.getHeight());
        }
        Bitmap bitmap_temp = Bitmap.createBitmap(src, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return bitmap_temp;
    }

    public static Bitmap applyFilter_grayscale(Bitmap bitmap) throws FileNotFoundException, IOException {

        Bitmap bitmap_temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        GrayscaleFilter filter = new GrayscaleFilter();
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter.filterRGB(x, y, bitmap.getPixel(x, y)));
            }
        }
        return bitmap_temp;
    }

    public static Bitmap applyFilter_channelMixR(Bitmap bitmap) throws FileNotFoundException, IOException {

        Bitmap bitmap_temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        ChannelMixFilter filter = new ChannelMixFilter();
        filter.setIntoR(127);
        filter.setIntoG(0);
        filter.setIntoB(0);
        filter.setBlueGreen(0);
        filter.setGreenRed(0);
        filter.setRedBlue(0);
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter.filterRGB(x, y, bitmap.getPixel(x, y)));
            }
        }
        return bitmap_temp;
    }

    public static Bitmap applyFilter_channelMixG(Bitmap bitmap) throws FileNotFoundException, IOException {

        Bitmap bitmap_temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        ChannelMixFilter filter = new ChannelMixFilter();
        filter.setIntoR(0);
        filter.setIntoG(127);
        filter.setIntoB(0);
        filter.setBlueGreen(0);
        filter.setGreenRed(0);
        filter.setRedBlue(0);
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter.filterRGB(x, y, bitmap.getPixel(x, y)));
            }
        }
        return bitmap_temp;
    }

    public static Bitmap applyFilter_edge(Bitmap bitmap) throws FileNotFoundException, IOException {

        EdgeFilter filter = new EdgeFilter();
        int[] src = AndroidUtils.bitmapToIntArray(bitmap);
        src = filter.filter(src, bitmap.getWidth(), bitmap.getHeight());
        Bitmap bitmap_temp = Bitmap.createBitmap(src, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return bitmap_temp;
    }

    public static Bitmap applyFilter_block(Bitmap bitmap) throws FileNotFoundException, IOException {

        BlockFilter filter = new BlockFilter();
        int[] src = AndroidUtils.bitmapToIntArray(bitmap);
        src = filter.filter(src, bitmap.getWidth(), bitmap.getHeight());
        Bitmap bitmap_temp = Bitmap.createBitmap(src, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return bitmap_temp;
    }

    public static Bitmap applyFilter_HSBAdjustFilter(final Bitmap bitmap) throws FileNotFoundException, IOException {
        Bitmap bitmap_temp = null;

        bitmap_temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        GrayscaleFilter filter = new GrayscaleFilter();
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter.filterRGB(x, y, bitmap.getPixel(x, y)));
            }
        }
        HSBAdjustFilter filter2 = new HSBAdjustFilter();
        filter2.setHFactor((float) -65.0);
        filter2.setSFactor((float) -0.52);
        filter2.setBFactor((float) -0.07);
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter2.filterRGB(x, y, bitmap.getPixel(x, y)));
            }
        }
        return bitmap_temp;
    }

}
