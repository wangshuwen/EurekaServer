package com.cst.xinhe.terminal.monitor.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.dao.chat.ChatMsgMapper;
import com.cst.xinhe.persistence.dao.chat.TemporarySendListMapper;
import com.cst.xinhe.persistence.dao.e_call.ECallMapper;
import com.cst.xinhe.persistence.dao.rang_setting.RangSettingMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dao.updateIp.TerminalIpPortMapper;
import com.cst.xinhe.persistence.dto.voice.VoiceDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.persistence.model.chat.TemporarySendList;
import com.cst.xinhe.persistence.model.e_call.ECall;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.rt_gas.GasPosition;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.cst.xinhe.terminal.monitor.server.channel.ChannelMap;
import com.cst.xinhe.terminal.monitor.server.client.*;
import com.cst.xinhe.terminal.monitor.server.handle.NettyServerHandler;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import com.cst.xinhe.terminal.monitor.server.utils.RSTL;
import com.cst.xinhe.terminal.monitor.server.utils.SequenceIdGenerate;
import io.netty.channel.Channel;
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
    private TerminalMonitorClient terminalMonitorClient;

    @Resource
    private RSTL rstl;

    @Resource
    private RangSettingMapper rangSettingMapper;

    @Resource
    private WebServiceClient webServiceClient;

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
    private WebServiceClient staffGroupTerminalServiceClient;

    @Resource
    private StaffMapper staffMapper;


    @Resource
    private ECallMapper eCallMapper;

    @Resource
    private StaffOrganizationMapper staffOrganizationMapper;
    @Resource
    private TerminalIpPortMapper terminalIpPortMapper;

    @Resource
    private StaffTerminalMapper staffTerminalMapper;


    @Resource
    private ChatMsgMapper chatMsgMapper;

    @Override
    public void terminalHangUp(RequestData customMsg) {
        String ip = customMsg.getTerminalIp();
        Integer port = customMsg.getTerminalPort();
        String ipPort=ip+":"+port;
        int terminalId = customMsg.getTerminalId();
//        Map<String, Object> staffInfo = staffService.findStaffIdByTerminalId(terminalId);

//        Map<String, Object> staffInfo = staffGroupTerminalServiceClient.findStaffIdByTerminalId(terminalId);
        Map<String, Object> staffInfo = staffMapper.selectStaffInfoByTerminalId(terminalId);
//        customMsg.getTerminalIp();
        //定义map储存数据
        Integer groupId = (Integer) staffInfo.get("group_id");
        String dept_name = getDeptNameByGroupId(groupId);
        staffInfo.put("dept_name",dept_name);
        HashMap<String, Object> map = new HashMap<>();
        map.put("cmd","2008");//2008表示发起呼叫
        map.put("ipPort",ipPort);
        map.put("staffInfo",staffInfo);

            map.put("result","10");
            //发送给浏览器，终端已接听
//            WSVoiceStatusServer.
        String keyStr= JSON.toJSONString(new WebSocketData(3,map));
        voiceMonitorServerClient.sendInfoToWs(keyStr);
        //WSVoiceStatusServer.sendInfo(keyStr);

        //type=3的语音通话插入数据库，12456，前端访问插入
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setTerminalId(terminalId);
        chatMsg.setStatus(false);
        chatMsg.setPostUserId((Integer)staffInfo.get("staff_id"));
        chatMsg.setLengthMsg(3);
        chatMsg.setPostTime(new Date());
        chatMsg.setReceiceUserId(0);
        chatMsgMapper.insertSelective(chatMsg);



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
        kafkaClient.sendSelfCheckResult("malfunction.tut",JSON.toJSONString(malfunction),customMsg.getTerminalId());
    }

    @Override
    public void sendSoftWareVersion(RequestData customMsg) {
        byte[] body = customMsg.getBody();
        int first = body[0] & 0xff;
        int second = body[1] & 0xff;
        HashMap<String, Object> map = new HashMap<>();
        map.put("first",first);

        map.put("second",second);
        map.put("terminalId",customMsg.getTerminalId());

        kafkaClient.sendSelfCheckResult("softWareVersion.tut",JSON.toJSONString(map),customMsg.getTerminalId());
    }

    @Override
    public void sendHandWareVersion(RequestData customMsg) {
        byte[] body = customMsg.getBody();
        int first = body[0] & 0xff;
        int second = body[1] & 0xff;
        HashMap<String, Object> map = new HashMap<>();
        map.put("first",first);
        map.put("second",second);
        map.put("terminalId",customMsg.getTerminalId());

        kafkaClient.sendSelfCheckResult("handWareVersion.tut",JSON.toJSONString(map),customMsg.getTerminalId());

    }

    @Override
    public void sendUpLoadIp(RequestData customMsg) {

        kafkaClient.send("updateIp.tut",JSON.toJSONString(customMsg),customMsg.getTerminalId());
    }

    @Override
    public void sendTerminalInfoToQueue(RequestData customMsg) {
        kafkaClient.send("terminalInfo.tut",JSON.toJSONString(customMsg),customMsg.getTerminalPort());
    }

    @Override
    public void sendUpdateVoiceStatus(RequestData customMsg) {
        kafkaClient.send("updateVoiceReadStatus.tut",JSON.toJSONString(customMsg),customMsg.getTerminalId());
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
        kafkaClient.send("powerStatus.tut",JSON.toJSONString(lackElectric),customMsg.getTerminalId());
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
            voiceMonitorServerClient.setBusyLineIsF(true);
            //发送给浏览器，终端已接听
        //            WSVoiceStatusServer.
        }else if(result==0x66){
            map.put("result","66");
            voiceMonitorServerClient.setBusyLineIsF(false);
        }else{
            map.put("result","88");//拒接
            voiceMonitorServerClient.setBusyLineIsF(false);
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

        return webServiceClient.getWarnLevelSettingByGasLevelId(standardId);
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
                kafkaClient.send("voiceSender.tut", JSON.toJSONString(voiceDto), customMsg.getTerminalId());
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

        for (int i = 2, j = 0 ; i < (2 + charArrLenOfStaffName * 3) && j < charArrLenOfStaffName; i = i + 3,j++){
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
        for (int i = 2 + charArrLenOfStaffName * 3,j = 0 ; i < len && j < charArrLenOfDeptName; i = i+3,j++){
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

    @Override
    public void eCall(RequestData customMsg) {
        // 紧急呼叫

        GasPosition gasPosition = GasPosition.getInstance();
        byte[] body = customMsg.getBody();

//        GasInfo gasInfo = GasInfo.getInstance();
        double co0 = ((long) (((body[0] & 0xff) << 8) + (body[1] & 0xff)) / 10.0);
        gasPosition.setCo(co0);
        gasPosition.setCoUnit((int) body[2]);
        double co20 = ((long) (((body[3] & 0xff) << 8) + (body[4] & 0xff)) / 10.0);

        gasPosition.setCo2(co20);
        gasPosition.setCo2Unit((int) body[5]);
        double o20 = ((long) (((body[6] & 0xff) << 8) + (body[7] & 0xff)) / 10.0);
        gasPosition.setO2(o20);
        gasPosition.setO2Unit((int) body[8]);

        double ch40 = ((long) (((body[9] & 0xff) << 8) + (body[10] & 0xff)) / 10.0);
        gasPosition.setCh4(ch40);
        gasPosition.setCh4Unit((int) body[11]);
        /**
         * 判断温度数据的零上零下问题
         */
        double t0 = ((long) (((body[12] & 0xff) << 8) + (body[13] & 0xff)) / 10.0);
        byte flag = (byte) (body[14] & 0xff);
        if (flag == 0x00) {
            gasPosition.setTemperature(t0);
            gasPosition.setTemperatureUnit(0);
        }
        if (flag == 0x01) {
            gasPosition.setTemperature(t0);
            gasPosition.setTemperatureUnit(1);
        }
        if (flag == 0x10) {
            gasPosition.setTemperature(-t0);
            gasPosition.setTemperatureUnit(0);
        }
        if (flag == 0x11) {
            gasPosition.setTemperature(-t0);
            gasPosition.setTemperatureUnit(1);
        }
        double h0 = ((long) (((body[15] & 0xff) << 8) + (body[16] & 0xff)) / 10.0);
        gasPosition.setHumidity(h0);
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

//        UpLoadGasDto upLoadGasDto = UpLoadGasDto.getInstance();


        gasPosition.setSequenceId(customMsg.getSequenceId());


        gasPosition.setTerminalRealTime(customMsg.getTime());


        gasPosition.setTerminalIp(customMsg.getTerminalIp());


        gasPosition.setCreateTime(new Date());

//        gasPosition.setCreateTime(DateConvert.convertStampToDate(String.valueOf(System.currentTimeMillis()),19));

        gasPosition.setStationId(customMsg.getStationId());

        gasPosition.setStationIp(customMsg.getStationIp());

        gasPosition.setTerminalId(customMsg.getTerminalId());

//        RssiInfo rssiInfo = RssiInfo.getInstance();
        TerminalRoad road = new TerminalRoad();
        GasWSRespVO staff = findStaffNameByTerminalId(gasPosition.getTerminalId());
        Integer staffId = staff.getStaffId();
        gasPosition.setStaffId(staffId);
        gasPosition.setStaffName(staff.getStaffName());
        Map<String, Object> deptName = staffGroupTerminalServiceClient.getDeptAndGroupNameByStaffId(staffId);
        gasPosition.setDeptName((String)deptName.get("deptName"));


        try {
            road = rstl.locateConvert(gasPosition.getTerminalId(), baseStation1, baseStation2, rssi1, rssi2);
        }catch (RuntimeServiceException e){
            String b = staffMapper.findStaffTypeById(staffId);
            if ("1".equals(b)){
                wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(11,gasPosition)));
            } else {
                wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(10,gasPosition)));
            }
        }


        gasPosition.setInfoType(0);
        gasPosition.setTempPositionName(road.getTempPositionName());
        gasPosition.setPositionX(road.getPositionX());
        gasPosition.setPositionY(road.getPositionY());
        gasPosition.setPositionZ(road.getPositionZ());
        road.setStationId(gasPosition.getStationId());
        if(road.getTempPositionName()!=null&&!"".equals(road.getTempPositionName())){
            sendTempRoadName(customMsg.getTerminalId(),customMsg.getTerminalIp(),customMsg.getTerminalPort(),road.getTempPositionName());
        }


        //----------------------------------以下是判断出入问题------------------------------------
        //去除staffGroupTerminalServiceClient
        Map<String, Object> param = new HashMap<>();
        param.put("type",4);
        param.put("status",1);
        RangSetting rangSetting = rangSettingMapper.selectUrlByTypeAndStatus(param);
        if (null != rangSetting)
         gasPosition.setRangeUrl(rangSetting.getUrl());
        else
            gasPosition.setRangeUrl("static/audio/emergencyList/1.wav"); //添加默认铃声
        WebSocketData webSocketData = new WebSocketData(9,gasPosition);
        ECall eCall = new ECall();
        eCall.setCh4(gasPosition.getCh4());
        eCall.setCh4Unit(gasPosition.getCh4Unit());
        eCall.setCo(gasPosition.getCo());
        eCall.setCoUnit(gasPosition.getCoUnit());
        eCall.setCo2(gasPosition.getCo2());
        eCall.setCo2Unit(gasPosition.getCo2Unit());
        eCall.setCreateTime(gasPosition.getCreateTime());
        eCall.setField3(gasPosition.getField3());
        eCall.setField3Unit(gasPosition.getField3Unit());
        eCall.setHumidity(gasPosition.getHumidity());
        eCall.setHumidityUnit(gasPosition.getHumidityUnit());
        eCall.setO2(gasPosition.getO2());
        eCall.setO2Unit(gasPosition.getO2Unit());
        eCall.setTemperature(gasPosition.getTemperature());
        eCall.setTemperatureUnit(gasPosition.getTemperatureUnit());
        eCall.setTerminalId(gasPosition.getTerminalId());
        eCall.setTerminalIp(gasPosition.getTerminalIp());
        eCall.setTempPositionName(gasPosition.getTempPositionName());
        eCall.setTerminalRealTime(gasPosition.getTerminalRealTime());
        eCall.setStaffId(gasPosition.getStaffId());
        eCall.setStationId(gasPosition.getStationId());
        eCall.setStationId1(gasPosition.getStationId1());
        eCall.setStationId2(gasPosition.getStationId2());
        eCall.setStationIp(gasPosition.getStationIp());
        eCall.setWifiStrength1(gasPosition.getWifiStrength1());
        eCall.setWifiStrength2(gasPosition.getWifiStrength2());
        eCall.setPositionX(gasPosition.getPositionX());
        eCall.setPositionY(gasPosition.getPositionY());
        eCall.setPositionZ(gasPosition.getPositionZ());
        eCall.setSequenceId(gasPosition.getSequenceId());
        eCallMapper.insert(eCall);
        wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(webSocketData));
    }


    private GasWSRespVO findStaffNameByTerminalId(Integer terminalId) {

        Map<String, Object> resultMap = staffTerminalMapper.selectStaffNameByTerminalId(terminalId);

        GasWSRespVO gasWSRespVO = new GasWSRespVO();
        if (null != resultMap && resultMap.size() > 0) {
            Integer staffId = (Integer) resultMap.get("staff_id");
            String staffName = (String) resultMap.get("staff_name");
            Integer isPerson = (Integer) resultMap.get("is_person");
            Integer groupId = (Integer) resultMap.get("group_id");
            gasWSRespVO.setStaffName(staffName);
            gasWSRespVO.setStaffId(staffId);
            gasWSRespVO.setIsPerson(isPerson);
            gasWSRespVO.setGroupId(groupId);
            return gasWSRespVO;
        } else {
            gasWSRespVO.setStaffName("未知人员");
            gasWSRespVO.setStaffId(0);
            gasWSRespVO.setIsPerson(0);
            return gasWSRespVO;
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
