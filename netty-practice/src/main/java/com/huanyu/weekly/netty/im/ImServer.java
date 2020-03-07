package com.huanyu.weekly.netty.im;

import com.huanyu.weekly.netty.im.protocol.ImRequestProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImServer {
    public static final Map<Long, Channel> sessionMap = new ConcurrentHashMap<>();
    public static final Map<Long, String> userInfoMap = new ConcurrentHashMap<>();

    private void createImServer(int port) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline cp = ch.pipeline();
                            cp.addLast(new LoggingHandler());
                            // The connection is closed when there is no inbound traffic for 11 seconds.
                            // https://netty.io/4.1/api/index.html
                            cp.addLast("idleStateHandler", new IdleStateHandler(
                                    0, 10, 0));

                            // ProtoBuf编码器和解码器 https://netty.io/4.1/api/index.html
                            // Decoders
                            cp.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
                            cp.addLast("protobufDecoder",
                                    new ProtobufDecoder(ImRequestProto.ImRequest.getDefaultInstance()));
                            // Encoder
                            cp.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
                            cp.addLast("protobufEncoder", new ProtobufEncoder());

                            cp.addLast("handler", new ImServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                System.out.println("启动ImServer成功");
            }
        } catch (InterruptedException e) {
            boss.shutdownGracefully().sync();
            work.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int port = 9901;
        ImServer imServer = new ImServer();
        imServer.createImServer(port);
    }
}
