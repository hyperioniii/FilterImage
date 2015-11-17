package com.example.linhnh.myapplication.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.linhnh.myapplication.R;


public class ImageAutoScale extends ImageView {

    private float mHeightPerWidth;

    public ImageAutoScale(Context context) {
        super(context);
    }

    public float getHeightPerWidth() {
        return mHeightPerWidth;
    }

    public void setHeightPerWidth(float heightPerWidth) {
        this.mHeightPerWidth = heightPerWidth;
    }

    public ImageAutoScale(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs);
    }

    /**
     * Parses the attributes.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    private void parseAttributes(final Context context, final AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageAutoScale);
        try {
            mHeightPerWidth = a.getFloat(R.styleable.ImageAutoScale_heightPerWidth, 1f);
        } finally {
            a.recycle();
        }
    }

    public ImageAutoScale(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * mHeightPerWidth);
        setMeasuredDimension(width, height);
    }
}
