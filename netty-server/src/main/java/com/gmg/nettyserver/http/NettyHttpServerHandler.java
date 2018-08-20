package com.gmg.nettyserver.http;

import com.netty.im.core.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.tomcat.util.http.fileupload.UploadContext;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/20  10:52
 */
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){
            DefaultHttpRequest defaultHttpRequest=(DefaultHttpRequest)msg;
            System.out.println("uri"+defaultHttpRequest.getUri());
            System.out.println("method"+defaultHttpRequest.getMethod());
            System.out.println(msg);
        }

        if(msg instanceof HttpContent){
            LastHttpContent httpContent=(LastHttpContent)msg;
            ByteBuf byteBuf=httpContent.content();
            if(byteBuf instanceof EmptyByteBuf){
                System.out.println("content:无数据");
            }else {
                String content=new String(ByteUtils.objectToByte(byteBuf));
                System.out.println("content:"+content);
            }

        }
        FullHttpMessage response=new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, //版本
                HttpResponseStatus.OK, //状态码
                Unpooled.wrappedBuffer("欢迎来到这里".getBytes())//内容
                );
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/plain;charset=UTF-8");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}