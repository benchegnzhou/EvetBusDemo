package com.itcast.zbc.eventbus;

/**
 * Created by Zbc on 2016/8/2.
 */
public class MessageEvent {
    public String msgM;

    public MessageEvent(String msg) {
        msgM = msg;
    }

    public String getMsg() {
        return msgM;
    }


}
