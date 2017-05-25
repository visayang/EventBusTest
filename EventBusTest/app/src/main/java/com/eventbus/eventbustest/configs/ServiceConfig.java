package com.eventbus.eventbustest.configs;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class ServiceConfig {
    /*URL*/

    public final static String WEATHER_URL = "http://api.k780.com:88/?app=weather.future";

    /*标记*/

    /**
     * 网络请求超时时间
     */
    public final static int TIMEOUT_MILLISECONDS = 30000;
    /**
     * 网络状态
     */
    public static final int NETWORK_WIFI = 11000;//WIFI
    public static final int NETWORK_NO_WIFI = 11001;//移动网络
    public static final int NETWORK_NO_AVAILABLE = 11002;// 没有网络可用

    /*
     * 网络请求返回状态码
     */
    /**
     * 成功（业务）
     */
    public static final int SUCCESS = 1;
    /**
     * 失败（业务）
     */
    public static final int FAILED = 2;

    /**
     * 异常（程序）
     */
    public static final int ERROR = -1;

}
