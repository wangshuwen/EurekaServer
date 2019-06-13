package com.cst.xinhe.stationpartition.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.coordinate.CoordinateMapper;
import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaRecordMapper;
import com.cst.xinhe.persistence.dto.warning_area.CoordinateDto;
import com.cst.xinhe.persistence.model.coordinate.Coordinate;
import com.cst.xinhe.persistence.model.coordinate.CoordinateExample;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaExample;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecordExample;
import com.cst.xinhe.persistence.vo.resp.WarningAreaVO;
import com.cst.xinhe.stationpartition.service.client.KafkaConsumerServiceClient;
import com.cst.xinhe.stationpartition.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.stationpartition.service.client.WsPushServiceClient;
import com.cst.xinhe.stationpartition.service.service.WarningAreaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private StaffOrganizationMapper staffOrganizationMapper;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Resource
    private KafkaConsumerServiceClient kafkaConsumerServiceClient;

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
    public Integer deleteCoordinate(Integer id) {

        //删除坐标点
        CoordinateExample example = new CoordinateExample();
        example.createCriteria().andWarningAreaIdEqualTo(id);
        Integer result = coordinateMapper.deleteByExample(example);
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
        Set<Integer> areaIdList=new HashSet<>();
        if(null != areaId){
            areaIdList.add(areaId);
        }
        if(null == deptId){
            deptId=0;
        }
//        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(deptId);
        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(deptId);

        if(null != type){
            WarningAreaExample example = new WarningAreaExample();
            WarningAreaExample.Criteria criteria = example.createCriteria();
            criteria.andWarningAreaTypeEqualTo(type);
            criteria.andWarningAreaIdEqualTo(areaId);
            List<WarningArea> areaList = warningAreaMapper.selectByExample(example);
            if(null != areaList &&areaList.size()>0){
                for (WarningArea warningArea : areaList) {
                    areaIdList.add(warningArea.getWarningAreaId());
                }
            }
        }

        Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list = warningAreaRecordMapper.findAreaRecordByAreaId(areaIdList,staffName,deptIds);

        //修改for循环远程服务调用
        ArrayList<Integer> groupIds = new ArrayList<>();
        for (HashMap<String, Object> map : list) {
            Integer groupId = (Integer) map.get("groupId");
            groupIds.add(groupId);
        }
        List<Map<String, Object>> stafflist = staffGroupTerminalServiceClient.getDeptNameByGroupIds(groupIds);

        for (HashMap<String, Object> map : list) {
            Date inTime = (Date) map.get("inTime");
            Integer groupId = (Integer) map.get("groupId");
            if(stafflist!=null&&stafflist.size()>0){
                for (Map<String,Object> stringObjectMap : stafflist) {
                    Integer groupId1 = (Integer) stringObjectMap.get("groupId");
                    if(groupId.equals(groupId1)){
                        String groupName = (String) stringObjectMap.get("groupName");
                        map.put("deptName",groupName);
                    }

                }
            }
//            String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
            //String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);

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
            String deptName = getDeptNameByGroupId(groupId);
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

            //超员报警
            if(type==1){
                kafkaConsumerServiceClient.overmanedAlarm(1,staffId,areaId);
            }else{
                kafkaConsumerServiceClient.overmanedAlarm(2,staffId, areaId);
            }

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

    @Override
    public Page printReportOfWarningArea(Integer type, String date,Integer startPage, Integer pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("p_type",type);
        param.put("p_date",date);
        Date currentTime = new Date();
        Page<Map<String, Object>> page = PageHelper.startPage(startPage,pageSize);
        List<Map<String, Object>> result = warningAreaRecordMapper.getWarningWarePersonNumber(param);
        List<Map<String, Object>> list = page.getResult();
        for (Map<String, Object> map: list){
            Integer groupId = (Integer) map.get("groupId");
            String groupName = getDeptNameByGroupId(groupId);
            map.put("groupName",groupName);
            Date in = (Date)map.get("inTime");
            long res =  currentTime.getTime() - in.getTime();
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            long day = res / nd;
            long hour = res % nd / nh;
            long min = res % nd % nh / nm;
            map.put("outTime", day + "天" + hour + "小时" + min + "分钟");
        }
        return page;
    }
    public String getDeptNameByGroupId(Integer groupId) {
        String deptName = "";
        StaffOrganization staffOrganization = staffOrganizationMapper.selectByPrimaryKey(groupId);
        if (staffOrganization != null) {
            deptName = staffOrganization.getName();
            if (staffOrganization.getParentId() != 0) {
                String parentName = getDeptNameByGroupId(staffOrganization.getParentId());
                deptName = parentName + "/" + deptName;
            }
        }

        return deptName;
    }

}
