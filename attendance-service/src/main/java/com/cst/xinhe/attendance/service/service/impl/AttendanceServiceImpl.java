package com.cst.xinhe.attendance.service.service.impl;

import com.cst.xinhe.attendance.service.service.AttendanceRulesGenerator;
import com.cst.xinhe.attendance.service.service.AttendanceService;
import com.cst.xinhe.attendance.service.service.StaffOrganizationService;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dao.attendance.AttendanceMapper;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRuleExample;
import com.cst.xinhe.persistence.model.attendance.TimeStandard;
import com.cst.xinhe.persistence.vo.req.AttendanceParamsVO;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.persistence.vo.resp.AttendanceInfoVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/12/17/10:26
 */
@Service
public class AttendanceServiceImpl implements AttendanceService, AttendanceRulesGenerator {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;

    @Resource
    private TimeStandardMapper timeStandardMapper;
    @Resource
    AttendanceMapper attendanceMapper;

    @Resource
    private StaffOrganizationService staffOrganizationService;

    @Override
    public int addTimeStandardInfo(TimeStandardVO standardVO) throws ParseException {
        TimeStandard timeStandard = new TimeStandard();

        timeStandard.setTimeStandardName(standardVO.getTimeStandardName());
        timeStandard.setUpdateTime(standardVO.getUpdateTime());
        timeStandard.setCreateTime(standardVO.getCreateTime());
        timeStandard.setUserId(standardVO.getUserId());
        timeStandard.setElasticTime(standardVO.getElasticTime());

        timeStandard.setFlag(standardVO.getFlag());
        timeStandard.setLeaveEarlyTime(standardVO.getLeaveEarlyTime());
        timeStandard.setLateTime(standardVO.getLateTime());
        timeStandard.setOverTime(standardVO.getOverTime());
        timeStandard.setSeriousLateTime(standardVO.getSeriousLateTime());
        timeStandard.setSeriousLeaveEarlyTime(standardVO.getSeriousLeaveEarlyTime());
        timeStandard.setSeriousTime(standardVO.getSeriousTime());

        timeStandard.setTimeStandardDesc(standardVO.getTimeStandardDesc());
        timeStandard.setUpdateTime(standardVO.getUpdateTime());

        String start = "2018-01-01 " + standardVO.getEndTime() + ":00";
        String end = "2018-01-01 " +standardVO.getEndTime() + ":00";

        timeStandard.setEndTime(DateConvert.convertStringToDate(start,19));
        timeStandard.setStartTime(DateConvert.convertStringToDate(end,19));
        return timeStandardMapper.insertVo(standardVO);
    }

    @Override
    public int modifyTimeStandardInfo(TimeStandardVO standardVO) throws ParseException {
        TimeStandard timeStandard = new TimeStandard();

        timeStandard.setTimeStandardName(standardVO.getTimeStandardName());
        timeStandard.setUpdateTime(standardVO.getUpdateTime());
        timeStandard.setCreateTime(standardVO.getCreateTime());
        timeStandard.setUserId(standardVO.getUserId());
        timeStandard.setElasticTime(standardVO.getElasticTime());

        timeStandard.setTimeStandardId(standardVO.getTimeStandardId());
        timeStandard.setFlag(standardVO.getFlag());
        timeStandard.setLeaveEarlyTime(standardVO.getLeaveEarlyTime());
        timeStandard.setLateTime(standardVO.getLateTime());
        timeStandard.setOverTime(standardVO.getOverTime());
        timeStandard.setSeriousLateTime(standardVO.getSeriousLateTime());
        timeStandard.setSeriousLeaveEarlyTime(standardVO.getSeriousLeaveEarlyTime());
        timeStandard.setSeriousTime(standardVO.getSeriousTime());

        timeStandard.setTimeStandardDesc(standardVO.getTimeStandardDesc());
        timeStandard.setUpdateTime(standardVO.getUpdateTime());

        String start = "2018-01-01 " + standardVO.getStartTime() + ":00";
        String end = "2018-01-01 " +standardVO.getEndTime() + ":00";

        timeStandard.setEndTime(DateConvert.convertStringToDate(start,19));
        timeStandard.setStartTime(DateConvert.convertStringToDate(end,19));
        return timeStandardMapper.updateByPrimaryKey(timeStandard);
    }

    @Transactional
    @Override
    public int deleteTimeStandard(Integer[] ids) {
        int count = 0;
        for (Integer id : ids){
            int res = timeStandardMapper.deleteByPrimaryKey(id);
            if (res == 1){
                count++;
            }
        }
        return count;
    }

    @Override
    public Page getTimeStandardByParams(Map<String, Object> params) {
        String timeStandardName =(String) params.get("timeStandardName");
        Time startTime =(Time) params.get("startTime");
        Page page = PageHelper.startPage((Integer) params.get("startPage"),(Integer) params.get("pageSize"));
        List<TimeStandardVO> standardList = timeStandardMapper.selectTimeStandardInfo(timeStandardName,startTime);
        return page;
    }

    @Override
    public Integer addAttendance(Attendance attendance) {
        return attendanceMapper.insertSelective(attendance);
    }

    @Override
    public Attendance findAttendanceByStaffIdAndEndTimeIsNull(Integer staffId) {

        return attendanceMapper.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
    }

    @Override
    public Integer updateAttendance(Attendance attendance) {

        return attendanceMapper.updateByPrimaryKeySelective(attendance);
    }

