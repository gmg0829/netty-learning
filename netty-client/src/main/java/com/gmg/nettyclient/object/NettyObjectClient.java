package com.gmg.nettyclient.object;

import com.gmg.nettyclient.string.ClientStringHandler;
import com.netty.im.core.message.MessageDecoder;
import com.netty.im.core.message.MessageEncoder;
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
 * @date 2018/8/17  16:53
 */
public class NettyObjectClient {
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
                        ch.pipeline().addLast("decoder", new MessageDecoder());
                        ch.pipeline().addLast("encoder", new MessageEncoder());
                        ch.pipeline().addLast(new ObjectHandle());
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
