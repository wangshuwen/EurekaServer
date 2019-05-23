package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeOtherException;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.utils.array.ArrayQueue;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.*;
import com.cst.xinhe.kafka.consumer.service.service.RSTL;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.rt_gas.GasPositionMapper;
import com.cst.xinhe.persistence.dao.rt_gas.GasPositionWarnMapper;
import com.cst.xinhe.persistence.dao.rt_gas.RtGasInfoMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.station_standard_relation.StationStandardRelationMapper;
import com.cst.xinhe.persistence.dao.terminal_road.TerminalRoadMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaRecordMapper;
import com.cst.xinhe.persistence.dto.GasInfo;
import com.cst.xinhe.persistence.dto.RssiInfo;
import com.cst.xinhe.persistence.dto.UpLoadGasDto;
import com.cst.xinhe.persistence.dto.warn_level_setting.GasWarnSettingDto;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.rt_gas.GasPosition;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.station_standard_relation.StationStandardRelation;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.model.warn_level.GasStandard;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecordExample;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/24/11:05
 */
@Component
public class GasKafka extends BaseLog {
    //存储更新基站 队列
    public static Map<Integer, ArrayQueue<TerminalRoad>> attendanceMap=new HashMap<>();

    @Resource
    private SystemServiceClient systemServiceClient;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private AttendanceServiceClient attendanceServiceClient;

    @Resource
    private StationPartitionServiceClient stationPartitionServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Resource
    private GasServiceClient gasServiceClient;

    @Resource
    private BaseStationMapper baseStationMapper;


    @Resource
    private WarningAreaMapper warningAreaMapper;

    @Resource
    StationStandardRelationMapper stationStandardRelationMapper;

    @Resource
    private WarningAreaRecordMapper warningAreaRecordMapper;

    @Resource
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;

    @Resource
    RtGasInfoMapper rtGasInfoMapper;

    @Resource
    TimeStandardMapper timeStandardMapper;

    @Resource
    GasPositionMapper gasPositionMapper;

//    @Resource
//    LevelSettingService levelSettingService;
//    @Resource
//    private PartitionService partitionService;

    public static List<GasWSRespVO> list = new ArrayList<>();

    private static int gasNum = 0;

    private static List<GasPosition> gasPositions = Collections.synchronizedList(new ArrayList<>());


    public static Set<Integer> overTimeSet= Collections.synchronizedSet(new HashSet());
    public static Set<Integer> seriousTimeSet= Collections.synchronizedSet(new HashSet());

//    @Resource
//    ProducerService producerService;
//    private Map<String, Object> map;

//    ObjectMapper json = new ObjectMapper();
//    @Resource
//    private StaffOrganizationService staffOrganizationService;

//    @Resource
//    private BaseStationService baseStationService;

//    @Resource
//    private AttendanceService attendanceService;

    @Resource
    private StaffMapper staffMapper;


//    @Resource
//    private TerminalRoadMapper teminalRoadMapper;

    //@Autowired
    //KafkaTemplate<String, Object> kafkaTemplate;

//    @Resource
//    KafkaSender kafkaSender;

//    @Resource
//    StaffService staffService;

    private volatile boolean isWarn = false;

    @Resource
    RSTL rstl;

//    @Resource
//    GasPositionWarnMapper gasPositionWarnMapper;

