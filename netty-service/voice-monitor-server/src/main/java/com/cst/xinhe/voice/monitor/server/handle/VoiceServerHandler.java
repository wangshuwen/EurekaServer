package com.cst.xinhe.voice.monitor.server.handle;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.voice.monitor.server.channel.VoiceChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName VoiceServerHandler
 * @Description
 * @Auther lifeng
 * @DATE 2018/11/28 14:30
 * @Vserion v0.0.1
 */
@ChannelHandler.Sharable
@Component
public class VoiceServerHandler extends ChannelInboundHandlerAdapter {

    private static VoiceServerHandler voiceServerHandler;

    @Resource
    private WSVoiceServer wSVoiceServer;

    @Resource
    WSVoiceStatusServer wsVoiceStatusServer;
    @Resource
    StaffService staffService;
    @Resource
    TerminalUpdateIpMapper terminalUpdateIpMapper;
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());


    //注入 上传数据服务



//    @Resource
//    private Client client;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        voiceServerHandler = this;
        voiceServerHandler.wSVoiceServer = this.wSVoiceServer;
        voiceServerHandler.wsVoiceStatusServer = this.wsVoiceStatusServer;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(req);
       /* for (byte b : req) {
            System.out.printf(" 0x%02x", b);
        }*/
        voiceServerHandler.wSVoiceServer.sendMessage(req);
        ReferenceCountUtil.release(msg);
    }

    /**
     * @param
     * @return void
     * @description 建立连接 执行的方法
     * @date 17:35 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.info("语音[" + str + "] 连接成功");
        //默认是和一个人通话
        VoiceChannelMap.addChannel("channel", ctx.channel());
        Map<String, Object> map = new HashMap<>();
        map.put("cmd", "2008");
        map.put("result", "11");
        map.put("ipPort", str);
        voiceServerHandler.wsVoiceStatusServer.sendInfo(JSON.toJSONString(new WebSocketData(3, map)));
        log.info("语音[" + str + "] 加入session");
        log.info("当前实时语音流连接数量" + VoiceChannelMap.getChannelNum());
    }

    /**
     * @param
     * @return void
     * @description 断开连接执行的方法
     * @date 17:35 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws IOException {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();


        Map<String, Object> map = new HashMap<>();
//        TerminalUpdateIp terminalUpdateIp = terminalUpdateIpMapper.findTerminalIdByIpAndPort(clientIP, port);
//        if(terminalUpdateIp!=null){
//            Integer terminalId=terminalUpdateIp.getTerminalNum();
//            //员工信息
//            Map<String, Object> staffInfo = staffService.findStaffIdByTerminalId(terminalId);
//            map.put("staffInfo",staffInfo);
//
//        }

        map.put("cmd", "2008");
        map.put("result", "99");
        map.put("ipPort", str);
        voiceServerHandler.wsVoiceStatusServer.sendInfo(JSON.toJSONString(new WebSocketData(3, map)));
        log.info("语音[" + str + "] 已断开连接");
        VoiceChannelMap.removeChannelByName("channel");
        log.info("语音[" + str + "] 被移出session");
    }

    /**
     * @param
     * @return void
     * @description 数据读取完成执行方法
     * @date 17:35 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.info("读取语音[" + str + "] 数据完成");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已被移除");
//        System.out.println("移除客户端");
    }

    /**
     * @param
     * @return void
     * @description 心跳处理
     * @date 17:36 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                //TODO 读超时
                ctx.channel().close();
                VoiceChannelMap.removeChannelByName("channel");
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                //TODO 写超时
                ctx.channel().close();
                VoiceChannelMap.removeChannelByName("channel");
            }
            if (event.state() == IdleState.ALL_IDLE) {
                //清除超时会话
                ChannelFuture writeAndFlush = ctx.writeAndFlush("client will be remove");
                writeAndFlush.addListener((ChannelFutureListener) future -> {
                    //TODO 通知web端显示并存储数据库
                    //TODO 语音掉线
                    ctx.channel().close();
                    VoiceChannelMap.removeChannelByName("channel");
                });
            }
        } else {
            super.userEventTriggered(ctx, evt);
            VoiceChannelMap.removeChannelByName("channel");
        }
    }

    /**
     * @param
     * @return void
     * @description 异常处理方法
     * @date 17:37 2018/11/21
     * @auther lifeng
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        StringBuffer sb = new StringBuffer(clientIP);
        sb.append(":");
        sb.append(port);
        String str = sb.toString();
        log.error("语音[" + str + "] 出现异常" + cause.getLocalizedMessage());
        VoiceChannelMap.removeChannelByName("channel");
        cause.printStackTrace();
    }
}
