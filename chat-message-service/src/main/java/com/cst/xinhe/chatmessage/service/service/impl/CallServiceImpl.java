package com.cst.xinhe.chatmessage.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.chatmessage.service.client.KafkaClient;
import com.cst.xinhe.chatmessage.service.client.TerminalMonitorClient;
import com.cst.xinhe.chatmessage.service.service.CallService;
import com.cst.xinhe.chatmessage.service.service.ChatMessageService;
import com.cst.xinhe.chatmessage.service.util.IpAddr;
import com.cst.xinhe.common.netty.utils.FileUtils;
import com.cst.xinhe.common.utils.FileType;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dao.chat.TemporarySendListMapper;
import com.cst.xinhe.persistence.dao.e_call.ECallMapper;
import com.cst.xinhe.persistence.dao.staff.StaffOrganizationMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dto.voice.VoiceDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.persistence.model.chat.TemporarySendList;
import com.cst.xinhe.persistence.model.e_call.ECall;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-05 10:35
 **/
@Service
public class CallServiceImpl implements CallService {


    @Resource
    private StaffOrganizationMapper staffOrganizationMapper;

    @Resource
    private ChatMessageService chatMsgService;

    @Resource
    private StaffTerminalMapper staffTerminalMapper;

    @Resource
    private KafkaClient kafkaClient;

    @Value("${ipPrefix}")
    private String ipPrefix;


    @Resource
    private TerminalMonitorClient terminalMonitorClient;


    @Resource
    private TerminalUpdateIpMapper terminalUpdateIpMapper;

    @Resource
    private TemporarySendListMapper temporarySendListMapper;

    @Resource
    private ECallMapper eCallMapper;

    @Value("${constant.webBaseUrl}")
    public String webBaseUrl ;
    @Value("${constant.basePath}")
    public String basePath ;
    @Value("${constant.rangBasePath}")
    public String rangBasePath ;

