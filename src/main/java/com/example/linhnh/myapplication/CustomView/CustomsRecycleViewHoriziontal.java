package com.example.linhnh.myapplication.CustomView;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.linhnh.myapplication.util.DebugLog;

/**
 * Created by LinhNguyen on 11/5/2015.
 */
public class CustomsRecycleViewHoriziontal extends RecyclerView {
    private boolean mIsScrolling;
    private boolean mIsTouching;

    private Runnable mScrollingRunnable;
    private OnScrollListener mOnScrollListener;

    float x,y,oldx,oldy;

    public interface OnScrollListener {
         void onScrollChanged(CustomsRecycleViewHoriziontal scrollView, int x, int y, int oldX, int oldY);
         void onEndScroll(CustomsRecycleViewHoriziontal scrollView);
    }

    public CustomsRecycleViewHoriziontal(Context context) {
        super(context);
    }

    public CustomsRecycleViewHoriziontal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomsRecycleViewHoriziontal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        final int action = ev.getAction();
//        switch (action & MotionEventCompat.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                x =   ev.getX();
//                y = ev.getY();
//                DebugLog.d("x===y===  " + x+"=="+y);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mIsTouching = true;
//                mIsScrolling = true;
//                oldx = ev.getX();
//                oldx = ev.getX();
//
//                if (mOnScrollListener != null) {
//            mOnScrollListener.onScrollChanged(this,(int) x,(int) y,(int) oldx,(int) oldy);
//        }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (mIsTouching && !mIsScrolling) {
//                    if (mOnScrollListener != null) {
//                        mOnScrollListener.onEndScroll(this);
//                    }
//                }
//
//                mIsTouching = false;
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                x =   ev.getX();
                y = ev.getY();
                DebugLog.d("x===y===  " + x + "==" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                mIsTouching = true;
                mIsScrolling = true;
                oldx = ev.getX();
                oldx = ev.getX();

                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollChanged(this, (int) x, (int) y, (int) oldx, (int) oldy);
                }
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (Math.abs(oldx - x) > 0) {
//            if (mScrollingRunnable != null) {
//                removeCallbacks(mScrollingRunnable);
//            }
//
//            mScrollingRunnable = new Runnable() {
//                public void run() {
//                    if (mIsScrolling && !mIsTouching) {
//                        if (mOnScrollListener != null) {
//                            mOnScrollListener.onEndScroll(CustomsRecycleViewHoriziontal.this);
//                        }
//                    }
//
//                    mIsScrolling = false;
//                    mScrollingRunnable = null;
//                }
//            };
//
//            postDelayed(mScrollingRunnable, 200);
//        }
//
//        if (mOnScrollListener != null) {
//            mOnScrollListener.onScrollChanged(this,(int) x,(int) y,(int) oldx,(int) oldy);
//        }
//    }

    public OnScrollListener getOnScrollListener() {
        return mOnScrollListener;
    }

    public void setOnScrollListener(OnScrollListener mOnEndScrollListener) {
        this.mOnScrollListener = mOnEndScrollListener;
    }
}
