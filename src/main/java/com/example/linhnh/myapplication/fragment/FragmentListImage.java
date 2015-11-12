package com.example.linhnh.myapplication.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.adapter.DividerItemDecoration;
import com.example.linhnh.myapplication.adapter.ListEventAdapter;
import com.example.linhnh.myapplication.constant.HeaderIconOption;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.model.CalenderEvent;
import com.example.linhnh.myapplication.CustomView.CustomsRecycleViewHoriziontal;
import com.example.linhnh.myapplication.util.DebugLog;
import com.example.linhnh.myapplication.util.FragmentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by LinhNguyen on 11/6/2015.
 */
public class FragmentListImage extends BaseFragment {

    @InjectView(R.id.list_event)
    CustomsRecycleViewHoriziontal mRecyclerView;

    List<CalenderEvent> data = new ArrayList<>();

    @InjectView(R.id.swipe_refresh_list_img)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    ListEventAdapter adapter;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_list_image;
    }

    public static FragmentListImage intantce() {
        FragmentListImage fm = new FragmentListImage();
        return fm;
    }

    @Override
    protected void initView(View root) {
        MainScreenSettingEvent mainScreenSettingEvent = new MainScreenSettingEvent("My title ", HeaderIconOption.RIGHT_NONE, HeaderIconOption.LEFT_SETTING);
        EventBus.getDefault().post(mainScreenSettingEvent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        mSwipeRefreshLayout.setRefreshing(false);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(r, 2000);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                FragmentUtil.pushFragment(getActivity(), FragmentEditImage.intantce(), null);
            }
        });

        fab.setRippleColor(getResources().getColor(R.color.colorPrimary));
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        setUi();
    }

    public void setUi() {
        getdata();
        adapter = new ListEventAdapter(data) {
            @Override
            public void dataSender(int truyencaikhigithitruyen) {

            }
        };
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setOnScrollListener(new CustomsRecycleViewHoriziontal.OnScrollListener() {
            @Override
            public void onScrollChanged(CustomsRecycleViewHoriziontal scrollView, int x, int y, int oldX, int oldY) {
                DebugLog.d("============4============" + x + "++" + y);
                DebugLog.d("============5============" + oldX + "++" + oldY);

                // thằng này bắt scroll list view
                adapter.swipeView(x, oldX);
            }

            @Override
            public void onEndScroll(CustomsRecycleViewHoriziontal scrollView) {

            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int heightSc = displaymetrics.heightPixels;
        int widthSc = displaymetrics.widthPixels;
    }

    public void getdata() {
        for (int i = 0; i < 10; i++) {
            data.add(new CalenderEvent("", "http://res.vtc.vn/media/vtcnews/2014/epi/viettu/2014_03_11/Tu%20Anh/duong-tu-anh-13_MHSK.jpg?width=200abcdefffffffffff"));
        }
    }
}
