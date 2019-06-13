package com.cst.xinhe.web.service.attendance.elasticsrearch.service.impl;

import com.cst.xinhe.attendance.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.attendance.service.client.StationPartitionServiceClient;
import com.cst.xinhe.attendance.service.elasticsrearch.entity.EsAttendanceEntity;
import com.cst.xinhe.attendance.service.elasticsrearch.repository.AttendanceRepository;
import com.cst.xinhe.attendance.service.elasticsrearch.service.EsAttendanceService;
import com.cst.xinhe.persistence.dao.attendance.AttendanceMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.staff.StaffJobMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.model.attendance.TimeStandard;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.vo.req.AttendanceParamsVO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * @create: 2019-04-09 15:10
 **/
@Service
public class EsAttendanceServiceImpl implements EsAttendanceService {

    @Resource
    private AttendanceRepository attendanceRepository;
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private AttendanceMapper attendanceMapper;
    @Resource
    private StaffJobMapper staffJobMapper;
    @Resource
    private BaseStationMapper baseStationMapper;
    @Resource
    private TimeStandardMapper timeStandardMapper;
    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;
    @Resource
    private StationPartitionServiceClient stationPartitionServiceClient;
    @Override
    public org.springframework.data.domain.Page<EsAttendanceEntity> searchAttendanceByStaffType(Integer startPage, Integer pageSize, Integer staffType, String staffName1, String currentDate) {



        List<Integer> staffList = staffMapper.findStaffIdByStaffType(staffType);
        Pageable pageable = new PageRequest(startPage-1,pageSize);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        List<Staff> staffList1=null;
        Set<Integer> set = new HashSet<>();
        if(null != staffName1 &&!"".equals(staffName1)){
             staffList1 = staffGroupTerminalServiceClient.selectStaffByLikeName(staffName1);
        }

        if(staffList!=null&&staffList.size()>0){
            for (Integer staffid : staffList) {
                if(null != staffList1 && staffList1.size()>0){
                    for (Staff staff : staffList1) {
                       if(staffid.equals(staff.getStaffId())){
                           set.add(staffid);
                       }
                    }
                }else{
                    set.add(staffid);
                }
            }
        }
        if(set!=null&&set.size()>0){
                for (Integer integer : set) {
                    builder.should(QueryBuilders.termQuery("staffid",integer).boost(100));
                }
        }
        if(currentDate!=null&&!"".equals(currentDate)){

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            try {
                Date parse = sf.parse(currentDate);
                Calendar instance = Calendar.getInstance();
                instance.setTime(parse);
                instance.set(Calendar.DAY_OF_MONTH,1);//设置当前时间为本月第一天
                Date start = instance.getTime();
                //获取当前月最后一天
                instance.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date end = instance.getTime();
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");

                String startStr = sf1.format(start);
                String endStr = sf1.format(end);

                BoolQueryBuilder builderRange = QueryBuilders.boolQuery();
                builderRange.must(QueryBuilders.rangeQuery("inore").format("yyyy-MM-dd").gte(startStr).lte(endStr));
                builderRange.must(builder);

                builder=builderRange;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        FieldSortBuilder sort = SortBuilders.fieldSort("attendanceid").order(SortOrder.ASC);
        NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder().withSort(sort).withQuery(builder).withPageable(pageable);
        Page<EsAttendanceEntity> page = attendanceRepository.search(nativeBuilder.build());

        List<EsAttendanceEntity> content = page.getContent();
       if(content!=null&&content.size()>0){
           for (EsAttendanceEntity esAttendanceEntity : content) {
               if(set!=null){
                   esAttendanceEntity.setLeaderSum(set.size());
               }else{
                   esAttendanceEntity.setLeaderSum(0);
               }

               Integer staffid = esAttendanceEntity.getStaffid();
               String staffName = staffMapper.selectStaffNameById(staffid);
               esAttendanceEntity.setStaffname(staffName);
               Date inore = esAttendanceEntity.getInore();
               Date outore = esAttendanceEntity.getOutore();
               if(outore==null){
                   outore=new Date();
               }
                   //封装时长
                   long nd = 1000 * 24 * 60 * 60;
                   long nh = 1000 * 60 * 60;
                   long nm = 1000 * 60;
                   long diff = outore.getTime()-inore.getTime();
                   long day = diff / nd;
                   long hour = diff % nd / nh;
                   long min = diff % nd % nh / nm;
                esAttendanceEntity.setTimeLong(day + "天" + hour + "小时" + min + "分钟");

                //封装每月下井次数
               Calendar c = Calendar.getInstance();
               c.setTime(inore);
               c.set(Calendar.DAY_OF_MONTH,1);//设置当前时间为本月第一天
               Date firstDay = c.getTime();
               //获取当前月最后一天
               c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
               Date lastDay = c.getTime();

               SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
               String firstDay1 = simple.format(firstDay);
               String lastDay1 = simple.format(lastDay);

               BoolQueryBuilder builder1 = QueryBuilders.boolQuery();
               builder1.must(QueryBuilders.termQuery("staffid",staffid));
               builder1.must(QueryBuilders.rangeQuery("inore").format("yyyy-MM-dd").gte(firstDay1).lte(lastDay1));
               Iterable<EsAttendanceEntity> search = attendanceRepository.search(builder1);
               Iterator<EsAttendanceEntity> iterator = search.iterator();
               int sum=0;
               while (iterator.hasNext()) {
                   iterator.next();
                   sum++;
               }
               esAttendanceEntity.setInOreSum(sum);
           }
       }
        return page;
    }



    /**
     * 根据员工ID，查询所有的员工信息
     * @return
     */
    public Page<EsAttendanceEntity> searchAttendanceByParams(AttendanceParamsVO attendanceParamsVO) {

        Pageable pageable = new PageRequest(attendanceParamsVO.getStartPage()-1,attendanceParamsVO.getPageSize());
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        Integer orgId = attendanceParamsVO.getOrgId();




        String currentDate = attendanceParamsVO.getCurrentDate1();
        String startTime = attendanceParamsVO.getStartTime1();
        String endTime = attendanceParamsVO.getEndTime1();
        Integer jobType = attendanceParamsVO.getJobType();
        String staffName = attendanceParamsVO.getStaffName();
        Integer timeStandardId = attendanceParamsVO.getTimeStandardId();
        if(orgId!=null){
            Boolean flag=true;
            List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(orgId);
            List<Integer> staffids =staffGroupTerminalServiceClient.findAllStaffByGroupIds(deptIds);
            if(staffids!=null&&staffids.size()>0){
                for (Integer staffid : staffids) {
                    builder.should(QueryBuilders.termQuery("staffid",staffid));
                }
                flag=false;
            }
            if(flag){
                builder.must(QueryBuilders.termQuery("staffid",0));
            }
        }
        if(currentDate!=null&&!"0".equals(currentDate)){

           builder.must(QueryBuilders.rangeQuery("starttime").format("yyyy-MM-dd").gte(currentDate).lte(currentDate));
        }
        if(null != startTime &&!"0".equals(startTime)){
            builder.must(QueryBuilders.rangeQuery("starttime").format("yyyy-MM-dd").gte(startTime));
        }
        if(null != endTime){
            builder.must(QueryBuilders.rangeQuery("starttime").format("yyyy-MM-dd").lte(endTime));
        }


        if(null != timeStandardId){
            builder.must(QueryBuilders.termQuery("ruleid",timeStandardId));
        }

        if(null != jobType){
            List<Staff> staff = staffGroupTerminalServiceClient.selectStaffListByJobType(jobType);
            if(null != staff &&staff.size() > 0){
                for (Staff staff1 : staff) {
                    builder.should(QueryBuilders.termQuery("staffid",staff1.getStaffId()));
                }
            }else{
                builder.must(QueryBuilders.termQuery("staffid",0));
            }
        }

        if(null != staffName &&!"".equals(staffName)){
            List<Staff> staffList = staffGroupTerminalServiceClient.selectStaffByLikeName(staffName);
            if(null != staffList && staffList.size()>0){
                for (Staff staff : staffList) {
                    builder.should(QueryBuilders.termQuery("staffid",staff.getStaffId()));
                }
            }else{
                builder.should(QueryBuilders.termQuery("staffid",0));
            }

        }

        FieldSortBuilder sort = SortBuilders.fieldSort("attendanceid").order(SortOrder.ASC);
        NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder().withSort(sort).withQuery(builder).withPageable(pageable);
        Page<EsAttendanceEntity> page = attendanceRepository.search(nativeBuilder.build());
       // attendanceRepository.
        List<EsAttendanceEntity> list = page.getContent();
        Set<Integer> list1 = new HashSet<>();
        for (EsAttendanceEntity attendance : list) {
            list1.add(attendance.getStaffid());
        }

        Map<Integer,Map<String,Object>> res = staffGroupTerminalServiceClient.findGroupNameByStaffId(list1);

        Iterator<EsAttendanceEntity> iterator = list.iterator();
        while (iterator.hasNext()){
            EsAttendanceEntity attendance = iterator.next();
            Integer staffid = attendance.getStaffid();
            Integer basestationid = attendance.getBasestationid();
            Integer ruleid = attendance.getRuleid();
            Map<String, Object> obj = res.get(staffid);
           if(obj!=null){
               Integer jobId = (Integer)obj.get("jobId");
               StaffJob staffJob = staffJobMapper.selectByPrimaryKey(jobId);

               if(staffJob!=null)
                   attendance.setJobname(staffJob.getJobName());

               String staffName1 = (String) obj.get("staffName");
               attendance.setStaffname(staffName1);

               String deptName = (String) obj.get("deptName");
               attendance.setDeptname(deptName);
           }

               BaseStation station = baseStationMapper.findBaseStationByNum(basestationid);
               TimeStandard timeStandard = timeStandardMapper.selectByPrimaryKey(ruleid);
               if(timeStandard!=null){
                   attendance.setTimestandardname(timeStandard.getTimeStandardName());
               }
               if(station!=null)
                   attendance.setStationname(station.getBaseStationName());


        }
        return page;
    }




    /**
     * 获取所有的警告信息
     * @param startPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<EsAttendanceEntity> searchAttendanceInfo(Integer startPage, Integer pageSize) {
        Pageable pageable = new PageRequest(startPage - 1 ,pageSize);
        Page<EsAttendanceEntity> page = attendanceRepository.findAll(pageable);
        return page;
    }


}
