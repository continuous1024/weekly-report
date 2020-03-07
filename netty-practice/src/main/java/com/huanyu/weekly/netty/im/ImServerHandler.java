package com.huanyu.weekly.netty.im;

import com.huanyu.weekly.netty.im.protocol.ImRequestProto;
import com.huanyu.weekly.netty.im.protocol.ImResponseProto;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

public class ImServerHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("检测ImClient是否已经断开连接");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                long heartBeatTime = 2000;
                Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
                long now = System.currentTimeMillis();
                if (lastReadTime != null && now - lastReadTime > heartBeatTime){
                    Long userId = null;
                    for (Map.Entry<Long, Channel> entry : ImServer.sessionMap.entrySet()) {
                        if (entry.getValue().equals(ctx.channel())) {
                            userId = entry.getKey();
                        }
                    }

                    if (userId != null) {
                        ImServer.sessionMap.remove(userId);
                        ImServer.userInfoMap.remove(userId);
                    }

                    ctx.channel().close();
                }
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("接收到ImClient的消息，进行处理");
        if (msg instanceof ImRequestProto.ImRequest) {
            ImRequestProto.ImRequest imRequest = (ImRequestProto.ImRequest) msg;
            if (MessageEnum.LOGIN.getValue() == imRequest.getType()) {
                System.out.println("接收到ImClient的登录消息，保存用户信息 " + msg);
                ImServer.sessionMap.put(imRequest.getRequestId(), ctx.channel());
                ImServer.userInfoMap.put(imRequest.getRequestId(), imRequest.getRequestMessage());
            }

            if (MessageEnum.PING.getValue() == imRequest.getType()) {
                System.out.println("接收到ImClient的心跳消息");
                NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
                ImResponseProto.ImResponse response = ImResponseProto.ImResponse.newBuilder()
                        .setResponseId(0L)
                        .setResponseMessage("heartbeat")
                        .setType(MessageEnum.PONG.getValue()).build();
                ChannelFuture channelFuture = ctx.writeAndFlush(response);
                channelFuture.addListeners((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        System.out.println("服务端发送心跳响应消息成功");
                    }
                });
            }

            if (MessageEnum.MSG.getValue() == imRequest.getType()) {
                System.out.println("接收到ImClient的单聊消息");
                Long receiveUserId = imRequest.getRequestId();
                Channel channel = ImServer.sessionMap.get(receiveUserId);
                if (channel == null) {
                    System.out.println(receiveUserId + " 断开连接了！！！");
                } else {
                    String message = ImServer.userInfoMap.get(receiveUserId) + ": " + imRequest.getRequestMessage();
                    // 转发消息给对应的user
                    ImResponseProto.ImResponse response = ImResponseProto.ImResponse.newBuilder()
                            .setResponseId(receiveUserId)
                            .setResponseMessage(message)
                            .setType(MessageEnum.MSG.getValue()).build();
                    ChannelFuture channelFuture = channel.writeAndFlush(response);
                    channelFuture.addListeners((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            System.out.println("服务端转发响应给对应的user " + response);
                        }
                    });
                }
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
