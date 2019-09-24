package com.cst.xinhe.voice.monitor.server.ws;

import com.cst.xinhe.voice.monitor.server.channel.VoiceChannelMap;
import com.cst.xinhe.voice.monitor.server.context.SpringContextUtil;
import com.cst.xinhe.voice.monitor.server.process.ProcessRtVoice;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 14:34
 **/
@ServerEndpoint(value = "/voiceStreamWebSocket")
@Component
public class WSVoiceServer {

    @Autowired
    private WSVoiceServer  wSVoiceServer;

    private static ObjectMapper json= new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(WSVoiceServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WSVoiceServer> webSocketSet = new CopyOnWriteArraySet<>();
    private static  Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    public WSVoiceServer() {
        this.wSVoiceServer = SpringContextUtil.getBean(WSVoiceServer.class);
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        //房间状态设置为有人，属于可呼叫状态
        ProcessRtVoice.openRoom();
        sessions.put("a", session);
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //设置房间没人，不可呼叫
      //  ProcessRtVoice.closeRoom();
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(byte[] message, Session session) {
        //发送语音数据到终端
        Channel channel = VoiceChannelMap.getChannelByName("channel");
        if (null != channel)
            channel.writeAndFlush(message);


       /* for (byte b : req) {
            System.out.printf(" 0x%02x", b);
        }*/
      /*  try {
            wSVoiceServer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }*/


    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessage(byte[] message) throws IOException, EncodeException {
        sessions.get("a").getBasicRemote().sendBinary(ByteBuffer.wrap(message));
    }


    /**
     * 群发自定义消息
     */
    public  static void sendInfo(String message) throws IOException {
        log.info(message);
        for (WSVoiceServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WSVoiceServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WSVoiceServer.onlineCount--;
    }


}
