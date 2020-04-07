package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.io.Console;

/**
 * @author Upoint0002
 */
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    private Console console;
    public WebSocketClientHandler (Console console) {
        this.console = console;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("客户端收到" + o);
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(console.readLine()));
        ((WebSocketFrame) o).retain();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Connect success");
    }
}
