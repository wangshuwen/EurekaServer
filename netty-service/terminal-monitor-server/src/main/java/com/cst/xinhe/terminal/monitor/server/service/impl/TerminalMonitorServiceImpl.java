package com.cst.xinhe.terminal.monitor.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.chat.TemporarySendListMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dto.voice.VoiceDto;
import com.cst.xinhe.persistence.model.chat.TemporarySendList;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.terminal.monitor.server.channel.ChannelMap;
import com.cst.xinhe.terminal.monitor.server.client.*;
import com.cst.xinhe.terminal.monitor.server.handle.NettyServerHandler;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import com.cst.xinhe.terminal.monitor.server.utils.SequenceIdGenerate;
import io.netty.channel.Channel;
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

    @Autowired
    private VoiceMonitorServerClient voiceMonitorServerClient;

    @Autowired
    private KafkaClient kafkaClient;

    @Resource
    private BaseStationMapper baseStationMapper;

    @Autowired
    private WsPushServiceClient wsPushServiceClient;

    @Autowired
    private KafkaConsumerClient kafkaConsumerClient;

    @Resource
    private TemporarySendListMapper temporarySendListMapper;

    @Autowired
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private TerminalUpdateIpMapper terminalUpdateIpMapper;
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

        voiceMonitorServerClient.sendCallInfo(customMsg);
    }

    @Override
    public void sendGasInfoToQueue(RequestData customMsg) {

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
        kafkaClient.send("updateIp.tut",JSON.toJSONString(customMsg),customMsg.getTerminalPort());
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
    public TerminalUpdateIp findTerminalIdByIpAndPort(String terminalIp, int port) {

        /*return staffGroupTerminalServiceClient.findTerminalIdByIpAndPort(terminalIp, port);*/
        return  terminalUpdateIpMapper.findTerminalIdByIpAndPort(terminalIp, port);
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
}
