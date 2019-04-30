package com.cst.xinhe.gas.service.service.impl;

import com.cst.xinhe.gas.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.gas.service.elasticsearch.service.GasPositionService;
import com.cst.xinhe.gas.service.service.GasInfoService;
import com.cst.xinhe.persistence.dao.rt_gas.RtGasInfoMapper;
import com.cst.xinhe.persistence.dao.terminal_road.TerminalRoadMapper;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @ClassName GasInfoServiceImpl
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/21 16:10
 * @Vserion v0.0.1
 */
@Service
public class GasInfoServiceImpl implements GasInfoService {

    @Resource
    private RtGasInfoMapper rtGasInfoMapper;

    @Resource
    private TerminalRoadMapper terminalRoadMapper;

//    @Autowired
//    private StaffOrganizationService staffOrganizationService;
//
//    @Autowired
//    private StaffService staffService;

    @Autowired
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;



    @Autowired
    private GasPositionService gasPositionService;

    @Override
    public Page findGasInfoByStaffName(Integer gasFlag, String staffName, Integer startPage, Integer pageSize) {

              Page page = new Page();
                //todo es分页查询
                org.springframework.data.domain.Page<GasPositionEntity> gasPositionList = gasPositionService.searchGasPositionWarnInfoByStaffId(gasFlag,staffName, startPage, pageSize);
//                gasPositionWarnEntityList.getContent(); //分页内的数据
//                gasPositionWarnEntityList.getSize();
//                System.out.println(gasPositionWarnEntityList.toString());

                for (GasPositionEntity item : gasPositionList.getContent()) {
                    Integer staffid = item.getStaffid();
                   // Staff staff = staffService.findStaffById(staffid);
                    Staff staff = staffGroupTerminalServiceClient.findStaffById(staffid);
                    item.setStaffname(staff.getStaffName());
                }
                List<GasPositionEntity> list = page.getResult();
                page.setPages(gasPositionList.getTotalPages());   // 总分页数

                System.out.println("总共条数： " + gasPositionList.getTotalElements());
                page.setTotal(gasPositionList.getTotalElements()); // 总共条数
                page.setPageNum(startPage);
                page.setPageSize(pageSize);

                list.clear();
                list.addAll(gasPositionList.getContent());    // 总共数据
                page.setPageNum(gasPositionList.getNumber()); //当前页数
                System.out.println("当前页数： " + gasPositionList.getPageable().toString());
                System.out.println("总分页数： " + gasPositionList.getTotalPages());
//                lists = rtGasInfoMapper.selectGasRoadByStaffId();

        /*} else {
            //todo es分页查询
//            page = PageHelper.startPage(startPage, pageSize);
//            lists = rtGasInfoMapper.selectGasInfoByRecently();
            org.springframework.data.domain.Page<GasPositionWarnEntity> gasPositionWarnEntityList = gasPositionWarnService.searchGasPositionWarnInfo(startPage, pageSize);

//            gasPositionWarnEntityList.getContent(); //分页内的数据
//            gasPositionWarnEntityList.getSize();

            List<GasPositionWarnEntity> list = page.getResult();
            page.setPages(gasPositionWarnEntityList.getTotalPages()); // 总分页数
            page.setTotal(gasPositionWarnEntityList.getTotalElements());    // 总共条数
            list.clear();
            list.addAll(gasPositionWarnEntityList.getContent());    // 总共数据
            page.setPageNum(gasPositionWarnEntityList.getNumber()); //当前页数
            for (GasPositionWarnEntity item : gasPositionWarnEntityList.getContent()) {
                Integer staffid = item.getStaffid();
                String staffname = staffMapper.selectStaffNameById(staffid);
                item.setStaffname(staffname);
            }
        }*/
        //根据Name先查人
        return page;
    }

