package ares.remoting.framework.provider;

import ares.remoting.framework.helper.PropertyConfigHelper;
import ares.remoting.framework.model.AresRequest;
import ares.remoting.framework.serialization.NettyDecoderHandler;
import ares.remoting.framework.serialization.NettyEncoderHandler;
import ares.remoting.framework.serialization.common.SerializeType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

    private static NettyServer nettyServer = new NettyServer();

    private Channel channel;
    //服务端boss线程组
    private EventLoopGroup bossGroup;
    //服务端worker线程组
    private EventLoopGroup workerGroup;
    //序列化类型配置信息
    private SerializeType serializeType = PropertyConfigHelper.getSerializeType();

    /**
     * 启动netty服务
     * @param port
     */
    public void start(final int port) {
        synchronized (NettyServer.class) {// 确保同一之间只启动一个netty服务端
            if (bossGroup != null || workerGroup != null) {
                return;
            }

            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //注册解码器NettyDecoderHandler(自定义解码器)
                            ch.pipeline().addLast(new NettyDecoderHandler(AresRequest.class, serializeType));
                            //注册解码器NettyEncoderHandler(自定义编码器)
                            ch.pipeline().addLast(new NettyEncoderHandler(serializeType));
                            //注册服务端业务逻辑处理器NettyServerInvokeHandler
                            ch.pipeline().addLast(new NettyServerInvokeHandler());
                        }
                    });
            try {
                channel = serverBootstrap.bind(port).sync().channel();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void stop() {
        if (null == channel) {
            throw new RuntimeException("Netty Server Stoped");
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }

    private NettyServer() {
    }

    //饿汉式
    public static NettyServer singleton() {
        return nettyServer;
    }
}
