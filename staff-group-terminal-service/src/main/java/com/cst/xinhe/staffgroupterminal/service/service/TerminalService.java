package com.cst.xinhe.staffgroupterminal.service.service;


import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.github.pagehelper.Page;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TerminalService
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/12 9:29
 * @Vserion v0.0.1
 */

public interface TerminalService {

    StaffTerminal getTeminalByNum(Integer TeminalId);

    List<StaffTerminal> getAllTerminal();

    Page<StaffTerminal> getNotBinDingTerminals(Integer pageNum, Integer pageSize, Integer terminalId);

    boolean unBind(Integer staffTerminalId);

    boolean binding(Integer staffId, Integer staffTerminalId);

    int addTerminal(StaffTerminal staffTerminal);

    Integer findTerminalInfoByStaffId(Integer staffId);

    Page findTerminalInfoByParams(Integer startPage, Integer pageSize, Integer terminalId);

    int deleteTerminalByTerminalId(Integer[] ids);

    int updateTerminalByTerminalId(StaffTerminal staffTerminal);

    boolean checkTerminalExist(Integer terminalId);

    int insertToUpdataIpByTerminal(Integer terminalId);

    Map<String, Object> selectStationIpByStationId(Integer stationId);

    int addTerminals(List<StaffTerminal> staffTerminals);

    Map<String, Object> findTerminalAndPersonInfoByMac(String mac);

    TerminalUpdateIp findTerminalIdByIpAndPort(String terminalIp, Integer port);

    Map<String, Object> selectTerminalIpInfoByTerminalId(Integer terminalId);

    void updateIpInfoByTerminalId(TerminalUpdateIp terminalUpdateIp);

    void updateIpInfoByStationId(TerminalUpdateIp terminalUpdateIp);

    Integer getRtBattery(Integer terminalNum);

    void sendGetRtBatteryCmd(Integer terminalNum);
}
