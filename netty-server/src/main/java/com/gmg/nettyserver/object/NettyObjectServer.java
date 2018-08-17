package com.gmg.nettyserver.object;

import com.gmg.nettyserver.string.StringHandle;
import com.netty.im.core.message.MessageDecoder;
import com.netty.im.core.message.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import sun.misc.MessageUtils;

/**
 * @author gmg
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/17  15:04
 */
public class NettyObjectServer {

    public void run(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        //实体类传输数据，jdk序列化
                        ch.pipeline().addLast("decoder", new MessageDecoder());
                        ch.pipeline().addLast("encoder", new MessageEncoder());
                        ch.pipeline().addLast(new ObjectHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
