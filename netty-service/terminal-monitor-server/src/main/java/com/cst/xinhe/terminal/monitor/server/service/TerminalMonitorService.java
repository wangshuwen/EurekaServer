package com.cst.xinhe.terminal.monitor.server.service;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;

import java.util.Map;

public interface TerminalMonitorService {
    boolean getChannelByIpPort(String ipPort);

    void sendResponseData(ResponseData responseData);

    void sendInfoToKafka(String s, RequestData customMsg);

    void checkSendCheckOnline(RequestData customMsg);

    void sendCallInfo(RequestData customMsg);

    void sendGasInfoToQueue(RequestData customMsg);

    void sendSelfCheckResult(RequestData customMsg);

    void sendSoftWareVersion(RequestData customMsg);

    void sendHandWareVersion(RequestData customMsg);

    void sendUpLoadIp(RequestData customMsg);

    void sendTerminalInfoToQueue(RequestData customMsg);

    void sendUpdateVoiceStatus(RequestData customMsg);

    void sendPowerStatus(RequestData customMsg);

    void processResponse(RequestData customMsg);

    void sendConfiguredResponse(RequestData customMsg);

    Map<String, Object> findStandardIdByStationNum(Integer stationNum);

    GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId);

    void removeStaffSet(Integer staffId);

    void removeLeaderSet(Integer staffId);

    void removeOutPersonSet(Integer staffId);

    void removeCarSet(Integer staffId);

    void sendInfoToWsServer(String toJSONString);

    void pushRtPersonData();

    Integer findTerminalIdByIpAndPort(String terminalIp, int port);

    Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId);

    Integer getBatteryNumByTerminalNum(Integer terminalNum);

    void checkTempSendListAndToSend(RequestData customMsg);

    void searchPersonInfoByTerminalId(RequestData customMsg);
}
