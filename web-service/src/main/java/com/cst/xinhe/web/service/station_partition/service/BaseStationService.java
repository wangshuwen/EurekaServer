package com.cst.xinhe.web.service.station_partition.service;

import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.vo.req.BaseStationBindingStandardVO;
import com.cst.xinhe.persistence.vo.resp.BaseStationPositionVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseStationService
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/26 16:06
 * @Vserion v0.0.1
 */

public interface BaseStationService {

    int addBaseStation(BaseStation station);

    PageInfo<BaseStation> findBaseStationInfo(String begin, String end, Integer startPage, Integer pageSize);

    PageInfo<BaseStation> findAllBaseStationInfo(Integer startPage, Integer pageSize);

    PageInfo<BaseStation> findBaseStationInfoByStart(String begin, Integer startPage, Integer pageSize);

    PageInfo<BaseStation> findBaseStationInfoByEnd(String end, Integer startPage, Integer pageSize);

    int updateStationInfo(BaseStation station);

    int deleteStationById(Integer id);

    int deleteStationByIds(Integer[] ids);

    List<BaseStationPositionVO> findBaseStationPositionInfo();

    List<BaseStationPositionVO> findBaseStationPositionInfoNotUsed();

    /**
     * @param
     * @return boolean
     * @description 判断 基站的ID 是否存在  ，存在返回true   不存在返回false
     * @date 10:31 2018/11/20
     * @auther lifeng
     **/
    boolean checkStationExists(Integer baseStationNum);

    int updateStationInfoOfRemove(BaseStation station);

    List<BaseStation> findAllStationByZoneId(Integer zoneId);

    List<BaseStation> findAllStationByAreaId(Integer areaId);


    BaseStation findBaseStationByNum(int stationId);

    BaseStation findBaseStationById(int stationId);

    List<BaseStation> findBaseStationByZoneId(Integer zoneId);

    Page findBaseStationInfoByParams(Integer startPage, Integer pageSize, Map<String, Object> params);

    void updateStationByNumSelective(BaseStation baseStation);

    List<BaseStation> findBaseStationByZoneIds(List<Integer> sonIdsById);

    boolean bindingLevelStandard(BaseStationBindingStandardVO baseStationBindingStandardVO);

    Map<String, Object> findStandardIdByStationNum(Integer stationNum);

    List<Map<String, Object>> findAllStation();

    int updateStationType(Integer stationNum, Integer type);

    Map<String, Object> findBaseStationByType(Integer type);

    double findFrequencyByStationId(Integer stationId);

    int modifyBaseStationFrequency(Integer[] stationIds, int frequency);

    boolean checkBindStatus(Integer stationNum, Integer type);

    void updateByStationNumSelective(BaseStation baseStation);
}

