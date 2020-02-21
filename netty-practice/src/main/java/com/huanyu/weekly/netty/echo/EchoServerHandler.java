package com.huanyu.weekly.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 在 EchoServerHandler 中，你仍然需要将传入消息回送给发送者，而 write()操作是异步的，
 * 直到 channelRead()方法返回后可能仍然没有完成。为此，EchoServerHandler 扩展了 ChannelInboundHandlerAdapter，其在这个时间点上不会释放消息。
 * 消息在 EchoServerHandler 的 channelReadComplete()方法中，当 writeAndFlush()方 法被调用时被释放
 *
 * Sharable 标示一个ChannelHandler可以被多个Channel安全地共享
 * @author arron
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 对于每个传入的消息都要调用;
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        // 将接收到的消息写给发送者，而不冲刷出站消息
        ctx.write(msg);
    }

    /**
     * 通知ChannelInboundHandler最后一次对channelRead()的调用是当前批量读取中的最后一条消息
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将未决消息冲刷到远程节点，并且关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 在读取操作期间，有异常抛出时会调用。
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常栈跟踪
        cause.printStackTrace();
        // 关闭该Channel
        ctx.close();
    }
}