    private static final String TOPIC = "gas_kafka.tut";

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private void process(List<ConsumerRecord<?, ?>> records){
        executorService.execute(() -> {
            for (ConsumerRecord<?, ?> record : records) {
                Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                logger.info("Received: " + record);
                if (kafkaMessage.isPresent()) {
                    Object message = record.value();

                    String str = (String) message;
                    JSONObject jsonObject = JSON.parseObject(str);
                    RequestData requestData = RequestData.getInstance();
                    requestData.setTime(jsonObject.getDate("time"));
                    requestData.setBody(jsonObject.getBytes("body"));
                    requestData.setLength(jsonObject.getInteger("length"));
                    requestData.setResult(jsonObject.getByteValue("result"));
                    requestData.setCmd(jsonObject.getInteger("cmd"));
                    requestData.setStationIp(jsonObject.getString("stationIp"));
                    requestData.setStationIp1(jsonObject.getInteger("stationIp1"));
                    requestData.setStationIp2(jsonObject.getInteger("stationIp2"));
                    requestData.setNdName(jsonObject.getInteger("ndName"));
                    requestData.setSequenceId(jsonObject.getInteger("sequenceId"));
                    requestData.setStationId(jsonObject.getInteger("stationId"));
                    requestData.setStationPort(jsonObject.getInteger("stationPort"));

                    requestData.setTerminalIp1(jsonObject.getInteger("terminalIp1"));
                    requestData.setTerminalIp2(jsonObject.getInteger("terminalIp2"));
                    requestData.setTerminalIp(jsonObject.getString("terminalIp"));
                    requestData.setTerminalPort(jsonObject.getInteger("terminalPort"));

                    requestData.setTerminalId(jsonObject.getInteger("terminalId"));

                    GasPosition gasPosition = new GasPosition();
                    byte[] body = requestData.getBody();

                    GasInfo gasInfo = GasInfo.getInstance();
                    double co0 = ((long) (((body[0] & 0xff) << 8) + (body[1] & 0xff)) / 10.0);
                    gasInfo.setCo(co0);
                    gasPosition.setCo(co0);
                    gasInfo.setCoFlag(body[2]);
                    gasPosition.setCoUnit((int)body[2]);
                    double co20 = ((long) (((body[3] & 0xff) << 8) + (body[4] & 0xff)) / 10.0);

                    gasInfo.setCo2(co20);
                    gasPosition.setCo2(co20);
                    gasInfo.setCo2Flag(body[5]);
                    gasPosition.setCo2Unit((int)body[5]);
                    double o20 = ((long) (((body[6] & 0xff) << 8) + (body[7] & 0xff)) / 10.0);
                    gasInfo.setO2(o20);
                    gasPosition.setO2(o20);
                    gasInfo.setO2Flag(body[8]);
                    gasPosition.setO2Unit((int)body[8]);

                    double ch40 = ((long) (((body[9] & 0xff) << 8) + (body[10] & 0xff)) / 10.0);
                    gasInfo.setCh4(ch40);
                    gasPosition.setCh4(ch40);
                    gasInfo.setCh4Flag(body[11]);
                    gasPosition.setCh4Unit((int)body[11]);
                    /**
                     * 判断温度数据的零上零下问题
                     */
                    double t0 = ((long) (((body[12] & 0xff) << 8) + (body[13] & 0xff)) / 10.0);
                    byte flag = (byte) (body[14] & 0xff);
                    if (flag == 0x00) {
                        gasInfo.setT(t0);
                        gasPosition.setTemperature(t0);
                        gasInfo.settFlag(0);
                        gasPosition.setTemperatureUnit(0);
                    }
                    if (flag == 0x01) {
                        gasInfo.setT(t0);
                        gasPosition.setTemperature(t0);
                        gasInfo.settFlag(1);
                        gasPosition.setTemperatureUnit(1);
                    }
                    if (flag == 0x10) {
                        gasInfo.setT(-t0);
                        gasPosition.setTemperature(-t0);
                        gasInfo.settFlag(0);
                        gasPosition.setTemperatureUnit(0);
                    }
                    if (flag == 0x11) {
                        gasInfo.setT(-t0);
                        gasPosition.setTemperature(-t0);
                        gasPosition.setTemperatureUnit(1);
                        gasInfo.settFlag(1);
                    }
                    double h0 = ((long) (((body[15] & 0xff) << 8) + (body[16] & 0xff)) / 10.0);
                    gasInfo.setH(h0);
                    gasPosition.setHumidity(h0);
                    gasInfo.sethFlag(body[17]);
                    gasPosition.setHumidityUnit((int)body[17]);
                    Integer baseStation1 = ((body[18] & 0xff) << 8) + (body[19] & 0xff);
                    gasPosition.setStationId1(baseStation1);
                    Integer int_rssi1 = body[20] & 0xff;
                    Integer decimal_rssi1 = body[21] & 0xff;
                    String t_rssi1 = int_rssi1 +
                            "." +
                            decimal_rssi1;
                    double rssi1 = Double.parseDouble(t_rssi1);
                    gasPosition.setWifiStrength1(rssi1);
                    Integer baseStation2 = ((body[22] & 0xff) << 8) + (body[23] & 0xff);
                    gasPosition.setStationId2(baseStation2);
                    Integer int_rssi2 = body[24] & 0xff;
                    Integer decimal_rssi2 = body[25] & 0xff;
                    String t_rssi2 = int_rssi2 +
                            "." +
                            decimal_rssi2;
                    double rssi2 = Double.parseDouble(t_rssi2);
                    gasPosition.setWifiStrength2(rssi2);
                    UpLoadGasDto upLoadGasDto = UpLoadGasDto.getInstance();

                    upLoadGasDto.setSequenceId(requestData.getSequenceId());

                    gasPosition.setSequenceId(requestData.getSequenceId());

                    upLoadGasDto.setRT(DateConvert.convert(requestData.getTime(), 19));

                    gasPosition.setTerminalRealTime(requestData.getTime());

                    upLoadGasDto.setTerminalIp(requestData.getTerminalIp());

                    gasPosition.setTerminalIp(requestData.getTerminalIp());

                    upLoadGasDto.setCreateTime(DateConvert.convert(new Date(), 19));

                    gasPosition.setCreateTime(new Date());
//        gasPosition.setCreateTime(DateConvert.convertStampToDate(String.valueOf(System.currentTimeMillis()),19));

                    upLoadGasDto.setStationId(requestData.getStationId());
                    gasPosition.setStationId(requestData.getStationId());

                    upLoadGasDto.setStationIp(requestData.getStationIp());
                    gasPosition.setStationIp(requestData.getStationIp());

                    upLoadGasDto.setTerminalId(requestData.getTerminalId());
                    gasPosition.setTerminalId(requestData.getTerminalId());


                    upLoadGasDto.setGasInfo(gasInfo);


                    RssiInfo rssiInfo = RssiInfo.getInstance();
                    rssiInfo.setUpLoadGasDto(upLoadGasDto);
                    rssiInfo.setTerminalId(requestData.getTerminalId());
                    rssiInfo.setStationId1(baseStation1);
                    rssiInfo.setRssi1(rssi1);
                    rssiInfo.setStationId2(baseStation2);
                    rssiInfo.setRssi2(rssi2);
                    //封装返回的气体信息
                    upLoadGasDto.setRssiInfo(rssiInfo);

                    TerminalRoad road = rstl.locateConvert(gasPosition.getTerminalId(), baseStation1, baseStation2, rssi1, rssi2);

                    gasPosition.setInfoType(0);
                    gasPosition.setTempPositionName(road.getTempPositionName());
                    gasPosition.setPositionX(road.getPositionX());
                    gasPosition.setPositionY(road.getPositionY());
                    gasPosition.setPositionZ(road.getPositionZ());
                    road.setStationId(gasPosition.getStationId());


                    //----------------------------------以下是判断出入问题------------------------------------
            GasWSRespVO staff = staffGroupTerminalServiceClient.findStaffNameByTerminalId(gasPosition.getTerminalId());
                    Integer staffId = staff.getStaffId();
                    gasPosition.setStaffId(staffId);
                    gasPosition.setStaffName(staff.getStaffName());
                    //---------------------判断该员工是否在限制区域，滞留时间过长，则报警开始-----------------------
                    WarningAreaRecordExample example = WarningAreaRecordExample.getInstance();
                    WarningAreaRecordExample.Criteria criteria = example.createCriteria();
                    criteria.andStaffIdEqualTo(staffId);
                    criteria.andOutTimeIsNull();
                    List<WarningAreaRecord> warningAreaRecords = warningAreaRecordMapper.findWarningAreaRecordByStaffAndOutTime(staffId);
                    if (null != warningAreaRecords && warningAreaRecords.size() > 0) {
                        WarningAreaRecord warningAreaRecord = warningAreaRecords.get(0);
                        WarningArea warningArea = warningAreaMapper.selectByPrimaryKey(warningAreaRecord.getWarningAreaId());
                        if(warningArea!=null){
                            if(warningArea.getWarningAreaType()==1){
                                //在重点区域内
                                road.setIsOre(3);
                                gasPosition.setIsOre(3);
                            }else{
                                road.setIsOre(4);
                                gasPosition.setIsOre(4);
                                //在限制区域内
                                String time = warningArea.getResidenceTime();
                                double residenceTime = Double.parseDouble(time);//单位分钟
                                Date now = new Date();
                                long realLong = now.getTime() - warningAreaRecord.getInTime().getTime();
                                if (realLong > residenceTime) {
                                    WebSocketData data = WebSocketData.getInstance();
                                    data.setType(7);
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("code", 2);
                                    //查询报警人员的信息
                                    String staffName = staff.getStaffName();
                                    Staff staff1 = staffMapper.selectByPrimaryKey(staffId);
                                    Integer groupId = staff1.getGroupId();
//                                    String deptName = staffOrganizationService.getDeptNameByGroupId(groupId);
                                    String deptName = staffGroupTerminalServiceClient.getDeptNameByGroupId(groupId);
                                    HashMap<Object, Object> staffInfo = new HashMap<>();
                                    staffInfo.put("staffId", staffId);
                                    staffInfo.put("staffName", staffName);
                                    staffInfo.put("deptName", deptName);
                                    staffInfo.put("areaName", warningArea.getWarningAreaName());
                                    //封装时长
                                    long nd = 1000 * 24 * 60 * 60;
                                    long nh = 1000 * 60 * 60;
                                    long nm = 1000 * 60;
                                    long diff = realLong;
                                    long day = diff / nd;
                                    long hour = diff % nd / nh;
                                    long min = diff % nd % nh / nm;
                                    staffInfo.put("timeLong", day + "天" + hour + "小时" + min + "分钟");
                                    map.put("data", staffInfo);
                                    data.setData(map);
                                    try {
                                        wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    //------------------------判断出入问题结束--------------------------------------

                    //--------------------------------判断员工是否是出矿入矿---------------------------------
                    Map<String, Object> entryStation = baseStationMapper.selectBaseStationByType(1);
//        Map<String, Object> attendanceStation = baseStationService.findBaseStationByType(2);
                    Map<String, Object> attendanceStation = baseStationMapper.selectBaseStationByType(2);
                    //井口基站编号
                    Integer entryId = (Integer) entryStation.get("baseStationNum");
                    //考勤基站编号
                    Integer attendanceId = (Integer) attendanceStation.get("baseStationNum");

                    Integer stationId = gasPosition.getStationId();
                    Integer terminalId = gasPosition.getTerminalId();
                    //连接基站为井口基站或者考勤基站，保存进队列，存储map中
                    if (stationId.equals(entryId) || stationId.equals(attendanceId)) {
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
                                gasPosition.setIsOre(1);
                            }
                            //出矿
                            if (head.getStationId().equals(attendanceId) && end.getStationId().equals(entryId)) {
                                road.setIsOre(2);
                                gasPosition.setIsOre(2);
                            }
                        }
                    } else {
                        road.setIsOre(0);
                        gasPosition.setIsOre(0);
                    }


                    //---------------------------------------判断员工是否超时未上井开始------------------------------------
                    StaffAttendanceRealRule realRule = staffAttendanceRealRuleMapper.selectByPrimaryKey(staffId);
                    TimeStandardVO standard = timeStandardMapper.selectTimeStandardInfoByStaffId(staffId);
                    //超时时长
                    Integer overTime = standard.getOverTime();
                    //严重超时时长
                    Integer seriousTime = standard.getSeriousTime();
                    //本应下班时间
                    Date endTime = realRule.getRealRuleEndTime();
                    Date over_time = new Date();
                    Date serious_time = new Date();
                    //超时时间
                    over_time.setTime(endTime.getTime() + overTime * 60 * 60 * 1000);
                    //严重超时时间
                    serious_time.setTime(endTime.getTime() + seriousTime * 60 * 60 * 1000);

                    Date date = new Date();
                    WebSocketData data = WebSocketData.getInstance();

                    if (serious_time.before(date)) {

                        try {
                            boolean contains = overTimeSet.contains(staffId);
                            if (!contains) {
                                //严重超时
                                data.setType(2);
                                data.setData(1);
                                //标记严重超时的人
                                realRule.setIsOverTime(0);
                                realRule.setIsSeriousTime(1);
                                realRule.setFinalTime(new Date());
                                staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
                                overTimeSet.add(staffId);
                                wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                            }
                            //严重超时加1

              /*  //超时-1
                data.setType(1);
                data.setData(-1);
                WSPersonNumberServer.sendInfo(JSON.toJSONString(data));*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (over_time.before(date)) {
                        boolean contains = seriousTimeSet.contains(staffId);
                        if (!contains) {
                            seriousTimeSet.add(staffId);
                            //超时
                            data.setType(1);
                            data.setData(1);
                            //标记超时的人
                            realRule.setIsOverTime(1);
                            realRule.setFinalTime(new Date());
                            staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
                            try {
                                wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //---------------------------------------判断员工是否超时未上井结束------------------------------------
                    GasWSRespVO gasWSRespVO = GasWSRespVO.getInstance();
                    gasWSRespVO.setO2(gasPosition.getO2());
                    gasWSRespVO.setO2_type(gasPosition.getO2Unit());
                    gasWSRespVO.setTemperature(gasPosition.getTemperature());
                    gasWSRespVO.setTemperature_type(gasPosition.getTemperatureUnit());
                    gasWSRespVO.setHumidity(gasPosition.getHumidity());
                    gasWSRespVO.setHumidity_type(gasPosition.getHumidityUnit());
                    gasWSRespVO.setCo2(gasPosition.getCo2());
                    gasWSRespVO.setCo2_type(gasPosition.getCo2Unit());
                    gasWSRespVO.setCh4(gasPosition.getCh4());
                    gasWSRespVO.setCh4_type(gasPosition.getCh4Unit());
                    gasWSRespVO.setSequenceId(gasPosition.getSequenceId());
                    gasWSRespVO.setCo(gasPosition.getCo());
                    gasWSRespVO.setCo_type(gasPosition.getCoUnit());
                    gasWSRespVO.setStaffId(staff.getStaffId());
                    gasWSRespVO.setStaffName(staff.getStaffName());
                    gasWSRespVO.setRt(gasPosition.getTerminalRealTime());
                    gasWSRespVO.setCreateTime(gasPosition.getCreateTime());
                    gasWSRespVO.setIsPerson(staff.getIsPerson());
                    gasWSRespVO.setGroupName(staff.getGroupName());
                    gasWSRespVO.setDeptName(staff.getDeptName());

                    String tempPositionName = road.getTempPositionName();
                    Integer terminalRoadId = road.getTerminalRoadId();
                    gasWSRespVO.setTempRoadName(tempPositionName);
                    gasWSRespVO.setTerminalRoadId(terminalRoadId);

                    gasWSRespVO.setTerminalRoad(road);


                    //封装插入数据库

                    //警告气体插入警告气体表
                    //根据系统气体等级标准划分，判断气体是不是警报气体
                    //查找气体标准id
                    StationStandardRelation stationStandardRelation = stationStandardRelationMapper.selectStandardByStationId(requestData.getStationId());
                    if(null != stationStandardRelation){
                        Integer standardId = stationStandardRelation.getStandardId();
                        GasLevelVO gasLevelVO = systemServiceClient.getWarnLevelSettingByGasLevelId(standardId);
                        //气体标准值
                        GasStandard gasStandard = gasLevelVO.getGasStandard();
                        Double ch4Standard = gasStandard.getCh4Standard();
                        Double coStandard = gasStandard.getCoStandard();
                        Double o2Standard = gasStandard.getO2Standard();
                        Double hStandard = gasStandard.gethStandard();
                        Double tStandard = gasStandard.gettStandard();

                        //实际气体值
                        GasInfo gasInfoWarn = upLoadGasDto.getGasInfo();
                        double ch4 = gasPosition.getCh4();
                        double co = gasPosition.getCo();
                        double o2 = gasPosition.getO2();
                        double h = gasPosition.getHumidity();
                        double t = gasPosition.getTemperature();


                        //气体警报等级规则
                        //判断ch4气体等级
                        List<GasWarnSettingDto> ch4WarnSettingDto = gasLevelVO.getCh4WarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : ch4WarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if(ch4>=(multiple*ch4Standard)){
                                //警报等级大的会把等级小的覆盖掉（已排序根据multiple）
                                gasInfoWarn.setCh4Flag(gasWarnSettingDto.getLevelDataId());
                                isWarn=true;
                            }
                        }
                        //判断co气体等级
                        List<GasWarnSettingDto> coWarnSettingDto=gasLevelVO.getCoWarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : coWarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if(co>=(multiple*coStandard)){
                                gasInfoWarn.setCoFlag(gasWarnSettingDto.getLevelDataId());
                                isWarn=true;
                            }
                        }
                        //判断湿度等级
                        List<GasWarnSettingDto> hWarnSettingDto=gasLevelVO.gethWarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : hWarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if(h>=(multiple*hStandard)){
                                gasInfoWarn.sethFlag(gasWarnSettingDto.getLevelDataId());
                                isWarn=true;
                            }
                        }
                        //判断O2气体等级
                        List<GasWarnSettingDto> O2WarnSettingDto=gasLevelVO.getO2WarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : O2WarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if(o2>=(multiple*o2Standard)){
                                gasInfoWarn.setO2Flag(gasWarnSettingDto.getLevelDataId());
                                isWarn=true;
                            }
                        }
                        //判断温度气体等级
                        List<GasWarnSettingDto> tWarnSettingDto= gasLevelVO.gettWarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : tWarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if(t>=(multiple*tStandard)){
                                gasInfoWarn.settFlag(gasWarnSettingDto.getLevelDataId());
                                isWarn=true;
                            }
                        }

                        gasInfoWarn.setCo2Flag(0);

                        if(isWarn) {
                            gasPosition.setGasFlag(1);
                            gasInfoWarn.setIsWarn(true);
                            upLoadGasDto.setGasInfo(gasInfoWarn);

                            //推送警告气体
                            // 判断等级，根据等级查找url
                            int contrastParameter = 0;
                            if (gasPosition.getCh4Unit() > contrastParameter) {
                                contrastParameter = gasPosition.getCh4Unit();
                            }
                            if (gasPosition.getCoUnit() > contrastParameter) {
                                contrastParameter = gasPosition.getCoUnit();
                            }
                            if (gasPosition.getO2Unit() > contrastParameter) {
                                contrastParameter = gasPosition.getO2Unit();
                            }
                            if (gasPosition.getTemperatureUnit() > contrastParameter) {
                                contrastParameter = gasPosition.getTemperatureUnit();
                            }
                            if (gasPosition.getHumidityUnit() > contrastParameter) {
                                contrastParameter = gasPosition.getHumidityUnit();
                            }
                            gasWSRespVO.setGasLevel(contrastParameter);
//                String url = levelDataService.findRangUrlByLevelDataId(contrastParameter);
                            String url = systemServiceClient.findRangUrlByLevelDataId(contrastParameter);
                            if (null != url && !"".equals(url)) {
                                gasWSRespVO.setRangUrl(url);
                            }
                            try {
//                    WebsocketServer.sendInfo(JSON.toJSONString(new WebSocketData(1, gasWSRespVO)));
                                wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(1, gasWSRespVO)));
                            } catch (Exception e) {
                                throw new RuntimeOtherException(ResultEnum.WEBSOCKET_SEND_ERROR);
                            }







                        }else{
                            gasPosition.setGasFlag(0);
                            gasInfoWarn.setIsWarn(false);
                            upLoadGasDto.setGasInfo(gasInfoWarn);// 发送队列插入
                        }
                        gasNum++;
                        System.out.println("--------------------------已插入气体数量：-------------------------"+gasNum);
                       /* gasPositions.add(gasPosition);
                        if (gasPositions.size() > 200){
                            gasPositionMapper.insertGasPositions(gasPositions);
                            gasPositions.clear();
                        }*/
                        Integer insert = gasPositionMapper.insertSingleGas(gasPosition);

                        isWarn = false;
                    }







                    //--------------------------定位部门、区域筛选开始----------------------------------
                    Staff staff1 = staffMapper.selectByPrimaryKey(staffId);
                    Integer groupId = staff1.getGroupId();
                    Integer orgId = wsPushServiceClient.getWSSiteServerOrgId();
//        Integer zoneId = WSSiteServer.zoneId;
                    Integer zoneId = wsPushServiceClient.getWSSiteServerZoneId();
                    Map<String ,Object> map = new HashMap<>();



                    if(null == orgId && null == zoneId){

                        try {
                            map.put("gasWSRespVO", gasWSRespVO);
                            map.put("type", 1);
                            wsPushServiceClient.sendWSSiteServer(JSON.toJSONString(map));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(null != orgId && null == zoneId){
                        List<Integer> deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(orgId);
                        for (Integer deptId : deptIds) {
                            if(groupId.equals(deptId)){
                                try {
                                    map.put("gasWSRespVO", gasWSRespVO);
                                    map.put("type", 1);
                                    wsPushServiceClient.sendWSSiteServer(JSON.toJSONString(map));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    if(null == orgId && null != zoneId){
                        List<Integer>  zoneIds = stationPartitionServiceClient.getSonIdsById(zoneId);
//                    List<BaseStation> baseStations    = baseStationService.findBaseStationByZoneIds(zoneIds);
                        List<BaseStation> baseStations    = stationPartitionServiceClient.findBaseStationByZoneIds(zoneIds);
                        for (BaseStation baseStation : baseStations) {
                            //该终端所连基站
                            if(stationId.equals(baseStation.getBaseStationNum())){
                                try {
                                    map.put("gasWSRespVO", gasWSRespVO);
                                    map.put("type", 1);
                                    wsPushServiceClient.sendWSSiteServer(JSON.toJSONString(map));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if(null != orgId && null != zoneId){
                        List<Integer>   deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(orgId);
//                    List<Integer>  zoneIds = partitionService.getSonIdsById(zoneIdGas);
                        List<Integer>  zoneIds = stationPartitionServiceClient.getSonIdsById(zoneId);
//                    List<BaseStation> baseStations    = baseStationService.findBaseStationByZoneIds(zoneIds);
                        List<BaseStation> baseStations    = stationPartitionServiceClient.findBaseStationByZoneIds(zoneIds);
                        for (Integer deptId : deptIds) {
                            for (BaseStation baseStation : baseStations) {
                                //该终端所连基站
                                if (stationId.equals(baseStation.getBaseStationNum()) && groupId.equals(deptId)) {
                                    try {
                                        map.put("gasWSRespVO", gasWSRespVO);
                                        map.put("type", 1);
                                        wsPushServiceClient.sendWSSiteServer(JSON.toJSONString(map));
                                    } catch (Exception e) {
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



                        try {
                            jsonArray.addAll(list);

                            //--------------------------气体部门、区域筛选开始----------------------------------

                            Integer orgIdGas = wsPushServiceClient.getWSSiteServerOrgId();

                            Integer zoneIdGas = wsPushServiceClient.getWSSiteServerZoneId();
                            if(null == orgIdGas && null == zoneIdGas){
                                try {
                                    wsPushServiceClient.sendWSServer(jsonArray.toJSONString()); //发送实时监控气体
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if(null != orgIdGas && null == zoneIdGas){
//                                List<Integer>   deptIds = staffOrganizationService.findSonIdsByDeptId(orgId);
                                List<Integer>   deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(orgId);
                                for (Integer deptId : deptIds) {
                                    if(groupId.equals(deptId)){
                                        try {
                                            wsPushServiceClient.sendWSServer(jsonArray.toJSONString()); //发送实时监控气体
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            if(null == orgIdGas && null != zoneIdGas){
                                List<Integer>   deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(orgId);
//                    List<Integer>  zoneIds = partitionService.getSonIdsById(zoneIdGas);
                                List<Integer>  zoneIds = stationPartitionServiceClient.getSonIdsById(zoneId);
//                    List<BaseStation> baseStations    = baseStationService.findBaseStationByZoneIds(zoneIds);
                                List<BaseStation> baseStations    = stationPartitionServiceClient.findBaseStationByZoneIds(zoneIds);
                                for (BaseStation baseStation : baseStations) {
                                    //该终端所连基站
                                    if(stationId.equals(baseStation.getBaseStationNum())){
                                        try {
                                            wsPushServiceClient.sendWSServer(jsonArray.toJSONString());//发送实时监控气体
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }
                            if(null != orgIdGas && null != zoneIdGas){
                                List<Integer>   deptIds = staffGroupTerminalServiceClient.findSonIdsByDeptId(orgId);
//                    List<Integer>  zoneIds = partitionService.getSonIdsById(zoneIdGas);
                                List<Integer>  zoneIds = stationPartitionServiceClient.getSonIdsById(zoneId);
//                    List<BaseStation> baseStations    = baseStationService.findBaseStationByZoneIds(zoneIds);
                                List<BaseStation> baseStations    = stationPartitionServiceClient.findBaseStationByZoneIds(zoneIds);
                                for (Integer deptId : deptIds) {
                                    for (BaseStation baseStation : baseStations) {
                                        //该终端所连基站
                                        if (stationId.equals(baseStation.getBaseStationNum()) && groupId.equals(deptId)) {
                                            try {
                                                wsPushServiceClient.sendWSServer(jsonArray.toJSONString());//发送实时监控气体
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }

                            //--------------------------气体部门、区域筛选结束----------------------------------

                            list.clear();
                            jsonArray.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeOtherException(ResultEnum.WEBSOCKET_SEND_ERROR);
                        }
                    }
                }
            }
        });

    }




    @KafkaListener(groupId = "gas_kafka", id = "gas_kafka01", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void listen0(List<ConsumerRecord<?, ?>> records) {
        process(records);
    }

    @KafkaListener(groupId = "gas_kafka", id = "gas_kafka11", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void listen1(List<ConsumerRecord<?, ?>> records) {
        process(records);
    }

    @KafkaListener(groupId = "gas_kafka", id = "gas_kafka21", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void listen2(List<ConsumerRecord<?, ?>> records) {
        process(records);
    }

}
