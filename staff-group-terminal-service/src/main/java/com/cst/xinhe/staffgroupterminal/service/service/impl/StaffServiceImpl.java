package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.staff_terminal_relation.StaffTerminalRelationMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dto.staff.StaffInfoDto;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffExample;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelationExample;
import com.cst.xinhe.persistence.vo.req.StaffInfoVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffOrganizationService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentileRanks;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName StaffServiceImpl
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 10:24
 * @Vserion v0.0.1
 */
@Service
public class StaffServiceImpl implements StaffService {


    @Resource
    private StaffMapper staffMapper;

    @Resource
    private StaffTerminalMapper staffTerminalMapper;

    @Resource
    private StaffOrganizationService staffOrganizationService;

    @Resource
    private StaffJobService staffJobService;

    @Resource
    private StaffTerminalRelationMapper staffTerminalRelationMapper;


    @Override
    public int addStaff(StaffInfoVO staffInfoVO) {

        Staff staff = new Staff();

        staff.setCreateTime(new Date());
        staff.setGroupId(staffInfoVO.getGroupId());
        staff.setIsPerson(staffInfoVO.getIsPerson());
        staff.setStaffBirthday(staffInfoVO.getStaffBirthday());
        staff.setStaffIdCard(staffInfoVO.getStaffIdCard());
        staff.setStaffJobId(staffInfoVO.getJobId());
        staff.setStaffName(staffInfoVO.getStaffName());
        staff.setStaffPhone(staffInfoVO.getStaffPhone());
        staff.setStaffSex(staffInfoVO.getStaffSex());

        int result = staffMapper.insertSelective(staff);

        if (result == 1) {
            if (staffInfoVO.getTerminalId() != null && staffInfoVO.getTerminalId() > 0) {
                staffTerminalMapper.updateTerminalBinding(staff.getStaffId(), staffInfoVO.getTerminalId());
            }
            return 1;
        } else {
            return 0;
        }
    }


    @Override
    @Transactional
    public int deleteStaffByIds(Integer[] ids) {
        int count = 0;
        for (Integer id : ids) {

            int result_b = staffMapper.deleteByPrimaryKey(id);
            boolean flag = staffTerminalMapper.countTerminalNumByStaffId(id);
            if (flag)
                staffTerminalMapper.updateTerminalUnBindingByStaffId(id);
            StaffTerminalRelationExample staffTerminalRelationExample = new StaffTerminalRelationExample();
            StaffTerminalRelationExample.Criteria criteria =  staffTerminalRelationExample.createCriteria();
            criteria.andStaffIdEqualTo(id);
            criteria.andTypeEqualTo(1);
            List<StaffTerminalRelation> staffTerminalRelations = staffTerminalRelationMapper.selectByExample(staffTerminalRelationExample);
            if (null != staffTerminalRelations && staffTerminalRelations.size() == 1){
                Integer staffTerminalRelationId = staffTerminalRelations.get(0).getStaffTerminalRelationId();
                StaffTerminalRelation staffTerminalRelation = new StaffTerminalRelation();
                staffTerminalRelation.setStaffTerminalRelationId(staffTerminalRelationId);
                staffTerminalRelation.setType(0);
//                try {
                    staffTerminalRelation.setUpdateTime(new Date());
//                    staffTerminalRelation.setUpdateTime(DateConvert.convertStampToDate(String.valueOf(System.currentTimeMillis()),19));
//                } catch (Exception e) {
//                    throw new RuntimeServiceException(ErrorCode.DATA_TYPE_IS_ERROR);
//                }
                staffTerminalRelationMapper.updateByPrimaryKeySelective(staffTerminalRelation);
            }
            if (result_b == 1)
                count++;
        }
        return count;
    }

