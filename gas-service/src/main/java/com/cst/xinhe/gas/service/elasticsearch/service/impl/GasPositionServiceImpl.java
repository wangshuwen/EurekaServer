package com.cst.xinhe.gas.service.elasticsearch.service.impl;

import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.gas.service.elasticsearch.repository.GasPositionRepository;
import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.gas.service.elasticsearch.service.GasPositionService;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffExample;
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
}