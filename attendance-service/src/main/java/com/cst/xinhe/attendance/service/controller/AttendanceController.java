package com.cst.xinhe.attendance.service.controller;


import com.cst.xinhe.attendance.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.attendance.service.elasticsrearch.entity.EsAttendanceEntity;
import com.cst.xinhe.attendance.service.elasticsrearch.service.EsAttendanceService;
import com.cst.xinhe.attendance.service.service.AttendanceService;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.vo.req.AttendanceParamsVO;
import com.cst.xinhe.persistence.vo.resp.AttendanceInfoVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;


/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/12/17/11:18
 */
@RestController
@Api(value = "AttendanceController", tags = "考勤信息接口")
@RequestMapping("attendance/")
public class AttendanceController {

    @Resource
    private AttendanceService attendanceService;

//    @Resource
//    private StaffOrganizationService staffOrganizationService;

    @Resource
    private EsAttendanceService esAttendanceService;

    @Autowired
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @GetMapping("getInfoByParams")
    @ApiOperation("多条件查询考勤信息")
    public String getAttendanceInfoByParams2332(
            @RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
            @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
            @RequestParam(name = "staffName", required = false) String staffName,
            @RequestParam(name = "orgId", required = false) Integer orgId,
            @RequestParam(name = "timeStandardId", required = false) Integer timeStandardId,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime,
            @RequestParam(name = "currentDate", required = false) String currentDate,
            @RequestParam(name = "jobType", required = false)Integer jobType) throws ParseException {
        AttendanceParamsVO attendanceParamsVO = new AttendanceParamsVO();
        if (null != currentDate && !"".equals(currentDate) && !"0".equals(currentDate))
            attendanceParamsVO.setCurrentDate1(currentDate);
        if (null != endTime && !"".equals(endTime) && !"0".equals(endTime))
            attendanceParamsVO.setEndTime1(endTime);
        attendanceParamsVO.setJobType(jobType);
        attendanceParamsVO.setStaffName(staffName);
        attendanceParamsVO.setOrgId(orgId);
        attendanceParamsVO.setPageSize(pageSize);
        attendanceParamsVO.setStartPage(startPage);
        attendanceParamsVO.setTimeStandardId(timeStandardId);


        if (null != startTime && !"".equals(startTime) && !"0".equals(startTime))
            attendanceParamsVO.setStartTime1(startTime);

        org.springframework.data.domain.Page<EsAttendanceEntity> page = esAttendanceService.searchAttendanceByParams(attendanceParamsVO);

        return  ResultUtil.jsonToStringSuccess(page);
    }



