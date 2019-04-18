# nettyWebSocket
SpringBoot+Netty+WebSocket 二进制文本图片上传
需求：
    1、使用 netty 实现 websocket 服务器

    2、实现 文本信息 的传递

    3、实现 二进制 信息的传递，如果是图片则传递到后台后在前台直接显示，非图片提示。（此处的图片和非图片是前端传递到后台的二进制数据然后后端在原封不动的直接返回到前台）

    4、只需要考虑 websocket 协议，不用处理http请求

 

实现细节：
    1、netty中对websocket增强的处理器
          WebSocketServerProtocolHandler 

              >> 此处理器可以处理了 webSocket 协议的握手请求处理，以及 Close、Ping、Pong控制帧的处理。对于文本和二进制的数据帧需要我们自己处理。

              >> 如果我们需要拦截 webSocket 协议握手完成后的处理，可以实现ChannelInboundHandler#userEventTriggered方法，并判断是否是 HandshakeComplete 事件。

              >> 参数：websocketPath 表示 webSocket 的路径

              >> 参数：maxFrameSize 表示最大的帧，如果上传大文件时需要将此值调大

    2、文本消息的处理
               客户端： 直接发送一个字符串即可

               服务端： 服务端给客户端响应文本数据，需要返回  TextWebSocketFrame 对象，否则客户端接收不到。

    3、二进制消息的处理
               客户端：向后台传递一个 blob 对象即可，如果我们需要传递额外的信息，那么可以在 blob 对象中进行添加，此例中自定义前4个字节表示数据的类型。

               服务端：处理 BinaryWebSocketFrame 帧，并获取前4个字节，判断是否是图片，然后返回 BinaryWebSocketFrame对象给前台。

    4、针对二进制消息的自定义协议如下：（此处实现比较简单）
          前四个字节表示文件类型，后面的字节表示具体的数据。

          在java中一个int是4个字节，在js中使用Int32表示

          此协议主要是判断前端是否传递的是 图片，如果是图片就直接传递到后台，然后后台在返回二进制数据到前台直接显示这个图片。非图片不用处理。

     5、js中处理二进制数据
           见 webSocket.html 文件中的处理。
           
     启动：NettybootApplication   
     var ws = new WebSocket("ws://192.168.1.84:5555/chat");  修改ws 的ip 地址为你自己的
     默认端口是：5555  application.properties中端口自行修改
     
     也可以通过jar nettyboot-0.0.1-SNAPSHOT.jar 运行 双击webSocket.html 测试效果。
     
     
