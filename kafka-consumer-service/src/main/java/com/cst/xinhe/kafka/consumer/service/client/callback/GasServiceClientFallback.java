package com.cst.xinhe.kafka.consumer.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.kafka.consumer.service.client.GasServiceClient;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 10:47
 **/
@Component
public class GasServiceClientFallback implements GasServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public GasWSRespVO findGasInfoByStaffIdAndTerminalId(Integer terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
