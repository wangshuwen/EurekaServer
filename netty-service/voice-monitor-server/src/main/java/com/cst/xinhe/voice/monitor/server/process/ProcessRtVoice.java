package com.cst.xinhe.voice.monitor.server.process;

import com.cst.xinhe.base.context.SpringContextUtil;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.rt_gas.RtGasInfoMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dao.terminal_road.TerminalRoadMapper;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.voice.monitor.server.channel.VoiceChannelMap;
import com.cst.xinhe.voice.monitor.server.service.VoiceMonitorService;
import com.cst.xinhe.voice.monitor.server.ws.WSVoiceStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ProcessRealTimeVoice
 * @Description
 * @Auther lifeng
 * @DATE 2018/12/6 9:47
 * @Vserion v0.0.1
 */
public class ProcessRtVoice {



    private volatile static ProcessRtVoice processRealTimeVoice;
    //从配置文件获取ip前缀
    private static String ip_prefix;

//    @Value("${ip.prefix}")
    public void setIp_prefix(String ip_prefix) {
        ProcessRtVoice.ip_prefix = "192.168.";
    }
//    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
//    private TerminalService terminalService = applicationContext.getBean(TerminalServiceImpl.class);
//    private TerminalUpdateIpMapper terminalUpdateIpMapper = applicationContext.getBean(TerminalUpdateIpMapper.class);

    //    @Autowired
    private TerminalService terminalService;
    //    @Autowired
    private StaffService staffService;
    //    @Autowired
    private RtGasInfoMapper rtGasInfoMapper;

    //    @Autowired
    private TerminalUpdateIpMapper terminalUpdateIpMapper;
    //    @Autowired
    private TerminalRoadMapper terminalRoadMapper;


    private VoiceMonitorService voiceMonitorService;
//    @PostConstruct
//    public void init() {
//        realTimeVoice = this;
//        realTimeVoice.terminalUpdateIpMapper = this.terminalUpdateIpMapper;
//        realTimeVoice.terminalService = this.terminalService;
//    }

    public ProcessRtVoice() {
        this.terminalUpdateIpMapper = SpringContextUtil.getBean(TerminalUpdateIpMapper.class);
        this.terminalService = SpringContextUtil.getBean(TerminalServiceImpl.class);
        this.terminalRoadMapper = SpringContextUtil.getBean(TerminalRoadMapper.class);
        this.rtGasInfoMapper = SpringContextUtil.getBean(RtGasInfoMapper.class);
        this.staffService = SpringContextUtil.getBean(StaffService.class);
        this.voiceMonitorService = SpringContextUtil.getBean(VoiceMonitorService.class);
    }

    public static ProcessRtVoice getProcessRealTimeVoice() {
        if (null == processRealTimeVoice) {
            synchronized (ProcessRtVoice.class) {
                if (null == processRealTimeVoice) {
                    processRealTimeVoice = new ProcessRtVoice();
                }
            }
        }
        return processRealTimeVoice;
    }

    private static boolean busyLine = false;    //默认false，处于可以呼叫状态，true处于占线  //用来判断是否占线


    //判断浏览器是不是属于可呼叫的状态，fasle不占线，true占线
    public static synchronized boolean isBusyLine() {
        return VoiceChannelMap.getChannelNum() > 0;
    }

    private static boolean roomStatus = false;//默认false，没人,处于不可呼叫状态，   //用来判断是否可以呼叫

    public static synchronized boolean getRoomStatus() { // 获取房间状态
        return roomStatus;
    }

    public static synchronized boolean openRoom() {  // ，设置为 true 可加入状态
        return ProcessRtVoice.roomStatus = true;
    }

    public static synchronized boolean closeRoom() { //退出 ，并设置为flase 可以进入状态
        return ProcessRtVoice.roomStatus = false;
    }

    private static ResponseData resp = new ResponseData();
    private static ObjectMapper json = new ObjectMapper();

    /**
     * 方法实现说明
     *
     * @param customMsg
     * @return
     * @throws
     * @author wangshuwen
     * @description 下面呼叫上面：检测是否在线，是否可以进行呼叫
     * @date 2018/12/11 13:36
     */
    public void sendCheckOnline(RequestData customMsg) {
        customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
        customMsg.setLength(34);
        customMsg.setNodeCount((byte) 0x00);
        customMsg.setNdName(0x2005);
        //  customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_CHECK_ONLINE);
        //判断是否浏览器在线可以呼叫
        if (getRoomStatus()) {
            if (isBusyLine()) {
                //在线，占线
                customMsg.setResult((byte) 0x66);
            } else {
                //在线，成功
                customMsg.setResult((byte) 0x55);
            }
        } else {
            //不在线，返回结果0x77表示浏览器未在线
            customMsg.setResult((byte) 0x77);
        }
        resp.setCustomMsg(customMsg);
        //根据协议回复给终端 不同结果值
//        SingletonClient.getSingletonClient().sendCmd(resp);
        voiceMonitorService.sendDataToTerminalMonitorServer(resp);
    }

