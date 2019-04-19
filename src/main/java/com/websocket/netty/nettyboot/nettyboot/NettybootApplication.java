package com.websocket.netty.nettyboot.nettyboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


@SpringBootApplication
public class NettybootApplication implements CommandLineRunner {
	@Resource
	private WebSocketServer webSocketServer;
	@Resource
	private PropertiesConfig propertiesConfig;

	public static void main(String[] args) {
		SpringApplication.run(NettybootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		webSocketServer.run(propertiesConfig.port);
	}

}
