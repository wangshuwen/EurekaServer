package com.cst.xinhe.persistence.dao.terminal;

import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TerminalUpdateIpMapper {

    void updateIpInfoByTerminalId(TerminalUpdateIp terminalUpdateIp);

    boolean checkTerminalIdIsNotExist(Integer terminalId);


    Map<String, Object> selectTerminalIpInfoByTerminalId(Integer terminalId);

    boolean checkStationIdIsNotExist(Integer stationId);

    TerminalUpdateIp findTerminalIdByIpAndPort(@Param("terminalIp") String terminalIp, @Param("terminalPort") int terminalPort);

    void updateIpInfoByStationId(TerminalUpdateIp terminalUpdateIp);

    Map<String, Object> selectStationIpByStationId(Integer stationId);

    //批量插入更新IP
    int insertUpdateIpTerminals(@Param("list") List<StaffTerminal> staffTerminals);

    Integer findStationIdByIpAndPort(@Param("stationIp") String stationIp,@Param("stationPort") Integer stationPort);
}
