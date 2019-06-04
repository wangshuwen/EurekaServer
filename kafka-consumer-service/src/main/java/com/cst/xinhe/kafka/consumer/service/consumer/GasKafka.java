package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.RuntimeOtherException;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.netty.utils.NettyDataUtils;
import com.cst.xinhe.common.utils.array.ArrayQueue;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.*;
import com.cst.xinhe.kafka.consumer.service.service.RSTL;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.rt_gas.GasPositionMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.dao.station_standard_relation.StationStandardRelationMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dao.warn_level.GasStandardMapper;
import com.cst.xinhe.persistence.dao.warn_level.GasWarnSettingMapper;
import com.cst.xinhe.persistence.dao.warn_level.LevelDataMapper;
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
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.staff.StaffOrganizationExample;
import com.cst.xinhe.persistence.model.station_standard_relation.StationStandardRelation;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.model.warn_level.GasStandard;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecordExample;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/24/11:05
 */
@Transactional
@Component
public class GasKafka {

    private static final Logger logger = LoggerFactory.getLogger(GasKafka.class);
    //存储更新基站 队列
    public static Map<Integer, ArrayQueue<TerminalRoad>> attendanceMap = Collections.synchronizedMap(new HashMap<>());

    @Resource
    private StaffOrganizationMapper staffOrganizationMapper;

    @Resource
    private TerminalMonitorClient terminalMonitorClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Resource
    private GasWarnSettingMapper gasWarnSettingMapper;

    @Resource
    private GasStandardMapper gasStandardMapper;

    @Resource
    private BaseStationMapper baseStationMapper;

    @Resource
    private LevelDataMapper levelDataMapper;

    @Resource
    private StaffTerminalMapper staffTerminalMapper;

    @Resource
    private WarningAreaMapper warningAreaMapper;

    @Resource
    private StationStandardRelationMapper stationStandardRelationMapper;

    @Resource
    private WarningAreaRecordMapper warningAreaRecordMapper;

    @Resource
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;


    @Resource
    private TimeStandardMapper timeStandardMapper;

    @Resource
    private GasPositionMapper gasPositionMapper;


    public static List<GasWSRespVO> list = Collections.synchronizedList(new ArrayList<>());

    private volatile static int gasNum = 0;


    public static Set<Integer> overTimeSet = Collections.synchronizedSet(new HashSet());
    public static Set<Integer> seriousTimeSet = Collections.synchronizedSet(new HashSet());

    private static List<GasPosition> gasPositionList = Collections.synchronizedList(new LinkedList<>());

    @Resource
    private StaffMapper staffMapper;


    private volatile boolean isWarn = false;

    @Resource
    RSTL rstl;


    private static final String TOPIC = "gas_kafka.tut";

    private ExecutorService executorService = Executors.newFixedThreadPool(6);


    class GasProcess implements Runnable {

        private List<ConsumerRecord<?, ?>> records;
        GasProcess(List<ConsumerRecord<?, ?>> records) {
            this.records = records;
        }

