package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.common.utils.GetUUID;
import com.cst.xinhe.common.utils.array.ArrayQueue;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.WebServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.kafka.consumer.service.entity.RTStaffInfo;
import com.cst.xinhe.kafka.consumer.service.redis.RedisService;
import com.cst.xinhe.persistence.dao.attendance.AttendanceMapper;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.base_station.OfflineStationMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dao.updateIp.TerminalIpPortMapper;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.model.updateIp.TerminalIpPort;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import io.swagger.models.auth.In;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName UpdateIpProcess
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/19 14:31
 * @Vserion v0.0.1
 */
@Component
public class UpdateIpProcess {
    //存储更新基站 队列
    public static final Map<Integer, ArrayQueue<TerminalUpdateIp>> attendanceMap = Collections.synchronizedMap(new HashMap<>());

    private static final Logger logger = LoggerFactory.getLogger(UpdateIpProcess.class);

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Resource
    private StaffOrganizationMapper staffOrganizationMapper;

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;

    @Resource
    private BaseStationMapper baseStationMapper;

    @Resource
    private WebServiceClient attendanceServiceClient;

    @Resource
    private TerminalUpdateIpMapper terminalUpdateIpMapper;

    @Resource
    private TimeStandardMapper timeStandardMapper;

    @Resource
    private AttendanceMapper attendanceMapper;

    @Resource
    private RedisService redisService;

    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    private static final String TOPIC = "updateIp.tut";

    @KafkaListener(groupId = "UpdateIpProcessTerminal", id = "UpdateIpProcessid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void sendUpdateIp0(List<ConsumerRecord<?, ?>> records) throws ParseException {
                fixedThreadPool.execute(() -> processT(records));
    }
    @KafkaListener(groupId = "UpdateIpProcessTerminal", id = "UpdateIpProcessid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void sendUpdateIp1(List<ConsumerRecord<?, ?>> records) throws ParseException{
                fixedThreadPool.execute(() -> processT(records));
    }
    @KafkaListener(groupId = "UpdateIpProcessTerminal", id = "UpdateIpProcessid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void sendUpdateIp2(List<ConsumerRecord<?, ?>> records) throws ParseException {
                fixedThreadPool.execute(() -> processT(records));
    }

