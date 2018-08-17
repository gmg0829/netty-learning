package com.gmg.nettyclient.object;

import com.netty.im.core.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/17  16:53
 */
public class ObjectHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        System.out.println("content:"+message.getContent());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
