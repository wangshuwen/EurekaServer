package com.cst.xinhe.ws.push.service.ws;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WSServer
 * @Description  气体信息推送
 * @Auther lifeng
 * @DATE 2018/8/31 14:31
 * @Vserion v0.0.1
 */

@ServerEndpoint(value = "/websocket")
@Component
public class WSServer {
    public static Integer orgId=null;
    public static Integer zoneId=null;


    private static final Logger log = LoggerFactory.getLogger(WSServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WSServer> webSocketSet = new CopyOnWriteArraySet<WSServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
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
    public void onMessage(String message, Session session) {

        //按区域或者部门进行筛选
       JSONObject jsonObject = JSONObject.parseObject(message);
        //将json字符串转成json对象后遍历键值对
        Map<String, Object> map = jsonObject;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if ("zoneId".equals(entry.getKey())) {
                zoneId = (Integer) entry.getValue();
            }
            if ("orgId".equals(entry.getKey())) {
                orgId = (Integer) entry.getValue();
            }
        }


        log.info("来自客户端的消息:" + message);

      /*  //群发消息
        for (WSServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    public synchronized void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        log.info(message);
        for (WSServer item : webSocketSet) {
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
        WSServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WSServer.onlineCount--;
    }

}
