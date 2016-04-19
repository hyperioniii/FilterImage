package com.example.linhnh.myapplication.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.drawView.MyDrawView;

import butterknife.Bind;

/**
 * Created by LinhNguyen on 4/19/2016.
 */
public class FragmentCanvas extends BaseFragment {

    @Bind(R.id.layout_canvass)
    public RelativeLayout parent;

    MyDrawView myDrawView;

    public static FragmentCanvas newIntance() {
        return new FragmentCanvas();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_canvas;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        myDrawView = new MyDrawView(getActivity());
        parent.addView(myDrawView);

    }
}
