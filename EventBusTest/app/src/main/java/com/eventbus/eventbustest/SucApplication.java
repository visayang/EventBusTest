package com.eventbus.eventbustest;

import android.app.Application;

/**
 * Created by Administrator on 2017/5/23.
 */

public class SucApplication extends Application {

    private static SucApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static SucApplication getApplication() {
        return mApplication;
    }
}
