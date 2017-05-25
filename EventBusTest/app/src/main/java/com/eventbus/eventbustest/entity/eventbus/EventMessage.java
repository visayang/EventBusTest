package com.eventbus.eventbustest.entity.eventbus;

import java.util.Objects;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class EventMessage {
    /**
     * 无网络连接
     */
    public static final int NO_NETWORK = 1000;

    public int tag;
    public Object obj;

    public EventMessage(int tag, Object obj) {
        this.tag = tag;
        this.obj = obj;
    }

    public EventMessage(int tag) {
        this.tag = tag;
    }
}
