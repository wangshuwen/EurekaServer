package com.cst.xinhe.gas.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.gas.service.client.AttendanceServiceClient;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 09:46
 **/
@Component
public class AttendanceServiceClientFallback implements AttendanceServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public TimeStandardVO getTimeStandardByStaffId(Integer staffId) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public StaffAttendanceRealRule findStaffAttendanceRealRuleById(Integer staffId) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public void updateStaffAttendanceRealRuleById(StaffAttendanceRealRule realRule) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }

    @Override
    public void addAttendance(Attendance attendance) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }

    @Override
    public Attendance findAttendanceByStaffIdAndEndTimeIsNull(Integer staffId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public void updateAttendance(Attendance attendance) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));


    }

    @Override
    public List<HashMap<String, Object>> getAttendanceStaff(List<Integer> deptIds, String staffName) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public Integer getUnAttendanceDept(Date date, List<Integer> deptIds) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
