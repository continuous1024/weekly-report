package com.huanyu.weekly.netty.im;

import lombok.Getter;

@Getter
public enum MessageEnum {
    PING(1),
    PONG(2),
    MSG(3),
    LOGIN(4);

    private Integer value;

    MessageEnum(int value) {
        this.value = value;
    }
}
