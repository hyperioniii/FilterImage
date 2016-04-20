package com.example.linhnh.myapplication.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.linhnh.myapplication.R;

import butterknife.Bind;

/**
 * Created by LinhNguyen on 4/20/2016.
 */
public class FragmentProcessImage extends BaseFragment {

    @Bind(R.id.img_view)
    ImageView imgView;

    @Bind(R.id.movebitmap)
    RelativeLayout rl;

    public DrawingView drawingView;

    public static FragmentProcessImage newIntance() {
        return new FragmentProcessImage();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_process_image;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        imgView.setImageBitmap(cropBitmap1());
        drawingView = new DrawingView(getActivity());
        rl.addView(drawingView);
    }

    private Bitmap cropBitmap1() {
        Bitmap bmp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar_default_small);
        Bitmap bmOverlay = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);

        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Canvas c = new Canvas(bmOverlay);
        c.drawBitmap(bmp2, 0, 0, null);
        c.drawRect(30, 30, 200, 200, p);

        return bmOverlay;
    }

    public Bitmap cropBitmap2() {
        Bitmap bmp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.sc000_avatar_b);
        Bitmap bitmap = Bitmap.createBitmap(bmp2, 50, 50, bmp2.getWidth() - 250, bmp2.getHeight() - 250);
        return bitmap;
    }

    class DrawingView extends View {
        Bitmap bitmap;

        float x, y;

        public DrawingView(Context context) {
            super(context);
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sc000_avatar_b);


        }


        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {

                }
                break;

                case MotionEvent.ACTION_MOVE: {
                    x = (int) event.getX();
                    y = (int) event.getY();

                    invalidate();
                }

                break;
                case MotionEvent.ACTION_UP:

                    x = (int) event.getX();
                    y = (int) event.getY();
                    System.out.println(".................." + x + "......" + y); //x= 345 y=530
                    invalidate();
                    break;
            }
            return true;
        }

        @Override
        public void onDraw(Canvas canvas) {
           /* Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.CYAN);
            canvas.drawBitmap(bitmap, x, y, paint);*/
            Bitmap bmOverlay = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.CYAN);
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            Canvas c = new Canvas(bmOverlay);
            canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, paint);  //originally bitmap draw at x=o and y=0
            c.drawRect(30, 30, 100, 100, paint);

        }
    }
}