    @Override
    public ChatMsg callStaffByStaffId(MultipartFile wavFile, Integer staffId, Integer userId) {
//        Integer terminalId = terminalService.findTerminalInfoByStaffId(staffId);
        Date currentDate = new Date();
        Integer terminalId = staffTerminalMapper.selectTerminalIdByStaffId(staffId);
//        Integer terminalId = staffGroupTerminalServiceClient.findTerminalInfoByStaffId(staffId);
        StringBuffer folderName = new StringBuffer(basePath);
        folderName.append(terminalId).append(File.separator);
        StringBuffer fileName = new StringBuffer(DateConvert.convert(currentDate, 15));
        Integer seq = terminalMonitorClient.getSequenceId();
        fileName.append(terminalId).append(seq);
        FileUtils.createFile(folderName.toString(), fileName.toString(), FileType.WAV);
        File sendFile = new File(folderName.toString() + fileName.toString() + "." + FileType.WAV);
        try {
            wavFile.transferTo(sendFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String realUrl = folderName.toString() + fileName.toString() + "." + FileType.WAV;

        VoiceDto voiceDto = new VoiceDto();

//        Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
//        Map<String, Object> terminalInfo = staffGroupTerminalServiceClient.selectTerminalIpInfoByTerminalId(terminalId);
        Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
        ChatMsg chatMsg = new ChatMsg();
        if (null != terminalInfo && !terminalInfo.isEmpty()) {

            String stationIp = (String) terminalInfo.get("station_ip");
            String stationIps[] = stationIp.split("\\.");
            Integer stationIp1 = Integer.parseInt(stationIps[0]);
            Integer stationIp2 = Integer.parseInt(stationIps[1]);

            String terminalIp = (String) terminalInfo.get("terminal_ip");
            String terminalIps[] = terminalIp.split("\\.");
            Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
            Integer terminalIp2 = Integer.parseInt(terminalIps[1]);

            Integer terminalPort = (Integer) terminalInfo.get("terminal_port");

            Integer stationPort = (Integer) terminalInfo.get("station_port");

            Integer stationId = (Integer) terminalInfo.get("station_id");
            voiceDto.setStaffId(staffId);
            voiceDto.setUserId(userId);
            voiceDto.setVoiceUrl(realUrl);
            voiceDto.setSeqId(seq);
            voiceDto.setStationId(stationId);
            voiceDto.setStationIp1(stationIp1);
            voiceDto.setStationIp2(stationIp2);
            voiceDto.setTerminalId(terminalId);
            voiceDto.setTerminalPort(terminalPort);
            if (null == stationPort)
                stationPort = new Integer(0);
            voiceDto.setStationPort(stationPort);
            voiceDto.setTerminalIp1(terminalIp1);
            voiceDto.setTerminalIp2(terminalIp2);
            voiceDto.setTerminalIp(terminalIp);

//            kafkaSender.send(voiceDto, "voiceSender.tut");


//            terminalMonitorClient.sendUrl(voiceDto); //发送 URL
            StringBuffer ipPort = new StringBuffer(ipPrefix);
            ipPort.append(terminalIp1).append(".").append(terminalIp2).append(":").append(terminalPort);
            boolean flag = terminalMonitorClient.getChanelByName(ipPort.toString());
            if (flag){
                kafkaClient.send("voiceSender.tut", JSON.toJSONString(voiceDto), terminalPort);
            }else {
                TemporarySendList temporarySendList = new TemporarySendList();
                temporarySendList.setType(0);
                temporarySendList.setCreateTime(currentDate);
                temporarySendList.setTerminalId(terminalId);
                temporarySendList.setVoiceUrl(realUrl);
                temporarySendListMapper.insert(temporarySendList);
            }
            chatMsg.setTerminalId(terminalId);
            chatMsg.setSequenceId(seq + "" + currentDate.getTime() + terminalId);
            chatMsg.setConvertTime(new Date());
            chatMsg.setStationIp(stationIp);
            chatMsg.setReceiceUserId(staffId);
            chatMsg.setIsDel(0);
            chatMsg.setPostUserId(userId);
            chatMsg.setPostTime(new Date());
            chatMsg.setPostIp(IpAddr.getCurrentIp().getHostAddress());
            chatMsg.setPostMsg(realUrl);
            chatMsg.setStatus(false);
            chatMsg.setReceiveIp(terminalIp);
            chatMsgService.insertRecord(chatMsg);
            chatMsg.setPostMsg(realUrl.replace(basePath, webBaseUrl));

        }
        return chatMsg;
    }

    @Override
    public boolean pingTerminal(Integer staffId) {

//        Integer terminalId = terminalService.findTerminalInfoByStaffId(staffId);
//        Integer terminalId = staffGroupTerminalServiceClient.findTerminalInfoByStaffId(staffId);
        Integer terminalId = staffTerminalMapper.selectTerminalIdByStaffId(staffId);
        if (null != terminalId &&terminalId!=0) {
            Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
//            Map<String, Object> terminalInfo =staffGroupTerminalServiceClient.selectStaffInfoByTerminalId(terminalId);
            if (terminalInfo != null && !terminalInfo.isEmpty()) {
                if (terminalInfo.containsKey("terminal_ip") && terminalInfo.containsKey("terminal_port")){
                    String terminalIp = (String) terminalInfo.get("terminal_ip");
                    Integer terminalPort = (Integer) terminalInfo.get("terminal_port");

                    String terminalIps[] = terminalIp.split("\\.");
                    Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
                    Integer terminalIp2 = Integer.parseInt(terminalIps[1]);
                    StringBuffer ipPort = new StringBuffer(ipPrefix);
                    ipPort.append(terminalIp1).append(".").append(terminalIp2).append(":").append(terminalPort);
                    boolean flag = terminalMonitorClient.getChanelByName(ipPort.toString());
                    return flag;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean pingTerminalByTerminalNum(Integer terminalNum) {
        //        Integer terminalId = terminalService.findTerminalInfoByStaffId(staffId);
//        Integer terminalNum = staffGroupTerminalServiceClient.findTerminalInfoByStaffId(staffId);
        if (terminalNum != null&&terminalNum!=0) {
            Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalNum);
//            Map<String, Object> terminalInfo =staffGroupTerminalServiceClient.selectTerminalIpInfoByTerminalId(terminalNum);
            if (null != terminalInfo && !terminalInfo.isEmpty()) {
                if (terminalInfo.containsKey("terminal_ip") && terminalInfo.containsKey("terminal_port")){
                    String terminalIp = (String) terminalInfo.get("terminal_ip");
                    Integer terminalPort = (Integer) terminalInfo.get("terminal_port");

                    String terminalIps[] = terminalIp.split("\\.");
                    Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
                    Integer terminalIp2 = Integer.parseInt(terminalIps[1]);
                    StringBuffer ipPort = new StringBuffer(ipPrefix);
                    ipPort.append(terminalIp1).append(".").append(terminalIp2).append(":").append(terminalPort);
                    boolean flag = terminalMonitorClient.getChanelByName(ipPort.toString());
                    return flag;
                }

            }
            return false;
        }
        return false;
    }

    @Override
    public PageInfo<Map<String, Object>> getECallList(Integer pageSize, Integer startPage, String staffName, Integer staffId) {
        Page<Map<String, Object>> page = PageHelper.startPage(startPage,pageSize);
        Map<String, Object> params = new HashMap<>();
        params.put("staffName", staffName);
        params.put("staffId", staffId);
        List<Map<String, Object>> mapList = eCallMapper.selectByParams(params);
        for (Map<String, Object> item : page.getResult()){
            Integer groupId = (Integer) item.get("group_id");
            String deptName = getDeptNameByGroupId(groupId);
            item.put("dept_name",deptName);
        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
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
