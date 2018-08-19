package com.gmg.nettyserver;

import com.gmg.nettyserver.object.NettyObjectServer;
import com.gmg.nettyserver.proto.ProtoServer;
import com.gmg.nettyserver.string.NettyStringServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

	public static void main(String[] args) {
		int port=2222;
		new Thread(()->{
			//new NettyStringServer().run(port);
			//new NettyObjectServer().run(port);
			new ProtoServer().run(port);
		}).start();
		SpringApplication.run(NettyServerApplication.class, args);
	}
}
