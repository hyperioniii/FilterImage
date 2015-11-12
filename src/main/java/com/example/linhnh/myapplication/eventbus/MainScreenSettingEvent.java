package com.example.linhnh.myapplication.eventbus;

import com.example.linhnh.myapplication.constant.HeaderIconOption;

public class MainScreenSettingEvent {
    public String title;

    public HeaderIconOption[] headerIconOptionList;

    boolean headerVisibility = true;

    boolean bottomTabVisibility = true;

    public MainScreenSettingEvent(String title, HeaderIconOption... headerIconOptionList) {
        this.title = title;
        this.headerIconOptionList = headerIconOptionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HeaderIconOption[] getHeaderIconOptionList() {
        return headerIconOptionList;
    }

    public void setHeaderIconOptionList(HeaderIconOption[] headerIconOptionList) {
        this.headerIconOptionList = headerIconOptionList;
    }

    public boolean isHeaderVisibility() {
        return headerVisibility;
    }

    public void setHeaderVisibility(boolean headerVisibility) {
        this.headerVisibility = headerVisibility;
    }

    public boolean isBottomTabVisibility() {
        return bottomTabVisibility;
    }

    public void setBottomTabVisibility(boolean bottomTabVisibility) {
        this.bottomTabVisibility = bottomTabVisibility;
    }

}
