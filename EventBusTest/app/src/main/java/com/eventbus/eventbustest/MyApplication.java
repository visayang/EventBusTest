package com.eventbus.eventbustest;

import android.app.Application;

/**
 * Created by Administrator
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static MyApplication getApplication() {
        return mApplication;
    }
}
