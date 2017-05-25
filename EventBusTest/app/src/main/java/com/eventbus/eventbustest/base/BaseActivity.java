package com.eventbus.eventbustest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eventbus.eventbustest.R;
import com.eventbus.eventbustest.components.progressDialog.ProgressHUD;
import com.eventbus.eventbustest.entity.eventbus.BackGroundMessage;
import com.eventbus.eventbustest.entity.eventbus.ServiceMessage;
import com.eventbus.eventbustest.entity.eventbus.EventMessage;
import com.eventbus.eventbustest.utils.EventBusUtils;
import com.eventbus.eventbustest.utils.RequestTemplate;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by Chad on 2017/5/24.
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
    public void OnEvent(EventMessage object) {
        onNormEvent((EventMessage) object);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ServiceMessage object) {
        onServiceEvent((ServiceMessage) object);
    }

    /**
     * 子线程运行
     * @param object
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void OnBackGround(BackGroundMessage object) {
        onThreadEvent((BackGroundMessage) object);
    }

    protected abstract void onThreadEvent(BackGroundMessage object);

    protected abstract void onServiceEvent(ServiceMessage object);

    protected abstract void onNormEvent(EventMessage object);

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
