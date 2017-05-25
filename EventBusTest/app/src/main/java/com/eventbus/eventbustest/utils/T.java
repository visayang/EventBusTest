package com.eventbus.eventbustest.utils;

import android.widget.Toast;

import com.eventbus.eventbustest.SucApplication;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class T {
    public static void show(String s) {
        Toast.makeText(SucApplication.getApplication(), s, Toast.LENGTH_SHORT).show();
    }
}
