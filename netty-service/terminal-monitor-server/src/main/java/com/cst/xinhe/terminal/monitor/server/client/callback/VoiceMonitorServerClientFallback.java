package com.cst.xinhe.terminal.monitor.server.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.terminal.monitor.server.client.VoiceMonitorServerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 13:11
 **/
@Component
public class VoiceMonitorServerClientFallback implements VoiceMonitorServerClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void checkSendCheckOnline(RequestData customMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }
}