    @Override
    public int updateStaffInfo(StaffInfoVO staffInfoVO) {

        Staff staff = new Staff();

        staff.setStaffName(staffInfoVO.getStaffName());

        staff.setStaffId(staffInfoVO.getStaffId());

        staff.setStaffSex(staffInfoVO.getStaffSex());

        staff.setStaffIdCard(staffInfoVO.getStaffIdCard());

        staff.setStaffBirthday(staffInfoVO.getStaffBirthday());

        staff.setGroupId(staffInfoVO.getGroupId());

        staff.setStaffPhone(staffInfoVO.getStaffPhone());

        staff.setIsPerson(staffInfoVO.getIsPerson());

        staff.setStaffJobId(staffInfoVO.getJobId());

       // staff.setStaffTypeId(staffInfoVO.getTerminalId());

        //TODO  更新员工信息
        int res = staffMapper.updateByPrimaryKeySelective(staff);

        if (staffInfoVO.getTerminalId() != null) {
            staffTerminalMapper.updateTerminalBinding(staffInfoVO.getStaffId(), staffInfoVO.getTerminalId());
        }

        return res;
    }

    @Override
    public String getStaffInfoByStaff(String staffName, Integer startPage, Integer pageSize, Integer orgId,Integer isPerson) {

        List<Integer> orgList=null;

        if (null != orgId && 0 != orgId){
            orgList = staffOrganizationService.findSonIdsByDeptId(orgId);
        }
        com.github.pagehelper.Page<StaffInfoDto> page = PageHelper.startPage(startPage, pageSize);
        List<StaffInfoDto> staffInfoDTOs = staffMapper.selectStaffByParams(staffName,orgList,isPerson);

        //设置部门名称
        List<StaffInfoDto> result = page.getResult();
        for (StaffInfoDto staffInfoDto : result) {
            staffInfoDto.setDeptName(staffOrganizationService.getDeptNameByGroupId(staffInfoDto.getGroupId()));
        }

        PageInfo pageInfo = new PageInfo<>(page);
        return ResultUtil.jsonToStringSuccess(pageInfo);
    }

    @Override
    public GasWSRespVO findStaffNameByTerminalId(Integer terminalId) {

        Map<String, Object> resultMap = staffTerminalMapper.selectStaffNameByTerminalId(terminalId);

        GasWSRespVO gasWSRespVO = new GasWSRespVO();
        if (resultMap!=null&&resultMap.size() > 0) {
            Integer staffId = (Integer) resultMap.get("staff_id");
            String staffName = (String) resultMap.get("staff_name");
            Integer isPerson = (Integer) resultMap.get("is_person");
            gasWSRespVO.setStaffName(staffName);
            gasWSRespVO.setStaffId(staffId);
            gasWSRespVO.setIsPerson(isPerson);
            return gasWSRespVO;
        } else {
            gasWSRespVO.setStaffName("未知人员");
            gasWSRespVO.setStaffId(0);
            gasWSRespVO.setIsPerson(0);
            return gasWSRespVO;
        }
    }

    @Override
    public Map<String, Object> findStaffGroupAndDeptByStaffId(Integer staffId) {
        return staffMapper.selectGroupAndDeptByStaffId(staffId);
    }

    @Override
    public Map<String, Object> findStaffIdByTerminalId(Integer terminalId) {
        return staffMapper.selectStaffInfoByTerminalId(terminalId);
    }

    @Override
    public HashMap<String, Object> getDeptAndGroupNameByStaffId(Integer staffId) {
        HashMap<String, Object> map = staffMapper.getDeptAndGroupNameByStaffId(staffId);
       if(map!=null&&map.size()>0){
           Integer groupId = (Integer) map.get("groupId");
           String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
           map.put("deptName",deptName);
       }
        return map;
    }

    @Override
    public Page findContacts(Integer staffId, String staffName, Integer groupId, Integer deptId, Integer pageSize, Integer startPage) {
        Map<String, Object> params = new HashMap<>();
        params.put("staffId", staffId);
        params.put("staffName", staffName);
        params.put("groupId", groupId);
        params.put("deptId", deptId);

        Page page = PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> lists = staffMapper.selectStaffInfoByParamsOfMap(params);

        return page;
    }

    @Override
    public List<Integer> findAllStaffByDeptId(Integer deptId) {
        return staffMapper.selectStaffsByDeptId(deptId);
    }

    @Override
    public List<Integer> findAllStaffByGroupId(Integer groupId) {
        return staffMapper.selectStaffIdsByGroupId(groupId);
    }

    @Override
    public void updateBindingBaseStation(List<Integer> ids) {
        Staff staff = new Staff();
        for (Integer item : ids) {
//            staff.setA(item);
            staffMapper.updateByPrimaryKey(staff);
        }
    }

