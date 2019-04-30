package com.cst.xinhe.voice.monitor.server.server;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.voice.monitor.server.service.VoiceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 14:33
 **/
@RestController
@RequestMapping("voice-monitor-server/")
public class VoiceMonitorController {

    @Autowired
    VoiceMonitorService voiceMonitorService;

    @PostMapping("checkSendCheckOnline")
    public String checkSendCheckOnline(@RequestBody RequestData requestData){
        voiceMonitorService.checkSendCheckOnline(requestData);
        return ResultUtil.jsonToStringSuccess();
    }

    @PostMapping("sendCallInfo")
    public String sendCallInfo(@RequestBody RequestData requestData){
        voiceMonitorService.sendCallInfo(requestData);
        return ResultUtil.jsonToStringSuccess();
    }

    @PostMapping("sendInfoToWs")
    public String sendInfoToWs(@RequestBody String keyStr){
        voiceMonitorService.sendInfoToWs(keyStr);
        return ResultUtil.jsonToStringSuccess();
    }

}
