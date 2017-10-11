package com.eventbus.eventbustest.utils;

import com.alibaba.fastjson.JSON;
import com.eventbus.eventbustest.base.BaseActivity;
import com.eventbus.eventbustest.configs.ServiceConfig;
import com.eventbus.eventbustest.entity.eventbus.ResponseMessage;
import com.eventbus.eventbustest.entity.eventbus.SpecialMessage;
import com.eventbus.eventbustest.entity.result.WeatherResult;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by Chad .
 * Version 1.0
 */

public class HttpUtil {
    private BaseActivity context;
    private OkHttpClient client;

    public HttpUtil(BaseActivity context) {
        this.context = context;
        client = new OkHttpClient();
    }

    private final int POST = 0;
    private final int GET = 1;
    private final int PUT = 2;

    public void postFace(String action, String method, FormEncodingBuilder params ) {
        callServiceFace(POST, action, method, params, true);
    }

    private void callServiceFace(int post, String action, final String method, FormEncodingBuilder params, final boolean showProgress) {
        if (Utils.checkNetwork() == ServiceConfig.NETWORK_NO_AVAILABLE) {
            //无网络可用
            EventBusUtils.post(new SpecialMessage(SpecialMessage.NO_NETWORK));
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    EventBusUtils.post(new BackGroundMessage());
//                }
//            }).start();
            return;
        }
        if (showProgress){
            //加载对话框
            context.showProgressBar();
        }
        String URL = ServiceConfig.WEATHER_URL;
        Request request = new Request.Builder().url(URL).post(params.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                postServiceErrorEvent(method, "请求失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(showProgress){
                    //取消显示对话框
                    context.stopProgressBar();
                }
                try {
                    WeatherResult result = JSON.parseObject(response.body().string(),WeatherResult.class);
                    if(result==null||result.success==0){
                        postServiceErrorEvent(method, "服务器请求数据异常");
                    }else{
                        //网络请求正常下的返回
                        postServiceEvent(method, result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    postServiceErrorEvent(method, "服务器请求数据异常");
                }
            }
        });
    }

    private void postServiceEvent(String method, WeatherResult result) {
        if (result.success == ServiceConfig.SUCCESS) {
            EventBusUtils.post(new ResponseMessage(method, result.result, true));
        } else {
            EventBusUtils.post(new ResponseMessage(method, result.msg, false));
        }
    }

    private void postServiceErrorEvent(String method, String msg) {
        EventBusUtils.post(new ResponseMessage(method, msg, false));
    }
}
