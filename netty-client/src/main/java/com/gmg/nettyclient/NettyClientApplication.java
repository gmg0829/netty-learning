package com.gmg.nettyclient;

import com.gmg.nettyclient.object.NettyObjectClient;
import com.gmg.nettyclient.proto.ProtoClient;
import com.gmg.nettyclient.string.NettyStringClient;
import com.netty.im.core.message.Message;
import com.netty.im.core.proto.MessageProto;
import io.netty.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class NettyClientApplication {

	public static void main(String[] args) {
		String host="127.0.0.1";
		int port=2222;
		//传输字符串
		//Channel channel=new NettyStringClient().conn(host,port);
		//channel.writeAndFlush("gmg");
		//传输对象
		//Channel channel=new NettyObjectClient().conn(host,port);
		String id=UUID.randomUUID().toString().replaceAll("-", "");
		Message message=new Message();
		message.setId(id);
		message.setContent("yr");
		//channel.writeAndFlush(message);
		//MessageProto
		MessageProto.Message protoMessage=MessageProto.Message.newBuilder().
				setId(id).setContent("gmg").build();
		Channel channel=new ProtoClient().conn(host,port);
		channel.writeAndFlush(protoMessage);
		SpringApplication.run(NettyClientApplication.class, args);
	}
}
