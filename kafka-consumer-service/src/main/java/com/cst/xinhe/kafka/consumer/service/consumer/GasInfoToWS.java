package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.RuntimeOtherException;
import com.cst.xinhe.common.utils.array.ArrayQueue;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.rt_gas.RtGasInfoMapper;
import com.cst.xinhe.persistence.dao.rt_gas.RtGasInfoWarnMapper;
import com.cst.xinhe.persistence.dao.terminal_road.TerminalRoadMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaRecordMapper;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.rt_gas.RtGasInfo;
import com.cst.xinhe.persistence.model.rt_gas.RtGasInfoWarn;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecordExample;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName GasInfoToWS
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/12 14:26
 * @Vserion v0.0.1
 */
@Component
public class GasInfoToWS {

    //存储更新基站 队列
    public static Map<Integer, ArrayQueue<TerminalRoad>> attendanceMap=new HashMap<>();
    @Resource
    RtGasInfoWarnMapper rtGasInfoWarnMapper;
    @Resource
    RtGasInfoMapper rtGasInfoMapper;
    @Resource
    RSTL rstl;
    @Resource
    private TerminalRoadMapper teminalRoadMapper;
    @Resource
    private StaffService staffService;
    @Resource
    private BaseStationService baseStationService;
    @Resource
    private AttendanceService attendanceService;
    @Resource
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;
    @Resource
    private PartitionService partitionService;
    @Resource
    private StaffOrganizationService staffOrganizationService;
    @Resource
    private WarningAreaRecordMapper warningAreaRecordMapper;
    @Resource
    private StaffTerminalRelationService staffTerminalRelationService;

    @Resource
    private WarningAreaMapper warningAreaMapper;

    private static ObjectMapper json = new ObjectMapper();
    public static List<GasWSRespVO> list = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(GasInfoToWS.class);

    private static double avgCoOfWork = 0D;
    private static double t_avgCoOfWork = 0D;
    private static double avgCh4OfWork = 0D;
    private static double t_avgCh4OfWork = 0D;
    private static double avgCoOfRoad = 0D;
    private static double t_avgCoOfRoad = 0D;
    private static double avgCh4OfRoad = 0D;
    private static double t_avgCh4OfRoad = 0D;

    private static List<Double> listOfCoR = new ArrayList<>();
    private static List<Double> listOfCoW = new ArrayList<>();
    private static List<Double> listOfCh4R = new ArrayList<>();
    private static List<Double> listOfCh4W = new ArrayList<>();
    ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(18);
    ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(18);
    ExecutorService fixedThreadPool3 = Executors.newFixedThreadPool(18);

    public static Set<Integer> overTimeSet= Collections.synchronizedSet(new HashSet());
    public static Set<Integer> seriousTimeSet= Collections.synchronizedSet(new HashSet());
    /**
     * 监听kafka.tut 的 topic
     *
     * @param record
     * @param topic  topic
     */
    private static final String TOPIC = "kafka.tut";
  //  @KafkaListener(id = "GasInfoToWeb", topics = "kafka.tut")
    @KafkaListener(groupId = "GasInfoToWS", id = "GasInfoToWSid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void listen0(List<ConsumerRecord<?, ?>> records) {
        logger.info("currentThreadId:{}, topicSize:{} ",Thread.currentThread().getId(),records.size());
//
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                        Object message = kafkaMessage.get();
//                        String str = (String) message;

                        fixedThreadPool1.execute(() -> processT(records));
//            }
//        }
    }

