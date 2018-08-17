package com.gmg.nettyserver.string;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/17  15:05
 */
public class StringHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("server:"+msg.toString());
        ctx.writeAndFlush(msg.toString()+"您好");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
