package com.cst.xinhe.terminal.monitor.server.handle;


import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.netty.utils.NettyDataUtils;
import com.cst.xinhe.persistence.dto.warn_level_setting.GasWarnSettingDto;
import com.cst.xinhe.persistence.model.warn_level.GasStandard;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.terminal.monitor.server.context.SpringContextUtil;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import com.cst.xinhe.terminal.monitor.server.service.impl.TerminalMonitorServiceImpl;
import com.cst.xinhe.terminal.monitor.server.utils.SequenceIdGenerate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 处理终端连接基站后的气体等级的设置
 * @author: lifeng
 * @create: 2019-01-21 16:46
 **/
public class ProcessSettingGasLevel {

    private volatile static ProcessSettingGasLevel processSettingGasLevel;

//    private BaseStationService baseStationService;
//
//    private LevelSettingService levelSettingService;

    TerminalMonitorService terminalMonitorService;

    private ProcessSettingGasLevel() {
//        this.baseStationService = SpringContextUtil.getBean(BaseStationServiceImpl.class);
//        this.levelSettingService = SpringContextUtil.getBean(LevelSettingServiceImpl.class);
        this.terminalMonitorService = SpringContextUtil.getBean(TerminalMonitorServiceImpl.class);
    }

    public static ProcessSettingGasLevel getSingletonClient() {
        if (processSettingGasLevel == null) {
            synchronized (SingletonClient.class) {
                if (processSettingGasLevel == null) {
                    processSettingGasLevel = new ProcessSettingGasLevel();
                }
            }
        }
        return processSettingGasLevel;
    }

