package com.cst.xinhe.ws.push.service.client.callback;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.ws.push.service.client.VoiceMonitorServerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 09:47
 **/
@Component
public class VoiceMonitorServerClientFallBak implements VoiceMonitorServerClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String send() {
        logger.error(ResultUtil.jsonToStringSuccess());
        return null;
    }
}
