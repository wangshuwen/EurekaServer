package com.cst.xinhe.station.monitor.server.config;

import com.cst.xinhe.station.monitor.server.codec.StationDecoder;
import com.cst.xinhe.station.monitor.server.codec.StationEncoder;
import com.cst.xinhe.station.monitor.server.handle.StationServerHandler;
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
 * @description: netty config
 * @author: lifeng
 * @create: 2019-04-26 14:18
 **/
@Component
public class NettyConfiguration {
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(NettyConfiguration.class);


    /**
     * 基站的端口号
     */
    @Value("${netty.port}")
    private int station_port;

    public void run(){
        ServerBootstrap stationServerBootstrap = new ServerBootstrap();

        //基站
        EventLoopGroup stationBossGroup = new NioEventLoopGroup();
        EventLoopGroup stationWorkerGroup = new NioEventLoopGroup();

        try {


            stationServerBootstrap.group(stationBossGroup, stationWorkerGroup);

            stationServerBootstrap.channel(NioServerSocketChannel.class);

            //基站服务配置
            stationServerBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipe = socketChannel.pipeline();
                    pipe.addLast(new IdleStateHandler(20, 20, 40, TimeUnit.SECONDS));
                    pipe.addLast(new StationDecoder(548, 18, 2, -26, 0, true));
                    pipe.addLast(new StationEncoder());
                    pipe.addLast(new StationServerHandler());
                }
            });
            stationServerBootstrap.option(ChannelOption.SO_BACKLOG, 4096);     // serverSocketChannel连接缓冲池大小
            stationServerBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);   //socketChannel true维持连接活跃 默认2小时，无操作断开
            stationServerBootstrap.childOption(ChannelOption.TCP_NODELAY, true);    //socketChannel 关闭缓冲=关闭延迟发送
            ChannelFuture stationFuture = stationServerBootstrap.bind(station_port);
            log.info("基站》》服务端已启动，端口号 [" + station_port + "]");

            stationFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            stationBossGroup.shutdownGracefully();
            stationWorkerGroup.shutdownGracefully();
        }

    }
}
