package com.huanyu.weekly.netty.im;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class NettyAttrUtil {
    private static final AttributeKey<Long> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");

    public static void updateReaderTime(Channel channel, Long time) {
        channel.attr(ATTR_KEY_READER_TIME).set(time);
    }


    public static Long getReaderTime(Channel channel) {
        return channel.attr(ATTR_KEY_READER_TIME).get();
    }
}
