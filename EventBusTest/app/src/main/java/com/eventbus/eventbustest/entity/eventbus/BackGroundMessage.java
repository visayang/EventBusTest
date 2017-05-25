package com.eventbus.eventbustest.entity.eventbus;

/**
 * Created by Chad on 2017/5/24.
 * Version 1.0
 */

public class BackGroundMessage {
    public String tag;
    public String msg;
    public boolean isSuccess;
public BackGroundMessage(){};

    public BackGroundMessage(String tag, String msg, boolean isSuccess) {
        this.tag = tag;
        this.msg = msg;
        this.isSuccess = isSuccess;
    }
}