    @Override
    public GasWSRespVO findGasInfoByStaffIdAndTerminalId(Integer terminalId) throws ParseException {
        Map<String, Object> map = rtGasInfoMapper.selectGasInfoByTerminalLastTime(terminalId);

//
//        rt_gas_info_id, co, co_unit, ch4, ch4_unit, o2, o2_unit, co2, co2_unit, temperature,
//                temperature_unit, humidity, humidity_unit, field_3, field_3_unit, create_time, terminal_id,
//                station_id, terminal_ip, station_ip, terminal_real_time, info_type, sequence_id,
//                position_id
        GasWSRespVO gasWSRespVO = new GasWSRespVO();
        gasWSRespVO.setRt((Date) map.get("terminal_real_time"));
        gasWSRespVO.setCo((double) map.get("co"));
        gasWSRespVO.setCo_type((Integer) map.get("co_unit"));
        gasWSRespVO.setCo2((double) map.get("co2"));
        gasWSRespVO.setCo2_type((Integer) map.get("co2_unit"));
        gasWSRespVO.setCh4((double) map.get("ch4"));
        gasWSRespVO.setCh4_type((Integer) map.get("ch4_unit"));
        gasWSRespVO.setO2((double) map.get("o2"));
        gasWSRespVO.setO2_type((Integer) map.get("o2_unit"));
        gasWSRespVO.setHumidity_type((Integer) map.get("humidity_unit"));
        gasWSRespVO.setHumidity((double) map.get("humidity"));
        gasWSRespVO.setTemperature((double) map.get("temperature"));
        gasWSRespVO.setTemperature_type((Integer) map.get("temperature_unit"));
        return gasWSRespVO;
    }


