package com.eventbus.eventbustest.entity.eventbus;

import java.util.Objects;

/**
 * Created by Chad .
 * Version 1.0
 */

public class SpecialMessage {
    /**
     * 无网络连接
     */
    public static final int NO_NETWORK = 1000;

    public int tag;
    public Object obj;

    public SpecialMessage(int tag, Object obj) {
        this.tag = tag;
        this.obj = obj;
    }

    public SpecialMessage(int tag) {
        this.tag = tag;
    }
}
