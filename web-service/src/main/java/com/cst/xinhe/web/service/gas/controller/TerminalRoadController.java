package com.cst.xinhe.web.service.gas.controller;


import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.dao.attendance.AttendanceMapper;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.web.service.gas.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.web.service.gas.service.TerminalRoadService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/11/30/10:16
 */
@RestController
@Api(value = "TerminalRoadController", tags = "终端路线")
@RequestMapping("terminalRoad/")
public class TerminalRoadController {
    @Resource
   private TerminalRoadService terminalRoadService;

    @Resource
    private AttendanceMapper attendanceMapper;
    @GetMapping("findTimeList")
    @ApiOperation(value = "获取员工id获取历史轨迹时间列表", notes = "根据员工id查询详细时间列表")
    public String findTimeList(
            @RequestParam(name ="staffId",required = false) int staffId,@RequestParam(name ="currentMonth",required = false) String currentMonth) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");


        String startTime="";
        String endTime="";
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH)+1;
        int day=instance.get(Calendar.DAY_OF_MONTH);

        if(currentMonth==null||"".equals(currentMonth)){
            endTime=""+year+"-"+month+"-"+day;
            startTime=""+year+"-"+month+"-"+1;
        }else{
            try {
                instance.setTime(simpleDate.parse(currentMonth));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int maxDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
            startTime=currentMonth+"-"+1;
            endTime=currentMonth+"-"+maxDay;
        }
        List<String> list = terminalRoadService.findTimeList(staffId,startTime,endTime);
        return list.size() > 0 ?ResultUtil.jsonToStringSuccess(list):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("findNowSiteByStaffId")
    @ApiOperation(value = "根据员工id查找员工现在的位置", notes = "")
    public String findNowSiteByStaffId(
            @RequestParam(name ="staffId",required = false) int staffId) {
        TerminalRoad terminalRoad =terminalRoadService.findNowSiteByStaffId(staffId);

        return ResultUtil.jsonToStringSuccess(terminalRoad);
    }



    @GetMapping("findTerminalRoadByTime")
    @ApiOperation(value = "根据员工id和时间获取历史轨迹列表", notes = "根据时间查询轨迹列表")
    public String findTerminalRoadByTime(
            @RequestParam(name ="staffId",required = false) int staffId,
            @RequestParam(name = "currentTime", required = false) String currentTime,
            @RequestParam(name = "pageSize",required = false,defaultValue = "12")Integer pageSize,
            @RequestParam(name = "startPage",required = false,defaultValue = "1")Integer startPage
            ) throws ParseException {
        if(currentTime==null||"".equals(currentTime))
           return  ResultUtil.jsonToStringSuccess();

        List<Map<String,Object>> list=terminalRoadService.findTerminalRoadByTime(staffId,currentTime,pageSize,startPage);
        return !list.isEmpty() ? ResultUtil.jsonToStringSuccess(list): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("findTerminalRoadByInOreTime")
    @ApiOperation(value = "根据员工id和入矿时间获取入矿以后的历史轨迹列表", notes = "根据时间查询轨迹列表")
    public String findTerminalRoadByInOreTime(
            @RequestParam(name ="staffId",required = false) Integer staffId,
            @RequestParam(name ="trackStartTime",required = false) String trackStartTime,
            @RequestParam(name ="trackEndTime",required = false) String trackEndTime,
            @RequestParam(name = "limit",required = false,defaultValue = "12")Integer pageSize,
            @RequestParam(name = "page",required = false,defaultValue = "1")Integer startPage
            ) {
//        Attendance attendance = attendanceServiceClient.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
        Attendance attendance = attendanceMapper.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
        Date inOre =null;
        if(null != attendance){
            inOre = attendance.getInOre();
        }
        String startTime = "";
        String endTime = "";

//            try {
                if (null != trackStartTime && !"".equals(trackStartTime) && !"0".equals(trackStartTime))
                startTime = trackStartTime;
                if (null != trackEndTime && !"".equals(trackEndTime) && !"0".equals(trackEndTime))
                endTime = trackEndTime;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

        org.springframework.data.domain.Page<GasPositionEntity> list=terminalRoadService.findTerminalRoadByInOreTime(staffId, inOre, startTime, endTime,startPage,pageSize);
        return list.getSize() > 0 ?ResultUtil.jsonToStringSuccess(list): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }




}
