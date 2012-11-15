package de.dennis_boldt.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextFrameHandler extends ChannelInboundMessageHandlerAdapter<TextWebSocketFrame> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String request = frame.getText();
        ctx.channel().write(new TextWebSocketFrame(request.toUpperCase()));
    }

}
