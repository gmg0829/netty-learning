package com.gmg.nettyclient.proto;

import com.gmg.nettyclient.constant.ConnConstant;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/20  10:15
 */

public class ConnectionListener implements ChannelFutureListener{

    private ProtoClient protoClient=new ProtoClient();

    @Override
    public void operationComplete(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()){
            final EventLoop loop=channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("开始重连操作");
                    protoClient.conn(ConnConstant.host,ConnConstant.port);
                }
            },1L, TimeUnit.SECONDS);
        }else{
            System.err.println("服务端连接成功");
        }
    }
}
