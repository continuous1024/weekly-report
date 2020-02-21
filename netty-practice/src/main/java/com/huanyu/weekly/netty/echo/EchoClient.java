package com.huanyu.weekly.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;

/**
 * @author arron
 */
public class EchoClient {
    public static final String LOCAL_HOST = "127.0.0.1";
    public static final String DEFAULT_PORT = "9999";

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        String host = LOCAL_HOST;
        int port = Integer.parseInt(DEFAULT_PORT);
        new EchoClient(host, port).start();
    }

    @SneakyThrows
    private void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 设置用于处理 Channel 所有事件的 EventLoopGroup
                    .channel(NioSocketChannel.class) // channel()方法指定了Channel的实现类。
                    .remoteAddress(new InetSocketAddress(host, port)) // 设置远程地址。或者，也可以通过 connect()方法来指 定它
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    }); // 设置将被添加到 ChannelPipeline 以接收事件通知的 ChannelHandler
            // 连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync(); // 连接到远程节点并返回一个 ChannelFuture，其将会在连接操作完成后接收到通知
            // 阻塞，直到Channel关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
