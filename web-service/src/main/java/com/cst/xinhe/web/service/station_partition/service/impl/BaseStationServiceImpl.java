package com.cst.xinhe.web.service.station_partition.service.impl;

import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.station_standard_relation.StationStandardRelationMapper;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.base_station.BaseStationExample;
import com.cst.xinhe.persistence.model.station_standard_relation.StationStandardRelation;
import com.cst.xinhe.persistence.model.station_standard_relation.StationStandardRelationExample;
import com.cst.xinhe.persistence.vo.req.BaseStationBindingStandardVO;
import com.cst.xinhe.persistence.vo.resp.BaseStationPositionVO;
import com.cst.xinhe.stationpartition.service.service.BaseStationService;
import com.cst.xinhe.stationpartition.service.service.PartitionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @ClassName BaseStationServiceImpl
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/26 16:07
 * @Vserion v0.0.1
 */
@Service
public class BaseStationServiceImpl implements BaseStationService {

    @Resource
    private BaseStationMapper baseStationMapper;

    @Resource
    private PartitionService partitionService;

    @Resource
    private StationStandardRelationMapper stationStandardRelationMapper;


    @Override
    public int addBaseStation(BaseStation station) {
        return baseStationMapper.insertSelective(station);
    }

    @Override
    public PageInfo<BaseStation> findBaseStationInfo(String begin, String end, Integer startPage, Integer pageSize) {
        PageHelper.startPage(startPage, pageSize);
        List<BaseStation> list = baseStationMapper.selectBaseStationInfoByTime(begin, end);
        PageInfo<BaseStation> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<BaseStation> findAllBaseStationInfo(Integer startPage, Integer pageSize) {
        PageHelper.startPage(startPage, pageSize);
        List<BaseStation> list = baseStationMapper.selectAllBaseStationInfo();
        PageInfo<BaseStation> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<BaseStation> findBaseStationInfoByStart(String begin, Integer startPage, Integer pageSize) {
        PageHelper.startPage(startPage, pageSize);
        List<BaseStation> list = baseStationMapper.selectBaseStationInfoByStartTime(begin);
        PageInfo<BaseStation> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<BaseStation> findBaseStationInfoByEnd(String end, Integer startPage, Integer pageSize) {
        PageHelper.startPage(startPage, pageSize);
        List<BaseStation> list = baseStationMapper.selectBaseStationInfoByEndTime(end);
        PageInfo<BaseStation> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int updateStationInfo(BaseStation station) {
        Map<String, Object> map = baseStationMapper.selectZoneNameAreaNameByStationId(station.getBaseStationId());
//        String zoneName = (String) map.get("zone_name");
//        String areaName = (String) map.get("area_name");
        Integer zoneId = (Integer)map.get("zone_id");
        String zoneNames = partitionService.geParentNamesById(zoneId);
        zoneNames=zoneNames.replaceAll("/","#");
        StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("0.00");
        sb.append(zoneNames).append("#")
                .append("(").append(df.format(station.getPositionX())).append(",")
                .append(df.format(station.getPositionY())).append(")");
        station.setBaseStationName(sb.toString());
        return baseStationMapper.updateByPrimaryKeySelective(station);
    }

    @Override
    public int deleteStationById(Integer id) {
        return baseStationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteStationByIds(Integer[] ids) {
        int count = 0;
        for (Integer item : ids) {
            if (baseStationMapper.deleteByPrimaryKey(item) == 1)
                count++;
        }
        return count;
    }

    @Override
    public List<BaseStationPositionVO> findBaseStationPositionInfo() {
        List<Map<String, Object>> maps = baseStationMapper.selectBaseStationPositionInfo();

        BaseStationPositionVO baseStationPositionVO = null;
        List<BaseStationPositionVO> list = Collections.synchronizedList(new ArrayList<>());
        for (Map<String, Object> item : maps) {
            baseStationPositionVO = new BaseStationPositionVO();

            baseStationPositionVO.setBaseStationId((Integer) item.get("base_station_id"));
            baseStationPositionVO.setBaseStationNum((Integer) item.get("base_station_num"));
            baseStationPositionVO.setPositionX((double) item.get("position_x"));
            baseStationPositionVO.setPositionY((double) item.get("position_y"));
            baseStationPositionVO.setPositionZ((double) item.get("position_z"));
            baseStationPositionVO.setBaseStationName((String) item.get("base_station_name"));

            list.add(baseStationPositionVO);
        }
        return list;
    }


    @Override
    public List<BaseStationPositionVO> findBaseStationPositionInfoNotUsed() {

        List<Map<String, Object>> maps = baseStationMapper.selectBaseStationPositionInfoNotUsed();

        BaseStationPositionVO baseStationPositionVO = null;
        List<BaseStationPositionVO> list = Collections.synchronizedList(new ArrayList<>());
        for (Map<String, Object> item : maps) {
            baseStationPositionVO = new BaseStationPositionVO();

            baseStationPositionVO.setBaseStationId((Integer) item.get("base_station_id"));
            baseStationPositionVO.setBaseStationNum((Integer) item.get("base_station_num"));
//            baseStationPositionVO.setPositionX((double) item.get("position_x"));
//            baseStationPositionVO.setPositionY((double) item.get("position_y"));
//            baseStationPositionVO.setPositionZ((double) item.get("position_z"));

            list.add(baseStationPositionVO);
        }
        return list;
    }

    @Override
    public boolean checkStationExists(Integer baseStationNum) {
        return baseStationMapper.selectCountStationByBaseStationNum(baseStationNum);
    }

    @Override
    public int updateStationInfoOfRemove(BaseStation station) {
        return baseStationMapper.updateBaseStationByStationId(station);
    }

    @Override
    public List<BaseStation> findAllStationByZoneId(Integer zoneId) {
        BaseStationExample stationExample = new BaseStationExample();
        BaseStationExample.Criteria criteria = stationExample.createCriteria();
        criteria.andZoneIdEqualTo(zoneId);
        return baseStationMapper.selectByExample(stationExample);
    }

    @Override
    public List<BaseStation> findAllStationByAreaId(Integer areaId) {

        return baseStationMapper.findAllStationByAreaId(areaId);
    }

    @Override
    public BaseStation findBaseStationById(int stationId) {
        return baseStationMapper.selectByPrimaryKey(stationId);
    }

    @Override
    public BaseStation findBaseStationByNum(int stationId) {
        return baseStationMapper.findBaseStationByNum(stationId);
    }

    @Override
    public List<BaseStation> findBaseStationByZoneId(Integer zoneId) {
        return baseStationMapper.selectBaseStationByZoneId(zoneId);
    }

    @Override
    public Page findBaseStationInfoByParams(Integer startPage, Integer pageSize, Map<String, Object> params) {
        Page page = PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> maps = baseStationMapper.selectBaseStationInfoByParams(params);
        return page;
    }

    @Override
    public void updateStationByNumSelective(BaseStation baseStation) {
        baseStationMapper.updateByStationNumSelective(baseStation);
    }

    @Override
    public List<BaseStation> findBaseStationByZoneIds(List<Integer> zoneIds) {
        return baseStationMapper.findBaseStationByZoneIds(zoneIds);
    }

    @Override
    @Transactional
    public boolean bindingLevelStandard(BaseStationBindingStandardVO baseStationBindingStandardVO) {
        Integer[] stationIds = baseStationBindingStandardVO.getBaseStationIds();
        Integer standardId = baseStationBindingStandardVO.getStandardId();
        int len = stationIds.length;
        int code = 0;
        for (Integer item: stationIds){
            StationStandardRelationExample stationStandardRelationExample = new StationStandardRelationExample();
            StationStandardRelationExample.Criteria criteria = stationStandardRelationExample.createCriteria();
            criteria.andBaseStationNumEqualTo(item);
            List<StationStandardRelation> relations = stationStandardRelationMapper.selectByExample(stationStandardRelationExample);

            if (relations.size() > 0) {

                criteria.andBaseStationNumEqualTo(item);
                StationStandardRelation stationStandardRelation = new StationStandardRelation();
                stationStandardRelation.setUpdateTime(new Date());
                stationStandardRelation.setStandardId(standardId);
                int a = stationStandardRelationMapper.updateByExampleSelective(stationStandardRelation, stationStandardRelationExample);
                if (a == 1){
                    code ++;
                }
            }else {
                StationStandardRelation stationStandardRelation = new StationStandardRelation();
                stationStandardRelation.setCreateTime(new Date());
                stationStandardRelation.setBaseStationNum(item);
                stationStandardRelation.setStandardId(standardId);
                int b = stationStandardRelationMapper.insert(stationStandardRelation);
                if (b == 1){
                    code ++;
                }
            }
        }
        return code == len ? true: false;
    }

    @Override
    public Map<String, Object> findStandardIdByStationNum(Integer stationNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("stationNum", stationNum);
        return baseStationMapper.selectStandardIdByStationNum(params);
    }

    @Override
    public List<Map<String, Object>> findAllStation() {
        List<Map<String, Object>> mapList = baseStationMapper.selectAllBaseStation();
        return mapList;
    }

    @Transactional
    @Override
    public int updateStationType(Integer stationNum, Integer type) {
        Map<String, Object> result = baseStationMapper.selectBaseStationByType(type);
        Map<String, Object> map = new HashMap<>();
        if (null != result){
            if (type == 1){
                Integer id = (Integer) result.get("baseStationNum");
                map.put("num", id);
                map.put("type", 0);
                baseStationMapper.updateBaseStationTypeByStationNum(map);
            }else {
                Integer id = (Integer) result.get("baseStationNum");
                map.put("num", id);
                map.put("type", 0);
                baseStationMapper.updateBaseStationTypeByStationNum(map);
            }

        }
        map.put("num", stationNum);
        map.put("type", type);
        return baseStationMapper.updateBaseStationTypeByStationNum(map);
    }

    @Override
    public Map<String, Object> findBaseStationByType(Integer type) {

        return baseStationMapper.selectBaseStationByType(type);
    }

    @Override
    public double findFrequencyByStationId(Integer stationId) {
        Map<String, Object> map = baseStationMapper.selectFrequencyByStationId(stationId);

        if (null != map && !map.isEmpty()){
            double result = (double)map.get("frequency");

            if (result != 0 ){
                return result;
            }else {
                return 60;
            }
        }else
            return 60; //默认值一分钟
    }

    @Transactional
    @Override
    public int modifyBaseStationFrequency(Integer[] stationIds, int frequency) {

        int count = 0 ;
        for (int item: stationIds){
            int  i = baseStationMapper.modifyFrequency(item,frequency);
            if (i == 1 )
                count ++;
        }
        return count;
    }
    /**
     * @description:    校验基站是否被同时设置为两种状态（井口基站&考勤基站）
     * @param: [stationNum, type]
     * @return: boolean
     * @author: lifeng
     * @date: 2019-04-01
     */
    @Override
    public boolean checkBindStatus(Integer stationNum, Integer type) {
        boolean f = baseStationMapper.selectCountStationByTypeAndId(stationNum,type);
        return f;
    }

    @Override
    public void updateByStationNumSelective(BaseStation baseStation) {
        baseStationMapper.updateByStationNumSelective(baseStation);
    }
}
