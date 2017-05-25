package com.eventbus.eventbustest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.eventbus.eventbustest.SucApplication;
import com.eventbus.eventbustest.configs.ServiceConfig;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class Utils {

    public static int checkNetwork() {
        ConnectivityManager mConnectivity = (ConnectivityManager) SucApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) SucApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        //检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return ServiceConfig.NETWORK_NO_AVAILABLE;
        }
        //判断网络连接类型，只有在3G或wifi里进行一些数据更新。
        int netType = info.getType();
        if (netType == ConnectivityManager.TYPE_WIFI) {
            return ServiceConfig.NETWORK_WIFI;
        } else if (netType == ConnectivityManager.TYPE_MOBILE
                && !mTelephony.isNetworkRoaming()) {
            return ServiceConfig.NETWORK_NO_WIFI;
        } else {
            return ServiceConfig.NETWORK_NO_AVAILABLE;
        }
    }
}