    @Override
    public void updateBindingBaseStation(List<Integer> ids, Integer stationId) {
        Staff staff = new Staff();
        for (Integer item : ids) {
            staff.setStaffId(item);
            staff.setAttendanceStationId(stationId);
            staffMapper.updateByPrimaryKeySelective(staff);
        }
    }

    @Override
    public Staff findStaffById(Integer staffId) {
        return staffMapper.selectByPrimaryKey(staffId);
    }
    @Override
    public List<Map<String, Object>> findStaffByIds(List<Integer> staffIds) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer staffId : staffIds) {
            Staff staff = findStaffById(staffId);
           if(null != staff){
               Map<String, Object> map = new HashMap<>();
               map.put("staffId",staff.getStaffId());
               map.put("staffName",staff.getStaffName());
               list.add(map);
           }
        }


        return list;
    }


    @Override
    public List<Staff> findStaffByTimeStandardId(Integer item) {
        StaffExample staffExample = new StaffExample();
        StaffExample.Criteria criteria = staffExample.createCriteria();
        criteria.andTimeStandardIdEqualTo(item);
        return staffMapper.selectByExample(staffExample);
    }

    @Transactional
    @Override
    public int bindingTimeStandard(Integer[] staffIds, Integer standardId) {
        int count = 0 ;
        for (Integer item: staffIds){
            StaffExample staffExample = new StaffExample();
            StaffExample.Criteria criteria = staffExample.createCriteria();
            criteria.andStaffIdEqualTo(item);
            Staff staff = new Staff();
            staff.setTimeStandardId(standardId);
            int i = staffMapper.updateByExampleSelective(staff,staffExample);
            if (i == 1)
                count ++;
        }
        return count;
    }

    @Transactional
    @Override
    public int addStaffs(List<StaffInfoVO> staffInfoVOs) {
        return staffMapper.insertStaffs(staffInfoVOs);
    }

    @Override
    public Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId) {
        Map<String, Object> map = staffMapper.selectStaffInfoByTerminalId(terminalId);
        return map;
    }

    @Override
    public List<Staff> selectStaffListByJobType(Integer jobType) {
        StaffExample example = new StaffExample();
        example.createCriteria().andStaffJobIdEqualTo(jobType);
        List<Staff> staff = staffMapper.selectByExample(example);
        return staff;
    }

    @Override
    public List<Staff> selectStaffByLikeName(String staffName) {
        StaffExample example = new StaffExample();
        example.createCriteria().andStaffNameLike("%"+staffName+"%");
        List<Staff> staffList = staffMapper.selectByExample(example);
        return staffList;
    }

    @Override
    public Map<Integer, List<Staff>> findStaffByTimeStandardIds(Integer[] ids) {
        Map<Integer,List<Staff>> map = new HashMap<>();
        for (Integer item: ids){
            StaffExample staffExample = new StaffExample();
            StaffExample.Criteria criteria = staffExample.createCriteria();
            criteria.andTimeStandardIdEqualTo(item);
            List<Staff> staffList = staffMapper.selectByExample(staffExample);
            map.put(item,staffList);
        }

        return map;
    }

    @Override
    public Map<Integer, List<Map<String, Object>>> findStaffNameAndGroupNameByStaffIds(List<Integer> params) {
        Map<Integer, List<Map<String, Object>>> result = new HashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> itemMap;
        for (Integer item: params){
            itemMap = new HashMap<>();
            Staff staff = findStaffById(item);
            itemMap.put("staff",staff);
            StaffJob staffJob = staffJobService.findJobById(staff.getStaffJobId());
            itemMap.put("StaffJob",staffJob);
            list.add(itemMap);
        }

        return result;
    }

    @Override
    public Map<Integer,Map<String,Object>> findGroupNameByIds(List<Integer> list1) {
        Map<Integer, Map<String,Object> > result = new HashMap<>();
        Map<String,Object> itemMap = new HashMap<>();
        for (Integer item: list1){
            Staff staff = staffMapper.selectByPrimaryKey(item);
            if (null == staff)
                continue;
            itemMap.put("jobId",staff.getStaffJobId());
            itemMap.put("staffName",staff.getStaffName());
            itemMap.put("deptName",staffOrganizationService.getDeptNameByGroupId(staff.getGroupId()));
            result.put(item,itemMap);
        }
        return result;
    }


}
