package com.eventbus.eventbustest.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eventbus.eventbustest.entity.eventbus.SpecialMessage;
import com.eventbus.eventbustest.entity.eventbus.ResponseMessage;
import com.eventbus.eventbustest.utils.EventBusUtils;
import com.eventbus.eventbustest.utils.RequestTemplate;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;

/**
 * Created by Chad.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    protected RequestTemplate mRequest;
    protected abstract void initView(View view);
    //获取布局文件ID
    protected abstract int getLayoutId();


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mRootView);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBusUtils.register(this);
        }
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {//Modified 2016-06-01</span>
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
        mRequest = mActivity.mRequest;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(Object object) {
        if (object instanceof SpecialMessage) {
            onNormEvent((SpecialMessage) object);
        } else if (object instanceof ResponseMessage) {
            onServiceEvent((ResponseMessage) object);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 网络请求回调
     *
     * @param msg 返回数据
     */
    public abstract void onServiceEvent(ResponseMessage msg);

    /**
     * 正常eventbus消息
     *
     * @param msg 消息数据
     */
    public abstract void onNormEvent(SpecialMessage msg);

    public void showProgressBar() {
        mActivity.showProgressBar();
    }

    public void showProgressBar(String message) {
        mActivity.showProgressBar(message);
    }

    public void stopProgressBar() {
        mActivity.stopProgressBar();
    }
}
