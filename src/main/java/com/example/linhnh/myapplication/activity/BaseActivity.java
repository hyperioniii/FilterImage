package com.example.linhnh.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.linhnh.myapplication.util.DebugLog;
import com.example.linhnh.myapplication.util.EventBusHelper;
import com.example.linhnh.myapplication.util.KeyboardUtil;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


    boolean isUnregistEventBus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onPreSetContentView(savedInstanceState);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            handleDeepLinkData(intent.getData());
        }
        setContentView(setContentViewId());
        ButterKnife.inject(this);
        EventBusHelper.register(this);
        isUnregistEventBus = false;
        initView();
        initData();
    }



    @Override
    protected void onDestroy() {
        if (!isUnregistEventBus) {
            EventBusHelper.unregister(this);
        }
        super.onDestroy();
    }

    /**
     * Handle data before setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPreSetContentView(Bundle savedInstanceState) {

    }

    /**
     * @return layout of activity
     */
    public abstract int setContentViewId();

    /**
     * Define your view
     */
    public abstract void initView();

    /**
     * Setup your data
     */
    public abstract void initData();

    protected void handleDeepLinkData(Uri uri) {
        DebugLog.i("uri: " + uri.toString());
    }

    @Override
    public void startActivity(Intent intent) {
        EventBusHelper.unregister(this);
        isUnregistEventBus = true;
        super.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUnregistEventBus) {
            EventBusHelper.register(this);
            isUnregistEventBus = false;
        }
    }

    public void hideKeyBoardWhenTouchOutside(ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    KeyboardUtil.hideSoftKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }
    }
}
