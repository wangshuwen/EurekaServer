package com.cst.xinhe.voice.monitor.server.service.impl;

import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.voice.monitor.server.client.TerminalMonitorClient;
import com.cst.xinhe.voice.monitor.server.service.VoiceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 14:49
 **/
@Service
public class VoiceMonitorServiceImpl implements VoiceMonitorService {

    @Autowired
    private TerminalMonitorClient terminalMonitorClient;

    @Override
    public String sendDataToTerminalMonitorServer(ResponseData responseData) {
        return terminalMonitorClient.sendResponseData(responseData);
    }

    @Override
    public Boolean getChannelByName(String ipPort) {
        return terminalMonitorClient.getChanelByName(ipPort);
    }
}
