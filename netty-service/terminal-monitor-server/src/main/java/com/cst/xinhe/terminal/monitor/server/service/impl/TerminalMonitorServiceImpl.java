package com.cst.xinhe.terminal.monitor.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.netty.utils.NettyDataUtils;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.chat.TemporarySendListMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dao.updateIp.TerminalIpPortMapper;
import com.cst.xinhe.persistence.dto.voice.VoiceDto;
import com.cst.xinhe.persistence.model.chat.TemporarySendList;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.terminal.monitor.server.channel.ChannelMap;
import com.cst.xinhe.terminal.monitor.server.client.*;
import com.cst.xinhe.terminal.monitor.server.handle.NettyServerHandler;
import com.cst.xinhe.terminal.monitor.server.redis.RedisService;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import com.cst.xinhe.terminal.monitor.server.utils.SequenceIdGenerate;
import io.netty.channel.Channel;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 11:14
 **/
@Service
public class TerminalMonitorServiceImpl implements TerminalMonitorService {

    @Resource
    private SystemServiceClient systemServiceClient;

    @Resource
    private VoiceMonitorServerClient voiceMonitorServerClient;

    @Resource
    private KafkaClient kafkaClient;

    @Resource
    private BaseStationMapper baseStationMapper;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @Resource
    private KafkaConsumerClient kafkaConsumerClient;

    @Resource
    private TemporarySendListMapper temporarySendListMapper;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private TerminalUpdateIpMapper terminalUpdateIpMapper;
    @Resource
    private TerminalIpPortMapper terminalIpPortMapper;

    @Resource
    private StaffTerminalMapper staffTerminalMapper;
    /**
     * 根据端口和Ip判断是否在线
     * @param ipPort
     * @return
     */
    @Override
    public boolean getChannelByIpPort(String ipPort) {
        Channel channel = ChannelMap.getChannelByName(ipPort);
        return channel != null;
    }

    /**
     * 发送数据到终端
     * @param responseData
     */
    @Override
    public void sendResponseData(ResponseData responseData) {
        System.out.println(responseData.toString());
        SingletonClient.getSingletonClient().sendCmd(responseData);
    }

    @Override
    public void sendInfoToKafka(String s, RequestData customMsg) {

    }

    /**
     * 检测在线情况
     * @param customMsg
     */
    @Override
    public void checkSendCheckOnline(RequestData customMsg) {
        voiceMonitorServerClient.checkSendCheckOnline(customMsg);
    }

    /**
     * 发送呼叫信息
     * @param customMsg
     */
    @Override
    public void sendCallInfo(RequestData customMsg) {
        voiceMonitorServerClient.setBusyLineIsF(true);
        voiceMonitorServerClient.sendCallInfo(customMsg);

    }

    @Override
    public void sendGasInfoToQueue(RequestData customMsg) {

        //根据终端ID取余分区，保证每个人都能进入同一个分区，实现分区消费数据有序
        kafkaClient.send("gas_kafka.tut",JSON.toJSONString(customMsg),customMsg.getTerminalId());
    }

    @Override
    public void sendSelfCheckResult(RequestData customMsg) {
        byte[] body = customMsg.getBody();
        int wifiError = body[0] & 0xff;
        int voiceError = body[1] & 0xff;
        int coError = body[2] & 0xff;
        int co2Error = body[3] & 0xff;
        int o2Error = body[4] & 0xff;
        int ch4Error = body[5] & 0xff;
        int tWError = body[6] & 0xff;
        int electric = body[7] & 0xff;

        Malfunction malfunction = Malfunction.getInstance();

        malfunction.setSelfCheckTime(customMsg.getTime());
        malfunction.setCo2Error(co2Error);
        malfunction.setCoError(coError);
        malfunction.setCh4Error(ch4Error);
        malfunction.setWifiError(wifiError);
        malfunction.setVoiceError(voiceError);
        malfunction.setTerminalId(customMsg.getTerminalId());
        malfunction.setTerminalIp(customMsg.getTerminalIp() + ":" + customMsg.getTerminalPort());
        malfunction.setCreateTime(new Date());
        malfunction.setO2Error(o2Error);
        malfunction.settError(tWError);
        malfunction.sethError(tWError);
        malfunction.setStatus(0);
        malfunction.setElectric(electric);

//        kafkaSender.send(malfunction,"malfunction.tut");
    //    producerService.send("malfunction.tut", JSON.toJSONString(malfunction),customMsg.getTerminalPort());
        kafkaClient.sendSelfCheckResult("malfunction.tut",JSON.toJSONString(malfunction),customMsg.getTerminalPort());
    }

