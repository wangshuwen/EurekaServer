package com.cst.xinhe.gas.service.elasticsearch.service.impl;

import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.gas.service.elasticsearch.repository.GasPositionRepository;
import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.gas.service.elasticsearch.service.GasPositionService;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffExample;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-15 17:41
 **/
@Service
public class GasPositionServiceImpl implements GasPositionService {

    @Resource
    private GasPositionRepository gasPositionRepository;

    @Resource
    private StaffMapper staffMapper;

    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Override
    public Iterable<GasPositionEntity> findTimeFlag(Integer startPage, Integer pageSize, Integer staffId) {


        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("staffid",staffId));

        Iterable<GasPositionEntity> search = gasPositionRepository.search(builder);
        return search;
    }

    @Override
    public List<Map<String, Object>> findRoadByStaffIdAndTime(int staffId, String currentTime) throws ParseException {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);
        Date s = DateConvert.convertStringToDate(currentTime,10);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(s);
        calendar.add(Calendar.DAY_OF_MONTH, + 1);//+1今天的时间加一天
        Date date = calendar.getTime();
        String endtime = DateConvert.convert(date, 10);
        QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd").gte(currentTime).lt(endtime);
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.ASC);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withQuery(queryBuilder1).withSort(sortBuilder);
        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = null;
        Iterator<GasPositionEntity> iterator = iterable.iterator();
        while (iterator.hasNext()){
            map = new HashMap<>();
            GasPositionEntity item = iterator.next();
            map.put("createTime", item.getCreatetime());
            map.put("tempPositionName", item.getTemppositionname());
            map.put("isOre", item.getIsore());
            result.add(map);
        }
        return result;
    }

    @Override
    public Page<GasPositionEntity> searchGasPositionWarnInfoByStaffId(Integer gasFlag, String staffName, Integer startPage, Integer pageSize) {
        Pageable pageable = new PageRequest(startPage - 1,pageSize);

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
       // builder.filter(QueryBuilders.termQuery("gasflag",1));
        if(staffName!=null&&!"".equals(staffName)){
            StaffExample example = new StaffExample();
            example.createCriteria().andStaffNameLike("%"+staffName+"%");
            List<Staff> staffList = staffMapper.selectByExample(example);
            if(staffList!=null&&staffList.size()>0){
                for (Staff staff : staffList) {
                    builder.should(QueryBuilders.termQuery("staffid",staff.getStaffId()));
                }
            }else{
                builder.should(QueryBuilders.termQuery("staffid",0));
            }
        }
        if(gasFlag==1){
            builder.mustNot(QueryBuilders.termQuery("gasflag",0));
        }


        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("gaspositionid").order(SortOrder.ASC);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withSort(sortBuilder).withQuery(builder).withPageable(pageable);

        Page<GasPositionEntity> page = gasPositionRepository.search(nativeSearchQueryBuilder.build());
        return page;
    }

    @Override
    public Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOreTime, String startTime, String endTime, Integer startPage, Integer pageSize) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> itemMap = null;
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);
//        QueryBuilder queryBuilder0 = null;
        QueryBuilder queryBuilder1 = null;
//        QueryBuilder queryBuilder2 = null;
//        if (null != inOreTime){
//            queryBuilder0 = QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd").gte(inOreTime);
//        }
        if (null != startTime && null != endTime && !"".equals(startTime) && !"".equals(endTime)){
            queryBuilder1 = QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd").from(startTime).to(endTime);
//            gte(startTime).lte(endTime);
        }
//        if (null != endTime){
//            queryBuilder2 = QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd").lte(endTime);
//        }
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        Pageable pageable = new PageRequest(startPage,pageSize);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).withQuery(queryBuilder1).withSort(sortBuilder).withFields("createtime","temppositionname", "isore");
        Page<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
