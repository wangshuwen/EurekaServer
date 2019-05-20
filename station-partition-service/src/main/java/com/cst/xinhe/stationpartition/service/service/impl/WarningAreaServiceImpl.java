package com.cst.xinhe.stationpartition.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.coordinate.CoordinateMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaRecordMapper;
import com.cst.xinhe.persistence.dto.warning_area.CoordinateDto;
import com.cst.xinhe.persistence.model.coordinate.Coordinate;
import com.cst.xinhe.persistence.model.coordinate.CoordinateExample;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaExample;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecordExample;
import com.cst.xinhe.persistence.vo.resp.WarningAreaVO;
import com.cst.xinhe.stationpartition.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.stationpartition.service.client.WsPushServiceClient;
import com.cst.xinhe.stationpartition.service.service.WarningAreaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/3/18/13:59
 */
@Service
public class WarningAreaServiceImpl implements WarningAreaService {
    @Resource
    private WarningAreaMapper warningAreaMapper;
    @Resource
    private CoordinateMapper coordinateMapper;
    @Resource
    private WarningAreaRecordMapper warningAreaRecordMapper;

//    @Resource
//    private StaffOrganizationService staffOrganizationService;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Override
    public List<Coordinate> findCoordinateByAreaId(Integer areaId) {

        CoordinateExample example = new CoordinateExample();
        example.createCriteria().andWarningAreaIdEqualTo(areaId);
        List<Coordinate> list = coordinateMapper.selectByExample(example);
        return list;
    }



    @Override
    public Integer addAreaInfo(WarningArea warningArea) {
        return warningAreaMapper.insertSelective(warningArea);
    }



    @Override
    public Integer deleteAreaInfo(Integer id) {
        //删除区域信息
        Integer result = warningAreaMapper.deleteByPrimaryKey(id);
        //删除坐标点
        CoordinateExample example = new CoordinateExample();
        example.createCriteria().andWarningAreaIdEqualTo(id);
         result+= coordinateMapper.deleteByExample(example);

        return result;
    }

    @Override
    public Integer updateAreaInfo(WarningArea warningArea) {
        return warningAreaMapper.updateByPrimaryKeySelective(warningArea);
    }


    @Override
    public Integer addCoordinateList(List<Coordinate> list) {
        Integer result=0;
        for (Coordinate coordinate : list) {
            result+=coordinateMapper.insertSelective(coordinate);

        }
        return result;
    }

    @Override
    public Integer updateCoordinateList(List<Coordinate> list, Integer areaId) {
        Integer result=0;
        //删除原先的坐标点
        CoordinateExample example = new CoordinateExample();
        example.createCriteria().andWarningAreaIdEqualTo(areaId);
        result+= coordinateMapper.deleteByExample(example);

        for (Coordinate coordinate : list) {
            result+=coordinateMapper.insertSelective(coordinate);
        }
        return result;
    }

    @Override
    public WarningAreaVO getAllAreaInfo(Integer areaId) {
        WarningAreaVO warningAreaVO = new WarningAreaVO();
        CoordinateDto coordinateDto = warningAreaMapper.getAreaInfoByAreaId(areaId);
        List<CoordinateDto> coordinateDtos =warningAreaMapper.getOtherAreaInfoByAreaId(areaId);
        warningAreaVO.setCurrentArea(coordinateDto);
        warningAreaVO.setOtherArea(coordinateDtos);
        return warningAreaVO;
    }

    @Override
    public Page findAreaInfoByType(Integer type, Integer pageSize, Integer startPage, String name, Integer areaId) {

        WarningAreaExample example = new WarningAreaExample();
        WarningAreaExample.Criteria criteria = example.createCriteria();
        if(null != type && 0 != type) {
            criteria.andWarningAreaTypeEqualTo(type);
        }

        if((name != null) && !"".equals(name)){
            criteria.andWarningAreaNameLike("%"+name+"%");
        }

        if(null != areaId){
            criteria.andWarningAreaIdEqualTo(areaId);
        }
        Page page = PageHelper.startPage(startPage, pageSize);
        List<WarningArea> list = warningAreaMapper.selectByExample(example);
        for (WarningArea warningArea: list){
            int id = warningArea.getWarningAreaId();
            boolean counter = coordinateMapper.selectCountCoordinateByWarningAreaId(id);
            warningArea.setCounter(counter);

           Integer staffNum= warningAreaRecordMapper.findStaffNumByAreaId(id);
            warningArea.setStaffNum(staffNum);

        }
        return page;
    }

