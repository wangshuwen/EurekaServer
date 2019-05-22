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
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/18/9:24
 */
@ServerEndpoint(value = "/webSocketServer")//实时监控多项警报信息
@Component
public class WebsocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebsocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebsocketServer> webSocketSet = new CopyOnWriteArraySet<>();

//    private static ObjectMapper json = new ObjectMapper();

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
        String type="";

        log.info("来自客户端的消息:" + message);
       if(message!=null&&!"".equals(message)){
           JSONObject jsonObject = JSONObject.parseObject(message);
           //将json字符串转成json对象后遍历键值对
           Map<String, Object> map = jsonObject;
           for (Map.Entry<String, Object> entry : map.entrySet()) {
               if ("type".equals(entry.getKey())) {
                   type = (String) entry.getValue();
               }
           }
            //实时语音状态交互
           /*if(type.equals("3")){
               //封装数据通过协议传递到终端
               ResponseData responseData = new ResponseData();
               RequestData customMsg = new RequestData();

               String cmd = "";
               String ipPort = "";
               String result = "";
               String staffId = "";
               for (Map.Entry<String, Object> entry : map.entrySet()) {
                   if ("cmd".equals(entry.getKey())) {
                       cmd = (String) entry.getValue();
                   }
                   if ("ipPort".equals(entry.getKey())) {
                       ipPort = (String) entry.getValue();
                   }
                   if ("result".equals(entry.getKey())) {
                       result = (String) entry.getValue();
                   }
                   if ("staffId".equals(entry.getKey())) {
                       staffId = (String) entry.getValue();
                   }

               }

               //ip和port不为空
               if (!"".equals(ipPort) && ipPort != null) {
                   //设置终端ip和端口
                   String[] ips = ipPort.split(":");

                   customMsg.setTerminalPort(Integer.parseInt(ips[1]));
                   String ip[] = ips[0].split("\\.");
                   if (ip.length == 4) {
                       customMsg.setTerminalIp1(Integer.parseInt(ip[2]));
                       customMsg.setTerminalIp2(Integer.parseInt(ip[3]));
                       customMsg.setTerminalIp(ip[2] + "." + ip[3]);
                   } else {
                       customMsg.setTerminalIp1(Integer.parseInt(ip[0]));
                       customMsg.setTerminalIp2(Integer.parseInt(ip[1]));
                       customMsg.setTerminalIp(ip[0] + "." + ip[1]);
                   }

               }
               customMsg.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
               customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
               customMsg.setLength(34);
               customMsg.setNodeCount((byte) 0x00);
               customMsg.setStationPort(0);
               customMsg.setStationIp1(0);
               customMsg.setStationIp2(0);
               //55命令表示成功，可以进行语音传输
               if ("2008".equals(cmd)) {
                   switch (result) {
                       case "55":
                           customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);  //成功  //55命令表示成功，可以进行语音传输
                           customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_CALL);
                           responseData.setCustomMsg(customMsg);
                           SingletonClient.getSingletonClient().sendCmd(responseData);
                           break;
                       case "88":
                           customMsg.setResult((byte) 0x22);   //拒接
                           customMsg.setNdName(0x2005);
                           responseData.setCustomMsg(customMsg);
                           SingletonClient.getSingletonClient().sendCmd(responseData);
                           break;
                       case "99":
                           Channel channel = VoiceChannelMap.getChannelByName("channel");
                           channel.flush();
                           channel.close();
                           channel.closeFuture();
                           VoiceChannelMap.removeChannelByName("channel");//挂断
                           break;
                       case "33":
                           ProcessRealTimeVoice.checkOnline(staffId);  //上面呼叫下面查询：  返回55成功   66终端语音占线，77终端不在线
                           break;
                       case "44":
                           customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_SEARCH);
                           customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_CALL);   //上面呼叫下面
                           responseData.setCustomMsg(customMsg);
                           SingletonClient.getSingletonClient().sendCmd(responseData);
                           break;
                   }
               }

           }*/





       }

        //群发消息
      /*  for (WebsocketServer item : webSocketSet) {
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
    public  static void sendInfo(String message) throws IOException {
        log.info(message);
        for (WebsocketServer item : webSocketSet) {
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
        WebsocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebsocketServer.onlineCount--;
    }
}
