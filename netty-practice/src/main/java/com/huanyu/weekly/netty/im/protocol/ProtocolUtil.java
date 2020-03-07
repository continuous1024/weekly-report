package com.huanyu.weekly.netty.im.protocol;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtocolUtil {
    public static ImRequestProto.ImRequest newImRequest(
            Long requestId, String requestMessage, Integer type) {
        return ImRequestProto.ImRequest.newBuilder()
                .setRequestId(requestId)
                .setRequestMessage(requestMessage)
                .setType(type)
                .build();
    }

    public static byte[] serialize(ImRequestProto.ImRequest imRequest) {
        return imRequest.toByteArray();
    }

    public static ImRequestProto.ImRequest deserialize(byte[] imRequestBytes)
            throws InvalidProtocolBufferException {
        return ImRequestProto.ImRequest.parseFrom(imRequestBytes);
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        ImRequestProto.ImRequest imRequest = newImRequest(
                0L, "heartbeat", 1);
        byte[] imRequestBytes = serialize(imRequest);
        System.out.println(deserialize(imRequestBytes));
    }
}
