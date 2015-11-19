package com.example.linhnh.myapplication.util;

import android.graphics.Bitmap;
import android.view.View;

import com.example.linhnh.myapplication.filter.ChannelMixFilter;
import com.example.linhnh.myapplication.filter.ContrastFilter;
import com.example.linhnh.myapplication.filter.base.ApplyFilter;
import com.example.linhnh.myapplication.filter.util.AndroidUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinhNguyen on 11/19/2015.
 */
public class ListFilter {

    public static Bitmap get(Bitmap bitmapfist, int pos){
        Bitmap bitmap = null;
        try {
        switch (pos){
            case 0:
                    bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 1:
                bitmap = applyFilter_channelMixB(bitmapfist);
                break;
            case 2:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 3:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 4:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 5:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 6:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 7:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 8:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 9:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 10:
                bitmap = applyFilter_contrast(bitmapfist);
                break;
            case 11:
                bitmap = applyFilter_contrast(bitmapfist);
                break;

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
    * mầu 1 éo biết tả thế nào
    */
    public static Bitmap applyFilter_contrast(Bitmap bitmap) throws FileNotFoundException, IOException{

        ContrastFilter filter = new ContrastFilter();
        filter.setBrightness(1.1f);
        filter.setContrast(1.1f);

        int[] src = AndroidUtils.bitmapToIntArray(bitmap);
        int[] src2 = filter.filter(src, bitmap.getWidth(), bitmap.getHeight());
        Bitmap bitmap_temp = Bitmap.createBitmap(src2, bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        return bitmap_temp;
    }
    public static Bitmap applyFilter_channelMixB(Bitmap bitmap) throws FileNotFoundException, IOException{

        Bitmap bitmap_temp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        ChannelMixFilter filter = new ChannelMixFilter();
        filter.setIntoR(0);filter.setIntoG(0);filter.setIntoB(127);
        filter.setBlueGreen(0);filter.setGreenRed(0);filter.setRedBlue(0);
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                bitmap_temp.setPixel(x, y, filter.filterRGB(x,y,bitmap.getPixel(x, y)));
            }
        }
        return bitmap_temp;
    }

}
