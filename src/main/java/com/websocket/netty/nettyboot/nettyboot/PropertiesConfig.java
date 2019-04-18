package com.websocket.netty.nettyboot.nettyboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class PropertiesConfig {
    @Value("${port}")
    public int port;
}



