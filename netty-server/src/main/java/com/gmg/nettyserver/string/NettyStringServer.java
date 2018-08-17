package com.gmg.nettyserver.string;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/17  15:04
 */
public class NettyStringServer {

    public void run(int port){
        EventLoopGroup bossGroup=new NioEventLoopGroup();//接受客户端连接
        EventLoopGroup workGroup=new NioEventLoopGroup();//处理客户端读写

        ServerBootstrap bootstrap=new ServerBootstrap();

        bootstrap.group(bossGroup,workGroup).
                channel(NioServerSocketChannel.class).//指定nio方式
                childHandler(new ChannelInitializer<SocketChannel>() {//配置具体的数据处理方式 ，可以指定编解码器，处理数据的Handler
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast("decoder",new StringDecoder());
                        socketChannel.pipeline().addLast("encoder",new StringEncoder());
                        socketChannel.pipeline().addLast(new StringHandle());
                    }
                }).option(ChannelOption.SO_BACKLOG,128).childOption(ChannelOption.SO_KEEPALIVE,true);

        try {
            ChannelFuture channelFuture=bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