    @Override
    public void sendSoftWareVersion(RequestData customMsg) {
        kafkaClient.sendData("softWareVersion",customMsg);
    }

    @Override
    public void sendHandWareVersion(RequestData customMsg) {
        kafkaClient.sendData("handWareVersion",customMsg);
    }

    @Override
    public void sendUpLoadIp(RequestData customMsg) {

//        String key = customMsg.getTerminalIp() + ":" + customMsg.getTerminalPort();
//        String key1 = String.valueOf(customMsg.getTerminalId());
//        String val = JSON.toJSONString(customMsg);
        kafkaClient.send("updateIp.tut",JSON.toJSONString(customMsg),customMsg.getTerminalPort());
//        boolean flag = redisService.hasKey(key);
//        if (flag){
//            redisService.getAndSet(key,val);
//            redisService.getAndSet(key1,val);
//        }else {
//            redisService.setNoTime(key1,val);
//            redisService.setNoTime(key,val);
//        }

    }

    @Override
    public void sendTerminalInfoToQueue(RequestData customMsg) {
        kafkaClient.send("terminalInfo.tut",JSON.toJSONString(customMsg),customMsg.getTerminalPort());
    }

    @Override
    public void sendUpdateVoiceStatus(RequestData customMsg) {
        kafkaClient.send("updateVoiceReadStatus.tut",JSON.toJSONString(customMsg),customMsg.getTerminalPort());
    }

    @Override
    public void sendPowerStatus(RequestData customMsg) {
        byte[] body = customMsg.getBody();
        int electric_value = body[0] & 0xff;
        LackElectric lackElectric = LackElectric.getInstance();
        lackElectric.setElectricValue(electric_value);
        lackElectric.setLackType(1);
        lackElectric.setUploadId(customMsg.getTerminalId());
        lackElectric.setUploadTime(new Date());
        kafkaClient.send("powerStatus.tut",JSON.toJSONString(lackElectric),customMsg.getTerminalPort());
    }

    @Override
    public void processResponse(RequestData customMsg) {
        String ip = customMsg.getTerminalIp();
        Integer port = customMsg.getTerminalPort();
        String ipPort=ip+":"+port;
        int terminalId = customMsg.getTerminalId();
//        Map<String, Object> staffInfo = staffService.findStaffIdByTerminalId(terminalId);

//        Map<String, Object> staffInfo = staffGroupTerminalServiceClient.findStaffIdByTerminalId(terminalId);
        Map<String, Object> staffInfo = staffMapper.selectStaffInfoByTerminalId(terminalId);
//        customMsg.getTerminalIp();
        //定义map储存数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("cmd","2008");//2008表示发起呼叫
        map.put("ipPort",ipPort);
        map.put("staffInfo",staffInfo);


        byte result = customMsg.getResult();
        if(result==0x55){
            map.put("result","22");
            //发送给浏览器，终端已接听
//            WSVoiceStatusServer.
        }else{
            map.put("result","88");
        }

            String keyStr= JSON.toJSONString(new WebSocketData(3,map));
            voiceMonitorServerClient.sendInfoToWs(keyStr);
            //WSVoiceStatusServer.sendInfo(keyStr);
    }

    @Override
    public void sendConfiguredResponse(RequestData customMsg) {
        kafkaClient.send("configResp.tut",JSON.toJSONString(customMsg),customMsg.getTerminalPort());
    }