        @Override
        public void run() {
            process(this.records);
        }
        private void process(List<ConsumerRecord<?, ?>> records) {
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
                    gasPosition.setCoUnit((int) body[2]);
                    double co20 = ((long) (((body[3] & 0xff) << 8) + (body[4] & 0xff)) / 10.0);

                    gasInfo.setCo2(co20);
                    gasPosition.setCo2(co20);
                    gasInfo.setCo2Flag(body[5]);
                    gasPosition.setCo2Unit((int) body[5]);
                    double o20 = ((long) (((body[6] & 0xff) << 8) + (body[7] & 0xff)) / 10.0);
                    gasInfo.setO2(o20);
                    gasPosition.setO2(o20);
                    gasInfo.setO2Flag(body[8]);
                    gasPosition.setO2Unit((int) body[8]);

                    double ch40 = ((long) (((body[9] & 0xff) << 8) + (body[10] & 0xff)) / 10.0);
                    gasInfo.setCh4(ch40);
                    gasPosition.setCh4(ch40);
                    gasInfo.setCh4Flag(body[11]);
                    gasPosition.setCh4Unit((int) body[11]);
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
                    gasPosition.setHumidityUnit((int) body[17]);
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
                    sendTempRoadName(requestData.getTerminalId(),requestData.getTerminalIp(),requestData.getTerminalPort(),road.getTempPositionName());

                    //----------------------------------以下是判断出入问题------------------------------------
                    //去除staffGroupTerminalServiceClient
                    GasWSRespVO staff = findStaffNameByTerminalId(gasPosition.getTerminalId());
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
                        if (warningArea != null) {
                            if (warningArea.getWarningAreaType() == 1) {
                                //在重点区域内
                                road.setIsOre(3);
                                gasPosition.setIsOre(3);
                            } else {
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
//                                    //去除远程调用staffGroupTerminalServiceClient
                                    String deptName = getDeptNameByGroupId(groupId);
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
//          Map<String, Object> attendanceStation = baseStationService.findBaseStationByType(2);
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
                    if (null != stationStandardRelation) {
                        //获取基站ID
                        Integer standardId = stationStandardRelation.getStandardId();
                        //去除systemServiceClient远程调用
                        GasLevelVO gasLevelVO = getWarnLevelSettingByGasLevelId(standardId);
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
                            if (ch4 >= (multiple * ch4Standard)) {
                                //警报等级大的会把等级小的覆盖掉（已排序根据multiple）
                                gasInfoWarn.setCh4Flag(gasWarnSettingDto.getLevelDataId());
                                gasPosition.setCh4Unit(gasWarnSettingDto.getLevelDataId());
                                isWarn = true;
                            }
                        }
                        //判断co气体等级
                        List<GasWarnSettingDto> coWarnSettingDto = gasLevelVO.getCoWarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : coWarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if (co >= (multiple * coStandard)) {
                                gasInfoWarn.setCoFlag(gasWarnSettingDto.getLevelDataId());
                                gasPosition.setCoUnit(gasWarnSettingDto.getLevelDataId());
                                isWarn = true;
                            }
                        }
                        //判断湿度等级
                        List<GasWarnSettingDto> hWarnSettingDto = gasLevelVO.gethWarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : hWarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if (h >= (multiple * hStandard)) {
                                gasInfoWarn.sethFlag(gasWarnSettingDto.getLevelDataId());
                                gasPosition.setHumidityUnit(gasWarnSettingDto.getLevelDataId());
                                isWarn = true;
                            }
                        }
                        //判断O2气体等级
                        List<GasWarnSettingDto> O2WarnSettingDto = gasLevelVO.getO2WarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : O2WarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            //氧气浓度越低，警报等级越高
                            if (o2 <= (multiple * o2Standard)) {
                                gasInfoWarn.setO2Flag(gasWarnSettingDto.getLevelDataId());
                                gasPosition.setO2Unit(gasWarnSettingDto.getLevelDataId());
                                isWarn = true;
                            }
                        }
                        //判断温度气体等级
                        List<GasWarnSettingDto> tWarnSettingDto = gasLevelVO.gettWarnSettingDto();
                        for (GasWarnSettingDto gasWarnSettingDto : tWarnSettingDto) {
                            Double multiple = gasWarnSettingDto.getMultiple();
                            if ((multiple * tStandard) <= t) {
                                gasInfoWarn.settFlag(gasWarnSettingDto.getLevelDataId());
                                gasPosition.setTemperatureUnit(gasWarnSettingDto.getLevelDataId());
                                isWarn = true;
                            }
                        }

                        gasInfoWarn.setCo2Flag(0);