    private void processT(List<ConsumerRecord<?, ?>> records){

            Thread thread = Thread.currentThread();
            logger.error("ThreadId: {}" , thread.getId());
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                JSONObject jsonObject = JSON.parseObject(str);

                String terminalIp = jsonObject.getString("terminalIp");
                String stationIp = jsonObject.getString("stationIp");
                Integer terminalId = jsonObject.getInteger("terminalId");
                Integer stationId = jsonObject.getInteger("stationId");

                Integer terminalPort = jsonObject.getInteger("terminalPort");
                Integer stationPort = jsonObject.getInteger("stationPort");
                Integer seq = jsonObject.getInteger("sequenceId");

                TerminalUpdateIp terminalUpdateIp = new TerminalUpdateIp();
                terminalUpdateIp.setStationId(stationId);
                terminalUpdateIp.setStationIp(stationIp);
                terminalUpdateIp.setTerminalIp(terminalIp);
                terminalUpdateIp.setTerminalNum(terminalId);
                terminalUpdateIp.setUpdateTime(new Date());
                terminalUpdateIp.setStationPort(stationPort);
                terminalUpdateIp.setTerminalPort(terminalPort);

                /**
                 * @description 根据terminalId 检查是否存在terminalId;
                 *                  如果存在则更新
                 *                  若不存在直接插入
                 * @date 14:55 2018/10/19
                 * @auther lifeng
                 **/
                terminalUpdateIpMapper.updateIpInfoByTerminalId(terminalUpdateIp);

                //---------------------------员工考勤处理开始-------------------------
//                Map<String, Object> staffMap = staffService.findStaffIdByTerminalId(terminalId);
//                Map<String, Object> staffMap = staffGroupTerminalServiceClient.findStaffIdByTerminalId(terminalId);
                Map<String, Object> staffMap = staffMapper.selectStaffInfoByTerminalId(terminalId);
                Integer staffId = (Integer) staffMap.get("staff_id");
                // 根据ID 查找当前人的考勤标准，根据标准计算具体考勤情况
                TimeStandardVO standard =  timeStandardMapper.selectTimeStandardInfoByStaffId(staffId);
//
                Integer timeStandardId = standard.getTimeStandardId();
//                Map<String, Object> entryStation = webServiceClient.findBaseStationByType(1);
                Map<String, Object> entryStation = baseStationMapper.selectBaseStationByType(1);
//                Map<String, Object> attendanceStation = webServiceClient.findBaseStationByType(2);
                Map<String, Object> attendanceStation = baseStationMapper.selectBaseStationByType(2);
                RTStaffInfo rtStaffInfo = new RTStaffInfo();
                Integer groupId  = (Integer) staffMap.get("group_id");
                rtStaffInfo.setGroupId(groupId);
                rtStaffInfo.setAttendanceStationId((Integer) staffMap.get("attendance_station_id"));
                rtStaffInfo.setElasticTime((Integer) staffMap.get("elastic_time"));
                rtStaffInfo.setFlag((Integer) staffMap.get("flag"));
                rtStaffInfo.setStaffName((String)staffMap.get("staff_name"));
                rtStaffInfo.setGroupName(getDeptNameByGroupId(groupId));
                rtStaffInfo.setIsPerson(String.valueOf(staffMap.get("is_person")));
                rtStaffInfo.setLateTime((Integer) staffMap.get("late_time"));
                rtStaffInfo.setSeriousLateTime((Integer) staffMap.get("serious_late_time"));
                rtStaffInfo.setLeaveEarlyTime((Integer) staffMap.get("leave_early_time"));
                rtStaffInfo.setSeriousLeaveEarlyTime((Integer) staffMap.get("serious_leave_early_time"));
                rtStaffInfo.setOverTime((Integer) staffMap.get("over_time"));
                rtStaffInfo.setTimeStandardId((Integer) staffMap.get("time_standard_id"));
                rtStaffInfo.setSeriousTime((Integer) staffMap.get("serious_time"));
                rtStaffInfo.setStaffId(staffId);
                rtStaffInfo.setStaffJobId((Integer) staffMap.get("staff_job_id"));
                rtStaffInfo.setStaffJobName((String)staffMap.get("job_name"));
                String key = String.valueOf(staffId);
                if (redisService.hasKey(key)){
                    redisService.getAndSet(key,JSON.toJSONString(rtStaffInfo));
                }else {
                    redisService.set(key,JSON.toJSONString(rtStaffInfo),1L);
                }

                //井口基站编号
//            DecimalFormat fmt = new DecimalFormat("##0.0");// 时间小数点后截取以为小数
                Integer entryId = (Integer) entryStation.get("baseStationNum");
                //考勤基站编号
                Integer attendanceId = (Integer) attendanceStation.get("baseStationNum");
                //连接基站为井口基站或者考勤基站，保存进队列，存储map中
                if (stationId.equals(entryId) || stationId.equals(attendanceId)) {
                    ArrayQueue<TerminalUpdateIp> terminalUpdateIpQueue = attendanceMap.get(terminalId);
                    //判断队列是否为空
                    if (terminalUpdateIpQueue == null) {
                        terminalUpdateIpQueue = new ArrayQueue<>(2);
                    }
                    //此次更新ip对象保存到队列
                    terminalUpdateIpQueue.add(terminalUpdateIp);
                    attendanceMap.put(terminalId, terminalUpdateIpQueue);
                    //判断上班、下班时间，插入数据库
                    if (terminalUpdateIpQueue.getQueueSize() == 2) {
                        TerminalUpdateIp head = terminalUpdateIpQueue.getHead();
                        TerminalUpdateIp end = terminalUpdateIpQueue.getEnd();
                        //上班
                        if (head.getStationId().equals(entryId) && end.getStationId().equals(attendanceId)) {
                            Attendance attendance = new Attendance();
                            attendance.setAttendanceId(GetUUID.getUuid());
                            attendance.setStaffId(staffId);
                            attendance.setRuleId(timeStandardId);
                            attendance.setStartTime(end.getUpdateTime());
                            attendance.setInOre(head.getUpdateTime());
                            attendance.setBaseStationId(end.getStationId());
//                            //插入上班时间
//
//                            Date rt_Time = new Date(); // 当前时间
//                            if (rt_Time.getTime() > (standStartTime.getTime() + standard.getElasticTime()*1000*60)){
//                                attendance.setBackup1("迟到");
//                            }else {
//                                attendance.setBackup1("正常");
//                            }
//
//                            String currentDate = DateFormat.getDateInstance().format(rt_Time);
//
//                            StringBuffer currentStartTime = new StringBuffer(currentDate);
//                            currentStartTime.append(" ");
//                            currentStartTime.append(standard_startTime);
//                            Date current = DateConvert.convertStringToDate(currentStartTime.toString(),19); // 应该打卡的时间
//
//                            long first = (rt_Time.getTime() - current.getTime())/(1000*60);
//                            if (first > 0){ // 可能迟到
//                                //可能迟到
//                                if (first <= elastic_time){
//                                    //弹性时间内考勤
//                                    attendance.setBackup1("正常");
//                                }else{
//                                    if (first <= (elastic_time +late_time) ){
//                                        //一般迟到
//                                        attendance.setBackup1("迟到" + fmt.format(first/60.0) + "小时" );
//                                    }
//                                    else if (first > (elastic_time + serious_late_time)){
//                                        //严重迟到
//                                        attendance.setBackup1("严重迟到" +  fmt.format(first/60.0) + "小时" );
//                                    }
//                                }
//                            }else {//first < 0 正常考勤
//                                // 正常考勤情况
//                                attendance.setBackup1("正常，提前上班" +  fmt.format(-first/60.0) + "小时");
//                            }
//                            attendanceService.addAttendance(attendance);
//                            attendanceServiceClient.addAttendance(attendance);
                            attendanceMapper.insertSelective(attendance);
                        }
                        //下班
                        if (head.getStationId().equals(attendanceId) && end.getStationId().equals(entryId)) {
                            //推送井下总人数到前端
//                            TerminalInfoProcess.pushRtPersonData();

                            //------------------------------------超时处理开始--------------------------------------
                            //下班，查看该员工是否超时，如果超时，推送前端，超时人数减1
//                            StaffAttendanceRealRule realRule = staffAttendanceRealRuleMapper.selectByPrimaryKey(staffId);
                            StaffAttendanceRealRule realRule = attendanceServiceClient.findStaffAttendanceRealRuleById(staffId);
                            //员工下班，不再考勤
                            realRule.setIsAttendance(0);
                            realRule.setFinalTime(null);
                            WebSocketData data = new WebSocketData();
                            //超时
                            if (realRule.getIsOverTime() != null && realRule.getIsOverTime() == 1) {
                                GasKafka.overTimeSet.remove(staffId);
                                realRule.setIsOverTime(0);
                                data.setType(1);
                                data.setData(-1);
                                try {
//                                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                                    wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            //严重超时
                            if (realRule.getIsSeriousTime() != null && realRule.getIsSeriousTime() == 1) {
                                GasKafka.seriousTimeSet.remove(staffId);
                                realRule.setIsSeriousTime(0);
                                data.setType(2);
                                data.setData(-1);
                                try {
//                                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                                    wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
//                            attendanceServiceClient.updateStaffAttendanceRealRuleById(realRule);
                            //------------------------------------超时处理结束--------------------------------------
//                            Attendance attendance = attendanceService.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
//                            Attendance attendance = attendanceServiceClient.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
                            Attendance attendance = attendanceMapper.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
                            if (attendance != null) {
                                attendance.setEndTime(head.getUpdateTime());
                                attendance.setOutOre(end.getUpdateTime());
//                                Date rt_Time = new Date(); // 当前时间
//                                if (rt_Time.getTime() < standEndTime.getTime()){
//                                    attendance.setBackup2("早退");
//                                }else {
//                                    attendance.setBackup2("正常");
//                                }
//
//                                String currentDate = DateFormat.getDateInstance().format(rt_Time);

//                                StringBuffer currentStartTime = new StringBuffer(currentDate);
//                                currentStartTime.append(" ");
//                                currentStartTime.append(standard_endTime);
//                                Date current = DateConvert.convertStringToDate(currentStartTime.toString(),19);
//                                long second = (rt_Time.getTime() - rt_Time.getTime())/(1000*60) ;
//                                if (second > 0){ // 早退
//                                    //可能迟到
//                                    if (second <= leave_early_time){
//                                        //一般早退
//                                        attendance.setBackup2("早退" + fmt.format(second/60.0) + "小时" );
//                                    }
//                                    else if (second > leave_early_time && second <= serious_leave_early_time){
//                                        attendance.setBackup2("严重早退" + fmt.format(second/60.0) + "小时" );
//                                        //严重早退
//                                    }
//                                    else {
//                                        //异常上班
//                                        attendance.setBackup2("异常打卡数据");
//                                    }
//                                }else {
//                                    // 加班
//                                    attendance.setBackup2("加班" + fmt.format(-second/60.0) + "小时" );
//                                }
                                //添加下班时间
//                                attendanceService.updateAttendance(attendance);
                                attendanceServiceClient.updateAttendance(attendance);
                                String aid = attendance.getAttendanceId();
                                try {
                                    HandlingAttendanceRules.process(aid);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                //------------------------员工考勤处理结束------------------------------
                //------------------------将下井总人数，未考勤人数推送前端页面开始--------------------------
                WebSocketData data = new WebSocketData();
//                List<HashMap<String, Object>> attendanceCount = staffAttendanceRealRuleMapper.getAttendanceStaff(null, null);
                Long attendanceCount = attendanceServiceClient.getAttendanceStaffCount();
                if (null != attendanceCount) {
                    data.setData(attendanceCount);
                } else {
                    data.setData(0);
                }
                data.setType(3);

                try {
//                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
                    wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //本应该考勤，但未考勤总人数
                data.setType(4);
                Integer count = staffAttendanceRealRuleMapper.getUnAttendanceDept(new Date(), null);
//                Integer count = attendanceServiceClient.getUnAttendanceDept(new Date());
                data.setData(count);
                try {
                    wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //---------------------将下井总人数，未考勤人数推送前端页面结束--------------------------
            }
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


}