    public void sendMsg(RequestData requestData) {
        Integer stationNum = requestData.getStationId();
       // Map<String, Object> map = baseStationService.findStandardIdByStationNum(stationNum);
        Map<String, Object> map = terminalMonitorService.findStandardIdByStationNum(stationNum);
        if (map != null && !map.isEmpty()) {
            if (map.containsKey("standardId")){
                Integer standardId = (Integer) map.get("standardId");
                if (standardId != null) {
                    // 根据标准等级获取所有的警报等级具体信息
                    //GasLevelVO gasLevelVO = levelSettingService.getWarnLevelSettingByGasLevelId(standardId);
                    GasLevelVO gasLevelVO = terminalMonitorService.getWarnLevelSettingByGasLevelId(standardId);
                    // 获取具体信息，并封装 RequestData
                    ResponseData responseData = new ResponseData();
                    RequestData customMsg = new RequestData();

                    customMsg.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
                    customMsg.setTerminalId(requestData.getTerminalId());
                    customMsg.setTerminalIp1(requestData.getTerminalIp1());
                    customMsg.setTerminalIp2(requestData.getTerminalIp2());
                    customMsg.setTerminalPort(requestData.getTerminalPort());
                    customMsg.setSequenceId(SequenceIdGenerate.getSequenceId());

                    customMsg.setTime(new Date());

                    customMsg.setStationId(requestData.getStationId());
                    customMsg.setStationIp1(requestData.getStationIp1());
                    customMsg.setStationIp2(requestData.getStationIp2());
                    customMsg.setStationIp(requestData.getStationIp());
                    customMsg.setStationPort(requestData.getStationPort());
                    customMsg.setTerminalIp(requestData.getTerminalIp());
                    customMsg.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_CONTROL);
                    customMsg.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
                    customMsg.setNodeCount((byte) 0);
                    customMsg.setNdName(ConstantValue.MSG_BODY_NODE_NAME_GAS_LEVEL_STANDARD);
                    //TODO setBody

                    List<GasWarnSettingDto> ch4WarnSettingDto = gasLevelVO.getCh4WarnSettingDto();
                    List<GasWarnSettingDto> coWarnSettingDto = gasLevelVO.getCoWarnSettingDto();
                    List<GasWarnSettingDto> hWarnSettingDto = gasLevelVO.gethWarnSettingDto();
                    List<GasWarnSettingDto> tWarnSettingDto = gasLevelVO.gettWarnSettingDto();
                    List<GasWarnSettingDto> o2WarnSettingDto = gasLevelVO.getO2WarnSettingDto();
                    GasStandard gasStandard = gasLevelVO.getGasStandard();

                    double ch4Standard = gasStandard.getCh4Standard();
                    double coStandard = gasStandard.getCoStandard();
                    double o2Standard = gasStandard.getO2Standard();
                    double tStandard = gasStandard.gettStandard();
                    double hStandard = gasStandard.gethStandard();

                    int ch4Len = ch4WarnSettingDto.size();
                    int coLen = coWarnSettingDto.size();
                    int o2Len = o2WarnSettingDto.size();
                    int tLen = tWarnSettingDto.size();
                    int hLen = hWarnSettingDto.size();
                    int bodyLength = ch4Len * 2
                            + coLen * 2
                            + o2Len * 2
                            + tLen * 2
                            + hLen * 2
                            + 5; // 数字5 为5种气体的等级数量长度，每个长度占2字节

                    byte[] body = new byte[bodyLength];

                    byte[] bodyOfCh4Len = new byte[ch4Len * 2];
                    byte[] bodyOfCoLen = new byte[coLen * 2];
                    byte[] bodyOfO2Len = new byte[o2Len * 2];
                    byte[] bodyOfTLen = new byte[tLen * 2];
                    byte[] bodyOfHLen = new byte[hLen * 2];

//            bodyOfCh4Len[0] = (byte)ch4Len;

                    int index = 0;
                    body[index] = (byte) (ch4Len & 0xff);
                    index = index + 1;
//            List<Integer> list = new ArrayList<>();
//            list.add(ch4Len);
                    for (GasWarnSettingDto gasWarnSetting : ch4WarnSettingDto) {
                        double val = ch4Standard * gasWarnSetting.getMultiple();
                        Integer t = (int) changeValToDouble2(val) * 10;
                        byte[] tt = NettyDataUtils.intToByteArray(t);
                        body[index] = tt[2];
                        index = index + 1;
                        body[index] = tt[3];
                        index = index + 1;

                    }
//            list.add(coLen);
                    body[index] = (byte) (coLen & 0xff);
                    index = index + 1;
                    for (GasWarnSettingDto gasWarnSetting : coWarnSettingDto) {
                        double val = coStandard * gasWarnSetting.getMultiple();
                        Integer t = (int) changeValToDouble2(val) * 10;
                        byte[] tt = NettyDataUtils.intToByteArray(t);
                        body[index] = tt[2];
                        index = index + 1;
                        body[index] = tt[3];
                        index = index + 1;
                    }
                    body[index] = (byte) (o2Len & 0xff);
                    index = index + 1;
                    for (GasWarnSettingDto gasWarnSetting : o2WarnSettingDto) {
                        double val = o2Standard * gasWarnSetting.getMultiple();
                        Integer t = (int) changeValToDouble2(val) * 10;
                        byte[] tt = NettyDataUtils.intToByteArray(t);
                        body[index] = tt[2];
                        index = index + 1;
                        body[index] = tt[3];
                        index = index + 1;
                    }
                    body[index] = (byte) (tLen & 0xff);
                    index = index + 1;
                    for (GasWarnSettingDto gasWarnSetting : tWarnSettingDto) {
                        double val = tStandard * gasWarnSetting.getMultiple();
                        Integer t = (int) changeValToDouble2(val) * 10;
                        byte[] tt = NettyDataUtils.intToByteArray(t);
                        body[index] = tt[2];
                        index = index + 1;
                        body[index] = tt[3];
                        index = index + 1;
                    }
                    body[index] = (byte) (hLen & 0xff);
                    index = index + 1;
                    for (GasWarnSettingDto gasWarnSetting : hWarnSettingDto) {
                        double val = hStandard * gasWarnSetting.getMultiple();
                        Integer t = (int) changeValToDouble2(val) * 10;
                        byte[] tt = NettyDataUtils.intToByteArray(t);
                        body[index] = tt[2];
                        index = index + 1;
                        body[index] = tt[3];
                        index = index + 1;
                    }


//            for (int i = 0; i < list.size(); i++) {
//                body[i] = (byte) ((list.get(i)) & 0xff);
//            }

                    customMsg.setLength(bodyLength + 34);//TODO 另算
                    customMsg.setBody(body);
                    responseData.setCustomMsg(customMsg);
                    SingletonClient.getSingletonClient().sendCmd(responseData);
            }

            }
        }
    }

    private double changeValToDouble2(double val) {
        NumberFormat nf = new DecimalFormat("0.0 ");
        return Double.parseDouble(nf.format(val));
    }
}
