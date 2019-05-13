package com.cst.xinhe.stationpartition.service.service.impl;

import com.cst.xinhe.persistence.dao.mac_station.MacStationMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.model.mac_station.MacStation;
import com.cst.xinhe.stationpartition.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.stationpartition.service.service.MacStationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-17 11:10
 **/
@Service
public class MacStationServiceImpl implements MacStationService {

    @Resource
    MacStationMapper macStationMapper;

//    @Resource
//    private TerminalService terminalService;

    @Resource
    private StaffTerminalMapper staffTerminalMapper;

//    @Resource
//    private StaffOrganizationService staffOrganizationService;
//
//    @Resource
//    private StaffJobService staffJobService;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Override
    public List<Map<String, Object>> findMacNumber() {

        return macStationMapper.selectCountMacByStationId();
    }
    /**
     * @description:    根据基站的ID去查找 mac地址绑定的个人信息
     * @param: [stationId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author: lifeng
     * @date: 2019-04-19
     */
    @Override
    public PageInfo<MacStation> findPersonInfoByStation(Integer stationId, Integer startPage, Integer pageSize) {

        Page page = PageHelper.startPage(startPage, pageSize);
        List<MacStation> list = macStationMapper.selectMacStationByStationId(stationId);

        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String,Object> mapEntity = null;

        Set<Integer> setOfGroupId = new HashSet<>();
        Set<Integer> setOfJobId = new HashSet<>();

        for (Object item: page.getResult()){
//            Map<String, Object> map = terminalService.findTerminalAndPersonInfoByMac(((MacStation)item).getMac());
            Map<String, Object> map = staffTerminalMapper.selectTerminalAndPersonInfoByMac(((MacStation)item).getMac());
            if (null != map && !map.isEmpty()){
                Integer groupId = (Integer)map.get("groupId");
                Integer staffJobId = (Integer) map.get("staffJobId");
                setOfGroupId.add(groupId);
                setOfJobId.add(staffJobId);
            }
        }
        Map<Integer, String> groupNames = staffGroupTerminalServiceClient.findGroupNameByGroupIds(setOfGroupId);
        Map<Integer, String> jobNames = staffGroupTerminalServiceClient.findJobByJobId(setOfJobId);
        for (Object item: page.getResult()){
//            Map<String, Object> map = terminalService.findTerminalAndPersonInfoByMac(((MacStation)item).getMac());
            Map<String, Object> map = staffTerminalMapper.selectTerminalAndPersonInfoByMac(((MacStation)item).getMac());
            if (null != map && !map.isEmpty()){
                mapEntity = new HashMap<>();
                for (Map.Entry<String, Object> entry: map.entrySet()){
                    mapEntity.put(entry.getKey(),entry.getValue());
                }
                Integer groupId = (Integer)map.get("groupId");
                Integer staffJobId = (Integer) map.get("staffJobId");
//                String groupName = staffOrganizationService.getDeptNameByGroupId(groupId);
                String groupName = groupNames.get(groupId);
//                String jobName = staffJobService.findJobNameById(staffJobId);
                String jobName = jobNames.get(staffJobId);
                setOfGroupId.add(groupId);
                setOfJobId.add(staffJobId);
                mapEntity.put("groupName", groupName);
                mapEntity.put("jobName", jobName);
                mapList.add(mapEntity);
            }
        }
        page.getResult().clear();
        page.getResult().addAll(mapList);
        PageInfo<MacStation> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
}
