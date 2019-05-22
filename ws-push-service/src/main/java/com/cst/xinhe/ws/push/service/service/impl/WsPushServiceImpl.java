package com.cst.xinhe.ws.push.service.service.impl;

import com.cst.xinhe.base.context.SpringContextUtil;
import com.cst.xinhe.ws.push.service.service.WsPushService;
import com.cst.xinhe.ws.push.service.ws.WSPersonNumberServer;
import com.cst.xinhe.ws.push.service.ws.WSServer;
import com.cst.xinhe.ws.push.service.ws.WSSiteServer;
import com.cst.xinhe.ws.push.service.ws.WebsocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @program: WsPushServiceImpl
 * @description: websocket 推送
 * @author: lifeng
 * @create: 2019-04-29 09:19
 **/
@Service
public class WsPushServiceImpl implements WsPushService {

    public WsPushServiceImpl() {
    }

    @Override
    public void sendWebsocketServer(String jsonObject) throws IOException {
        WebsocketServer.sendInfo(jsonObject);
    }

    @Override
    public void sendWSSiteServer(String jsonObject) throws IOException {
//        wsSiteServer.sendMessage(jsonObject);
        WSSiteServer.sendInfo(jsonObject);
    }

    @Override
    public void sendWSServer(String jsonObject) throws IOException {
        WSServer.sendInfo(jsonObject);
    }

    @Override
    public void sendWSPersonNumberServer(String jsonObject) throws IOException {
        WSPersonNumberServer.sendInfo(jsonObject);
    }

    @Override
    public void sendInfo(String jsonObject) {
        try {
            WebsocketServer.sendInfo(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendWSBigDataServer(String jsonObject) {

    }
}
