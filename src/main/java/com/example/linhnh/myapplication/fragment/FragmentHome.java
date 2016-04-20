package com.example.linhnh.myapplication.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.util.FragmentUtil;

import butterknife.OnClick;

/**
 * Created by LinhNguyen on 4/19/2016.
 */
public class FragmentHome extends BaseFragment {

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    public static FragmentHome newIntance() {
        return new FragmentHome();
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.fragment_home_capture)
    public void clickCapture() {
        FragmentUtil.pushFragment(getActivity(), FragmentFilterCamera.newIntance(), null);
    }

    @OnClick(R.id.fragment_home_gallery)
    public void clickGallery() {
        FragmentUtil.pushFragment(getActivity(), FragmentFilterImg.newIntance(), null);
    }

    @OnClick(R.id.fragment_home_draw_canvas)
    public void clickCanvas() {
        FragmentUtil.pushFragment(getActivity(), FragmentCanvas.newIntance(), null);
    }

    @OnClick(R.id.fragment_home_cut_imge)
    public void clickCUT() {
        FragmentUtil.pushFragment(getActivity(), FragmentProcessImage.newIntance(), null);
    }
}
