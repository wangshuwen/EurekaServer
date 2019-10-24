package com.cst.xinhe.terminal.monitor.server.handle;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.utils.GetUUID;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.attendance.AttendanceMapper;
import com.cst.xinhe.persistence.dao.attendance.StaffAttendanceRealRuleMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dao.updateIp.TerminalIpPortMapper;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.terminal.monitor.server.channel.ChannelMap;
import com.cst.xinhe.terminal.monitor.server.client.WsPushServiceClient;
import com.cst.xinhe.terminal.monitor.server.context.SpringContextUtil;
import com.cst.xinhe.terminal.monitor.server.process.ProcessVoice;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import com.cst.xinhe.terminal.monitor.server.service.impl.TerminalMonitorServiceImpl;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @ClassName NettyServerHandler    终端 数据   业务处理类
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/17 16:43
 * @Vserion v0.0.1
 */
@Sharable
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private volatile static int gasNum = 0;
    private volatile static int offNum = 0;
    private volatile  static  int exceptNum=0;
    private volatile  static  int removeNum=0;
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    public static final Map<Integer, Integer> battery = new HashMap<>();

//    private UpLoadService upLoadService;

//    private TerminalUpdateIpMapper terminalUpdateIpMapper;

//    private StaffMapper staffMapper;

    private Map<String, Object> mapOfWs;

//    private ProducerService producerService;

    TerminalMonitorService terminalMonitorService;

    private StaffMapper staffMapper;

    private TerminalIpPortMapper terminalIpPortMapper;

    private WsPushServiceClient  wsPushServiceClient;

    //为演示做准备开始
    private TimeStandardMapper timeStandardMapper;
    private StaffAttendanceRealRuleMapper staffAttendanceRealRuleMapper;
    private AttendanceMapper  attendanceMapper;
    //为演示做准备结束

    //    private static NettyServerHandler nettyServerHandler;
//
//    //注入上传数据服务
//    @Resource
//    private UpLoadService upLoadService;
//    @Resource
//    private TerminalUpdateIpMapper terminalUpdateIpMapper;
//    @Resource
//    private StaffMapper staffMapper;
//
//    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
//    public void init() {
//        nettyServerHandler = this;
//        nettyServerHandler.terminalUpdateIpMapper = this.terminalUpdateIpMapper;
//        nettyServerHandler.upLoadService = this.upLoadService;
//    }
    private ProcessVoice processVoice;

    //处理实时语音
//    private ProcessRealTimeVoice processRealTimeVoice;

    public NettyServerHandler() {
//        this.upLoadService = SpringContextUtil.getBean(UpLoadServiceImpl.class);
//        this.terminalUpdateIpMapper = SpringContextUtil.getBean(TerminalUpdateIpMapper.class);
//        this.staffMapper = SpringContextUtil.getBean(StaffMapper.class);
        this.mapOfWs = new HashMap<>();
//        this.producerService = SpringContextUtil.getBean(ProducerServiceImpl.class);

        this.processVoice = ProcessVoice.getProcessVoice();
        this.terminalMonitorService = SpringContextUtil.getBean(TerminalMonitorServiceImpl.class);
        this.staffMapper = SpringContextUtil.getBean(StaffMapper.class);
        this.terminalIpPortMapper = SpringContextUtil.getBean(TerminalIpPortMapper.class);

        this.wsPushServiceClient=SpringContextUtil.getBean(WsPushServiceClient.class);
//        this.processRealTimeVoice = ProcessRealTimeVoice.getProcessRealTimeVoice();

        //为演示做准备开始
        this.timeStandardMapper=SpringContextUtil.getBean(TimeStandardMapper.class);
        this.staffAttendanceRealRuleMapper=SpringContextUtil.getBean(StaffAttendanceRealRuleMapper.class);
        this.attendanceMapper=SpringContextUtil.getBean(AttendanceMapper.class);
        //为演示做准备结束
    }

    //注入Kafka
