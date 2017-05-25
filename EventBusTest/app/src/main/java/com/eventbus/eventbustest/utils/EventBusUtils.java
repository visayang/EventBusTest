package com.eventbus.eventbustest.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class EventBusUtils {
    private static EventBus EventBusUtil;

    static {
        EventBusUtil = EventBus.getDefault();
    }

    public static void register(Object subscriber) {
        EventBusUtil.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBusUtil.unregister(subscriber);
    }

    public static void post(Object message) {
        EventBusUtil.post(message);
    }
    public static void postSticky(Object message) {
        EventBusUtil.postSticky(message);
    }

}
