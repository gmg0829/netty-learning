package com.gmg.nettyclient.string;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/17  15:25
 */
public class NettyStringClient {
    private Channel channel;

    public Channel conn(String host,int port){
            doConnect(host, port);
            return this.channel;
    }

    private void doConnect(String host,int port){
        EventLoopGroup workGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(workGroup).
                channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast("encoder", new StringEncoder());
                        ch.pipeline().addLast(new ClientStringHandler());
                    }
                });
        try {
            ChannelFuture channelFuture=bootstrap.connect(host, port).sync();
            channel=channelFuture.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }


    }
}
