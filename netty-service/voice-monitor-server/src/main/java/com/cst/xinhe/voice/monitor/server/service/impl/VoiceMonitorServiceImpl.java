package com.cst.xinhe.voice.monitor.server.service.impl;

import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.voice.monitor.server.client.TerminalMonitorClient;
import com.cst.xinhe.voice.monitor.server.process.ProcessRtVoice;
import com.cst.xinhe.voice.monitor.server.service.VoiceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cst.xinhe.voice.monitor.server.process.ProcessRtVoice.getRoomStatus;
import static com.cst.xinhe.voice.monitor.server.process.ProcessRtVoice.isBusyLine;

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

    @Override
    public void checkSendCheckOnline(RequestData requestData) {
        ProcessRtVoice.getProcessRealTimeVoice().sendCheckOnline(requestData);
    }

    @Override
    public void sendCallInfo(RequestData requestData) {
        ProcessRtVoice.getProcessRealTimeVoice().sendCallInfo(requestData);
    }
}
