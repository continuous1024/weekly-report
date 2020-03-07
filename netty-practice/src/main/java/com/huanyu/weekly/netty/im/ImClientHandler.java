package com.huanyu.weekly.netty.im;

import com.huanyu.weekly.netty.im.protocol.ImRequestProto;
import com.huanyu.weekly.netty.im.protocol.ImResponseProto;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@ChannelHandler.Sharable
public class ImClientHandler extends ChannelDuplexHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("ImClient和ImServer连接建立成功");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("ImClient连接断开，可以进行重连");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ImResponseProto.ImResponse){
            ImResponseProto.ImResponse response = (ImResponseProto.ImResponse) msg;
            if (MessageEnum.PONG.getValue().equals(response.getType())) {
                NettyAttrUtil.updateReaderTime(ctx.channel(), System.currentTimeMillis());
            }
        }

        System.out.println("ImClient接收到服务端的响应，响应内容：" + msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("IdleStateEvent事件触发，如果超过10秒没有给客户端发送消息，则开始发送心跳消息");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state().equals(IdleState.WRITER_IDLE)) {
                ImRequestProto.ImRequest pingRequest = ImRequestProto.ImRequest.newBuilder()
                        .setType(MessageEnum.PING.getValue())
                        .setRequestMessage("heartbeat")
                        .setRequestId(0L).build();
                ctx.writeAndFlush(pingRequest).addListeners((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        future.channel().close();
                    }
                });
            }
        }

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("发生异常时，打印异常堆栈，关闭channel");
        cause.printStackTrace();
        ctx.close();
    }
}