    /**
     * @param number 需要获取的气体数量
     * @return java.util.List<com.zkxh.demo.vo.resp.GasWSRespVO>
     * @description 获取最近上传气体信息数量 以时间倒序
     * @date 17:35 2018/10/26
     * @auther lifeng
     **/
    public List<GasWSRespVO> findGasInfoLastTenRecords(int number) {
        /*Integer orgId = WSSiteServer.orgId;
        Integer zoneId = WSSiteServer.zoneId;*/

        //多表查询改为单表查询
        List<Map<String, Object>> maps = rtGasInfoMapper.selectGasInfoLastTenData(number);
        List<GasWSRespVO> list = Collections.synchronizedList(new ArrayList<>());
        GasWSRespVO gasWSRespVO = null;


        for (Map<String, Object> item : maps) {
            gasWSRespVO = new GasWSRespVO();

            Integer positionId = (Integer) item.get("position_id");
            TerminalRoad terminalRoad = terminalRoadMapper.selectByPrimaryKey(positionId);
            if (terminalRoad != null) {
                gasWSRespVO.setTempRoadName(terminalRoad.getTempPositionName());
                Integer staffId = terminalRoad.getStaffId();
               // Staff staff = staffMapper.selectByPrimaryKey(staffId);
                Staff staff = staffGroupTerminalServiceClient.findStaffById(staffId);
                if (staff != null) {
                    gasWSRespVO.setStaffId(staffId);
                    gasWSRespVO.setStaffName(staff.getStaffName());
                    Integer group_id = staff.getGroupId();
                   /* //部门筛选
                    if(orgId!=null){
                        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
                       if(!deptIds.contains(group_id)){
                           //如果当前部门id不在id集合中，停止本次循环，继续下次循环
                           continue;
                       }
                    }*/


                   // String deptName = staffOrganizationService.getDeptNameByGroupId(group_id);
                    String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(group_id);
                    gasWSRespVO.setDeptName(deptName);
                }
            }

            gasWSRespVO.setRtGasInfoId((Integer) item.get("rtGasInfoId"));
            gasWSRespVO.setRt((Date) item.get("terminal_real_time"));
            gasWSRespVO.setCo((double) item.get("co"));
            gasWSRespVO.setCo_type((Integer) item.get("co_unit"));
            gasWSRespVO.setCo2((double) item.get("co2"));
            gasWSRespVO.setCo2_type((Integer) item.get("co2_unit"));
            gasWSRespVO.setCh4((double) item.get("ch4"));
            gasWSRespVO.setCh4_type((Integer) item.get("ch4_unit"));
            gasWSRespVO.setO2((double) item.get("o2"));
            gasWSRespVO.setStaffId((Integer) item.get("staff_id"));
            gasWSRespVO.setO2_type((Integer) item.get("o2_unit"));
            gasWSRespVO.setHumidity_type((Integer) item.get("humidity_unit"));
            gasWSRespVO.setHumidity((double) item.get("humidity"));
            gasWSRespVO.setTemperature((double) item.get("temperature"));
            gasWSRespVO.setTemperature_type((Integer) item.get("temperature_unit"));


            gasWSRespVO.setSequenceId((Integer) item.get("sequence_id"));
            gasWSRespVO.setCreateTime((Date) item.get("create_time"));
            //gasWSRespVO.setTempRoadName((String)item.get("tempPositionName"));


            list.add(gasWSRespVO);
        }




      /*  List<Map<String, Object>> maps = rtGasInfoMapper.selectGasInfoLastTenRecords(number);



        List<GasWSRespVO> list = Collections.synchronizedList(new ArrayList<>());
        GasWSRespVO gasWSRespVO = null;
        for (Map<String, Object> item : maps) {
            gasWSRespVO = new GasWSRespVO();
            gasWSRespVO.setRtGasInfoId((Integer) item.get("rtGasInfoId"));
            gasWSRespVO.setRt((Date) item.get("terminal_real_time"));
            gasWSRespVO.setCo((double) item.get("co"));
            gasWSRespVO.setCo_type((Integer) item.get("co_unit"));
            gasWSRespVO.setCo2((double) item.get("co2"));
            gasWSRespVO.setCo2_type((Integer) item.get("co2_unit"));
            gasWSRespVO.setCh4((double) item.get("ch4"));
            gasWSRespVO.setCh4_type((Integer) item.get("ch4_unit"));
            gasWSRespVO.setO2((double) item.get("o2"));
            gasWSRespVO.setStaffId((Integer) item.get("staff_id"));
            gasWSRespVO.setO2_type((Integer) item.get("o2_unit"));
            gasWSRespVO.setHumidity_type((Integer) item.get("humidity_unit"));
            gasWSRespVO.setHumidity((double) item.get("humidity"));
            gasWSRespVO.setTemperature((double) item.get("temperature"));
            gasWSRespVO.setTemperature_type((Integer) item.get("temperature_unit"));
//            Integer terminalId = (Integer) item.get("terminalId");
//            Map<String, Object> result = staffService.findStaffIdByTerminalId(terminalId);
//            staff.staff_id, staff_number, staff_name, staff_sex, staff_birthday, staff_id_card, staff_wedlock,
//                    staff_email, staff_address, staff_phone, staff_job_id, staff_native_place, staff_type_id,
//                    is_person, staff.create_time, group_id

            gasWSRespVO.setStaffName((String) item.get("staff_name"));
            gasWSRespVO.setSequenceId((Integer) item.get("sequence_id"));
            gasWSRespVO.setCreateTime((Date) item.get("create_time"));
            gasWSRespVO.setTempRoadName((String)item.get("tempPositionName"));
//            gasWSRespVO.setStaffNumber((String) result.get("staff_number"));
           Integer group_id= (Integer)item.get("group_id");
            String deptName = staffOrganizationService.getDeptNameByGroupId(group_id);
            gasWSRespVO.setDeptName(deptName);
            list.add(gasWSRespVO);
        }*/
        return list;
    }

    @Override
    public HashMap<String, Object> findRecentlyGasInfoByStaffId(Integer staffId) {
        return rtGasInfoMapper.findRecentlyGasInfoByStaffId(staffId);
    }

    @Override
    public Map<String, Object> selectGasInfoByTerminalLastTime(Integer terminalId) {
        return rtGasInfoMapper.selectGasInfoByTerminalLastTime(terminalId);
    }

    @Override
    public TerminalRoad selectRoadById(Integer positionId) {
        return terminalRoadMapper.selectByPrimaryKey(positionId);
    }
}
