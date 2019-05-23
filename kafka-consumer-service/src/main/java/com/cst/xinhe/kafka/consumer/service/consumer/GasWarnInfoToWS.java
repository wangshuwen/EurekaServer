package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.RuntimeOtherException;
import com.cst.xinhe.common.utils.array.ArrayQueue;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.SystemServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.kafka.consumer.service.service.RSTL;
import com.cst.xinhe.persistence.dao.terminal_road.TerminalRoadMapper;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
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
 * @ClassName GasWarnInfoToWS
 * @Description
 * @Auther lifeng
 * @DATE 2018/11/22 16:16
 * @Vserion v0.0.1
 */
@Component
public class GasWarnInfoToWS {

    Logger logger = LoggerFactory.getLogger(getClass());

    //存储更新基站 队列
    public static Map<Integer, ArrayQueue<TerminalRoad>> attendanceMap = new HashMap<>();

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Resource
    private SystemServiceClient systemServiceClient;

    @Resource
    RSTL rstl;
//    @Resource
//    private StaffService staffService;

//    @Resource
//    WSServer wsServer;
//    @Resource
//    private TerminalRoadMapper teminalRoadMapper;
//    @Resource
//    private LevelDataService levelDataService;
//    @Resource
//    private BaseStationService baseStationService;

    ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(9);
    ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(9);
    ExecutorService fixedThreadPool3 = Executors.newFixedThreadPool(9);
    public static List<GasWSRespVO> list = new ArrayList<GasWSRespVO>();



    /**
     * 监听kafka.tut 的 topic
     *
     * @param record
     * @param topic  topic
     */
    private static final String TOPIC = "warn_kafka.tut";



    private void processT(List<ConsumerRecord<?, ?>> records){
        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}" + thread.getId());
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
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
//                GasWSRespVO staff = staffService.findStaffNameByTerminalId(terminalId);
                GasWSRespVO staff = staffGroupTerminalServiceClient.findStaffNameByTerminalId(terminalId);
                road.setStaffId(staff.getStaffId());
                //终端所连基站id
                road.setStationId(stationId);

                //--------------------------------判断员工是否是出矿入矿---------------------------------
           /* Map<String, Object> entryStation  = baseStationService.findBaseStationByType(1);
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
                teminalRoadMapper.insert(road);
            }
*/


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
                // 判断等级，根据等级查找url
                int contrastParameter = 0;
                if (ch4Flag > contrastParameter) {
                    contrastParameter = ch4Flag;
                }
                if (coFlag > contrastParameter) {
                    contrastParameter = coFlag;
                }
                if (o2Flag > contrastParameter) {
                    contrastParameter = o2Flag;
                }
                if (tFlag > contrastParameter) {
                    contrastParameter = tFlag;
                }
                if (hFlag > contrastParameter) {
                    contrastParameter = hFlag;
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
//            list.add(gasWSRespVO);
//
//            JSONArray jsonArray = new JSONArray();
//
//
//            if (list.size() > 10) {
//                try {
//
//                    for (GasWSRespVO vo : list) {
//                        jsonArray.add(vo);
//                    }
//                    WSServer.sendInfo(jsonArray.toJSONString());
//                    list.clear();
//                    jsonArray.clear();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    throw new RuntimeOtherException(ResultEnum.WEBSOCKET_SEND_ERROR);
//                }
//            }
            }
        }

    }
    @KafkaListener(groupId = "GasWarnInfoToWS", id = "GasWarnInfoToWSid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void listen0(List<ConsumerRecord<?, ?>> records) {
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;

                fixedThreadPool1.execute(() -> processT(records));
//            }
//        }
    }
    @KafkaListener(groupId = "GasWarnInfoToWS", id = "GasWarnInfoToWSid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void listen1(List<ConsumerRecord<?, ?>> records) {
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;

                fixedThreadPool2.execute(() -> processT(records));
//            }
//        }
    }
    @KafkaListener(groupId = "GasWarnInfoToWS", id = "GasWarnInfoToWSid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void listen2(List<ConsumerRecord<?, ?>> records) {
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;

                fixedThreadPool3.execute(() -> processT(records));
//            }
//        }
    }
}
