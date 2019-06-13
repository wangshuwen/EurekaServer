package com.cst.xinhe.web.service.staff_group_terminal.service.impl;

import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.staff.StaffOrganizationExample;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/9/10:21
 */
@Service
public class StaffOrganizationServiceImpl implements StaffOrganizationService {
    @Resource
    private StaffOrganizationMapper staffOrganizationMapper;


    @Override
    public List<StaffOrganization> findStaffOrganization() {
        return findAllByParentId(0);
    }

    @Override
    public Integer add(StaffOrganization staffOrganization) {
        return staffOrganizationMapper.insert(staffOrganization);
    }

    @Override
    public Integer delete(Integer id) {
        return deleteAllByParentId(id);
    }

    @Override
    public Integer update(StaffOrganization staffOrganization) {
        return staffOrganizationMapper.updateByPrimaryKeySelective(staffOrganization);
    }

    @Override
    public String getDeptNameByGroupId(Integer groupId) {
        return findAllById(groupId);
    }

    @Override
    public List<Map<String,Object>> getDeptNameByGroupIds(List<Integer> groupIds) {
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (Integer groupId : groupIds) {
            String groupName = findAllById(groupId);
            HashMap<String, Object> map = new HashMap<>();
            map.put("groupId",groupId);
            map.put("groupName",groupName);
            list.add(map);
        }

        return list;
    }

    @Override
    public List<Integer> findSonIdsByDeptName(String DeptName) {
        ArrayList<Integer> deptIds = new ArrayList<>();
        StaffOrganizationExample example = new StaffOrganizationExample();
        example.createCriteria().andNameLike("%"+DeptName+"%");
        List<StaffOrganization> list = staffOrganizationMapper.selectByExample(example);

        if(list!=null){
            for (StaffOrganization staffOrganization : list) {
                deptIds.add(staffOrganization.getId());
                List<Integer> sonIds = findSonIdsById(staffOrganization.getId());
                if(sonIds!=null){
                    for (Integer sonId : sonIds) {
                        deptIds.add(sonId);
                    }
                }
            }
        }
        return deptIds;
    }

    @Override
    public List<Integer> findSonIdsByDeptId(Integer deptId) {
        List<Integer> deptIds = findSonIdsById(deptId);
        deptIds.add(deptId);
        return deptIds;
    }

    /**
    * @author      wangshuwen
    * @description
    * @return      根据父类id的到下一级的儿子
    * @date        2019/2/19 10:50
    */
    @Override
    public List<StaffOrganization> getOneSonByParent(int parentId) {
        StaffOrganizationExample example = new StaffOrganizationExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        return staffOrganizationMapper.selectByExample(example);
    }



    /**
     * 方法实现说明
     * @author      wangshuwen
     * @description  通过parentId查找本节点所有的子节点
     * @date        2019/1/9 11:07
     */
    private List<StaffOrganization> findAllByParentId(Integer parentId){
        StaffOrganizationExample example = new StaffOrganizationExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andParentIdEqualTo(parentId);
        //查询parent_id为0的顶级节点
        List<StaffOrganization> list = staffOrganizationMapper.selectByExample(example);
        if(list!=null){
            for (StaffOrganization staffOrganization : list) {
                staffOrganization.setLabel(staffOrganization.getName());
                List<StaffOrganization> children = findAllByParentId(staffOrganization.getId());
                if(children!=null){
                    staffOrganization.setChildren(children);
                }
            }

        }

        return list;
    }


    /**
     * 方法实现说明
     * @author      wangshuwen
     * @description  通过id查找本节点所有的父节点,把父节点名称拼成串
     * @date        2019/1/9 11:07
     */

    private String findAllById(Integer id){
         String deptName="";
        StaffOrganization staffOrganization = staffOrganizationMapper.selectByPrimaryKey(id);
        if(staffOrganization!=null){
            deptName=staffOrganization.getName();
            if(staffOrganization.getParentId()!=0){
                String parentName = findAllById(staffOrganization.getParentId());
                deptName=parentName+"/"+deptName;
            }
        }

        return deptName;
    }


    /**
     * 方法实现说明
     * @author      wangshuwen
     * @description  递归删除所有的子节点通过parentId
     * @date        2019/1/9 11:07
     */
    private Integer deleteAllByParentId(Integer parentId){
        //
        int result=staffOrganizationMapper.deleteByPrimaryKey(parentId);
        StaffOrganizationExample example = new StaffOrganizationExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        //查询parent_id为0的顶级节点
        List<StaffOrganization> list = staffOrganizationMapper.selectByExample(example);
        if(list!=null){
            for (StaffOrganization staffOrganization : list) {
                result+= deleteAllByParentId(staffOrganization.getId());
            }

        }

        return result;
    }

    /**
     * @author      wangshuwen
     * @description  通过id查找本节点所有的子节点id(不包括本节点id)
     * @return
     * @date        2019/1/11 16:42
     */
    private  List<Integer> findSonIdsById(Integer id) {
        if(id==null){
            id=0;
        }
        ArrayList<Integer> list = new ArrayList<>();
        StaffOrganizationExample example = new StaffOrganizationExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<StaffOrganization> sonList = staffOrganizationMapper.selectByExample(example);
        if(sonList!=null){
            for (StaffOrganization staffOrganization : sonList) {
                list.add(staffOrganization.getId());
                List<Integer> sonIds = findSonIdsById(staffOrganization.getId());
                if(sonIds!=null){
                    for (Integer sonId : sonIds) {
                        list.add(sonId);
                    }
                }
            }


        }
        return list;
    }



}
