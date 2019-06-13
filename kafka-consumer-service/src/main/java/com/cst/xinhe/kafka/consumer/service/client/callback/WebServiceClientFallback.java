package com.cst.xinhe.kafka.consumer.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.kafka.consumer.service.client.WebServiceClient;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 09:46
 **/
@Component
public class WebServiceClientFallback implements WebServiceClient {


    @Override
    public List<RangSetting> findRangByType(int i) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public String findRangUrlByLevelDataId(int contrastParameter) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }


    @Override
    public double findFrequencyByStationId(Integer stationId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return 0;
    }

    @Override
    public BaseStation findBaseStationByNum(Integer basestationid) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<String, Object> findBaseStationByType(int i) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Integer> getSonIdsById(Integer zoneId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public List<BaseStation> findBaseStationByZoneIds(List<Integer> zoneIds) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public void updateByStationNumSelective(BaseStation baseStation) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }

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
    public String getDeptNameByGroupId(Integer group_id) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<StaffOrganization> getOneSonByParent(int i) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Integer> findSonIdsByDeptId(Integer id) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Staff> findStaffByTimeStandardId(Integer item) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Integer> findAllStaffByGroupId(Integer deptId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));


        return null;
    }

    @Override
    public List<Staff> selectStaffListByJobType(Integer jobType) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Staff> selectStaffByLikeName(String staffName) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public StaffJob selectStaffJobByJobId(Integer jobId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<Integer, List<Staff>> findStaffByTimeStandardIds(Integer[] ids) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public GasWSRespVO findStaffNameByTerminalId(Integer terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public StaffTerminalRelation findNewRelationByTerminalId(Integer uploadId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public Map<String, Object> findStaffGroupAndDeptByStaffId(Integer staffId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public void updateIpInfoByTerminalId(TerminalUpdateIp terminalUpdateIp) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void updateIpInfoByStationId(TerminalUpdateIp terminalUpdateIp) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));


    }

    @Override
    public StaffTerminalRelation findNewRelationByStaffId(Integer staffId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public void deleteLeLackElectricByLackElectric(LackElectric lackElectric) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void addMalfunction(Malfunction malfunction) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));


    }

    @Override
    public void updateLackElectric(LackElectric lackElectric) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public Long getLackElectricList() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public Map<String, Object> getCountMalfunction() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public GasWSRespVO findGasInfoByStaffIdAndTerminalId(Integer terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
    @Override
    public void insertChatMsgSelective(ChatMsg chatMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void updateChatMegStatusBySeqId(ChatMsg chatMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }

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
    public Long getAttendanceStaffCount() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public Integer getUnAttendanceDept(Date date) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
