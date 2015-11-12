package com.example.linhnh.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.util.KeyboardUtil;

import butterknife.ButterKnife;

/**
 * Created by Envy 15T on 6/4/2015.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;

    protected ViewGroup fragmentViewParent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createRootView(inflater, container);

    }

    private View createRootView(LayoutInflater inflater, ViewGroup container) {
        if (isSkipGenerateBaseLayout()) {
            rootView = inflater.inflate(setLayoutId(), container, false);
            ButterKnife.inject(this, rootView);
        } else {
            rootView = inflater.inflate(R.layout.layout_base_fragment, container, false);
            fragmentViewParent = (ViewGroup) rootView.findViewById(R.id.fragmentViewParent);
            fragmentViewParent.addView(inflater.inflate(setLayoutId(), container, false));
            ButterKnife.inject(this, rootView);
        }
        return rootView;
    }

    protected boolean isSkipGenerateBaseLayout() {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            getArgument(getArguments());
        }
        initView(rootView);
        initData();
    }

    abstract protected int setLayoutId();

    abstract protected void initView(View root);

    abstract protected void getArgument(Bundle bundle);

    abstract protected void initData();

    private void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    protected boolean checkFragmentVisible() {
        if (isVisible() && getActivity() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void hideKeyBoardWhenTouchOutside(ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (checkFragmentVisible()) {
                        KeyboardUtil.hideSoftKeyboard(getActivity());
                    }
                    return false;
                }
            });
        }
    }
}