//        for (GasPositionEntity item: iterable.getContent()){
//            itemMap = new HashMap<>();
//            itemMap.put("tempPositionName",item.getTemppositionname());
//            itemMap.put("isSore",item.getIsore());
//            itemMap.put("createTime",item.getCreatetime());
//            result.add(itemMap);
//        }
//        return result;
        return iterable;
    }

    @Override
    public TerminalRoad findNowSiteByStaffId(int staffId) {
        TerminalRoad terminalRoad = new TerminalRoad();
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sortBuilder).withMinScore(1);
        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
        for (GasPositionEntity gasPositionEntity : iterable) {
            terminalRoad.setPositionX(gasPositionEntity.getPositionx());
            terminalRoad.setPositionY(gasPositionEntity.getPositiony());
            terminalRoad.setPositionZ(gasPositionEntity.getPositionz());
        }
        return terminalRoad;
    }

    @Override
    public Map<String, Object> selectGasInfoByTerminalLastTime(Integer terminalId) {
        SortBuilder sortBuilder = SortBuilders.fieldSort("terminalrealtime").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.termQuery("terminalid", terminalId);

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sortBuilder).withMinScore(1);

        Map<String, Object> result = new HashMap<>();
        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
        for (GasPositionEntity gasPositionEntity : iterable) {
            result.put("terminal_real_time", gasPositionEntity.getTerminalrealtime());
            result.put("co",gasPositionEntity.getCo());
            result.put("co_unit",gasPositionEntity.getCounit());
            result.put("co2",gasPositionEntity.getCo2());
            result.put("co2_unit",gasPositionEntity.getCo2unit());
            result.put("ch4",gasPositionEntity.getCh4());
            result.put("ch4_unit",gasPositionEntity);
            result.put("o2",gasPositionEntity.getO2());
            result.put("o2_unit",gasPositionEntity.getO2unit());
            result.put("humidity_unit",gasPositionEntity.getHumidityunit());
            result.put("humidity",gasPositionEntity.getHumidity());
            result.put("temperature",gasPositionEntity.getTemperature());
            result.put("temperature_unit", gasPositionEntity.getTemperatureunit());
        }
        return result;
    }

    @Override
    public Map<String, Object> findRecentlyGasInfoByStaffId(Integer staffId) {
        SortBuilder sortBuilder = SortBuilders.fieldSort("gaspositionid").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sortBuilder).withMinScore(1);

        Map<String, Object> result = new HashMap<>();
        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
        for (GasPositionEntity gasPositionEntity : iterable) {
//            result.put("terminal_real_time", gasPositionEntity.getTerminalrealtime());
            result.put("co",gasPositionEntity.getCo());
//            result.put("co_unit",gasPositionEntity.getCounit());
            result.put("co2",gasPositionEntity.getCo2());
//            result.put("co2_unit",gasPositionEntity.getCo2unit());
            result.put("ch4",gasPositionEntity.getCh4());
//            result.put("ch4_unit",gasPositionEntity);
            result.put("o2",gasPositionEntity.getO2());
//            result.put("o2_unit",gasPositionEntity.getO2unit());
//            result.put("humidity_unit",gasPositionEntity.getHumidityunit());
            result.put("humidity",gasPositionEntity.getHumidity());
            result.put("temperature",gasPositionEntity.getTemperature());
//            result.put("temperature_unit", gasPositionEntity.getTemperatureunit());
        }
//         <select id="findRecentlyGasInfoByStaffId" resultType="java.util.HashMap">
//                SELECT *  FROM rt_gas_info r
//        left join staff_terminal s on r.terminal_id = s.terminal_id
//        left join terminal_road t on r.position_id = t.terminal_road_id
//        where s.staff_id = #{staffId}
//        order by rt_gas_info_id desc limit 1
//                </select>
        return result;
    }

    @Override
    public List<Map<String, Object>> selectGasInfoLastTenData(int number) {
        SortBuilder sortBuilder = SortBuilders.fieldSort("gaspositionid").order(SortOrder.DESC);

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withSort(sortBuilder).withMinScore(number);

        Map<String, Object> result;
        List<Map<String, Object>> list = new ArrayList<>();
        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
        for (GasPositionEntity gasPositionEntity : iterable) {
            result = new HashMap<>();
            result.put("rtGasInfoId", gasPositionEntity.getGaspositionid());
            result.put("staff_id", gasPositionEntity.getStaffid());
            result.put("terminal_real_time", gasPositionEntity.getTerminalrealtime());
            result.put("co",gasPositionEntity.getCo());
            result.put("co_unit",gasPositionEntity.getCounit());
            result.put("co2",gasPositionEntity.getCo2());
            result.put("co2_unit",gasPositionEntity.getCo2unit());
            result.put("temppositionname",gasPositionEntity.getTemppositionname());
            result.put("ch4",gasPositionEntity.getCh4());
            result.put("field_3",gasPositionEntity.getField3());
            result.put("field_3_unit",gasPositionEntity.getField3unit());
            result.put("ch4_unit",gasPositionEntity);
            result.put("o2",gasPositionEntity.getO2());
            result.put("o2_unit",gasPositionEntity.getO2unit());
            result.put("humidity_unit",gasPositionEntity.getHumidityunit());
            result.put("humidity",gasPositionEntity.getHumidity());
            result.put("temperature",gasPositionEntity.getTemperature());
            result.put("temperature_unit", gasPositionEntity.getTemperatureunit());
            result.put("create_time", gasPositionEntity.getCreatetime());
            result.put("terminal_id", gasPositionEntity.getTerminalid());
            list.add(result);
        }
//         <select id="selectGasInfoLastTenData" resultType="java.util.HashMap">
//                SELECT  a.rt_gas_info_id AS rtGasInfoId,  a.co,  a.co_unit,  a.ch4,  a.ch4_unit,  a.o2,  a.o2_unit,  a.co2,  a.co2_unit,  a.temperature,
//                a.temperature_unit,  a.humidity,  a.humidity_unit,  a.field_3,  a.field_3_unit,  a.create_time,  a.terminal_id,
//                a.station_id,  a.terminal_ip,  a.station_ip,  a.terminal_real_time,  a.info_type,  a.sequence_id,
//                a.position_id
//        FROM rt_gas_info a
//        ORDER BY a.rt_gas_info_id DESC LIMIT #{number}
//    </select>
        return list;
    }
}
