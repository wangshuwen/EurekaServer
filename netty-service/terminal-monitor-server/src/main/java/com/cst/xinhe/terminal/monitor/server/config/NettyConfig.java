package com.cst.xinhe.terminal.monitor.server.config;

import com.cst.xinhe.terminal.monitor.server.codec.CustomDecoder;
import com.cst.xinhe.terminal.monitor.server.codec.CustomEncoder;
import com.cst.xinhe.terminal.monitor.server.handle.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 17:51
 **/
@Component
public class NettyConfig {

    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 终端的端口号
     */
    @Value("${netty.port}")
    private int port;



    public NettyConfig() {
    }

    /**
     * 启动服务器方法
     */
    public void run() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            serverBootstrap.group(bossGroup, workerGroup);

            //设置socket工厂
            serverBootstrap.channel(NioServerSocketChannel.class);
            //客户端服务配置
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipe = socketChannel.pipeline();
                  //  pipe.addLast(new IdleStateHandler(80, 80, 160, TimeUnit.SECONDS));
                    pipe.addLast(new CustomDecoder(548, 18, 2, -26, 0, true));
                    pipe.addLast(new CustomEncoder());
                    pipe.addLast(new NettyServerHandler());
                }
            });
            //基站服务配置

            //语音服务配置


            //参数TCP设置
//            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 4096);     // serverSocketChannel连接缓冲池大小
//            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);   //socketChannel true维持连接活跃 默认2小时，无操作断开
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);    //socketChannel 关闭缓冲=关闭延迟发送
            ChannelFuture future = serverBootstrap.bind(port);
            log.info("终端》》服务端已启动，端口号 [" + port + "]");

            //基站



            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }

    }
}