//    @Resource
//    KafkaSender kafkaSender;

    //注入处理声音


    /**
     * @param
     * @return void
     * @description 数据处理方法  执行主要业务
     * @date 2018/9/17 16:20
     * @auther lifeng
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseData resp = ResponseData.getResponseData();
        if (msg instanceof RequestData) {
            RequestData customMsg = (RequestData) msg;
            if (customMsg.getType() == ConstantValue.MSG_HEADER_FREAME_HEAD) {
                int cmd = customMsg.getCmd();
                int ndName = customMsg.getNdName();
                log.info("终端发送的cmd："+cmd+"ndName："+ndName);
                switch (cmd) {
                    case ConstantValue.MSG_HEADER_COMMAND_ID_NULL:  //对讲语音数据
                        if (ndName != ConstantValue.MSG_BODY_NODE_NAME_VOICE_DATA) {
                            log.error("非法语音数据包");
                            break;
                        }
                        try{
                            log.info("处理语音数据");
                            String wavPath = processVoice.process(customMsg);
                            if (!("error").equals(wavPath)) {
                                log.info("接收并转码成功");
                                log.info("生成文件路径" + wavPath);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            log.info("单条语音接收失败");
                            log.info("单条语音接收失败");
                        }


                        //向终端响应信息
                        /*customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                        customMsg.setLength(34);
                        customMsg.setResult((byte) 0x55);
                        customMsg.setNodeCount((byte) 0x00);
                        customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_VOICE_DATA);
                        resp.setCustomMsg(customMsg);
                        SingletonClient.getSingletonClient().sendCmd(resp);*/
                        break;
                    case ConstantValue.MSG_HEADER_COMMAND_ID_REQUEST:
                        log.info("采集数据上报");
                        switch (ndName) {
                            case ConstantValue.MSG_BODY_NODE_NAME_SENSOR_DATA:
                                log.info("气体信息");

                                //实时查询领导、员工、车辆的数量
                                customMsg.setLength(34);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                customMsg.setNodeCount((byte) 0x00);

                                customMsg.setTime(new Date());
                                resp.setCustomMsg(customMsg);
                                log.info("终端断线数量："+offNum);
                                log.info("终端断线数量："+offNum);
                                log.info("终端异常数量："+exceptNum);
                                resp.setCode((byte) 0x55);
                                System.out.println("接收到的气体数量：" + ++gasNum);
                                SingletonClient.getSingletonClient().sendCmd(resp);//返回气体成功标记
                                log.info("返回气体确认结束");

                                log.info("终端断线数量："+offNum);
                                log.info("终端断线数量："+offNum);
                                log.info("终端异常数量："+exceptNum);
                                log.info("终端被移除数量："+removeNum);
                                //upLoadService.sendGasInfoToQueue(customMsg);
                                terminalMonitorService.sendGasInfoToQueue(customMsg);
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_SELF_CHECK_RESULT:
//                                upLoadService.sendSelfCheckResult(customMsg);
                                terminalMonitorService.sendSelfCheckResult(customMsg);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                resp.setCustomMsg(customMsg);
                                SingletonClient.getSingletonClient().sendCmd(resp);
                                log.info("自检结果");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_SOFTWARE_VERSION:
                                //   upLoadService.sendSoftWareVersion(customMsg);
                                terminalMonitorService.sendSoftWareVersion(customMsg);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                resp.setCustomMsg(customMsg);
                                SingletonClient.getSingletonClient().sendCmd(resp);
                                log.info("软件版本号");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_HANDWARE_VERSION:
                                //      upLoadService.sendHandWareVersion(customMsg);
                                terminalMonitorService.sendHandWareVersion(customMsg);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                customMsg.setLength(34);
                                resp.setCustomMsg(customMsg);
                                SingletonClient.getSingletonClient().sendCmd(resp);
                                log.info("硬件版本号");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_UPDATE_IP:
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setLength(34);
                                customMsg.setResult((byte) 0x55);
                                resp.setCustomMsg(customMsg);
                                SingletonClient.getSingletonClient().sendCmd(resp);
                                //更新IP
//                                upLoadService.sendUpLoadIp(customMsg);
                                terminalMonitorService.sendUpLoadIp(customMsg);
                                //数据分析页面
//                                upLoadService.sendTerminalInfoToQueue(customMsg);
//                                terminalMonitorService.sendTerminalInfoToQueue(customMsg);
                                //发送待发送数据
                                terminalMonitorService.checkTempSendListAndToSend(customMsg);
                                ProcessSettingGasLevel.getSingletonClient().sendMsg(customMsg); // 气体标准下发
                                ProcessUploadFrequency.getSingletonProcessUploadFrequency().process(customMsg); // 设置终端的上传气体的频率
                                log.info("更新IP");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_MSG_READ_STATUS:
//                                upLoadService.sendUpdateVoiceStatus(customMsg);
                                terminalMonitorService.sendUpdateVoiceStatus(customMsg);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                customMsg.setLength(34);
                                resp.setCustomMsg(customMsg);
                                log.info("终端更新已读状态");
                                // 终端更新已读状态
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_POWER_STATUS:
//                                upLoadService.sendPowerStatus(customMsg);
                                terminalMonitorService.sendPowerStatus(customMsg);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                customMsg.setLength(34);
                                resp.setCustomMsg(customMsg);
                                SingletonClient.getSingletonClient().sendCmd(resp);
                                log.info("终端欠电提醒");
                                // 终端欠电提醒
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_E_CALL:
                                //紧急呼叫
//                                upLoadService.sendPowerStatus(customMsg);
                                terminalMonitorService.eCall(customMsg);
                                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                                customMsg.setLength(34);
                                resp.setCustomMsg(customMsg);
                                SingletonClient.getSingletonClient().sendCmd(resp);
                                log.info("紧急呼叫");
                                break;
                            default:
                                log.error("未知命令包");
                                break;
                        }
                        break;
                    case ConstantValue.MSG_HEADER_COMMAND_ID_HEARTBEAT:
                       /* log.info("心跳数据");
                      *//*  customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                        customMsg.setLength(34);
                        customMsg.setResult((byte) 0x55);
                        customMsg.setNodeCount((byte) 0x00);*//*
                        resp.setCustomMsg(customMsg);
                        SingletonClient.getSingletonClient().sendCmd(resp);
                        log.info("返回心跳包确认结束");*/

                        System.out.println("-------------------------3--------------------");
                        System.out.println("-------------------------3--------------------");
                        System.out.println("-------------------------3--------------------");
                        log.info("心跳数据");
                        customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                        customMsg.setLength(34);
                        customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                        customMsg.setNodeCount((byte) 0x00);
                        System.out.println("--------------心跳回复开始-----------");
                        System.out.println(customMsg.toString());
                        System.out.println("--------------心跳回复结束-----------");
                        resp.setCustomMsg(customMsg);
                        SingletonClient.getSingletonClient().sendCmd(resp);
                        break;
                    case ConstantValue.MSG_HEADER_COMMAND_ID_SEARCH:    // 协议 查询
                        switch (ndName) {
                            case ConstantValue.MSG_BODY_NODE_NAME_CHECK_ONLINE: //  检测是否在线
                                terminalMonitorService.checkSendCheckOnline(customMsg);
                                log.info("检测浏览器是否在线");
//                                processRealTimeVoice.sendCheckOnline(customMsg);
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_CALL: // 发起呼叫
                                terminalMonitorService.sendCallInfo(customMsg);
//                                processRealTimeVoice.sendCallInfo(customMsg);
                                log.info("收到下面对上面发起呼叫");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_PERSON_INFO_SEARCH://个人消息查询
                                terminalMonitorService.searchPersonInfoByTerminalId(customMsg);
                                log.info("个人信息查询");
                                break;
                            case   ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_client://终端呼叫服务端，服务端未响应，终端挂断
                                terminalMonitorService.terminalHangUp(customMsg);
                                log.info("终端呼叫服务端，服务端未响应，终端挂断");
                        }
                        break;
                    case ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE://应答
                        switch (ndName) {
                            case ConstantValue.MSG_BODY_NODE_NAME_CHECK_POWER:
                                battery.put(customMsg.getTerminalId(), (int) customMsg.getBody()[0]);
                                log.info("终端设备应答：查询设备端电量");
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_REAL_TIME_CALL:
                                log.info("终端设备应答：检查在线情况");
                                // 检查在线情况
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_CHECK_ONLINE:
                                //result  55表示成功， 66表示终端设备繁忙
//                                upLoadService.processResponse(customMsg);
                                terminalMonitorService.processResponse(customMsg);
                                log.info("终端设备应答：响应状态值处理已经接听");
                                // 终端响应状态值处理已经接听
                                break;
                            case ConstantValue.MSG_BODY_NODE_NAME_CONFIGURED:
                                terminalMonitorService.sendConfiguredResponse(customMsg);
                                log.info("终端设备应答：配置基站子网掩码和IP");
//                                upLoadService.sendConfiguredResponse(customMsg);
                                break;
                        }
                        break;
                    default:
                        log.error("未知命令");
                        break;
                }
            } else {
                //数据包头错误不接收
                customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_RESPONSE);
                customMsg.setLength(34);
                customMsg.setResult(ConstantValue.MSG_BODY_RESULT_ERROR);
                customMsg.setNodeCount((byte) 0x00);
                resp.setCustomMsg(customMsg);
                SingletonClient.getSingletonClient().sendCmd(resp);
            }
            log.info("终端的服务端接收来自终端[ " + ctx.channel().remoteAddress() + "] 的消息为 " + customMsg.toString());
        } else {
            log.error("异常数据包{}" + msg.toString());
        }
        ReferenceCountUtil.release(msg);
    }

    /**
     *
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        String str = clientIP + ":" +
                port;
        ChannelMap.addChannel(str, ctx.channel());

            /*为演示准备开始*/
        TimeStandardVO standard =  timeStandardMapper.selectTimeStandardInfoByStaffId(2);
        Integer timeStandardId = standard.getTimeStandardId();

        StaffAttendanceRealRule realRule =staffAttendanceRealRuleMapper.selectByPrimaryKey(2);
        //StaffAttendanceRealRule realRule = attendanceServiceClient.findStaffAttendanceRealRuleById(2);
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(GetUUID.getUuid());
        attendance.setStaffId(2);
        attendance.setRuleId(timeStandardId);
        attendance.setStartTime(new Date());
        attendance.setInOre(new Date());
        attendance.setBaseStationId(3);
        //员工上班
        realRule.setIsAttendance(1);
        realRule.setFinalTime(null);
        staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
        attendanceMapper.insertSelective(attendance);

        WebSocketData data = new WebSocketData();
