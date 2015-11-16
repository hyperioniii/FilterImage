package com.example.linhnh.myapplication.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.activity.MainActivity;
import com.example.linhnh.myapplication.adapter.DividerItemDecoration;
import com.example.linhnh.myapplication.adapter.FilterAdapter;
import com.example.linhnh.myapplication.callback.OnHeaderIconClickListener;
import com.example.linhnh.myapplication.constant.HeaderIconOption;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by LinhNguyen on 11/12/2015.
 */
public class FragmentFilterImg extends BaseFragment implements OnHeaderIconClickListener{

    @InjectView(R.id.review_filter_img)
    ImageView imgFilter;

    @InjectView(R.id.line_demo_filter)
    RecyclerView mRecyclerView;

    FilterAdapter adapter;

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),null));
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        Glide.with(getActivity()).load(R.drawable.images).into(imgFilter);
        adapter = new FilterAdapter() ;
        mRecyclerView.setAdapter(adapter);
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
}
