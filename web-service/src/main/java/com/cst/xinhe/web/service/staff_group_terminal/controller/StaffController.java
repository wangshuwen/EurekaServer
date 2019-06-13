package com.cst.xinhe.web.service.staff_group_terminal.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeWebException;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dto.staff.StaffInfoDto;
import com.cst.xinhe.persistence.vo.req.StaffInfoVO;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @ClassName StaffController
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 10:07
 * @Vserion v0.0.1
 */
@RestController
@Api(value = "StaffController", tags = {"矿下员工信息操作接口"})
public class StaffController {


    @Resource
    private StaffService staffService;


    @Resource
    private StaffOrganizationService staffOrganizationService;


    @PostMapping("staff/addStaff")
    @ApiOperation(value = "矿下员工信息录入", notes = "对员工信息（必要信息录入）通过页面输入信息")
    public String addStaffInfo(@RequestBody StaffInfoVO staffInfoVO) {

        int res = staffService.addStaff(staffInfoVO);

        return res == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);

    }

    @PostMapping("staff/addStaffs")
    @ApiOperation(value = "解析Excel表格实现批量矿下员工信息录入", notes = "批量导入员工信息")
    public String addStaffsInfo(@RequestBody List<Map<String, Object>> mapOfList) throws ParseException {
        List<StaffInfoVO> staffInfoVOS = new ArrayList<>();
        StaffInfoVO staffInfoVO;
        try {
            for (Map<String, Object> item: mapOfList){
                staffInfoVO = new StaffInfoVO();
                Object staffName = item.get("姓名");
                Object staffSex = item.get("性别");
                Object staffIdCard = item.get("身份证号");
                Object staffBirthday = item.get("出生日期");
                Object staffPhone = item.get("电话");
                Object staffGroupId = item.get("分组");
                Object staffIsPerson = item.get("类型");
                Object staffJobId = item.get("工作内容");
                staffInfoVO.setStaffName(String.valueOf(staffName));
                staffInfoVO.setStaffBirthday(DateConvert.convertStampToDate(String.valueOf(staffBirthday),10));
                staffInfoVO.setStaffSex((Integer)staffSex);
                staffInfoVO.setStaffIdCard(String.valueOf(staffIdCard));
                staffInfoVO.setStaffPhone(String.valueOf(staffPhone));
                staffInfoVO.setGroupId((Integer)staffGroupId);
                staffInfoVO.setIsPerson((Integer)staffIsPerson);
                staffInfoVO.setJobId((Integer)staffJobId);
                staffInfoVOS.add(staffInfoVO);
            }
        } catch (Exception e){
            throw new RuntimeWebException(ErrorCode.DATA_TYPE_IS_ERROR);
        }
        int count = staffService.addStaffs(staffInfoVOS);
        if (staffInfoVOS.size() == count){
            return ResultUtil.jsonToStringSuccess();
        }else {
            return ResultUtil.jsonToStringError(ResultEnum.FAILED);
        }
    }

    @Transactional
    @DeleteMapping("staff/delStaff")
    @ApiOperation(value = "根据员工ID删除员工的基础信息", notes = "可以批量或单一删除信息，参数为数组形式")
    public String deleteStaffByIds(@RequestParam Integer[] ids) {
        int len = ids.length;
        int res = staffService.deleteStaffByIds(ids);
        return len == res ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @PutMapping("staff/updateStaff")
    @ApiOperation(value = "更新员工的基础信息", notes = "通过员工的ID更新信息")
    public String updateStaffInfo(@RequestBody StaffInfoVO staffInfoVO) {

        int res = staffService.updateStaffInfo(staffInfoVO);
        return res == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @GetMapping("staff/getStaffByPage")
    @ApiOperation(value = "获取员工基础信息", notes = "获取员工的信息，模糊查询")
    public String getAllStaffInfoByPage(@RequestParam(required = false, defaultValue = "8", name = "limit") Integer pageSize
            , @RequestParam(required = false, defaultValue = "1", name = "page") Integer startPage
            , @RequestParam(name = "staffName", required = false) String staffName
            ,@RequestParam(name = "orgId", required = false) Integer orgId
            ,@RequestParam(name = "isPerson", required = false) Integer isPerson) {
        PageInfo<StaffInfoDto> staffList = staffService.getStaffInfoByStaff(staffName, startPage, pageSize, orgId, isPerson);
        return staffList.getSize() > 0 ? ResultUtil.jsonToStringSuccess(staffList): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("staff/getContacts")
    @ApiOperation(value = "获取员工基础信息多条件查询", notes = "获取员工的信息，多条件查询，以及员工姓名的模糊查询")
    public String getStaffContacts(@RequestParam(required = false, name = "staffId") Integer staffId,
                                   @RequestParam(required = false, name = "staffName") String staffName,
                                   @RequestParam(required = false, name = "groupId") Integer groupId,
                                   @RequestParam(required = false, name = "deptId") Integer deptId,
                                   @RequestParam(required = false, name = "limit", defaultValue = "5") Integer pageSize,
                                   @RequestParam(required = false, name = "page", defaultValue = "1") Integer startPage) {
        Page page = staffService.findContacts(staffId, staffName, groupId, deptId, pageSize, startPage);
        List list = page.getResult();
        for (Object itemOfMap: list){
            Map<String, Object> map = (Map<String, Object>) itemOfMap;
            Integer t_deptId = (Integer)map.get("deptId");
            String t_deptName = staffOrganizationService.getDeptNameByGroupId(t_deptId);
            map.put("deptName", t_deptName);
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ?ResultUtil.jsonToStringSuccess(pageInfo): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @PutMapping("staff/bindingAttendanceBaseStation/{stationId}")
    @ApiOperation(value = "给制定的部门，分组，或者人员做一次绑定打卡基站ID")
    public String bindingAttendanceBaseStation(
            @PathVariable(name = "stationId", required = false) Integer stationId,
            @RequestParam(name = "deptId", required = false) Integer deptId,
            @RequestParam(name = "groupId", required = false) Integer groupId,
            @RequestParam(name = "staffIds", required = false) Integer[] staffIds) {

        List<Integer> ids = Collections.synchronizedList(new ArrayList<>());
        if (deptId != null && !"".equals(deptId)) {
            // 查询部门下所有员工
            ids = staffService.findAllStaffByDeptId(deptId);
        }
        if (groupId != null && !"".equals(groupId)) {
            //查询分组下所有员工
            ids = staffService.findAllStaffByGroupId(groupId);
        }
        if (null != staffIds && staffIds.length > 0) {
            //根据员工id更新信息
            ids = Arrays.asList(staffIds);
        }
        if (!ids.isEmpty()) {
            try {
                staffService.updateBindingBaseStation(ids, stationId);
                return ResultUtil.jsonToStringSuccess();
            } catch (RuntimeWebException exception) {
                return ResultUtil.jsonToStringError(ResultEnum.BIND_ATTENDANCE_STATION_ERROR);
            }
        } else {
            return ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
        }
    }

    @PostMapping("staff/bindingStandard")
    public String bindingStandard(@RequestParam(name = "standardId", required = true)Integer standardId,
                                  @RequestParam(name = "staffIds", required = true)Integer[] staffIds){
        int len = staffIds.length;
        if (len > 0){
                int result = staffService.bindingTimeStandard(staffIds,standardId);
                if (result == len)
                    return ResultUtil.jsonToStringSuccess();
                else {
                    return ResultUtil.jsonToStringError(ResultEnum.FAILED);
                }
            }
        return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
    }


}
