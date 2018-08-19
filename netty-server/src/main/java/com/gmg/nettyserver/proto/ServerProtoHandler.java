package com.gmg.nettyserver.proto;

import com.netty.im.core.message.Message;
import com.netty.im.core.proto.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by yr on 2018/8/18.
 */
public class ServerProtoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProto.Message message=( MessageProto.Message)msg;
        if (ConnectionPool.getChannel(message.getId())==null){
            ConnectionPool.putChannel(message.getId(),ctx);
        }
        System.err.println("server"+message.getContent());
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
