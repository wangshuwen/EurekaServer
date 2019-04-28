package com.cst.xinhe.voice.monitor.server.config;

import com.cst.xinhe.voice.monitor.server.codec.VoiceDataEncoder;
import com.cst.xinhe.voice.monitor.server.handle.VoiceServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 19:01
 **/
@Component
public class NettyConfig {
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 语音的端口号
     */
    @Value("${netty.port2}")
    private int voice_port;

    public NettyConfig() {
    }


    public void run() {
        ServerBootstrap voiceServerBootstrap = new ServerBootstrap();
        //语音
        EventLoopGroup voiceBossGroup = new NioEventLoopGroup();
        EventLoopGroup voiceWorkerGroup = new NioEventLoopGroup();
        try {
            voiceServerBootstrap.group(voiceBossGroup, voiceWorkerGroup);
            voiceServerBootstrap.channel(NioServerSocketChannel.class);
            voiceServerBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipe = socketChannel.pipeline();
//                    pipe.addLast(new IdleStateHandler(20, 20, 40, TimeUnit.SECONDS));
                    pipe.addLast(new StringEncoder());
                    pipe.addLast(new VoiceDataEncoder());
                    pipe.addLast(new VoiceServerHandler());
                }
            });
            //语音
            voiceServerBootstrap.option(ChannelOption.SO_BACKLOG, 4096);     // serverSocketChannel连接缓冲池大小
//            voiceServerBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);   //socketChannel true维持连接活跃 默认2小时，无操作断开
            voiceServerBootstrap.childOption(ChannelOption.TCP_NODELAY, true);    //socketChannel 关闭缓冲=关闭延迟发送
            ChannelFuture voiceFuture = voiceServerBootstrap.bind(voice_port);
            log.info("语音》》服务端已启动，端口号 [" + voice_port + "]");
            voiceFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            voiceBossGroup.shutdownGracefully();
            voiceWorkerGroup.shutdownGracefully();
        }


    }
}
