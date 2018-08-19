package com.gmg.nettyclient.proto;

import com.gmg.nettyclient.object.ObjectHandle;
import com.netty.im.core.kryo.KryoDecoder;
import com.netty.im.core.kryo.KryoEncoder;
import com.netty.im.core.proto.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * Created by yr on 2018/8/18.
 */
public class ProtoClient {
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
                        // 实体类传输数据，protobuf序列化
                        ch.pipeline().addLast("decoder", new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
                        ch.pipeline().addLast("encoder", new ProtobufEncoder());
                        ch.pipeline().addLast(new ProtoClientHandler());
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
