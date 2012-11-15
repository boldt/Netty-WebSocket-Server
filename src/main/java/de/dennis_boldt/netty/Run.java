package de.dennis_boldt.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpChunkAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import java.net.InetSocketAddress;

public class Run {

    public static void main(String[] args) throws Exception {
        final ServerBootstrap sb = new ServerBootstrap();
        try {
            sb.group(new NioEventLoopGroup(), new NioEventLoopGroup())
             .channel(NioServerSocketChannel.class)
             .localAddress(new InetSocketAddress(6666))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(final SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                        new HttpRequestDecoder(),
                        new HttpChunkAggregator(65536),
                        new HttpResponseEncoder(),
                        new WebSocketServerProtocolHandler("/websocket"),
                        new TextFrameHandler());
                }
            });

            final Channel ch = sb.bind().sync().channel();

            ch.closeFuture().sync();
        } finally {
            sb.shutdown();
        }
    }

}
