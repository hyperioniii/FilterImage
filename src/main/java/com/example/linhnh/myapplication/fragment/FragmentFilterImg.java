package com.example.linhnh.myapplication.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.linhnh.myapplication.CustomView.ImageAutoScale;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.activity.MainActivity;
import com.example.linhnh.myapplication.adapter.DividerItemDecoration;
import com.example.linhnh.myapplication.adapter.FilterAdapter;
import com.example.linhnh.myapplication.callback.OnHeaderIconClickListener;
import com.example.linhnh.myapplication.constant.HeaderIconOption;
import com.example.linhnh.myapplication.eventbus.MainScreenSettingEvent;
import com.example.linhnh.myapplication.util.FileUtils;
import com.example.linhnh.myapplication.util.FragmentUtil;
import com.example.linhnh.myapplication.util.ImagePickerHelper;
import com.example.linhnh.myapplication.util.UiUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by LinhNguyen on 11/12/2015.
 */
public class FragmentFilterImg extends BaseFragment implements OnHeaderIconClickListener{

    @InjectView(R.id.review_filter_img)
    ImageView imgFilter;

    @InjectView(R.id.line_demo_filter)
    RecyclerView mRecyclerView;

    @InjectView(R.id.btn_setting)
    ImageView btnSetting;

    @InjectView(R.id.img_main_add)
    ImageAutoScale imgAdd;

    FilterAdapter adapter;
    private ImagePickerHelper imagePickerHelper;

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
        UiUtil.hideView(btnSetting,true);

        imagePickerHelper = new ImagePickerHelper(this, new ImagePickerHelper.OnPickerSuccess() {
            @Override
            public void onFinish(Uri uri) {
                String path = FileUtils.getPath(getActivity(), uri);
                if (path == null) {
                    return;
                }
                File imageFile = new File(path);
                Glide.with(getActivity()).load("file://" +imageFile).into(imgFilter);
                UiUtil.hideView(imgAdd, true);
                UiUtil.hideView(imgAdd, false);
            }
        });


        adapter = new FilterAdapter() ;
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.img_main_add)
    public void choseImage(){
        imagePickerHelper.openImageIntent(ImagePickerHelper.NORMAL_TRIM_REQUEST_CODE);
    }
    @OnClick(R.id.btn_setting)
    public void btnSetting(){
        UiUtil.showView(mRecyclerView);
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePickerHelper.onActivityResult(requestCode, resultCode, data);
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
