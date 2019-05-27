package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.persistence.dao.staff_terminal_relation.StaffTerminalRelationMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelationExample;
import com.cst.xinhe.staffgroupterminal.service.service.StaffTerminalRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description:
 * @author: lifeng
 * @create: 2019-02-27 10:34
 **/
@Service
public class StaffTerminalRelationServiceImpl implements StaffTerminalRelationService {

    @Resource
    StaffTerminalRelationMapper staffTerminalRelationMapper;

    @Resource
    StaffTerminalMapper staffTerminalMapper;

    @Override
    public StaffTerminalRelation insert(StaffTerminalRelation staffTerminalRelation) {
        staffTerminalRelation.setCreateTime(new Date());
        staffTerminalRelationMapper.insert(staffTerminalRelation);
        return staffTerminalRelation;
    }

    @Override
    public StaffTerminalRelation findNewRelationByStaffId(Integer staffId) {
//        StaffTerminalRelationExample staffTerminalRelationExample = new StaffTerminalRelationExample();
//        StaffTerminalRelationExample.Criteria criteria = staffTerminalRelationExample.createCriteria();
//        criteria.andTypeEqualTo(1);
//        criteria.andStaffIdEqualTo(staffId);
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        params.put("staffId", staffId);
        List<StaffTerminalRelation> staffTerminalRelation = staffTerminalRelationMapper.findNewRelationByParams(params);
        if(staffTerminalRelation!=null&&staffTerminalRelation.size()>0){
            return staffTerminalRelation.get(0);
        }
            return null;
    }

    @Override
    public StaffTerminalRelation findNewRelationByTerminalId(Integer terminalId) {
//        StaffTerminalRelationExample staffTerminalRelationExample = new StaffTerminalRelationExample();
//        StaffTerminalRelationExample.Criteria criteria = staffTerminalRelationExample.createCriteria();
//        criteria.andTypeEqualTo(1);
//        criteria.andTerminalIdEqualTo(terminalId);
        Map<String, Object> params = new HashMap<>();
        params.put("type",1);
        params.put("terminalId",terminalId);
        List<StaffTerminalRelation> staffTerminalRelation = staffTerminalRelationMapper.findNewRelationByParams(params);
        return staffTerminalRelation.get(0);
    }

    @Override
    public int updateRelationToOld(StaffTerminalRelation staffTerminalRelation) {
        StaffTerminalRelationExample staffTerminalRelationExample = new StaffTerminalRelationExample();
        StaffTerminalRelationExample.Criteria criteria = staffTerminalRelationExample.createCriteria();
        criteria.andTerminalIdEqualTo(staffTerminalRelation.getTerminalId());
        criteria.andTypeEqualTo(1);
        StaffTerminalRelation record = new StaffTerminalRelation();
        record.setUpdateTime(new Date());
        record.setType(0);
        return staffTerminalRelationMapper.updateByExampleSelective(record, staffTerminalRelationExample);
    }

    @Override
    public StaffTerminalRelation findHistoryRelationById(Integer staffTerminalRelationId) {
        return staffTerminalRelationMapper.selectByPrimaryKey(staffTerminalRelationId);
    }
}