//                List<HashMap<String, Object>> attendanceCount = staffAttendanceRealRuleMapper.getAttendanceStaff(null, null);
      //  staffAttendanceRealRuleMapper.getAttendanceStaffCount(deptIds,staffName);
        Long attendanceCount = staffAttendanceRealRuleMapper.getAttendanceStaffCount(null,null);

        if (null != attendanceCount) {
            data.setData(attendanceCount);
        } else {
            data.setData(0);
        }
        data.setType(3);
        System.out.println("推送井下总人数："+attendanceCount);


        /*为演示准备结束*/

        log.info("终端[" + str + "] 连接成功");
        log.info("终端[" + str + "] 加入session");
        log.info("当前连接终端数量" + ChannelMap.getChannelNum());
    }

    /**
     * @param
     * @return void
     * @description 执行断开连接时执行的函数
     * @date 9:50 2018/12/6
     * @auther lifeng
     **/
  //  private ExecutorService executorService = Executors.newFixedThreadPool(6);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    //    executorService.execute(() -> {
            InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String clientIP = inSocket.getAddress().getHostAddress();
            int port = inSocket.getPort();
            String str = clientIP + ":" + port;
            log.info("终端[" + str + "] 已断开连接");
            ChannelMap.removeChannelByName(str);
            log.info("终端[" + str + "] 被移出session");
            log.info("当前数量[" + ChannelMap.getChannelNum() + "] 个");


            /*为演示做准备开始*/
        StaffAttendanceRealRule realRule =staffAttendanceRealRuleMapper.selectByPrimaryKey(2);
        Attendance attendance = attendanceMapper.findAttendanceByStaffIdAndEndTimeIsNull(2);
        if (attendance != null) {
            attendance.setEndTime(new Date());
            attendance.setOutOre(new Date());
            attendanceMapper.updateByPrimaryKeySelective(attendance);
        }

        realRule.setIsAttendance(0);
        realRule.setFinalTime(null);
        staffAttendanceRealRuleMapper.updateByPrimaryKeySelective(realRule);
        WebSocketData data = new WebSocketData();
