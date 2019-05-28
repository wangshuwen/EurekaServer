package com.cst.xinhe.voice.monitor.server.ws;

import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.voice.monitor.server.channel.VoiceChannelMap;
import com.cst.xinhe.voice.monitor.server.context.SpringContextUtil;
import com.cst.xinhe.voice.monitor.server.process.ProcessRtVoice;
import com.cst.xinhe.voice.monitor.server.service.VoiceMonitorService;
import com.cst.xinhe.voice.monitor.server.service.impl.VoiceMonitorServiceImpl;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 11:37
 **/
@ServerEndpoint(value = "/voiceStatusWebSocket")
@Component
public class WSVoiceStatus {


    private VoiceMonitorService voiceMonitorService;
    public WSVoiceStatus() {
        this.voiceMonitorService = SpringContextUtil.getBean(VoiceMonitorServiceImpl.class);
    }

    private static final Logger log = LoggerFactory.getLogger(WSVoiceStatus.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WSVoiceStatus> webSocketSet = new CopyOnWriteArraySet<>();

    //处理实时语音
//    @Resource
//    private ProcessRealTimeVoice processRealTimeVoice;
//    = new ProcessRealTimeVoice();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        //房间状态设置为有人，属于可呼叫状态
        ProcessRtVoice.openRoom();
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
        ProcessRtVoice.closeRoom();
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     *下面呼叫上面：
     * 后端发送：cmd：2008  result：44  ipPort
     * 前端返回：cmd:  2008  result：55成功，88拒接，99挂断
     *
     * 上面呼叫下面：
     * 前端发送：cmd：2008  result：33 查询   ipPort
     * 后端返回：cmd：2008  result：55成功   66终端语音占线，77终端不在线
     * 前端发送：cmd：2008  result：44呼叫     ipPort
     * 后端返回：cmd:  2008  result：22成功，88拒接，99挂断
     * @param message 浏览器发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        //封装数据通过协议传递到终端
        ResponseData responseData = new ResponseData();
        RequestData customMsg = new RequestData();

        String cmd = "";
        String ipPort = "";
        String result = "";
        String staffId = "";
        log.info("来自客户端的消息:" + message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        //将json字符串转成json对象后遍历键值对
        Map<String, Object> map = jsonObject;
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
                staffId = Integer.toString((int)entry.getValue());
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
//                    SingletonClient.getSingletonClient().sendCmd(responseData);
                    voiceMonitorService.sendDataToTerminalMonitorServer(responseData);
                    break;
                case "88":
                    customMsg.setResult((byte) 0x22);   //拒接
                    customMsg.setNdName(0x2005);
                    responseData.setCustomMsg(customMsg);
//                    SingletonClient.getSingletonClient().sendCmd(responseData);
                    voiceMonitorService.sendDataToTerminalMonitorServer(responseData);
                    break;
                case "99":
                    Channel channel = VoiceChannelMap.getChannelByName("channel");
                    channel.flush();
                    channel.close();
                    channel.closeFuture();
                    VoiceChannelMap.removeChannelByName("channel");//挂断
                    break;
                case "33":
                    ProcessRtVoice.checkOnline(staffId);  //上面呼叫下面查询：  返回55成功   66终端语音占线，77终端不在线
                    break;
                case "44":
                    customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_SEARCH);
                    customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_CALL);   //上面呼叫下面
                    responseData.setCustomMsg(customMsg);
//                    SingletonClient.getSingletonClient().sendCmd(responseData);
                    voiceMonitorService.sendDataToTerminalMonitorServer(responseData);
                    break;
            }


        }

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
        for (WSVoiceStatus item : webSocketSet) {
                item.sendMessage(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WSVoiceStatus.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WSVoiceStatus.onlineCount--;
    }


}