    @Override
    public Map<String, Object> findStandardIdByStationNum(Integer stationNum) {

//        baseStationService.findStandardIdByStationNum(stationNum);
        Map<String, Object> params = new HashMap<>();
        params.put("stationNum", stationNum);
        return baseStationMapper.selectStandardIdByStationNum(params);
    }

    @Override
    public GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId) {

        return systemServiceClient.getWarnLevelSettingByGasLevelId(standardId);
    }

    @Override
    public void removeStaffSet(Integer staffId) {
        kafkaConsumerClient.removeStaffSet(staffId);
    }

    @Override
    public void removeLeaderSet(Integer staffId) {
        kafkaConsumerClient.removeLeaderSet(staffId);
    }

    @Override
    public void removeOutPersonSet(Integer staffId) {
        kafkaConsumerClient.removeOutPersonSet(staffId);
    }

    @Override
    public void removeCarSet(Integer staffId) {
        kafkaConsumerClient.removeCarSet(staffId);
    }

    @Override
    public void sendInfoToWsServer(String toJSONString) {
        wsPushServiceClient.sendWSSiteServer(toJSONString);
    }

    @Override
    public void pushRtPersonData() {
        kafkaConsumerClient.pushRtPersonData();
    }

    @Override
    public Integer findTerminalIdByIpAndPort(String terminalIp, int port) {

        /*return staffGroupTerminalServiceClient.findTerminalIdByIpAndPort(terminalIp, port);*/
        return  terminalIpPortMapper.findTerminalIdByIpPort(terminalIp, port);
    }

    @Override
    public Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId) {

       /* return staffGroupTerminalServiceClient.selectStaffInfoByTerminalId(terminalId);*/
        return staffMapper.selectStaffInfoByTerminalId(terminalId);
    }

    @Override
    public Integer getBatteryNumByTerminalNum(Integer terminalNum) {
        return NettyServerHandler.battery.get(terminalNum);
    }

    @Override
    public void checkTempSendListAndToSend(RequestData customMsg) {
        Integer terminalId = customMsg.getTerminalId();
        List<TemporarySendList> list =  temporarySendListMapper.checkWaitingForDataToBeSent(terminalId);
        if (null != list && !list.isEmpty()){
            for (TemporarySendList temporarySendList: list) {
                //todo 发送数据

                //待发送的音频URL
                String voiceUrl = temporarySendList.getVoiceUrl();
                // 发送队列

                VoiceDto voiceDto = new VoiceDto();
                voiceDto.setUserId(0);
                voiceDto.setVoiceUrl(voiceUrl);
                voiceDto.setSeqId(SequenceIdGenerate.getSequenceId());
                voiceDto.setStationId(customMsg.getStationId());
                voiceDto.setStationIp1(customMsg.getStationIp1());
                voiceDto.setStationIp2(customMsg.getStationIp2());
                voiceDto.setTerminalId(terminalId);
                voiceDto.setTerminalPort(customMsg.getTerminalPort());
                voiceDto.setStationPort(customMsg.getStationPort());
                voiceDto.setTerminalIp1(customMsg.getTerminalIp1());
                voiceDto.setTerminalIp2(customMsg.getTerminalIp2());
                voiceDto.setTerminalIp(customMsg.getTerminalIp());
                kafkaClient.send("voiceSender.tut", JSON.toJSONString(voiceDto), customMsg.getTerminalPort());
                temporarySendListMapper.updateTemporarySendListIsSend(temporarySendList.getTemporarySendListId());
            }
        }
    }

    @Override
    public void searchPersonInfoByTerminalId(RequestData customMsg) {

        Integer terminalId = customMsg.getTerminalId();
        Map<String, Object> staffInfo = staffMapper.selectStaffInfoByTerminalId(terminalId);
        if (null == staffInfo)
            return;
        Integer staffId = (Integer) staffInfo.get("staff_id");
        if (null == staffId)
            return;
        Map<String, Object> res = staffGroupTerminalServiceClient.getDeptAndGroupNameByStaffId(staffId);

        if (null == res){
            return;
        }

        String staffName = ((String) staffInfo.get("staff_name")).trim();
        String deptName = ((String) res.get("deptName")).trim();


        char[] charArrOfStaffName = staffName.toCharArray();
        char[] charArrOfDeptName = deptName.toCharArray();

        int charArrLenOfStaffName = charArrOfStaffName.length;
        int charArrLenOfDeptName = charArrOfDeptName.length;

        // 数据包总长度 = 34长度的包头 + staffName的长度 + deptName的长度 + 2字节的长度位置
        int len = 34 + charArrLenOfStaffName * 3 + charArrLenOfDeptName * 3 + 2;

        byte[] body = new byte[len];
//        byte[] len1 = NettyDataUtils.intToByteArray(charArrLenOfStaffName);
//        byte[] len2 = NettyDataUtils.intToByteArray(charArrLenOfDeptName);
        // 暂时认定，员工姓名长度以及部门的长度 都不超过255一个字节的长度 即 80 的字符
        body[0] = (byte) ((charArrLenOfStaffName * 3) & 0xff);
        body[1] = (byte) ((charArrLenOfDeptName * 3) & 0xff);

        for (int i = 2,j = 0 ; i < (2 + charArrLenOfStaffName * 3) && j < charArrLenOfStaffName; i = i + 3,j++){
            if (charArrOfStaffName[j] <= 128) {
                body[i] = 0;
                body[i + 1] = 0;
                body[i + 2] = (byte) (charArrOfStaffName[j] &0xff);
            } else {
                String s = String.valueOf(charArrOfStaffName[j]);
                byte[] t_s_b = s.getBytes();
                body[i] = (byte)(t_s_b[0]&0xff);
                body[i + 1] = (byte)(t_s_b[1]&0xff);
                body[i + 2] = (byte)(t_s_b[2]&0xff);
            }
        }
        for (int i = 2 + charArrLenOfStaffName,j = 0 ; i < len && j < charArrLenOfDeptName; i = i+3,j++){
            if (charArrOfDeptName[j] <= 128) {
                body[i] = 0;
                body[i + 1] = 0;
                body[i + 2] = (byte) (charArrOfDeptName[j] &0xff);
            } else {
                String s = String.valueOf(charArrOfDeptName[j]);
                byte[] t_s_b = s.getBytes();
                body[i] = (byte)(t_s_b[0]&0xff);
                body[i + 1] = (byte)(t_s_b[1]&0xff);
                body[i + 2] = (byte)(t_s_b[2]&0xff);
            }
        }



//        body[1] =
//        for (int i = 0, j = 0; i < body.length && j < charArrLen; i = i + 3, j++) {
//            if (charArr[j] <= 128) {
//                body[i] = 0;
//                body[i + 1] = 0;
//                body[i + 2] = (byte) (charArr[j] &0xff);
//            } else {
//                String s = String.valueOf(charArr[j]);
//                byte[] t_s_b = s.getBytes();
//                body[i] = (byte)(t_s_b[0]&0xff);
//                body[i + 1] = (byte)(t_s_b[1]&0xff);
//                body[i + 2] = (byte)(t_s_b[2]&0xff);
//            }
//        }


        ResponseData responseData = ResponseData.getResponseData();
//        RequestData requestData = RequestData.getInstance();
//
//        requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
//        requestData.setStationIp("0.0");
//        requestData.setStationId(customMsg.getStationId());
//        requestData.setStationPort();
//        requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
//        requestData.setSequenceId(SequenceIdGenerate.getSequenceId());
//        requestData.setNdName(ConstantValue.MSG_BODY_NODE_NAME_PERSON_INFO_SEARCH);
//        requestData.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
//        requestData.setNodeCount((byte) 0);
//        requestData.setLength(len);
//        requestData.setBody(body);
        customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
        customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_PERSON_INFO_SEARCH);
        customMsg.setSequenceId(SequenceIdGenerate.getSequenceId());
        customMsg.setLength(len);
        customMsg.setBody(body);
        responseData.setCustomMsg(customMsg);
        SingletonClient.getSingletonClient().sendCmd(responseData);
    }

}