    @KafkaListener(groupId = "GasInfoToWS", id = "GasInfoToWSid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void listen1(List<ConsumerRecord<?, ?>> records) {
        logger.info("currentThreadId:{}, topicSize:{} ",Thread.currentThread().getId(),records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;

                fixedThreadPool2.execute(new Runnable() {
                    @Override
                    public void run() {
                        processT(records);
                    }
                });
//            }
//        }
    }
    @KafkaListener(groupId = "GasInfoToWS", id = "GasInfoToWSid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void listen2(List<ConsumerRecord<?, ?>> records) {
        logger.info("currentThreadId:{}, topicSize:{} ",Thread.currentThread().getId(),records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;


                fixedThreadPool3.execute(new Runnable() {
                    @Override
                    public void run() {
                            processT(records);
                    }
                });
//            }
//        }
    }
    private void process(String str){
        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}" , thread.getId());
//        //判断是否NULL
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//            //获取消息
//            Object message = kafkaMessage.get();
//            String str = (String) message;
            JSONObject jsonObject = JSON.parseObject(str);
            JSONObject gasInfo = jsonObject.getJSONObject("gasInfo");

            double co = gasInfo.getDouble("co");
            Integer coFlag = gasInfo.getInteger("coFlag");
            double co2 = gasInfo.getDouble("co2");
            Integer co2Flag = gasInfo.getInteger("co2Flag");
            double t = gasInfo.getDouble("t");
            Integer tFlag = gasInfo.getInteger("tFlag");
            double h = gasInfo.getDouble("h");
            Integer hFlag = gasInfo.getInteger("hFlag");
            double ch4 = gasInfo.getDouble("ch4");
            Integer ch4Flag = gasInfo.getInteger("ch4Flag");
            double o2 = gasInfo.getDouble("o2");
            Integer o2Flag = gasInfo.getInteger("o2Flag");
            Boolean isWarn = gasInfo.getBoolean("isWarn");

            Date rt = jsonObject.getDate("rT");
            Date createTime = jsonObject.getDate("createTime");
            Integer sequenceId = jsonObject.getInteger("sequenceId");
            String stationIp = jsonObject.getString("stationIp");
            Integer stationId = jsonObject.getInteger("stationId");
            Integer terminalId = jsonObject.getInteger("terminalId");
            String terminalIp = jsonObject.getString("terminalIp");

            JSONObject rssiObj = jsonObject.getJSONObject("rssiInfo");
            Integer terminalId_t = rssiObj.getInteger("terminalId");// 关于定位的终端ID
            Integer stationId1 = rssiObj.getInteger("stationId1");
            Integer stationId2 = rssiObj.getInteger("stationId2");
            double rssi1 = rssiObj.getDouble("rssi1");
            double rssi2 = rssiObj.getDouble("rssi2");

            TerminalRoad road = rstl.locateConvert(terminalId, stationId1, stationId2, rssi1, rssi2);

            GasWSRespVO staff = staffService.findStaffNameByTerminalId(terminalId);
            Integer staffId=staff.getStaffId();

            //---------------------判断该员工是否在限制区域，滞留时间过长，则报警开始-----------------------
        WarningAreaRecordExample example = new WarningAreaRecordExample();
        WarningAreaRecordExample.Criteria criteria = example.createCriteria();
        criteria.andStaffIdEqualTo(staffId);
        criteria.andOutTimeIsNull();
        List<WarningAreaRecord> records = warningAreaRecordMapper.selectByExample(example);
        if(records!=null&&records.size()>0){
            WarningAreaRecord record = records.get(0);
            WarningArea warningArea = warningAreaMapper.selectByPrimaryKey(record.getWarningAreaId());
            String time = warningArea.getResidenceTime();
            double residenceTime = Double.parseDouble(time);//单位分钟
            Date now=new Date();
            long realLong = now.getTime() - record.getInTime().getTime();
            if(realLong>residenceTime){
                WebSocketData data=  new WebSocketData();
                data.setType(7);
                HashMap<String, Object> map = new HashMap<>();
                map.put("code",2);
                //查询报警人员的信息
                String staffName=staff.getStaffName();
                Staff staff1=staffService.findStaffById(staffId);
                Integer groupId = staff1.getGroupId();
                String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
                HashMap<Object, Object> staffInfo = new HashMap<>();
                staffInfo.put("staffId",staffId);
                staffInfo.put("staffName",staffName);
                staffInfo.put("deptName",deptName);
                staffInfo.put("areaName",warningArea.getWarningAreaName());
                    //封装时长
                    long nd = 1000 * 24 * 60 * 60;
                    long nh = 1000 * 60 * 60;
                    long nm = 1000 * 60;
                    long diff = realLong;
                    long day = diff / nd;
                    long hour = diff % nd / nh;
                    long min = diff % nd % nh / nm;
                staffInfo.put("timeLong",day + "天" + hour + "小时" + min + "分钟");
                map.put("data",staffInfo);
                data.setData(map);
                try {
                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
           //----------------------判断该员工是否在限制区域，滞留时间过长，则报警结束-----------------------

//            StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByTerminalId(terminalId);
//            road.setStaffId(staffTerminalRelation.getStaffTerminalRelationId());
            road.setStaffId(staffId);
            road.setStationId(stationId);
            //--------------------------------判断员工是否是出矿入矿---------------------------------
            Map<String, Object> entryStation  = baseStationService.findBaseStationByType(1);
            Map<String, Object> attendanceStation = baseStationService.findBaseStationByType(2);
            //井口基站编号
            Integer entryId = (Integer) entryStation.get("baseStationNum");
            //考勤基站编号
            Integer attendanceId = (Integer) attendanceStation.get("baseStationNum");
            //连接基站为井口基站或者考勤基站，保存进队列，存储map中
            if(stationId.equals(entryId) || stationId.equals(attendanceId)) {
                ArrayQueue<TerminalRoad> terminalUpdateIpQueue = attendanceMap.get(terminalId);
                //判断队列是否为空
                if (terminalUpdateIpQueue == null) {
                    terminalUpdateIpQueue = new ArrayQueue<>(2);
                }
                //此次更新ip对象保存到队列
                terminalUpdateIpQueue.add(road);
                attendanceMap.put(terminalId, terminalUpdateIpQueue);
                //判断是入矿还是出矿
                if (terminalUpdateIpQueue.getQueueSize() == 2) {
                    TerminalRoad head = terminalUpdateIpQueue.getHead();
                    TerminalRoad end = terminalUpdateIpQueue.getEnd();
                    //入矿
                    if (head.getStationId().equals(entryId) && end.getStationId().equals(attendanceId)) {
                        road.setIsOre(1);

                    }
                    //出矿
                    if(head.getStationId().equals(attendanceId)  && end.getStationId().equals(entryId)){
                        road.setIsOre(2);
                    }
                    teminalRoadMapper.insert(road);
                }
            }else{
                road.setIsOre(0);
                teminalRoadMapper.insert(road);
            }

            //---------------------------------------判断员工是否超时未上井开始------------------------------------
            StaffAttendanceRealRule realRule = staffAttendanceRealRuleMapper.selectByPrimaryKey(staffId);
            TimeStandardVO standard = attendanceService.getTimeStandardByStaffId(staffId);
            //超时时长
            Integer overTime = standard.getOverTime();
            //严重超时时长
            Integer seriousTime = standard.getSeriousTime();
            //本应下班时间
            Date endTime = realRule.getRealRuleEndTime();
            Date over_time= new  Date();
            Date serious_time= new  Date();
            //超时时间
            over_time.setTime(endTime.getTime()+overTime*60*60*1000);
            //严重超时时间
            serious_time.setTime(endTime.getTime()+seriousTime*60*60*1000);

            Date date = new Date();
            WebSocketData data = new WebSocketData();

            if(serious_time.before(date)){

                try {
                    boolean contains = overTimeSet.contains(staffId);
                    if(!contains){
                        //严重超时
                        data.setType(2);
                        data.setData(1);
                        //标记严重超时的人
                        realRule.setIsOverTime(0);
                        realRule.setIsSeriousTime(1);
                        realRule.setFinalTime(new Date());
                        staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
                        overTimeSet.add(staffId);
                        WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                    }
                    //严重超时加1

                  /*  //超时-1
                    data.setType(1);
                    data.setData(-1);
                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));*/
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else  if(over_time.before(date)){
                boolean contains = seriousTimeSet.contains(staffId);
                if(!contains){
                    seriousTimeSet.add(staffId);
                    //超时
                    data.setType(1);
                    data.setData(1);
                    //标记超时的人
                    realRule.setIsOverTime(1);
                    realRule.setFinalTime(new Date());
                    staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
                    try {
                        WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }



            //---------------------------------------判断员工是否超时未上井结束------------------------------------

            //TODO 根据 terminalId 查找人名称
            //            System.out.println(co + " co " + ch4 + "  t  " + t + " h " + h + "   co2 " + co2 + "  o2  " + o2);
            GasWSRespVO gasWSRespVO = new GasWSRespVO();
            gasWSRespVO.setO2(o2);
            gasWSRespVO.setO2_type(o2Flag);
            gasWSRespVO.setTemperature(t);
            gasWSRespVO.setTemperature_type(tFlag);
            gasWSRespVO.setHumidity(h);
            gasWSRespVO.setHumidity_type(hFlag);
            gasWSRespVO.setCo2(co2);
            gasWSRespVO.setCo2_type(co2Flag);
            gasWSRespVO.setCh4(ch4);
            gasWSRespVO.setCh4_type(ch4Flag);
            gasWSRespVO.setSequenceId(sequenceId);
            gasWSRespVO.setCo(co);
            gasWSRespVO.setCo_type(coFlag);
            gasWSRespVO.setStaffId(staff.getStaffId());
            gasWSRespVO.setStaffName(staff.getStaffName());
            gasWSRespVO.setRt(rt);
            gasWSRespVO.setCreateTime(createTime);
            gasWSRespVO.setIsPerson(staff.getIsPerson());
            gasWSRespVO.setGroupName(staff.getGroupName());
            gasWSRespVO.setDeptName(staff.getDeptName());

            String tempPositionName = road.getTempPositionName();
            Integer terminalRoadId = road.getTerminalRoadId();
            gasWSRespVO.setTempRoadName(tempPositionName);
            gasWSRespVO.setTerminalRoadId(terminalRoadId);

            gasWSRespVO.setTerminalRoad(road);


            //封装插入数据库
            RtGasInfo rtGasInfo = new RtGasInfo();

            rtGasInfo.setHumidity(h);
            rtGasInfo.setTemperature(t);
            rtGasInfo.setTerminalRealTime(rt);
            rtGasInfo.setTerminalIp(terminalIp);
            rtGasInfo.setTerminalId(terminalId);
            rtGasInfo.setStationIp(stationIp);
            rtGasInfo.setStationId(stationId);
            rtGasInfo.setO2(o2);
            rtGasInfo.setCo(co);
            rtGasInfo.setCo2(co2);
            rtGasInfo.setCh4(ch4);
            rtGasInfo.setInfoType(true);
            rtGasInfo.setSequenceId(sequenceId);
            rtGasInfo.setCreateTime(createTime);

            rtGasInfo.setCh4Unit(ch4Flag);
            rtGasInfo.setCoUnit(coFlag);
            rtGasInfo.setHumidityUnit(hFlag);
            rtGasInfo.setTemperatureUnit(tFlag);
            rtGasInfo.setO2Unit(o2Flag);
            rtGasInfo.setCo2Unit(co2Flag);

            rtGasInfo.setPositionId(road.getTerminalRoadId());
//            rtGasInfo.setCh4(upLoadGasDto ''to.getH());
            rtGasInfoMapper.insert(rtGasInfo);
            //警告气体插入警告气体表
            if(isWarn){
                Integer rtGasInfoId = rtGasInfo.getRtGasInfoId();
                RtGasInfoWarn warn = new RtGasInfoWarn();
                warn.setRtGasInfoId(rtGasInfoId);
                rtGasInfoWarnMapper.insert(warn);
            }

            //--------------------------定位部门、区域筛选开始----------------------------------
            Staff staff1=staffService.findStaffById(staffId);
            Integer groupId = staff1.getGroupId();
            Integer orgId = WSSiteServer.orgId;
            Integer zoneId = WSSiteServer.zoneId;
            Map<String ,Object> map = new HashMap<>();



            if(orgId==null&&zoneId==null){

                try {
                    map.put("gasWSRespVO", gasWSRespVO);
                    map.put("type", 1);
                    WSSiteServer.sendInfo(JSON.toJSONString(map));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(orgId!=null&&zoneId==null){
                List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
                for (Integer deptId : deptIds) {
                    if(groupId.equals(deptId)){
                        try {
                            map.put("gasWSRespVO", gasWSRespVO);
                            map.put("type", 1);
                            WSSiteServer.sendInfo(JSON.toJSONString(map));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if(orgId==null&&zoneId!=null){
                List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
                List<BaseStation> baseStations = baseStationService.findBaseStationByZoneIds(zoneIds);
                for (BaseStation baseStation : baseStations) {
                    //该终端所连基站
                    if(stationId.equals(baseStation.getBaseStationNum())){
                        try {
                            map.put("gasWSRespVO", gasWSRespVO);
                            map.put("type", 1);
                            WSSiteServer.sendInfo(JSON.toJSONString(map));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(orgId!=null&&zoneId!=null){
                List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
                List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
                List<BaseStation> baseStations = baseStationService.findBaseStationByZoneIds(zoneIds);
                for (Integer deptId : deptIds) {
                    for (BaseStation baseStation : baseStations) {
                        //该终端所连基站
                        if (stationId.equals(baseStation.getBaseStationNum()) && groupId.equals(deptId)) {
                            try {
                                map.put("gasWSRespVO", gasWSRespVO);
                                map.put("type", 1);
                                WSSiteServer.sendInfo(JSON.toJSONString(map));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            //--------------------------定位部门、区域筛选结束----------------------------------

            list.add(gasWSRespVO);

            if (list.size() >= 10) {
                JSONArray jsonArray = new JSONArray();
                HashMap<String,Object> gasNumMap=rtGasInfoMapper.selectAllGasFaultNum();
                HashMap<String, Object> mapJson = new HashMap<>();
                mapJson.put("code",2);
                mapJson.put("data",gasNumMap);


                try {
                    for (GasWSRespVO vo : list) {
                        jsonArray.add(vo);
                    }
                    //气体故障推送:大数据页面
                    WSBigDataServer.sendInfo(json.writeValueAsString(mapJson));

                    //--------------------------气体部门、区域筛选开始----------------------------------
                    Integer orgIdGas = WSServer.orgId;
                    Integer zoneIdGas = WSServer.zoneId;


                    if(orgIdGas==null&&zoneIdGas==null){
                        try {
                            WSServer.sendInfo(jsonArray.toJSONString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(orgIdGas!=null&&zoneIdGas==null){
                        List<Integer>   deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
                        for (Integer deptId : deptIds) {
                            if(groupId.equals(deptId)){
                                try {
                                    WSServer.sendInfo(jsonArray.toJSONString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    if(orgIdGas==null&&zoneIdGas!=null){
                        List<Integer>  zoneIds = partitionService.getSonIdsById(zoneId);
                        List<BaseStation> baseStations    = baseStationService.findBaseStationByZoneIds(zoneIds);
                        for (BaseStation baseStation : baseStations) {
                            //该终端所连基站
                            if(stationId.equals(baseStation.getBaseStationNum())){
                                try {
                                    WSServer.sendInfo(jsonArray.toJSONString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if(orgIdGas!=null&&zoneIdGas!=null){
                        List<Integer>   deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
                        List<Integer>  zoneIds = partitionService.getSonIdsById(zoneId);
                        List<BaseStation> baseStations    = baseStationService.findBaseStationByZoneIds(zoneIds);
                        for (Integer deptId : deptIds) {
                            for (BaseStation baseStation : baseStations) {
                                //该终端所连基站
                                if (stationId.equals(baseStation.getBaseStationNum()) && groupId.equals(deptId)) {
                                    try {
                                        WSServer.sendInfo(jsonArray.toJSONString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    //--------------------------气体部门、区域筛选结束----------------------------------


                    list.clear();
                    jsonArray.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeOtherException(ResultEnum.WEBSOCKET_SEND_ERROR);
                }
            }


        }
        private void processT(List<ConsumerRecord<?, ?>> r){
                Thread thread = Thread.currentThread();
                logger.error("ThreadId: {}" , thread.getId());
            for (ConsumerRecord<?, ?> item : r) {
                Optional<?> kafkaMessage = Optional.ofNullable(item.value());
                logger.info("Received: " + item);
                if (kafkaMessage.isPresent()) {
//        //判断是否NULL
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//            //获取消息
//            Object message = kafkaMessage.get();
//            String str = (String) message;
//                    JSONObject jsonObject = JSON.parseObject(str);
//                    JSONObject gasInfo = jsonObject.getJSONObject("gasInfo");
//
//                    double co = gasInfo.getDouble("co");
//                    Integer coFlag = gasInfo.getInteger("coFlag");
//                    double co2 = gasInfo.getDouble("co2");
//                    Integer co2Flag = gasInfo.getInteger("co2Flag");
//                    double t = gasInfo.getDouble("t");
//                    Integer tFlag = gasInfo.getInteger("tFlag");
//                    double h = gasInfo.getDouble("h");
//                    Integer hFlag = gasInfo.getInteger("hFlag");
//                    double ch4 = gasInfo.getDouble("ch4");
//                    Integer ch4Flag = gasInfo.getInteger("ch4Flag");
//                    double o2 = gasInfo.getDouble("o2");
//                    Integer o2Flag = gasInfo.getInteger("o2Flag");
//                    Boolean isWarn = gasInfo.getBoolean("isWarn");
//
//                    Date rt = jsonObject.getDate("rT");
//                    Date createTime = jsonObject.getDate("createTime");
//                    Integer sequenceId = jsonObject.getInteger("sequenceId");
//                    String stationIp = jsonObject.getString("stationIp");
//                    Integer stationId = jsonObject.getInteger("stationId");
//                    Integer terminalId = jsonObject.getInteger("terminalId");
//                    String terminalIp = jsonObject.getString("terminalIp");
//
//                    JSONObject rssiObj = jsonObject.getJSONObject("rssiInfo");
//                    Integer terminalId_t = rssiObj.getInteger("terminalId");// 关于定位的终端ID
//                    Integer stationId1 = rssiObj.getInteger("stationId1");
//                    Integer stationId2 = rssiObj.getInteger("stationId2");
//                    double rssi1 = rssiObj.getDouble("rssi1");
//                    double rssi2 = rssiObj.getDouble("rssi2");
//
//                    TerminalRoad road = rstl.locateConvert(terminalId, stationId1, stationId2, rssi1, rssi2);
//
//                    GasWSRespVO staff = staffService.findStaffNameByTerminalId(terminalId);
//                    Integer staffId = staff.getStaffId();
//
//                    //---------------------判断该员工是否在限制区域，滞留时间过长，则报警开始-----------------------
//                    WarningAreaRecordExample example = new WarningAreaRecordExample();
//                    WarningAreaRecordExample.Criteria criteria = example.createCriteria();
//                    criteria.andStaffIdEqualTo(staffId);
//                    criteria.andOutTimeIsNull();
//                    List<WarningAreaRecord> records = warningAreaRecordMapper.selectByExample(example);
//                    if (records != null && records.size() > 0) {
//                        WarningAreaRecord record = records.get(0);
//                        WarningArea warningArea = warningAreaMapper.selectByPrimaryKey(record.getWarningAreaId());
//                      if(warningArea!=null){
//                          if(warningArea.getWarningAreaType()==1){
//                              //在重点区域内
//                                road.setIsOre(3);
//                          }else{
//                              road.setIsOre(4);
//                              //在限制区域内
//                              String time = warningArea.getResidenceTime();
//                              double residenceTime = Double.parseDouble(time);//单位分钟
//                              Date now = new Date();
//                              long realLong = now.getTime() - record.getInTime().getTime();
//                              if (realLong > residenceTime) {
//                                  WebSocketData data = new WebSocketData();
//                                  data.setType(7);
//                                  HashMap<String, Object> map = new HashMap<>();
//                                  map.put("code", 2);
//                                  //查询报警人员的信息
//                                  String staffName = staff.getStaffName();
//                                  Staff staff1 = staffService.findStaffById(staffId);
//                                  Integer groupId = staff1.getGroupId();
//                                  String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
//                                  HashMap<Object, Object> staffInfo = new HashMap<>();
//                                  staffInfo.put("staffId", staffId);
//                                  staffInfo.put("staffName", staffName);
//                                  staffInfo.put("deptName", deptName);
//                                  staffInfo.put("areaName", warningArea.getWarningAreaName());
//                                  //封装时长
//                                  long nd = 1000 * 24 * 60 * 60;
//                                  long nh = 1000 * 60 * 60;
//                                  long nm = 1000 * 60;
//                                  long diff = realLong;
//                                  long day = diff / nd;
//                                  long hour = diff % nd / nh;
//                                  long min = diff % nd % nh / nm;
//                                  staffInfo.put("timeLong", day + "天" + hour + "小时" + min + "分钟");
//                                  map.put("data", staffInfo);
//                                  data.setData(map);
//                                  try {
//                                      WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
//                                  } catch (IOException e) {
//                                      e.printStackTrace();
//                                  }
//                              }
//
//                          }
//                      }
//
//
//                    }
//                    //----------------------判断该员工是否在限制区域，滞留时间过长，则报警结束-----------------------
//
////            StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByTerminalId(terminalId);
////            road.setStaffId(staffTerminalRelation.getStaffTerminalRelationId());
//                    road.setStaffId(staffId);
//                    road.setStationId(stationId);
//                    //--------------------------------判断员工是否是出矿入矿---------------------------------
//                    Map<String, Object> entryStation = baseStationService.findBaseStationByType(1);
//                    Map<String, Object> attendanceStation = baseStationService.findBaseStationByType(2);
//                    //井口基站编号
//                    Integer entryId = (Integer) entryStation.get("baseStationNum");
//                    //考勤基站编号
//                    Integer attendanceId = (Integer) attendanceStation.get("baseStationNum");
//                    //连接基站为井口基站或者考勤基站，保存进队列，存储map中
//                    if (stationId.equals(entryId) || stationId.equals(attendanceId)) {
//                        ArrayQueue<TerminalRoad> terminalUpdateIpQueue = attendanceMap.get(terminalId);
//                        //判断队列是否为空
//                        if (terminalUpdateIpQueue == null) {
//                            terminalUpdateIpQueue = new ArrayQueue<>(2);
//                        }
//                        //此次更新ip对象保存到队列
//                        terminalUpdateIpQueue.add(road);
//                        attendanceMap.put(terminalId, terminalUpdateIpQueue);
//                        //判断是入矿还是出矿
//                        if (terminalUpdateIpQueue.getQueueSize() == 2) {
//                            TerminalRoad head = terminalUpdateIpQueue.getHead();
//                            TerminalRoad end = terminalUpdateIpQueue.getEnd();
//                            //入矿
//                            if (head.getStationId().equals(entryId) && end.getStationId().equals(attendanceId)) {
//                                road.setIsOre(1);
//
//                            }
//                            //出矿
//                            if (head.getStationId().equals(attendanceId) && end.getStationId().equals(entryId)) {
//                                road.setIsOre(2);
//                            }
//                            teminalRoadMapper.insert(road);
//                        }
//                    } else {
//                        road.setIsOre(0);
//                        teminalRoadMapper.insert(road);
//                    }
//
//                    //---------------------------------------判断员工是否超时未上井开始------------------------------------
//                    StaffAttendanceRealRule realRule = staffAttendanceRealRuleMapper.selectByPrimaryKey(staffId);
//                    TimeStandard standard = attendanceService.getTimeStandardByStaffId(staffId);
//                    //超时时长
//                    Integer overTime = standard.getOverTime();
//                    //严重超时时长
//                    Integer seriousTime = standard.getSeriousTime();
//                    //本应下班时间
//                    Date endTime = realRule.getRealRuleEndTime();
//                    Date over_time = new Date();
//                    Date serious_time = new Date();
//                    //超时时间
//                    over_time.setTime(endTime.getTime() + overTime * 60 * 60 * 1000);
//                    //严重超时时间
//                    serious_time.setTime(endTime.getTime() + seriousTime * 60 * 60 * 1000);
//
//                    Date date = new Date();
//                    WebSocketData data = new WebSocketData();
//
//                    if (serious_time.before(date)) {
//
//                        try {
//                            boolean contains = overTimeSet.contains(staffId);
//                            if (!contains) {
//                                //严重超时
//                                data.setType(2);
//                                data.setData(1);
//                                //标记严重超时的人
//                                realRule.setIsOverTime(0);
//                                realRule.setIsSeriousTime(1);
//                                realRule.setFinalTime(new Date());
//                                staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
//                                overTimeSet.add(staffId);
//                                WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
//                            }
//                            //严重超时加1
//
//                  /*  //超时-1
//                    data.setType(1);
//                    data.setData(-1);
//                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));*/
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else if (over_time.before(date)) {
//                        boolean contains = seriousTimeSet.contains(staffId);
//                        if (!contains) {
//                            seriousTimeSet.add(staffId);
//                            //超时
//                            data.setType(1);
//                            data.setData(1);
//                            //标记超时的人
//                            realRule.setIsOverTime(1);
//                            realRule.setFinalTime(new Date());
//                            staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
//                            try {
//                                WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//
//
//                    //---------------------------------------判断员工是否超时未上井结束------------------------------------
//
//                    //TODO 根据 terminalId 查找人名称
//                    //            System.out.println(co + " co " + ch4 + "  t  " + t + " h " + h + "   co2 " + co2 + "  o2  " + o2);
//                    GasWSRespVO gasWSRespVO = new GasWSRespVO();
//                    gasWSRespVO.setO2(o2);
//                    gasWSRespVO.setO2_type(o2Flag);
//                    gasWSRespVO.setTemperature(t);
//                    gasWSRespVO.setTemperature_type(tFlag);
//                    gasWSRespVO.setHumidity(h);
//                    gasWSRespVO.setHumidity_type(hFlag);
//                    gasWSRespVO.setCo2(co2);
//                    gasWSRespVO.setCo2_type(co2Flag);
//                    gasWSRespVO.setCh4(ch4);
//                    gasWSRespVO.setCh4_type(ch4Flag);
//                    gasWSRespVO.setSequenceId(sequenceId);
//                    gasWSRespVO.setCo(co);
//                    gasWSRespVO.setCo_type(coFlag);
//                    gasWSRespVO.setStaffId(staff.getStaffId());
//                    gasWSRespVO.setStaffName(staff.getStaffName());
//                    gasWSRespVO.setRt(rt);
//                    gasWSRespVO.setCreateTime(createTime);
//                    gasWSRespVO.setIsPerson(staff.getIsPerson());
//                    gasWSRespVO.setGroupName(staff.getGroupName());
//                    gasWSRespVO.setDeptName(staff.getDeptName());
//
//                    String tempPositionName = road.getTempPositionName();
//                    Integer terminalRoadId = road.getTerminalRoadId();
//                    gasWSRespVO.setTempRoadName(tempPositionName);
//                    gasWSRespVO.setTerminalRoadId(terminalRoadId);
//
//                    gasWSRespVO.setTerminalRoad(road);
//
//
//                    //封装插入数据库
//                    RtGasInfo rtGasInfo = new RtGasInfo();
//
//                    rtGasInfo.setHumidity(h);
//                    rtGasInfo.setTemperature(t);
//                    rtGasInfo.setTerminalRealTime(rt);
//                    rtGasInfo.setTerminalIp(terminalIp);
//                    rtGasInfo.setTerminalId(terminalId);
//                    rtGasInfo.setStationIp(stationIp);
//                    rtGasInfo.setStationId(stationId);
//                    rtGasInfo.setO2(o2);
//                    rtGasInfo.setCo(co);
//                    rtGasInfo.setCo2(co2);
//                    rtGasInfo.setCh4(ch4);
//                    rtGasInfo.setInfoType(true);
//                    rtGasInfo.setSequenceId(sequenceId);
//                    rtGasInfo.setCreateTime(createTime);
//
//                    rtGasInfo.setCh4Unit(ch4Flag);
//                    rtGasInfo.setCoUnit(coFlag);
//                    rtGasInfo.setHumidityUnit(hFlag);
//                    rtGasInfo.setTemperatureUnit(tFlag);
//                    rtGasInfo.setO2Unit(o2Flag);
//                    rtGasInfo.setCo2Unit(co2Flag);
//
//                    rtGasInfo.setPositionId(road.getTerminalRoadId());
////            rtGasInfo.setCh4(upLoadGasDto ''to.getH());
//                    rtGasInfoMapper.insert(rtGasInfo);
//                    //警告气体插入警告气体表
//                    if (isWarn) {
//                        Integer rtGasInfoId = rtGasInfo.getRtGasInfoId();
//                        RtGasInfoWarn warn = new RtGasInfoWarn();
//                        warn.setRtGasInfoId(rtGasInfoId);
//                        rtGasInfoWarnMapper.insert(warn);
//                    }
//                    //System.out.println(rtGasInfo.getRtGasInfoId());
//
////            if (rtGasInfo.getStationId() == 2) {
////               listOfCoW.add(rtGasInfo.getCo());
////               listOfCh4W.add(rtGasInfo.getCh4());
////            }
////            if (rtGasInfo.getStationId() == 3){
////                listOfCoR.add(rtGasInfo.getCo());
////                listOfCh4R.add(rtGasInfo.getCh4());
////            }
////            if (listOfCh4R.size() == 3 || listOfCh4W.size() == 3 || listOfCoR.size() == 3 || listOfCoW.size() == 3){
////                for (double item: listOfCh4R)
////                    avgCh4OfRoad += item;
////                for (double item: listOfCh4W)
////                    avgCh4OfWork += item;
////                for (double item: listOfCoW)
////                    avgCoOfWork += item;
////                for (double item: listOfCoR)
////                    avgCoOfRoad += item;
////                avgCh4OfRoad = avgCh4OfRoad / 3.0;
////                avgCoOfRoad = avgCoOfRoad / 3.0;
////                avgCh4OfWork = avgCh4OfWork / 3.0;
////                avgCoOfWork = avgCoOfWork / 3.0;
////
////                Map<String,Object> resultOfCo = new HashMap<>();
////                Map<String,Object> dataOfCo = new HashMap<>();
////                Map<String,Object> resultOfCh4 = new HashMap<>();
////                Map<String,Object> dataOfCh4 = new HashMap<>();
////                resultOfCo.put("code",0);
////                resultOfCh4.put("code",1);
////                dataOfCo.put("work",avgCoOfWork);
////                dataOfCo.put("road",avgCoOfRoad);
////                dataOfCo.put("time",Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
////                dataOfCh4.put("work",avgCh4OfWork);
////                dataOfCh4.put("road",avgCh4OfRoad);
////                dataOfCh4.put("time",Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));
////                resultOfCo.put("data",dataOfCo);
////                resultOfCh4.put("data",dataOfCh4);
////                String coResult = JSON.toJSONString(resultOfCo);
////                String ch4Result = JSON.toJSONString(resultOfCh4);
////
////                try {
////                    WSBigDataServer.sendInfo(ch4Result);
////                    WSBigDataServer.sendInfo(coResult);
////                    listOfCoR.clear();
////                    listOfCoW.clear();
////                    listOfCh4W.clear();
////                    listOfCh4R.clear();
////                    avgCh4OfRoad = 0;
////                    avgCh4OfWork = 0;
////                    avgCoOfRoad = 0;
////                    avgCoOfWork = 0;
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
//
//                    //--------------------------定位部门、区域筛选开始----------------------------------
//                    Staff staff1 = staffService.findStaffById(staffId);
//                    Integer groupId = staff1.getGroupId();
//                    Integer orgId = WSSiteServer.orgId;
//                    Integer zoneId = WSSiteServer.zoneId;
//                    Map<String, Object> map = new HashMap<>();
//
//
//                    if (orgId == null && zoneId == null) {
//
//                        try {
//                            map.put("gasWSRespVO", gasWSRespVO);
//                            map.put("type", 1);
//                            WSSiteServer.sendInfo(JSON.toJSONString(map));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (orgId != null && zoneId == null) {
//                        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
//                        for (Integer deptId : deptIds) {
//                            if (groupId.equals(deptId)) {
//                                try {
//                                    map.put("gasWSRespVO", gasWSRespVO);
//                                    map.put("type", 1);
//                                    WSSiteServer.sendInfo(JSON.toJSONString(map));
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//
//                    if (orgId == null && zoneId != null) {
//                        List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
//                        List<BaseStation> baseStations = baseStationService.findBaseStationByZoneIds(zoneIds);
//                        for (BaseStation baseStation : baseStations) {
//                            //该终端所连基站
//                            if (stationId.equals(baseStation.getBaseStationNum())) {
//                                try {
//                                    map.put("gasWSRespVO", gasWSRespVO);
//                                    map.put("type", 1);
//                                    WSSiteServer.sendInfo(JSON.toJSONString(map));
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                    if (orgId != null && zoneId != null) {
//                        List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
//                        List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
//                        List<BaseStation> baseStations = baseStationService.findBaseStationByZoneIds(zoneIds);
//                        for (Integer deptId : deptIds) {
//                            for (BaseStation baseStation : baseStations) {
//                                //该终端所连基站
//                                if (stationId.equals(baseStation.getBaseStationNum()) && groupId.equals(deptId)) {
//                                    try {
//                                        map.put("gasWSRespVO", gasWSRespVO);
//                                        map.put("type", 1);
//                                        WSSiteServer.sendInfo(JSON.toJSONString(map));
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    //--------------------------定位部门、区域筛选结束----------------------------------
//
//                    list.add(gasWSRespVO);
//
//                    if (list.size() >= 10) {
//                        JSONArray jsonArray = new JSONArray();
//                        HashMap<String, Object> gasNumMap = rtGasInfoMapper.selectAllGasFaultNum();
//                        HashMap<String, Object> mapJson = new HashMap<>();
//                        mapJson.put("code", 2);
//                        mapJson.put("data", gasNumMap);
//
//
//                        try {
//                            for (GasWSRespVO vo : list) {
//                                jsonArray.add(vo);
//                            }
//                            //气体故障推送:大数据页面
//                            WSBigDataServer.sendInfo(json.writeValueAsString(mapJson));
//
//                            //--------------------------气体部门、区域筛选开始----------------------------------
//                            Integer orgIdGas = WSServer.orgId;
//                            Integer zoneIdGas = WSServer.zoneId;
//
//
//                            if (orgIdGas == null && zoneIdGas == null) {
//                                try {
//                                    WSServer.sendInfo(jsonArray.toJSONString());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            if (orgIdGas != null && zoneIdGas == null) {
//                                List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
//                                for (Integer deptId : deptIds) {
//                                    if (groupId.equals(deptId)) {
//                                        try {
//                                            WSServer.sendInfo(jsonArray.toJSONString());
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                            }
//
//                            if (orgIdGas == null && zoneIdGas != null) {
//                                List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
//                                List<BaseStation> baseStations = baseStationService.findBaseStationByZoneIds(zoneIds);
//                                for (BaseStation baseStation : baseStations) {
//                                    //该终端所连基站
//                                    if (stationId.equals(baseStation.getBaseStationNum())) {
//                                        try {
//                                            WSServer.sendInfo(jsonArray.toJSONString());
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                            }
//                            if (orgIdGas != null && zoneIdGas != null) {
//                                List<Integer> deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
//                                List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
//                                List<BaseStation> baseStations = baseStationService.findBaseStationByZoneIds(zoneIds);
//                                for (Integer deptId : deptIds) {
//                                    for (BaseStation baseStation : baseStations) {
//                                        //该终端所连基站
//                                        if (stationId.equals(baseStation.getBaseStationNum()) && groupId.equals(deptId)) {
//                                            try {
//                                                WSServer.sendInfo(jsonArray.toJSONString());
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            //--------------------------气体部门、区域筛选结束----------------------------------
//
//
//                            list.clear();
//                            jsonArray.clear();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            throw new RuntimeOtherException(ResultEnum.WEBSOCKET_SEND_ERROR);
//                        }
//                    }

                }
            }
    }



}
