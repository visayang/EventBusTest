package com.eventbus.eventbustest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eventbus.eventbustest.components.progressDialog.ProgressHUD;
import com.eventbus.eventbustest.entity.eventbus.BackGroundMessage;
import com.eventbus.eventbustest.entity.eventbus.ResponseMessage;
import com.eventbus.eventbustest.entity.eventbus.SpecialMessage;
import com.eventbus.eventbustest.utils.EventBusUtils;
import com.eventbus.eventbustest.utils.RequestTemplate;
import com.eventbus.eventbustest.utils.T;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by Chad .
 * Version 1.0
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected RequestTemplate mRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        EventBusUtils.register(this);
        mRequest = new RequestTemplate(this);
        initProgressBar();
        initData();
    }

    protected abstract void initData();

    protected abstract int getContentViewId();

    /**
     *  主线程运行
     * @param object
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(SpecialMessage object) {
        T.LogInfo("OnEvent-SpecialMessage");

        onNormEvent((SpecialMessage) object);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ResponseMessage object) {
        T.LogInfo("OnEvent-ResponseMessage");
        onServiceEvent((ResponseMessage) object);
    }

    /**
     * 子线程运行
     * @param object
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void OnBackGround(BackGroundMessage object) {
        T.LogInfo("OnBackGround");
        onThreadEvent((BackGroundMessage) object);
    }

    protected abstract void onThreadEvent(BackGroundMessage object);

    protected abstract void onServiceEvent(ResponseMessage object);

    protected abstract void onNormEvent(SpecialMessage object);

    protected void beforeSetContentView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    /*==============进度条==============*/
    private ProgressHUD mProgressHUD;

    private void initProgressBar() {
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.newInstance(this, "正在加载中...", false, null);
        }
        mProgressHUD.setMessage("正在加载中...");
    }

    public void showProgressBar() {
        if (mProgressHUD != null && mProgressHUD.isShowing())
            return;
//        initProgressBar();
        mProgressHUD.show();
    }


    public void showProgressBar(String message) {
        if (mProgressHUD != null && mProgressHUD.isShowing())
            return;
//        initProgressBar();
        mProgressHUD.setMessage(message);
        mProgressHUD.show();
    }

    public void stopProgressBar() {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
        }
    }
}
