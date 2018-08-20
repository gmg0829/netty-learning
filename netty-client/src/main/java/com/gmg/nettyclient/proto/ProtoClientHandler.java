package com.gmg.nettyclient.proto;

import com.gmg.nettyclient.constant.ConnConstant;
import com.netty.im.core.proto.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by yr on 2018/8/18.
 */
public class ProtoClientHandler extends ChannelInboundHandlerAdapter {

    private ProtoClient protoClient=new ProtoClient();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProto.Message message = (MessageProto.Message) msg;
        System.out.println("client:" + message.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent=(IdleStateEvent)evt;
            if(idleStateEvent.state().equals(IdleState.READER_IDLE)){
                System.out.println("长期没有收到服务器推送消息");
            }else if(idleStateEvent.state().equals(IdleState.WRITER_IDLE)){
                System.out.println("长期没有向服务器推送消息");
                //发送心跳包
                ctx.writeAndFlush(MessageProto.Message.newBuilder().setType(1));
            }else if(idleStateEvent.state().equals(IdleState.ALL_IDLE)){
                System.out.println("All");
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("掉线了");
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                protoClient.conn(ConnConstant.host,ConnConstant.port);
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }
}
