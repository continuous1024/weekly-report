package com.huanyu.weekly.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Echo服务端
 * @author arron
 */
public class EchoServer {
    public static final String DEFAULT_PORT = "9999";

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = Integer.parseInt(DEFAULT_PORT);
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        // 不会改变的类，使用final修饰
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(group) // 设置ServerBootstrap要用的EventLoopGroup。这个EventLoopGroup将用于ServerChannel和被接受的子 Channel 的 I/O 处理
                .channel(NioServerSocketChannel.class) // 设置将要被实例化的 ServerChannel类。如果该实现类 没提供默认的构造函数，可以通过调用channel Factory()方法来指定一个工厂类，它将会被bind()方 法调用
                .localAddress(new InetSocketAddress(port)) // 指定 Channel 应该绑定到的本地地址。如果没有指定， 则将由操作系统创建一个随机的地址。或者，也可以通过 bind()或者 connect()方法指定 localAddress
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 当一个新的连接被接受时，一个新的子Channel将会被创建。
                    // ChannelInitializer会把serverHandler添加到该channel的ChannelPipeline中
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                }); // 设置将被添加到 ChannelPipeline 以接收事件通知的 ChannelHandler
            // 异步地绑定服务器，调用sync方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync(); // 绑定Channel并返回一个ChannelFuture，其将会在绑定操作完成后接收到通知，在那之后必须调用Channel.connect()方法来建立连接
            // 获取channel的closeFuture, 并阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}
