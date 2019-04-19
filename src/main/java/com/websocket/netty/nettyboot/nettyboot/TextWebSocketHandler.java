package com.websocket.netty.nettyboot.nettyboot;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * 处理 web socket 文本消息
 *
 * @author edson.liu
 * @date  2019/04/03 - 14:37
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		log.info("接收到客户端的消息:[{}]", msg.text());
		// 如果是向客户端发送文本消息，则需要发送 TextWebSocketFrame 消息
		InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		String ip = inetSocketAddress.getHostName();
		String txtMsg = "[" + ip + "][" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ==> " + msg.text();
		ctx.channel().writeAndFlush(new TextWebSocketFrame(txtMsg));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		log.error("服务器发生了异常:", cause);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			log.info("web socket 握手成功。");
			WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
			String requestUri = handshakeComplete.requestUri();
			log.info("requestUri:[{}]", requestUri);
			String subproTocol = handshakeComplete.selectedSubprotocol();
			log.info("subproTocol:[{}]", subproTocol);
			handshakeComplete.requestHeaders().forEach(entry -> log.info("header key:[{}] value:[{}]", entry.getKey(), entry.getValue()));
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
