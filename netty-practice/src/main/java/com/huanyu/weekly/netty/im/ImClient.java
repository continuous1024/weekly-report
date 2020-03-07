package com.huanyu.weekly.netty.im;

import com.huanyu.weekly.netty.im.protocol.ImRequestProto;
import com.huanyu.weekly.netty.im.protocol.ImResponseProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ImClient {
    public static final long DEFAULT_USER1_ID = 100000;
    public static final long DEFAULT_USER2_ID = 100001;
    public static final String DEFAULT_USER1_NAME = "user1";
    public static final String DEFAULT_USER2_NAME = "user2";

    private Channel channel;

    private void createImClient(String remoteHost, int remotePort) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline cp = ch.pipeline();
                        cp.addLast("logger", new LoggingHandler());

                        // sends a ping message when there is
                        // no outbound traffic for 10 seconds.
                        // https://netty.io/4.1/api/index.html
                        cp.addLast("idleStateHandler",
                                new IdleStateHandler(
                                        0, 10, 0));

                        // ProtoBuf编码器和解码器 https://netty.io/4.1/api/index.html
                        // Decoders
                        cp.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
                        cp.addLast("protobufDecoder",
                                new ProtobufDecoder(ImResponseProto.ImResponse.getDefaultInstance()));
                        // Encoder
                        cp.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
                        cp.addLast("protobufEncoder", new ProtobufEncoder());

                        // handler
                        cp.addLast("handler", new ImClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(remoteHost, remotePort).sync();
        channel = channelFuture.channel();
    }

    private void login(Long userId, String message) {
        ImRequestProto.ImRequest loginRequest = ImRequestProto.ImRequest.newBuilder()
                .setRequestId(userId)
                .setRequestMessage(message)
                .setType(MessageEnum.LOGIN.getValue()).build();
        ChannelFuture future = channel.writeAndFlush(loginRequest);
        future.addListener((ChannelFutureListener) channelFuture ->
                System.out.println(userId + ":" + message + "注册客户端成功")
        );
    }

    public static void main(String[] args) throws InterruptedException {
        String remoteHost = "127.0.0.1";
        int remotePort = 9901;
        ImClient user1 = new ImClient();
        ImClient user2 = new ImClient();
        user1.createImClient(remoteHost, remotePort);
        user1.login(DEFAULT_USER1_ID, DEFAULT_USER1_NAME);

        user2.createImClient(remoteHost, remotePort);
        user2.login(DEFAULT_USER2_ID, DEFAULT_USER2_NAME);

        // user1 发送消息给 user2
        user1.sendMessage(DEFAULT_USER2_ID, "Hello, I am " + DEFAULT_USER1_NAME);
        // user2 发送消息给 user1
        user2.sendMessage(DEFAULT_USER1_ID, "Hello, I am " + DEFAULT_USER2_NAME);
    }

    private void sendMessage(Long receiveUserId, String message) {
        ImRequestProto.ImRequest msgRequest = ImRequestProto.ImRequest.newBuilder()
                .setRequestId(receiveUserId)
                .setRequestMessage(message)
                .setType(MessageEnum.MSG.getValue()).build();
        ChannelFuture future = channel.writeAndFlush(msgRequest);
        future.addListener((ChannelFutureListener) channelFuture ->
                System.out.println(receiveUserId + "发送消息 " + message + " 成功")
        );
    }
}
