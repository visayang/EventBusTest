package com.eventbus.eventbustest.utils;

import com.eventbus.eventbustest.MyApplication;
import com.eventbus.eventbustest.base.BaseActivity;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by Chad .
 * Version 1.0
 */

public class RequestTemplate {
    private HttpUtil httpUtil;
    private MyApplication mApplication;

    public RequestTemplate(BaseActivity context) {
        httpUtil = new HttpUtil(context);
        mApplication = MyApplication.getApplication();
    }

    /**
     * 查询天气
     */
    public static final String WEATHER = "weather";

    public void getWeather(){
        FormEncodingBuilder build = new FormEncodingBuilder();
        build.add("weaid","1");
        build.add("appkey","10003");
        build.add("sign","b59bc3ef6191eb9f747dd4e83c99f2a4");
        build.add("format","json");
        httpUtil.postFace("",WEATHER,build);
    }
}