                        if (isWarn) {
                            gasPosition.setGasFlag(1);
                            gasInfoWarn.setIsWarn(true);
                            upLoadGasDto.setGasInfo(gasInfoWarn);

                            //推送警告气体
                            // 判断等级，根据等级查找url
                            int contrastParameter = 0;
                            if (gasInfoWarn.getCh4Flag() > contrastParameter) {
                                contrastParameter = gasInfoWarn.getCh4Flag();
                            }
                            if (gasInfoWarn.getCoFlag() > contrastParameter) {
                                contrastParameter = gasInfoWarn.getCoFlag();
                            }
                            if (gasInfoWarn.getO2Flag() > contrastParameter) {
                                contrastParameter = gasInfoWarn.getO2Flag();
                            }
                            if (gasInfoWarn.gettFlag() > contrastParameter) {
                                contrastParameter = gasInfoWarn.gettFlag();
                            }
                            if (gasInfoWarn.gethFlag() > contrastParameter) {
                                contrastParameter = gasInfoWarn.gethFlag();
                            }
                            gasWSRespVO.setGasLevel(contrastParameter);
                            //去除服务远程调用
                            System.out.println("实际计算的等级：" + contrastParameter);
                            Map<String, Object> map = levelDataMapper.selectRangUrlByLevelDataId(contrastParameter);
                            if(null != map && !map.isEmpty()){
                                String url = (String) map.get("url");

                                if (null != url && !"".equals(url)) {
                                    gasWSRespVO.setRangUrl(url);
                                }
                            }

                            //判断具体发送警报的等级，并发送
                            if (contrastParameter > 0) {
                                try {
                                    wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(1, gasWSRespVO)));
                                } catch (Exception e) {
                                    throw new RuntimeOtherException(ResultEnum.WEBSOCKET_SEND_ERROR);
                                }
                            }
                        } else {
                            //非警报数据
                            gasPosition.setGasFlag(0);
                            gasInfoWarn.setIsWarn(false);
                            upLoadGasDto.setGasInfo(gasInfoWarn);// 发送队列插入
                        }

                        gasNum++;
                        /*
                        gasPositionList.add(gasPosition);
                        System.out.println("--------------------------已插入气体数量：-------------------------" + gasNum);
                        if (gasPositionList.size() > 1000){
                            gasPositionMapper.insertGasPositions(gasPositionList);
                            gasPositionList.clear();
                        }*/
                        Integer insert = gasPositionMapper.insertSingleGas(gasPosition);
                        System.out.println("--------------------------已插入气体数量：-------------------------" + gasNum);
                        if (insert == 1) {
                            System.out.println("插入第" + gasNum + "条成功！");
                        }
                        isWarn = false;
                    }


                    //--------------------------定位部门、区域筛选开始----------------------------------
                    Staff staff1 = staffMapper.selectByPrimaryKey(staffId);
                    Integer groupId = staff1.getGroupId();
                    Integer orgId = wsPushServiceClient.getWSSiteServerOrgId();
                    Integer zoneId = wsPushServiceClient.getWSSiteServerZoneId();
                    Map<String, Object> map = new HashMap<>();

                    if (null == orgId && null == zoneId) {
                        try {
                            map.put("gasWSRespVO", gasWSRespVO);
                            map.put("type", 1);
                            wsPushServiceClient.sendWSSiteServer(JSON.toJSONString(map));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != orgId && null == zoneId) {
                        List<Integer> deptIds = findSonIdsByDeptId(orgId);
                        for (Integer deptId : deptIds) {
                            if (groupId.equals(deptId)) {
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
                    if (null == orgId && null != zoneId) {
                        List<Integer> zoneIds = getSonIdsById(zoneId);
                        List<BaseStation> baseStations = baseStationMapper.findBaseStationByZoneIds(zoneIds);
                        for (BaseStation baseStation : baseStations) {
                            //该终端所连基站
                            if (stationId.equals(baseStation.getBaseStationNum())) {
                                try {
                                    map.put("gasWSRespVO", gasWSRespVO);
                                    map.put("type", 1); // 1 证明时正常发送的数据， 其他就是 终端断开
                                    wsPushServiceClient.sendWSSiteServer(JSON.toJSONString(map));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (null != orgId && null != zoneId) {
                        List<Integer> deptIds = findSonIdsByDeptId(orgId); //查找所有的机构所属的子机构
                        List<Integer> zoneIds = getSonIdsById(zoneId); //查找该区域下所有的子区域
                        List<BaseStation> baseStations = baseStationMapper.findBaseStationByZoneIds(zoneIds); //查询该区域下的所有基站
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
                            if (null == orgIdGas && null == zoneIdGas) {
                                try {
                                    wsPushServiceClient.sendWSServer(jsonArray.toJSONString()); //发送实时监控气体
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (null != orgIdGas && null == zoneIdGas) {
                                List<Integer> deptIds = findSonIdsByDeptId(orgId);
                                for (Integer deptId : deptIds) {
                                    if (groupId.equals(deptId)) {
                                        try {
                                            wsPushServiceClient.sendWSServer(jsonArray.toJSONString()); //发送实时监控气体
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            if (null == orgIdGas && null != zoneIdGas) {
                                List<Integer> zoneIds = getSonIdsById(zoneId);
                                List<BaseStation> baseStations = baseStationMapper.findBaseStationByZoneIds(zoneIds);
                                for (BaseStation baseStation : baseStations) {
                                    //该终端所连基站
                                    if (stationId.equals(baseStation.getBaseStationNum())) {
                                        try {
                                            wsPushServiceClient.sendWSServer(jsonArray.toJSONString());//发送实时监控气体
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (null != orgIdGas && null != zoneIdGas) {
                                List<Integer> deptIds = findSonIdsByDeptId(orgId);
                                List<Integer> zoneIds = getSonIdsById(zoneId);
                                List<BaseStation> baseStations = baseStationMapper.findBaseStationByZoneIds(zoneIds);
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
        }

        private void sendTempRoadName(Integer terminalId, String ip,Integer port, String tempPositionName) {
            ResponseData responseData = ResponseData.getResponseData();
            RequestData requestData = new RequestData();

            char[] charArr = tempPositionName.toCharArray();
            System.out.println(charArr);
            System.out.println("======");
            System.out.println("char长度：" + charArr.length);
            int charArrLen = charArr.length;
            byte[] body = new byte[charArrLen * 3];
            for (int i = 0, j = 0; i < body.length && j < charArrLen; i = i + 3, j++) {
                if (charArr[j] <= 128) {
                    body[i] = 0;
                    body[i + 1] = 0;
                    body[i + 2] = (byte) (charArr[j] &0xff);
                } else {
                    String s = String.valueOf(charArr[j]);
                    byte[] t_s_b = s.getBytes();
                    body[i] = (byte)(t_s_b[0]&0xff);
                    body[i + 1] = (byte)(t_s_b[1]&0xff);
                    body[i + 2] = (byte)(t_s_b[2]&0xff);
                }
            }
            int realLen = 34 + body.length;
            requestData.setLength(realLen);
            requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
            requestData.setStationPort(0);
            requestData.setStationIp1(0);
            requestData.setStationIp2(0);
            requestData.setStationId(0);
            requestData.setStationIp("0.0");

            requestData.setTerminalIp1(Integer.parseInt(ip.split("\\.")[0]));
            requestData.setTerminalIp2(Integer.parseInt(ip.split("\\.")[1]));
            requestData.setTerminalIp(ip);
            requestData.setTerminalPort(port);
            requestData.setTerminalId(terminalId);
            requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_REQUEST);
            requestData.setSequenceId(terminalMonitorClient.getSequenceId());
            requestData.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
            requestData.setNodeCount((byte) 0);
            requestData.setNdName(ConstantValue.MSG_BODY_NODE_NAME_POSITION_SHOW);

            requestData.setBody(body);

            responseData.setCustomMsg(requestData);
//            NettyDataUtils.toHexByteByStrings();
//            requestData.setLength();
//            responseData.setCustomMsg();
            try {
                terminalMonitorClient.sendResponseData(responseData);
            }catch (Exception e){
                System.out.println(responseData.toString());
                e.printStackTrace();

            }

        }
    }




    @KafkaListener(groupId = "gas_kafka", id = "gas_kafka01", topicPartitions = {@TopicPartition(topic = TOPIC, partitions = {"0"})})
    public void listen0(List<ConsumerRecord<?, ?>> records) {
//        process(records);
        executorService.execute(new GasProcess(records));

    }

    @KafkaListener(groupId = "gas_kafka", id = "gas_kafka11", topicPartitions = {@TopicPartition(topic = TOPIC, partitions = {"1"})})
    public void listen1(List<ConsumerRecord<?, ?>> records) {
//        process(records);
        executorService.execute(new GasProcess(records));
    }

    @KafkaListener(groupId = "gas_kafka", id = "gas_kafka21", topicPartitions = {@TopicPartition(topic = TOPIC, partitions = {"2"})})
    public void listen2(List<ConsumerRecord<?, ?>> records) {
//        process(records);
        executorService.execute(new GasProcess(records));
    }

    public GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId) {
        Map<String, Object> params = new HashMap<>();
        params.put("standardId", standardId);
        params.put("gasType", 5);
        List<GasWarnSettingDto> o2GasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 1);
        List<GasWarnSettingDto> coGasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 2);
        List<GasWarnSettingDto> ch4GasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 3);
        List<GasWarnSettingDto> tGasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 4);
        List<GasWarnSettingDto> hGasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);

        GasStandard standard = gasStandardMapper.selectByPrimaryKey(standardId);

        GasLevelVO gasLevelVO = new GasLevelVO();

        gasLevelVO.setGasStandard(standard);
        gasLevelVO.setCh4WarnSettingDto(ch4GasWarnSettingList);
        gasLevelVO.setCoWarnSettingDto(coGasWarnSettingList);
        gasLevelVO.setO2WarnSettingDto(o2GasWarnSettingList);
        gasLevelVO.sethWarnSettingDto(hGasWarnSettingList);
        gasLevelVO.settWarnSettingDto(tGasWarnSettingList);

        return gasLevelVO;
    }


    public GasWSRespVO findStaffNameByTerminalId(Integer terminalId) {

        Map<String, Object> resultMap = staffTerminalMapper.selectStaffNameByTerminalId(terminalId);

        GasWSRespVO gasWSRespVO = new GasWSRespVO();
        if (null != resultMap && resultMap.size() > 0) {
            Integer staffId = (Integer) resultMap.get("staff_id");
            String staffName = (String) resultMap.get("staff_name");
            Integer isPerson = (Integer) resultMap.get("is_person");
            gasWSRespVO.setStaffName(staffName);
            gasWSRespVO.setStaffId(staffId);
            gasWSRespVO.setIsPerson(isPerson);
            return gasWSRespVO;
        } else {
            gasWSRespVO.setStaffName("未知人员");
            gasWSRespVO.setStaffId(0);
            gasWSRespVO.setIsPerson(0);
            return gasWSRespVO;
        }
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


    public List<Integer> getSonIdsById(Integer id) {
        List<Integer> sonIdsById = findSonIdsById(id);
        sonIdsById.add(id);
        return sonIdsById;
    }

    private List<Integer> findSonIdsById(Integer id) {
        if (null == id) {
            id = 0;
        }
        ArrayList<Integer> list = new ArrayList<>();
        StaffOrganizationExample example = new StaffOrganizationExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<StaffOrganization> sonList = staffOrganizationMapper.selectByExample(example);
        if (null != sonList) {
            for (StaffOrganization staffOrganization : sonList) {
                list.add(staffOrganization.getId());
                List<Integer> sonIds = findSonIdsById(staffOrganization.getId());
                if (null != sonIds) {
                    for (Integer sonId : sonIds) {
                        list.add(sonId);
                    }
                }
            }
        }
        return list;
    }

    public List<Integer> findSonIdsByDeptId(Integer deptId) {
        List<Integer> deptIds = findSonIdsById(deptId);
        deptIds.add(deptId);
        return deptIds;
    }


}
