package com.example.linhnh.myapplication.activity;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.linhnh.myapplication.CustomView.CustomHeaderToolBar;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.callback.OnHeaderIconClickListener;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.fragment.FragmentEditImage;
import com.example.linhnh.myapplication.fragment.FragmentListImage;
import com.example.linhnh.myapplication.util.DebugLog;
import com.example.linhnh.myapplication.util.FragmentUtil;
import com.example.linhnh.myapplication.util.UiUtil;

import butterknife.InjectView;

public class MainActivity extends BaseActivity implements OnHeaderIconClickListener ,FragmentManager.OnBackStackChangedListener{

    @InjectView(R.id.toolbar)
    CustomHeaderToolBar toolBar;

    boolean isNextBackPopAllStack = false;

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        toolBar.setOnHeaderIconClickListener(this);
    }

    @Override
    public void initData() {
            FragmentUtil.replaceFragment(MainActivity.this, FragmentListImage.intantce(), null);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(MainScreenSettingEvent event) {
        if (event.title != null) {
            DebugLog.d("=.="+event.title);
            toolBar.setTvTitle(event.title);
        }
        if (event.isHeaderVisibility()) {
            UiUtil.showView(toolBar);
            if (event.headerIconOptionList != null && event.headerIconOptionList.length > 0) {
                int size = event.headerIconOptionList.length;
                for (int i = 0; i < size; i++) {
                    toolBar.handleIconOption(event.headerIconOptionList[i]);
                }
            }
        } else {
            UiUtil.hideView(toolBar, true);
        }
        if (event.isBottomTabVisibility()) {
//            UiUtil.showView(commonTab);
        } else {
//            UiUtil.hideView(commonTab, true);
        }
    }

    private void popEntireFragmentBackStack() {
        final FragmentManager fm = getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fm.popBackStack();
        }
//        showHideLeftIcon(null);
    }

    @Override
    public void onBackPressed() {
            onHeaderBack();
    }

    @Override
    public void onHeaderBack() {
        final FragmentManager fm = getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount();
        DebugLog.i("backStackCount: " + backStackCount);
        if (backStackCount > 0) {
            DebugLog.e("toolbarBackClick" + isNextBackPopAllStack);
            if (isNextBackPopAllStack) {
                popEntireFragmentBackStack();
                isNextBackPopAllStack = false;
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            finish();
        }
        DebugLog.i("imgLeftBack:");
    }

    @Override
    public void onHeaderClose() {
        DebugLog.i("onHeaderClose:");
    }

    @Override
    public void onHeaderSetting() {
        DebugLog.i("onHeaderSetting:");
    }

    @Override
    public void onHeaderEdit() {
        DebugLog.i("onHeaderEdit:");
    }

    @Override
    public void onHeaderDelete() {
        DebugLog.i("onHeaderDelete:");
    }

    @Override
    public void onBackStackChanged() {

    }
}