    @GetMapping("getInfoByParams1111")
    @ApiOperation("多条件查询考勤信息")
    public String getAttendanceInfoByParams(
            @RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
            @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
            @RequestParam(name = "staffName", required = false) String staffName,
            @RequestParam(name = "orgId", required = false) Integer orgId,
            @RequestParam(name = "timeStandardId", required = false) Integer timeStandardId,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime,
            @RequestParam(name = "currentDate", required = false) String currentDate,
            @RequestParam(name = "jobType", required = false)Integer jobType) throws ParseException {
        AttendanceParamsVO attendanceParamsVO = new AttendanceParamsVO();
        if (null != currentDate && !"".equals(currentDate) && !"0".equals(currentDate))
            attendanceParamsVO.setCurrentDate(DateConvert.convertStringToDate(currentDate,10));
        if (null != endTime && !"".equals(endTime) && !"0".equals(endTime))
            attendanceParamsVO.setEndTime(DateConvert.convertStringToDate(endTime,10));
        attendanceParamsVO.setJobType(jobType);
        attendanceParamsVO.setOrgId(orgId);
        attendanceParamsVO.setPageSize(pageSize);
        attendanceParamsVO.setStartPage(startPage);
        attendanceParamsVO.setTimeStandardId(timeStandardId);
        attendanceParamsVO.setStaffName(staffName);
        if (null != startTime && !"".equals(startTime) && !"0".equals(startTime))
            attendanceParamsVO.setStartTime(DateConvert.convertStringToDate(startTime,10));
        Page page = attendanceService.findAttendanceInfo(attendanceParamsVO);
        List<AttendanceInfoVO> infoVOList = page.getResult();
        for (AttendanceInfoVO attendanceInfoVO: infoVOList){
            Integer t_orgId = attendanceInfoVO.getOrgId();
            //String orgName = staffOrganizationService.getDeptNameByGroupId(t_orgId);
            String orgName = staffGroupTerminalServiceClient.getDeptNameByGroupId(t_orgId);
            attendanceInfoVO.setDeptName(orgName);
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("getAttendanceDept")
    @ApiOperation("获取现在矿下考勤的员工的部门和每个部门的人数")
    public String getAttendanceDept(){
        List<Map<String,Object>> resultList = new ArrayList<>();
        //List<StaffOrganization> list = staffOrganizationService.getOneSonByParent(1);
        List<StaffOrganization> list  = staffGroupTerminalServiceClient.getOneSonByParent(1);
        for (StaffOrganization org : list) {
           // List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(org.getId());
            List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(org.getId());
            //根据部门id的到该部门下的所有员工数
            Integer count= attendanceService.getAttendanceDept(deptIds);
            Map<String, Object> map = new HashMap<>();
            map.put("deptName",org.getName());
            map.put("count",count);
            map.put("deptId",org.getId());
            resultList.add(map);
        }
        return !resultList.isEmpty() ? ResultUtil.jsonToStringSuccess(resultList) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }
    @GetMapping("getUnAttendanceDept")
    @ApiOperation("获取现在矿下未考勤的员工的部门和每个部门的人数")
    public String getUnAttendanceDept(){
        List<Map<String,Object>> resultList = new ArrayList<>();
//        List<StaffOrganization> list= staffOrganizationService.getOneSonByParent(1);
        List<StaffOrganization> list= staffGroupTerminalServiceClient.getOneSonByParent(1);
        for (StaffOrganization org : list) {
//            List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(org.getId());
            List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(org.getId());
            //根据部门id的到该部门下的所有员工数
            Integer count= attendanceService.getUnAttendanceDept(deptIds);
            Map<String, Object> map = new HashMap<>();
            map.put("deptName",org.getName());
            map.put("count",count);
            map.put("deptId",org.getId());
            resultList.add(map);
        }

        return !resultList.isEmpty() ? ResultUtil.jsonToStringSuccess(resultList) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }
    @GetMapping("getOverTimeDept")
    @ApiOperation("获取超时未上井的员工部门和每个部门的人数")
    public String getOverTimeDept(){
        List<Map<String,Object>> resultList = new ArrayList<>();
//        List<StaffOrganization> list= staffOrganizationService.getOneSonByParent(1);
        List<StaffOrganization> list= staffGroupTerminalServiceClient.getOneSonByParent(1);
        for (StaffOrganization org : list) {
//            List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(org.getId());
            List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(org.getId());
            //根据部门id的到该部门下的所有员工数
            Integer count= attendanceService.getOverTimeDept(deptIds);
            Map<String, Object> map = new HashMap<>();
            map.put("deptName",org.getName());
            map.put("count",count);
            map.put("deptId",org.getId());
            resultList.add(map);
        }


        return !resultList.isEmpty() ? ResultUtil.jsonToStringSuccess(resultList) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }
    @GetMapping("getSeriousTimeDept")
    @ApiOperation("获取严重超时未上井的员工部门和每个部门的人数")
    public String getSeriousTimeDept(){
        List<Map<String,Object>> resultList = new ArrayList<>();
//        List<StaffOrganization> list= staffOrganizationService.getOneSonByParent(1);
        List<StaffOrganization> list= staffGroupTerminalServiceClient.getOneSonByParent(1);
        for (StaffOrganization org : list) {
//            List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(org.getId());
            List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(org.getId());
            //根据部门id的到该部门下的所有员工数
            Integer count= attendanceService.getSeriousTimeDept(deptIds);
            Map<String, Object> map = new HashMap<>();
            map.put("deptName",org.getName());
            map.put("count",count);
            map.put("deptId",org.getId());
            resultList.add(map);
        }

        return !resultList.isEmpty() ? ResultUtil.jsonToStringSuccess(resultList) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }



    @GetMapping("getAttendanceStaff")
    @ApiOperation("获取现在矿下考勤的所有员工")
    public String getAttendanceStaff(
            @RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
            @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
            @RequestParam(name = "deptId", required = false)Integer deptId,
            @RequestParam(name = "staffName",required = false)String staffName){
        if(deptId==null){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);

        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list= attendanceService.getAttendanceStaff(deptIds,staffName);
        List<HashMap<String,Object>> result = page.getResult();
        for (HashMap<String, Object> map : result) {
            Integer groupId= (Integer) map.get("groupId");
          //  String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
            map.put("deptName",deptName);
            //入矿时间
            Integer staffId = (Integer) map.get("staffId");
            Attendance attendance = attendanceService.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
           if(attendance!=null){
               Date inOre = attendance.getInOre();
               map.put("inOreTime",inOre);
               //封装时长
               long nd = 1000 * 24 * 60 * 60;
               long nh = 1000 * 60 * 60;
               long nm = 1000 * 60;
               long diff = new Date().getTime()-inOre.getTime();
               long day = diff / nd;
               long hour = diff % nd / nh;
               long min = diff % nd % nh / nm;
               map.put("timeLong",day + "天" + hour + "小时" + min + "分钟");
           }
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }
    @GetMapping("getUnAttendanceStaff")
    @ApiOperation("获取应该考勤但没有考勤的员工")
    public String getUnAttendanceStaff(
            @RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
            @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
            @RequestParam(name = "deptId", required = false)Integer deptId,
            @RequestParam(name = "staffName",required = false)String staffName){
        if(deptId==null){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);
        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list= attendanceService.getUnAttendanceStaff(deptIds,staffName);
        List<HashMap<String,Object>> result = page.getResult();
        for (HashMap<String, Object> map : result) {
            Integer groupId= (Integer) map.get("groupId");
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
            map.put("deptName",deptName);
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }
    @GetMapping("getOverTimeStaff")
    @ApiOperation("获取超时未上井的所有员工")
    public String getOverTimeStaff(@RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
                                   @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
                                   @RequestParam(name = "deptId", required = false)Integer deptId,
                                   @RequestParam(name = "staffName",required = false) String staffName){
        if(deptId==null){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);
        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list= attendanceService.getOverTimeStaff(deptIds,staffName);

        List<HashMap<String,Object>> result = page.getResult();
        for (HashMap<String, Object> map : result) {
            Integer groupId= (Integer) map.get("groupId");
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName =  staffGroupTerminalServiceClient.getDeptNameByGroupId(deptId);
            map.put("deptName",deptName);
            //入矿时间
            Integer staffId = (Integer) map.get("staffId");
            Attendance attendance = attendanceService.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
            if(attendance!=null){
                Date inOre = attendance.getInOre();
                map.put("inOreTime",inOre);
                //封装时长
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                long diff = new Date().getTime()-inOre.getTime();
                long day = diff / nd;
                long hour = diff % nd / nh;
                long min = diff % nd % nh / nm;
                map.put("timeLong",day + "天" + hour + "小时" + min + "分钟");
            }


        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }
    @GetMapping("getSeriousTimeStaff")
    @ApiOperation("获取严重超时未上井的所有员工")
    public String getSeriousTimeStaff(@RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
                                      @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
                                      @RequestParam(name = "deptId", required = false)Integer deptId,
                                      @RequestParam(name = "staffName",required = false)String staffName){
        if(deptId==null){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);
        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list= attendanceService.getSeriousTimeStaff(deptIds,staffName);

        List<HashMap<String,Object>> result = page.getResult();
        for (HashMap<String, Object> map : result) {
            Integer groupId= (Integer) map.get("groupId");
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
            map.put("deptName",deptName);
            //入矿时间
            Integer staffId = (Integer) map.get("staffId");
            Attendance attendance = attendanceService.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
            if(attendance!=null){
                Date inOre = attendance.getInOre();
                map.put("inOreTime",inOre);
                //封装时长
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                long diff = new Date().getTime()-inOre.getTime();
                long day = diff / nd;
                long hour = diff % nd / nh;
                long min = diff % nd % nh / nm;
                map.put("timeLong",day + "天" + hour + "小时" + min + "分钟");
            }
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }



}
