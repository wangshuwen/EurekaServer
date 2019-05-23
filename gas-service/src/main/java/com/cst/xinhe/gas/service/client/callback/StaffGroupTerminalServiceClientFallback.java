package com.cst.xinhe.gas.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.gas.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 16:28
 **/
@Component
public class StaffGroupTerminalServiceClientFallback implements StaffGroupTerminalServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Map<String, Object> findStaffIdByTerminalId(int terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public TerminalUpdateIp findTerminalIdByIpAndPort(String terminalIp, int port) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public HashMap<String, Object> getDeptAndGroupNameByStaffId(Integer staffId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Staff findStaffById(Integer staffid) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Map<String,Object>>  findStaffByIds(List<Integer> staffIds) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }


    @Override
    public String getDeptNameByGroupId(Integer group_id) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<Integer, Map<String, Object>> findGroupNameByStaffId(Set<Integer> list1) {
        return null;
    }
}
