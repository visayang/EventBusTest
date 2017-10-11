package com.eventbus.eventbustest.entity.eventbus;

/**
 * Created by Chad
 * Version 1.0
 */

public class ResponseMessage {
    public String tag;
    public String msg;
    public boolean isSuccess;

    public ResponseMessage(String tag, String msg, boolean isSuccess) {
        this.tag = tag;
        this.msg = msg;
        this.isSuccess = isSuccess;
    }
}
