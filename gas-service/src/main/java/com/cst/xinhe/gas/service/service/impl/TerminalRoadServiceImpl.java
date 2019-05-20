package com.cst.xinhe.gas.service.service.impl;

import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.gas.service.elasticsearch.service.GasPositionService;
import com.cst.xinhe.gas.service.service.TerminalRoadService;
import com.cst.xinhe.persistence.dao.terminal_road.TerminalRoadMapper;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.github.pagehelper.Page;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
    public Page findTimeList(int staffId, Integer pageSize, Integer startPage) {

        Page page = new Page();
        Iterable<GasPositionEntity> timeFlag = gasPositionService.findTimeFlag(startPage, pageSize, staffId);
        for (GasPositionEntity item : timeFlag) {
            String convert = DateConvert.convert(item.getCreatetime(), 10);
            set.add(convert);
        }

        ArrayList<String> sumList = new ArrayList<>(set);
        //清空set，防止set存储数据过多，大量占用内存
        set.clear();
        Collections.sort(sumList);
        //计算当前页，开始到结束的索引位置
        int start=(startPage-1)*pageSize;
        int end=startPage*pageSize-1;
         page.setTotal(sumList.size());
        page.setPageSize(pageSize);
        page.setPageNum(startPage);
        List list = page.getResult();
        list.clear();
        //取分页的数据
        if(end>(sumList.size()-1))
            end=sumList.size()-1;
        for(int i=0;i<sumList.size();i++){
            if(i>=start&&i<=end){
                list.add(sumList.get(i));
            }
        }

       /* List<String> result = new ArrayList<>();
        for (GasPositionEntity item: gasPositionEntities.getContent()){
            result.add(DateConvert.convert(item.getCreatetime(),10));
        }*/

       // list.addAll(result);
        return page;
    }

    @Override
    public List<Map<String,Object>> findTerminalRoadByTime(int staffId, String currentTime) throws ParseException {

        List<Map<String, Object>> result = gasPositionService.findRoadByStaffIdAndTime(staffId,currentTime);

//        return terminalRoadMapper.findTerminalRoadByTime(staffId,currentTime);
        return result;
    }

    @Override
    public org.springframework.data.domain.Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOreTime, Date startTime, Date endTime, Integer startPage, Integer pageSize) {
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
