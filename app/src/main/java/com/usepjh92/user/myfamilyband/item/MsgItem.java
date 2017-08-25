package com.usepjh92.user.myfamilyband.item;


public class MsgItem {

    String name;
    String msg;

    boolean isMe; // 나의 메시지인가??

    public MsgItem(String name, String msg, boolean isMe) {
        this.name = name;
        this.msg = msg;
        this.isMe = isMe;
    }
}