    /**
     * 方法实现说明
     *
     * @param customMsg
     * @return
     * @throws
     * @author wangshuwen
     * @description 下面呼叫上面: 发送呼叫请求
     * @date 2018/12/11 13:37
     */
    public void sendCallInfo(RequestData customMsg) {
        //判断协议命令
        //发送给前端websocket指令，同时发送呼叫人的ID与终端的ID ，类似于KEY，
        String ip = customMsg.getTerminalIp();
        Integer port = customMsg.getTerminalPort();
        String ipPort = ip + ":" + port;
        HashMap<String, Object> map = new HashMap<>();
        map.put("cmd", "2008");//2008表示发起呼叫
        map.put("ipPort", ipPort);
        map.put("result", "44");
        //TODO 根据IP查找人员ID 人员所在的部门分组，最近一次气体信息以及定位信息
        TerminalUpdateIp terminalUpdateIp = terminalUpdateIpMapper.findTerminalIdByIpAndPort(ip, port);
        if (terminalUpdateIp != null) {
            Integer terminalId = terminalUpdateIp.getTerminalNum();
            //员工信息
            Map<String, Object> staffInfo = staffService.findStaffIdByTerminalId(terminalId);
            Integer staffId = (Integer) staffInfo.get("staff_id");
            //员工、部门、分组名称
            HashMap<String, Object> nameInfo = staffService.getDeptAndGroupNameByStaffId(staffId);
            //气体信息
            Map<String, Object> gasInfo = rtGasInfoMapper.selectGasInfoByTerminalLastTime(terminalId);
            Integer positionId = (Integer) gasInfo.get("position_id");
            //终端路线信息
            TerminalRoad positionInfo = terminalRoadMapper.selectByPrimaryKey(positionId);
            map.put("staffInfo", staffInfo);
            map.put("nameInfo", nameInfo);
            map.put("gasInfo", gasInfo);
            map.put("positionInfo", positionInfo);
        }

        try {
            String keyStr = json.writeValueAsString(new WebSocketData(3, map));
            WSVoiceStatus.sendInfo(keyStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使前端响铃并
    }

    /**
     * @description: 上面呼叫下面：检测是否在线，是否可以进行呼叫
     * @param: [staffId] 实际值为员工id
     * @return: void
     * @author: wangshuwen&lifeng
     * @date: 2019-04-02
     */
    public static void checkOnline(String staffId) {

        //根据员工id查询对应的ip和port
        Integer terminalId = getProcessRealTimeVoice().terminalService.findTerminalInfoByStaffId(Integer.parseInt(staffId));
        Map<String, Object> ipInfoMap = getProcessRealTimeVoice().terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
        String terminalIp = (String) ipInfoMap.get("terminal_ip");
        Integer terminalPort = (Integer) ipInfoMap.get("terminal_port");
        String ipPort = ip_prefix + terminalIp + ":" + terminalPort;
        Map<String, Object> staffInfo = getProcessRealTimeVoice().staffService.findStaffIdByTerminalId(terminalId);
        //定义map储存数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("cmd", "2008");//2008表示发起呼叫
        map.put("ipPort", ipPort);
        map.put("staffInfo", staffInfo);
        //判断终端
        //Channel channel = ChannelMap.getChannelByName(ipPort); //todo client search ipPort return JSON
        boolean f = getProcessRealTimeVoice().voiceMonitorService.getChannelByName(ipPort);
        getProcessRealTimeVoice().voiceMonitorService.sendDataToTerminalMonitorServer(resp);
        int voiceChannelNum = VoiceChannelMap.getChannelNum();
        if (!f) {
            if (voiceChannelNum > 0) {
                //表示语音通道被占用，正在通话中
                map.put("result", "66");
            } else {
                //表示终端不在线
                map.put("result", "77");
            }
        } else {
            //表示终端在线，可以呼叫
            map.put("result", "55");
        }
        try {
            String keyStr = json.writeValueAsString(new WebSocketData(3, map));
            WSVoiceStatus.sendInfo(keyStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
