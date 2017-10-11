package com.eventbus.eventbustest.utils;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.eventbus.eventbustest.MyApplication;

/**
 * Created by Chad.
 * Version 1.0
 */

public class T {
    private static String TAG = "123";

    public static void show(String s) {
        Toast.makeText(MyApplication.getApplication(), s, Toast.LENGTH_SHORT).show();
    }

    public static void showOnThread(String s) {
        Looper.prepare();
        Toast.makeText(MyApplication.getApplication(), s, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public static void LogInfo(String msg){
        Log.i(TAG, msg);
    }
}