//                List<HashMap<String, Object>> attendanceCount = staffAttendanceRealRuleMapper.getAttendanceStaff(null, null);
        //  staffAttendanceRealRuleMapper.getAttendanceStaffCount(deptIds,staffName);
        Long attendanceCount = staffAttendanceRealRuleMapper.getAttendanceStaffCount(null,null);

        if (null != attendanceCount) {
            data.setData(attendanceCount);
        } else {
            data.setData(0);
        }
        data.setType(3);
        System.out.println("推送井下总人数："+attendanceCount);
        try {
//                    WSPersonNumberServer.sendInfo(JSON.toJSONString(data));
            wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*为演示做准备结束*/


            //实时查询：根据IP和端口到终端ip更新表查找,移除断开连接的人或车
            String[] split = clientIP.split("\\.");
            String terminalIp = split[2] + "." + split[3];
//            TerminalUpdateIp terminalUpdateIp = terminalUpdateIpMapper.findTerminalIdByIpAndPort(terminalIp, port);
            Integer terminalId = terminalIpPortMapper.findTerminalIdByIpPort(terminalIp, port);
//            TerminalUpdateIp terminalUpdateIp = terminalMonitorService.findTerminalIdByIpAndPort(terminalIp, port);
            if (null != terminalId) {
                //掉线终端
                processOfflineTerminalSendWs(terminalId);


                //处理终端数量发送
//                Integer terminalId = terminalUpdateIp.getTerminalNum();
                Map<String, Object> map = staffMapper.selectStaffInfoByTerminalId(terminalId);
//                Map<String, Object> map = terminalMonitorService.selectStaffInfoByTerminalId(terminalId);
                Integer isPerson = (Integer) map.get("is_person");
                Integer staffId = (Integer) map.get("staff_id");
                //实时推送通讯录此人在线
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("staffId",staffId);
                map1.put("code",0);
                wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(12, map1)));

                System.out.println("-------------------------------");
                System.out.println("推送终端不在线");
                System.out.println("-------------------------------");

                offNum++;
                log.info("终端断线数量："+offNum);


                if (null != staffId) {
                    switch (isPerson) {
                        case 0:
                            // TerminalInfoProcess.staffSet.remove(staffId);
                            terminalMonitorService.removeStaffSet(staffId);
                            break;
                        case 1:
                            // TerminalInfoProcess.leaderSet.remove(staffId);
                            terminalMonitorService.removeLeaderSet(staffId);
                            break;
                        case 2:
                            // TerminalInfoProcess.outPersonSet.remove(staffId);
                            terminalMonitorService.removeOutPersonSet(staffId);
                            break;
                        case 3:
                            //  TerminalInfoProcess.carSet.remove(staffId);
                            terminalMonitorService.removeCarSet(staffId);
                            break;
                    }
                }
                //实时查询：数据推送到前端页面
                //TerminalInfoProcess.pushRtPersonData();
                //terminalMonitorService.pushRtPersonData();
            }
        // });

    }

    private void processOfflineTerminalSendWs(Integer terminalId) {
        //Map<String, Object> map = staffMapper.selectStaffInfoByTerminalId(terminalId);
        Map<String, Object> map = terminalMonitorService.selectStaffInfoByTerminalId(terminalId);
        if (null == map) {
            throw new RuntimeServiceException(ErrorCode.TERMINAL_AND_STAFF_NOT_BINDING);
        }
//        Integer isPerson = (Integer) map.get("is_person");
        Integer staffId = (Integer) map.get("staff_id");
        mapOfWs.put("type", 2);
        mapOfWs.put("staffId", staffId);
//            WSSiteServer.sendInfo(JSON.toJSONString(mapOfWs));
        terminalMonitorService.sendInfoToWsServer(JSON.toJSONString(mapOfWs));
    }

    /**
     * @param
     * @return void
     * @description 读取数据完成执行的函数
     * @date 9:51 2018/12/6
     * @auther lifeng
     **/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        String str = clientIP + ":" + port;
        log.info("读取终端[" + str + "] 数据完成");
    }

    /**
     * @param
     * @return void
     * @description 心跳机制处理
     * @date 9:51 2018/12/6
     * @auther lifeng
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        log.info("已经很长时间没有收到消息了userEventTriggered");
        String str = clientIP + ":" +
                port;
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                //TODO 读超时
                log.info("已经很长时间没有收到消息了");
                removeNum++;
                ctx.channel().close();
                ChannelMap.removeChannelByName(str);
            }
//            if (event.state() == IdleState.WRITER_IDLE) {
//                //TODO 写超时
//                ctx.channel().close();
//                ChannelMap.removeChannelByName(str);
//            }
            if (event.state() == IdleState.ALL_IDLE) {
                //清除超时会话
                ChannelFuture writeAndFlush = ctx.writeAndFlush("client will be remove");
                writeAndFlush.addListener((ChannelFutureListener) future -> {
                    //TODO 通知web端显示并存储数据库
                    //TODO 终端掉线
                    ctx.channel().close();
                    ChannelMap.removeChannelByName(str);
                });
            }
        }
//        else {
//
//            ChannelMap.removeChannelByName(str);
//        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * @param
     * @return void
     * @description 异常处理
     * @date 9:51 2018/12/6
     * @auther lifeng
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inSocket.getAddress().getHostAddress();
        int port = inSocket.getPort();
        String str = clientIP + ":" + port;
        exceptNum++;
        log.error("终端[" + str + "] 出现异常" + cause.getLocalizedMessage());
        //ChannelMap.removeChannelByName(str);
        cause.printStackTrace();
    }
}
