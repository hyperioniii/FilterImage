package com.example.linhnh.myapplication.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linhnh.myapplication.BaseApplication;
import com.example.linhnh.myapplication.R;
import com.example.linhnh.myapplication.callback.OnHeaderIconClickListener;
import com.example.linhnh.myapplication.constant.HeaderIconOption;
import com.example.linhnh.myapplication.util.StringUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LinhNguyen on 11/9/2015.
 */
public class CustomHeaderToolBar extends android.support.v7.widget.Toolbar{

    @InjectView(R.id.toolbar_left_icon_back)
    ImageView imgLeftBack;
    @InjectView(R.id.toolbar_left_icon_close)
    ImageView imgLeftClose;
    @InjectView(R.id.toolbar_left_icon_setting)
    ImageView imgLeftSetting;

    @InjectView(R.id.toolbar_right_icon_close)
    ImageView imgRightClose;
    @InjectView(R.id.toolbar_right_icon_edit)
    ImageView imgRightEdit;
    @InjectView(R.id.toolbar_right_icon_delete)
    ImageView imgRightDelete;

    @InjectView(R.id.toolbar_title)
    TextView tvTitle;


    private OnHeaderIconClickListener onHeaderIconClickListener;

    public void setOnHeaderIconClickListener(OnHeaderIconClickListener onHeaderIconClickListener) {
        this.onHeaderIconClickListener = onHeaderIconClickListener;
    }

    public CustomHeaderToolBar(Context context) {
        super(context);
        init();
    }

    public CustomHeaderToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomHeaderToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.customheadertoolbar, this); // your layout with <merge> as the root tag
        ButterKnife.inject(this);
    }

    public void setTvTitle(String title) {
        if(StringUtil.checkStringValid(title))
        tvTitle.setText(title);
    }

    @OnClick(R.id.toolbar_left_icon_back)
    public void imgLeftBack(){

        if (onHeaderIconClickListener != null){
        onHeaderIconClickListener.onHeaderBack();}

    };

    @OnClick(R.id.toolbar_left_icon_close)
    public void imgLeftClose(){
        if (onHeaderIconClickListener != null)
        onHeaderIconClickListener.onHeaderClose();
    };
    @OnClick(R.id.toolbar_left_icon_setting)
    public void imgLeftSetting(){
        if (onHeaderIconClickListener != null)
        onHeaderIconClickListener.onHeaderSetting();
    };

    @OnClick(R.id.toolbar_right_icon_edit)
    public void imgRightEdit(){
        if (onHeaderIconClickListener != null)
        onHeaderIconClickListener.onHeaderEdit();
    };
    @OnClick(R.id.toolbar_right_icon_delete)
    public void imgRightDelete(){
        if (onHeaderIconClickListener != null)
        onHeaderIconClickListener.onHeaderDelete();
    };

    @OnClick(R.id.toolbar_right_icon_close)
    public void setImgRightClose(){
        if (onHeaderIconClickListener != null)
            onHeaderIconClickListener.onHeaderClose();
    }

    private void showHideLeftIcon(View target) {
        showOrHide(imgLeftBack, target);
        showOrHide(imgLeftClose, target);
        showOrHide(imgLeftSetting, target);
    }

    private void showHideRightIcon(View target) {
        showOrHide(imgRightClose, target);
        showOrHide(imgRightEdit, target);
        showOrHide(imgRightDelete, target);
    }

    private void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    public void handleIconOption(HeaderIconOption headerIconOption) {
        switch (headerIconOption) {

            case LEFT_NONE:
                showHideLeftIcon(null);
                break;
            case RIGHT_NONE:
                showHideRightIcon(null);
                break;
            case LEFT_CLOSE:
                showHideLeftIcon(imgLeftClose);
                break;
            case RIGHT_CLOSE:
                showHideRightIcon(imgRightClose);
                break;
            case LEFT_BACK:
                showHideLeftIcon(imgLeftBack);
                break;
            case LEFT_SETTING:
                showHideLeftIcon(imgLeftSetting);
                break;
            case RIGHT_EDIT:
                showHideRightIcon(imgRightEdit);
                break;
//            case RIGHT_SEARCH:
//                showHideRightIcon(tvRightSearch);
//                break;
//            case RIGHT_CONFIRM:
//                showHideRightIcon(tvRightConfirm);
//                break;
            case RIGHT_DELETE:
                showHideRightIcon(imgRightDelete);
                break;
        }
    }
}
