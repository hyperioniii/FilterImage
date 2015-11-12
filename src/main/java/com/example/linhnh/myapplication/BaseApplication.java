package com.example.linhnh.myapplication;

import android.app.Application;

import com.example.linhnh.myapplication.util.SharedPrefUtils;


public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static SharedPrefUtils sharedPreferences;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = new SharedPrefUtils(getApplicationContext());
    }

    public static SharedPrefUtils getSharedPreferences() {
        return sharedPreferences;
    }

}
