package com.cst.xinhe.voice.monitor.server.service;

import com.cst.xinhe.common.netty.data.response.ResponseData;

public interface VoiceMonitorService {

    String sendDataToTerminalMonitorServer(ResponseData responseData);

    Boolean getChannelByName(String ipPort);
}