    @Override
    public Page findAttendanceInfo(AttendanceParamsVO attendanceParamsVO) {
        if (null != attendanceParamsVO.getOrgId() && 0 != attendanceParamsVO.getOrgId()){
            List<Integer> list = staffOrganizationService.findSonIdsByDeptId(attendanceParamsVO.getOrgId());
            if (list != null && !list.isEmpty())
                attendanceParamsVO.setStaffIdOfList(list);
        }
        Page page = PageHelper.startPage(attendanceParamsVO.getStartPage(),attendanceParamsVO.getPageSize());
        List<AttendanceInfoVO> infoVOList = attendanceMapper.selectAttendanceInfoByParams(attendanceParamsVO);

        return page;
    }

    @Override
    public TimeStandardVO getTimeStandardByStaffId(Integer staffId) {
        TimeStandardVO timeStandard =  timeStandardMapper.selectTimeStandardInfoByStaffId(staffId);
        if (null != timeStandard){
            return timeStandard;
        }else {
            throw new RuntimeServiceException(ErrorCode.NO_BINDING_TIME_STANDARD);
        }
    }

    @Override
    public void generatorRules(Date currentDate,Integer count) throws ParseException {
        List<Map<String, Object>>  mapList = staffMapper.selectStaffAttendanceRules();
        // 获取年月日、字符串类型
        String s_yyyyMMdd = DateFormat.getDateInstance().format(currentDate);
        for (Map<String, Object> item: mapList){
            StaffAttendanceRealRuleExample staffAttendanceRealRuleExample = new StaffAttendanceRealRuleExample();
            StaffAttendanceRealRuleExample.Criteria criteria = staffAttendanceRealRuleExample.createCriteria();
            Integer staffId = (Integer) item.get("staffId");
            Date strStartTime = (Date)item.get("startTime");
            Date strEndTime = (Date)item.get("endTime");
            long t_startTime = strStartTime.getTime();
            long t_endTime = strEndTime.getTime();
            String startTime = DateFormat.getTimeInstance().format(strStartTime);
            String endTime = DateFormat.getTimeInstance().format(strEndTime);
            StringBuffer realRuleOfStartTime = new StringBuffer();
            StringBuffer realRuleOfEndTime = new StringBuffer();
            if (t_startTime > t_endTime){
                // 结束时间加一天
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.getTime();
                String t_yyyyMMdd = DateFormat.getDateInstance().format(c.getTime());
                realRuleOfStartTime.append(s_yyyyMMdd).append(" ").append(startTime);
                realRuleOfEndTime.append(t_yyyyMMdd).append(" ").append(endTime);
            }else{
                realRuleOfStartTime.append(s_yyyyMMdd).append(" ").append(startTime);
                realRuleOfEndTime.append(s_yyyyMMdd).append(" ").append(endTime);
            }
            criteria.andStaffIdEqualTo(staffId);
            StaffAttendanceRealRule staffAttendanceRealRule = new StaffAttendanceRealRule();
            staffAttendanceRealRule.setStaffId(staffId);
            staffAttendanceRealRule.setRealRuleStartTime(DateFormat.getDateTimeInstance().parse(realRuleOfStartTime.toString()));
            staffAttendanceRealRule.setRealRuleEndTime(DateFormat.getDateTimeInstance().parse(realRuleOfEndTime.toString()));
            Long count1 = staffAttendanceRealRuleMapper.countByExample(staffAttendanceRealRuleExample);
            if (count1 == 0){
                // insert
                staffAttendanceRealRuleMapper.insert(staffAttendanceRealRule);
            }else {
                //update
                staffAttendanceRealRuleMapper.updateByExampleSelective(staffAttendanceRealRule,staffAttendanceRealRuleExample);
            }

        }
    }

    @Override
    public StaffAttendanceRealRule findRulesTimeByStaffId(Integer staffId) {
        return staffAttendanceRealRuleMapper.selectByPrimaryKey(staffId);
    }



    @Override
    public List<HashMap<String, Object>> getAttendanceStaff(List<Integer> deptIds, String staffName) {
        return staffAttendanceRealRuleMapper.getAttendanceStaff(deptIds,staffName);
    }

    @Override
    public List<HashMap<String, Object>> getUnAttendanceStaff(List<Integer> deptIds, String staffName) {
        Date nowDate = new Date();
        return staffAttendanceRealRuleMapper.getUnAttendanceStaff(nowDate,deptIds,staffName);
    }

    @Override
    public List<HashMap<String, Object>> getOverTimeStaff(List<Integer> deptIds, String staffName) {
        return staffAttendanceRealRuleMapper.getOverTimeStaff(deptIds,staffName);
    }

    @Override
    public List<HashMap<String, Object>> getSeriousTimeStaff(List<Integer> deptIds, String staffName) {
        return staffAttendanceRealRuleMapper.getSeriousTimeStaff(deptIds,staffName);
    }


    @Override
    public Integer getUnAttendanceDept(List<Integer> deptIds) {
        Date nowDate = new Date();
        return staffAttendanceRealRuleMapper.getUnAttendanceDept(nowDate,deptIds);
    }

    @Override
    public Integer getAttendanceDept(List<Integer> deptIds) {
        return staffAttendanceRealRuleMapper.getAttendanceDept(deptIds);
    }

    @Override
    public Integer getOverTimeDept(List<Integer> deptIds) {
        return staffAttendanceRealRuleMapper.getOverTimeDept(deptIds);
    }

    @Override
    public Integer getSeriousTimeDept(List<Integer> deptIds) {
        return staffAttendanceRealRuleMapper.getSeriousTimeDept(deptIds);
    }
}
