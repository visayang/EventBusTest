package com.eventbus.eventbustest.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import com.eventbus.eventbustest.R;
import com.eventbus.eventbustest.base.BaseActivity;
import com.eventbus.eventbustest.entity.eventbus.BackGroundMessage;
import com.eventbus.eventbustest.entity.eventbus.SpecialMessage;
import com.eventbus.eventbustest.entity.eventbus.ResponseMessage;
import com.eventbus.eventbustest.utils.RequestTemplate;
import com.eventbus.eventbustest.utils.T;

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
        T.LogInfo("onThreadEvent");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        T.show("wonderful");
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onServiceEvent(ResponseMessage sm) {
        switch (sm.tag){
            case RequestTemplate.WEATHER:
                if(sm.isSuccess){
                    textView.setText(sm.msg);
                }else{
                    textView.setText(sm.msg+"=========");
                }
                break;
        }
    }

    @Override
    protected void onNormEvent(SpecialMessage em) {
        switch (em.tag){
            case SpecialMessage.NO_NETWORK:
                textView.setText("暂无可用网络");
                break;
        }
    }
}
