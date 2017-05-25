package com.eventbus.eventbustest.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.eventbus.eventbustest.R;
import com.eventbus.eventbustest.base.BaseActivity;
import com.eventbus.eventbustest.entity.eventbus.BackGroundMessage;
import com.eventbus.eventbustest.entity.eventbus.EventMessage;
import com.eventbus.eventbustest.entity.eventbus.ServiceMessage;
import com.eventbus.eventbustest.entity.weather.WeatherDao;
import com.eventbus.eventbustest.utils.FastJsonUtils;
import com.eventbus.eventbustest.utils.RequestTemplate;
import com.eventbus.eventbustest.utils.T;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
        mRequest.getWeather();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onThreadEvent(BackGroundMessage object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        T.show("EventBus好美");
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onServiceEvent(ServiceMessage sm) {
        if(sm.isSuccess){
            switch (sm.tag){
                case RequestTemplate.WEATHER:
                    textView.setText(sm.msg);
                    break;
            }
        }
    }

    @Override
    protected void onNormEvent(EventMessage em) {
        switch (em.tag){
            case EventMessage.NO_NETWORK:
                textView.setText("我是主线程返回的");
                break;
        }
    }
}
