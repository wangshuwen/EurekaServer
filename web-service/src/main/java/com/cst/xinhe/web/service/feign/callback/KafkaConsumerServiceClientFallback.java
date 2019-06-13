package com.cst.xinhe.web.service.feign.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.web.service.feign.client.KafkaConsumerServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/6/12/9:59
 */
@Component
public class KafkaConsumerServiceClientFallback implements KafkaConsumerServiceClient {
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void overmanedAlarm(Integer type, Integer staffId, Integer areaId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }
}