    @Override
    public Page findAreaRecordByAreaId(Integer type, Integer areaId, Integer pageSize, Integer startPage, String staffName, Integer deptId) {
        List<Integer> areaIdList=new ArrayList<>();
        if(areaId!=null){
            areaIdList.add(areaId);
        }
        if(deptId==null){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);

        if(type!=null){
            WarningAreaExample example = new WarningAreaExample();
            WarningAreaExample.Criteria criteria = example.createCriteria();
            criteria.andWarningAreaTypeEqualTo(type);
            List<WarningArea> areaList = warningAreaMapper.selectByExample(example);
            if(areaList!=null&&areaList.size()>0){
                for (WarningArea warningArea : areaList) {
                    areaIdList.add(warningArea.getWarningAreaId());
                }
            }
        }

        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list = warningAreaRecordMapper.findAreaRecordByAreaId(areaIdList,staffName,deptIds);
        for (HashMap<String, Object> map : list) {
            Date inTime = (Date) map.get("inTime");
            Integer groupId = (Integer) map.get("groupId");
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
            map.put("deptName",deptName);
            //封装时长
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            long diff = new Date().getTime()-inTime.getTime();
            long day = diff / nd;
            long hour = diff % nd / nh;
            long min = diff % nd % nh / nm;
            map.put("timeLong",day + "天" + hour + "小时" + min + "分钟");
        }
        return page;
    }

    @Override
    public Page findHistoryAreaRecord(Integer areaId, Integer pageSize, Integer startPage, String staffName, Integer deptId) {
        if(deptId==null){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);

        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list = warningAreaRecordMapper.findHistoryAreaRecord(areaId,staffName,deptIds);
        for (HashMap<String, Object> map : list) {
            Date inTime = (Date) map.get("inTime");
            Date outTime = (Date) map.get("outTime");
            Integer groupId = (Integer) map.get("groupId");
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
            map.put("deptName",deptName);
            //封装时长
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            long diff = outTime.getTime()-inTime.getTime();
            long day = diff / nd;
            long hour = diff % nd / nh;
            long min = diff % nd % nh / nm;
            map.put("timeLong",day + "天" + hour + "小时" + min + "分钟");
        }

        return page;
    }

    @Override
    public Page findAreaRecordByStaffId(Integer type, Integer areaId, Integer pageSize, Integer startPage, Integer staffId) {
        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list = warningAreaRecordMapper.findAreaRecordByStaffId(type,areaId,staffId);

        for (HashMap<String, Object> map : list) {
            Date inTime = (Date) map.get("inTime");
            Date outTime = (Date) map.get("outTime");
            Integer groupId = (Integer) map.get("groupId");
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
            map.put("deptName",deptName);
           if(outTime==null){
               outTime=new Date();
           }
               //封装时长
               long nd = 1000 * 24 * 60 * 60;
               long nh = 1000 * 60 * 60;
               long nm = 1000 * 60;
               long diff = outTime.getTime()-inTime.getTime();
               long day = diff / nd;
               long hour = diff % nd / nh;
               long min = diff % nd % nh / nm;
               map.put("timeLong",day + "天" + hour + "小时" + min + "分钟");

        }
        return page;
    }

    @Override
    public Integer addAreaRecord(WarningAreaRecord warningAreaRecord) {
        //推送前端，该区域人数+1
        Integer areaId = warningAreaRecord.getWarningAreaId();
        WarningArea area = warningAreaMapper.selectByPrimaryKey(areaId);
        Integer type = area.getWarningAreaType();
        WebSocketData data = new WebSocketData();

        data.setType(6);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",type);
        map.put("data",1);
        data.setData(map);
//            WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
        wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
        return warningAreaRecordMapper.insertSelective(warningAreaRecord);
    }

    @Override
    public Integer updateOutTime(Integer staffId) {
        Integer result = 0;
        WarningAreaRecordExample example = new WarningAreaRecordExample();
        WarningAreaRecordExample.Criteria criteria = example.createCriteria();
        criteria.andOutTimeIsNull();
        criteria.andStaffIdEqualTo(staffId);
        List<WarningAreaRecord> records = warningAreaRecordMapper.selectByExample(example);
        if(null != records && records.size() > 0){
            WarningAreaRecord record = records.get(0);
            record.setOutTime(new Date());
            result += warningAreaRecordMapper.updateByPrimaryKeySelective(record);


            //推送前端，该区域人数-1
            Integer areaId = record.getWarningAreaId();
            WarningArea area = warningAreaMapper.selectByPrimaryKey(areaId);
            Integer type = area.getWarningAreaType();

            WebSocketData data = new WebSocketData();

            data.setType(6);
            HashMap<String, Object> map = new HashMap<>();
            map.put("code",type);
            map.put("data",-1);
            data.setData(map);
//                WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
        }



        return result;
    }

    @Override
    public Integer findStaffNumByType(Integer type) {

        return warningAreaRecordMapper.findStaffNumByType(type);
    }



    @Override
    public WarningAreaVO getAll() {
        WarningAreaVO warningAreaVO = new WarningAreaVO();
        List<CoordinateDto> coordinateDtos =warningAreaMapper.getAll();
        warningAreaVO.setOtherArea(coordinateDtos);
        return warningAreaVO;
    }
}
