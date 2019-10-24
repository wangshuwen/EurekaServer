package com.cst.xinhe.web.service.gas.service.impl;

import com.cst.xinhe.common.utils.convert.DateConvert;

import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.web.service.gas.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.web.service.gas.elasticsearch.service.GasPositionService;
import com.cst.xinhe.web.service.gas.service.TerminalRoadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/2/14/15:29
 */
@Service
public class TerminalRoadServiceImpl implements TerminalRoadService {
//    @Resource
//    private TerminalRoadMapper terminalRoadMapper;

    @Resource
    private GasPositionService gasPositionService;

    Set set= new HashSet<String>();
    @Override
    public List<String> findTimeList(int staffId, String startTime, String endTime) {

        Iterable<GasPositionEntity> timeFlag = gasPositionService.findTimeFlag(staffId,startTime,endTime);
        for (GasPositionEntity item : timeFlag) {
            String convert = DateConvert.convert(item.getCreatetime(), 10);
            set.add(convert);
        }

        ArrayList<String> sumList = new ArrayList<>(set);
        //清空set，防止set存储数据过多，大量占用内存
        set.clear();
        Collections.sort(sumList);

        return sumList;
    }

    @Override
    public List<Map<String,Object>> findTerminalRoadByTime(int staffId, String currentTime, Integer pageSize, Integer startPage) throws ParseException {

        List<Map<String, Object>> result = gasPositionService.findRoadByStaffIdAndTime(staffId,currentTime,startPage,pageSize);

//        return terminalRoadMapper.findTerminalRoadByTime(staffId,currentTime);
        return result;
    }

    @Override
    public org.springframework.data.domain.Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOreTime, String startTime, String endTime, Integer startPage, Integer pageSize) {
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);

//        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder);
//        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
//        Iterable<GasPositionEntity> iterable =
//                terminalRoadMapper.findTerminalRoadByInOreTime(staffId,inOreTime,startTime,endTime) ;
        return gasPositionService.findTerminalRoadByInOreTime(staffId,inOreTime,startTime,endTime,startPage,pageSize);
    }

    @Override
    public TerminalRoad findNowSiteByStaffId(int staffId) {
//        terminalRoadMapper.findNowSiteByStaffId(staffId);
        return gasPositionService.findNowSiteByStaffId(staffId);
    }
}
