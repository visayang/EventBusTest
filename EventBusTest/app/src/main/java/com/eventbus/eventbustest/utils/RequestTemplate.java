package com.eventbus.eventbustest.utils;

import com.eventbus.eventbustest.SucApplication;
import com.eventbus.eventbustest.base.BaseActivity;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class RequestTemplate {
    private HttpUtil httpUtil;
    private SucApplication mApplication;

    public RequestTemplate(BaseActivity context) {
        httpUtil = new HttpUtil(context);
        mApplication = SucApplication.getApplication();
    }

    /**
     * 查询天气
     */
    public static final String WEATHER = "weather";
    public void getWeather(){
        FormEncodingBuilder build = new FormEncodingBuilder();
        build.add("weaid","1");
        build.add("appkey","16359");
        build.add("sign","87369ebed4a43d7a96cb5dacec05bb5f");
        build.add("format","json");
        httpUtil.postFace("",WEATHER,build);
    }

}
