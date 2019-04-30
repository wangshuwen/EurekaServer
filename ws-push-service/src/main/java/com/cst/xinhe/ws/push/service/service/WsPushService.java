package com.cst.xinhe.ws.push.service.service;

import java.io.IOException;

public interface WsPushService {
    void sendWebsocketServer(String jsonObject) throws IOException;

    void sendWSSiteServer(String jsonObject) throws IOException;

    void sendWSServer(String jsonObject) throws IOException;

    void sendWSPersonNumberServer(String jsonObject) throws IOException;

    void sendInfo(String jsonObject);
}
