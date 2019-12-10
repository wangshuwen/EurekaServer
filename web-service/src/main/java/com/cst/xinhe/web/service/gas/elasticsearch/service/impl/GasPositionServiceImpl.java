package com.cst.xinhe.web.service.gas.elasticsearch.service.impl;

import com.cst.xinhe.persistence.dao.e_msg.ExceptionMessageMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffExample;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.web.service.gas.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.web.service.gas.elasticsearch.repository.GasPositionRepository;
import com.cst.xinhe.web.service.gas.elasticsearch.service.GasPositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import java.text.SimpleDateFormat;
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
    private StaffOrganizationMapper staffOrganizationMapper;

    @Resource
    private ExceptionMessageMapper exceptionMessageMapper;


    @Resource
    ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public Iterable<GasPositionEntity> findTimeFlag(Integer staffId, String startTime, String endtime) {


        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("staffid",staffId));
        builder.must(QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd").gte(startTime).lte(endtime));

        Iterable<GasPositionEntity> search = gasPositionRepository.search(builder);
        return search;
    }

    @Override
    public List<Map<String, Object>> findRoadByStaffIdAndTime(int staffId,  Integer startPage, Integer pageSize,String startTime,String endTime,Integer isOre) throws ParseException {
        Pageable pageable = new PageRequest(startPage - 1,pageSize);

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("staffid", staffId));
        if(isOre!=null){
            builder.must(QueryBuilders.termQuery("isore", isOre));
        }

        /*Date s = DateConvert.convertStringToDate(currentTime,10);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(s);
        calendar.add(Calendar.DAY_OF_MONTH, + 1);//+1今天的时间加一天
        Date date = calendar.getTime();
        String endtime = DateConvert.convert(date, 10);*/
        builder.must(QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd HH:mm:ss").from(startTime).to(endTime));
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.ASC);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(builder).withSort(sortBuilder).withPageable(pageable);
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
    public Page<GasPositionEntity> searchGasPositionWarnInfoByStaffId(Integer gasFlag, String staffName, Integer startPage, Integer pageSize, String startTime, String endTime) {
        Pageable pageable = new PageRequest(startPage - 1,pageSize);
        QueryBuilder queryBuilder1 = null;
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
       // builder.filter(QueryBuilders.termQuery("gasflag",1));
        if(null != staffName &&!"".equals(staffName)){
            StaffExample example = new StaffExample();
            example.createCriteria().andStaffNameLike("%"+staffName+"%");
            List<Staff> staffList = staffMapper.selectByExample(example);
            if(null != staffList &&staffList.size()>0){
                if(staffList.size()==1){
                    builder.must(QueryBuilders.termQuery("staffid",staffList.get(0).getStaffId()));
                }else{
                   // BoolQueryBuilder query = QueryBuilders.boolQuery();
                    for (Staff staff : staffList) {
                        builder.should(QueryBuilders.termQuery("staffid",staff.getStaffId()));
                    }
                    //builder.must(query);
                }
            }else{
                builder.must(QueryBuilders.termQuery("staffid",0));
            }
        }



        if (null != startTime && null != endTime && !"".equals(startTime) && !"".equals(endTime)){
            //添加时间查询
            //到es中查询，需要减8个小时才等于es中的时间
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate=null;
            Date endDate=null;
            try {
                startDate=sf1.parse(startTime);
                endDate=sf1.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar instance = Calendar.getInstance();
            instance.setTime(startDate);
            instance.add(Calendar.HOUR_OF_DAY, -8);
            startDate = instance.getTime();
            startTime=sf1.format(startDate);

            instance.setTime(endDate);
            instance.add(Calendar.HOUR_OF_DAY, -8);
            endDate = instance.getTime();
            endTime=sf1.format(endDate);

            queryBuilder1 = QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd HH:mm:ss").gte(startTime).lte(endTime);
        }


        if(gasFlag==1){
            builder.mustNot(QueryBuilders.termQuery("gasflag",0));
        }


        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withSort(sortBuilder).withQuery(builder).withQuery(queryBuilder1).withPageable(pageable);
        Page<GasPositionEntity> page = gasPositionRepository.search(nativeSearchQueryBuilder.build());
        return page;
    }

    @Override
    public Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOreTime, String startTime, String endTime, Integer startPage, Integer pageSize) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> itemMap = null;
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);
        QueryBuilder queryBuilder1 = null;

        if (null != startTime && null != endTime && !"".equals(startTime) && !"".equals(endTime)){
            queryBuilder1 = QueryBuilders.rangeQuery("createtime").format("yyyy-MM-dd").from(startTime).to(endTime);
        }

        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        Pageable pageable = new PageRequest(startPage - 1,pageSize);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(queryBuilder).withQuery(queryBuilder1).withSort(sortBuilder).withFields("createtime","temppositionname", "isore");
        Page<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
        return iterable;
    }

    @Override
    public TerminalRoad findNowSiteByStaffId(int staffId) {
        TerminalRoad terminalRoad = new TerminalRoad();
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);
        Pageable pageable = new PageRequest(0,1);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sortBuilder).withPageable(pageable);
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
        Pageable pageable = new PageRequest(0,1);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sortBuilder).withPageable(pageable);

        Map<String, Object> result = new HashMap<>();
        Iterable<GasPositionEntity> iterable = gasPositionRepository.search(searchQueryBuilder.build());
        for (GasPositionEntity gasPositionEntity : iterable) {
            result.put("terminal_real_time", gasPositionEntity.getTerminalrealtime());
            result.put("temp_position_name", gasPositionEntity.getTemppositionname());
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
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        QueryBuilder queryBuilder = QueryBuilders.termQuery("staffid", staffId);
        Pageable pageable = new PageRequest(0,1);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sortBuilder).withPageable(pageable);

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

        Pageable pageable = new PageRequest(0,number);
        SortBuilder sortBuilder = SortBuilders.fieldSort("createtime").order(SortOrder.DESC);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withSort(sortBuilder).withPageable(pageable);

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
            result.put("ch4_unit",gasPositionEntity.getCh4unit());
            result.put("o2",gasPositionEntity.getO2());
            result.put("o2_unit",gasPositionEntity.getO2unit());
            result.put("humidity_unit",gasPositionEntity.getHumidityunit());
            result.put("humidity",gasPositionEntity.getHumidity());
            result.put("temperature",gasPositionEntity.getTemperature());
            result.put("temperature_unit", gasPositionEntity.getTemperatureunit());
            result.put("create_time", gasPositionEntity.getCreatetime());
            result.put("terminal_id", gasPositionEntity.getTerminalid());
            result.put("sequence_id", gasPositionEntity.getSequenceid());
            list.add(result);
        }
        return list;
    }

    @Override
    public PageInfo<Map<String, Object>> getEMsgList(Integer pageSize, Integer startPage, String staffName, Integer staffId,Integer type) {

        Map<String, Object> params = new HashMap<>();
        params.put("staffName", staffName);
        params.put("staffId", staffId);
        params.put("type", type);
        com.github.pagehelper.Page<Map<String, Object>> page = PageHelper.startPage(startPage,pageSize);
        for (Map<String, Object> item : page.getResult()){
            Integer groupId = (Integer) item.get("group_id");
            String deptName = getDeptNameByGroupId(groupId);
            item.put("dept_name",deptName);
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
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
