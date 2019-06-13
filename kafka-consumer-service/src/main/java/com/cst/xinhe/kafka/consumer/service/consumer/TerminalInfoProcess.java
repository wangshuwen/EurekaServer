package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.kafka.consumer.service.client.WebServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 方法实现说明
* @author      wangshuwen
* @param
* @description  实时查询：根据终端id实时更新人员、车辆、领导
 *                 暂时未被使用
* @return
* @exception
* @date        2018/12/10 17:21
*/
@Component
public class TerminalInfoProcess {

    private static final Logger logger = LoggerFactory.getLogger(TerminalInfoProcess.class);

    public static Set<Integer> staffSet= Collections.synchronizedSet(new HashSet());
    public static Set<Integer> leaderSet= Collections.synchronizedSet(new HashSet());
    public static Set<Integer> outPersonSet= Collections.synchronizedSet(new HashSet());
    public static Set<Integer> carSet= Collections.synchronizedSet(new HashSet());

    @Resource
    private WebServiceClient webServiceClient;



    @Resource
    private StaffMapper staffMapper;
//    @Resource
//    private AttendanceService attendanceService;

    @Resource
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;
//
//    private static  StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper1;

//    @PostConstruct
//    public void init() {
//        this.staffAttendanceRealRuleMapper1 = this.staffAttendanceRealRuleMapper;
//    }
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(9);

    private static ObjectMapper json = new ObjectMapper();

    private static final String TOPIC = "terminalInfo.tut";

    private void processT(List<ConsumerRecord<?, ?>> records) {

        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}", thread.getId());
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;

                JSONObject jsonObject = JSON.parseObject(str);
                Integer terminalId = jsonObject.getInteger("terminalId");
                Integer stationId = jsonObject.getInteger("stationId");
                Map<String, Object> map = staffMapper.selectStaffInfoByTerminalId(terminalId);
//                Map<String, Object> map = staffGroupTerminalServiceClient.selectStaffInfoByTerminalId(terminalId);
                Integer staffId = (Integer) map.get("staff_id");

                //记录员工加入考勤
                StaffAttendanceRealRule realRule = staffAttendanceRealRuleMapper.selectByPrimaryKey(staffId);
//                StaffAttendanceRealRule realRule = attendanceServiceClient.findStaffAttendanceRealRuleById(staffId);
                realRule.setIsAttendance(1);
//                attendanceServiceClient.updateStaffAttendanceRealRuleById(realRule);
                staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
                //实时查询:人，车，领导
                Integer isPerson = (Integer) map.get("is_person");
                if (staffId != null) {
                    switch (isPerson) {
                        case 0:
                            staffSet.add(staffId);
                            break;
                        case 1:
                            leaderSet.add(staffId);
                            break;
                        case 2:
                            outPersonSet.add(staffId);
                            break;
                        case 3:
                            carSet.add(staffId);
                            break;
                    }
                }

                //实时查询：数据推送到前端页面
                pushRtPersonData();
            }
        }
    }


    //    @Transactional
  //  @KafkaListener(id = "liveupdateData", topics = "terminalInfo.tut")
    @KafkaListener(groupId = "TerminalInfoProcess", id = "TerminalInfoProcessid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void liveUpdateData0(List<ConsumerRecord<?, ?>> records) {
                fixedThreadPool.execute(() -> processT(records));
    }

    @KafkaListener(groupId = "TerminalInfoProcess",id = "TerminalInfoProcessid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void liveUpdateData1(List<ConsumerRecord<?, ?>> records) {

                fixedThreadPool.execute(() -> processT(records));
    }

    @KafkaListener(groupId = "TerminalInfoProcess",id = "TerminalInfoProcessid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void liveUpdateData2(List<ConsumerRecord<?, ?>> records) {

                fixedThreadPool.execute(() -> processT(records));
    }



        public static void pushRtPersonData(){
            //实时查询：数据推送到前端页面
            HashMap<String, Object> mapJson = new HashMap<>();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("staffNum",staffSet.size());
            dataMap.put("leaderNum",leaderSet.size());
            dataMap.put("outPersonNum",outPersonSet.size());
            dataMap.put("carNum",carSet.size());
            mapJson.put("code",3);//2008表示发起呼叫
            mapJson.put("data",dataMap);
            try {
                String jsonStr= json.writeValueAsString(mapJson);
//                WSBigDataServer.sendInfo(jsonStr);

            } catch (Exception e) {
                e.printStackTrace();
            }


           /* //井下总人数
            WebSocketData data = new WebSocketData();
            data.setType(3);
            data.setData(staffSet.size()+leaderSet.size()+outPersonSet.size());
            try {
                WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //本应该考勤，但未考勤总人数
            data.setType(4);
            Integer count = staffAttendanceRealRuleMapper1.getUnAttendanceDept(new Date(), null);
            data.setData(count);
            try {
                WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }
}
