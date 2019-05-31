package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dao.updateIp.TerminalIpPortMapper;
import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.model.updateIp.TerminalIpPort;
import com.cst.xinhe.staffgroupterminal.service.client.TerminalMonitorClient;
import com.cst.xinhe.staffgroupterminal.service.service.TerminalService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TerminalServiceImpl
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/12 9:29
 * @Vserion v0.0.1
 */
@Service
public class TerminalServiceImpl implements TerminalService {
    //extends BaseService<StaffTerminalMapper, StaffTerminal, StaffTerminalExample>
    @Resource
    private StaffTerminalMapper staffTerminalMapper;

    @Resource
    private TerminalUpdateIpMapper terminalUpdateIpMapper;

    @Resource
    private TerminalMonitorClient terminalMonitorClient;

    @Resource
    private TerminalIpPortMapper terminalIpPortMapper;

    @Override
    public StaffTerminal getTeminalByNum(Integer terminalId) {

        return staffTerminalMapper.getTeminalByNum(terminalId);
    }

    @Override
    public List<StaffTerminal> getAllTerminal() {
        return staffTerminalMapper.selectAllTerminals();
    }

    @Override
    public Page<StaffTerminal> getNotBinDingTerminals(Integer pageNum, Integer pageSize, Integer terminalId) {
        Page<StaffTerminal> page = PageHelper.startPage(pageNum,pageSize);
        List<StaffTerminal> list = staffTerminalMapper.getNotBinDingTerminals(terminalId);
        return page;
    }

    @Override
    public boolean unBind(Integer staffTerminalId) {
//        StaffTerminal staffTerminal = new StaffTerminal();
//        staffTerminal.setTerminalId(staffTerminalId);
//        staffTerminal.setStaffId(null);
//        int i = staffTerminalMapper.updateByPrimaryKey(staffTerminal);
        int i = staffTerminalMapper.updateTerminalUnBinding(staffTerminalId);
        return i == 1;
    }

    @Override
    public boolean binding(Integer staffId, Integer staffTerminalId) {
        StaffTerminal staffTerminal = new StaffTerminal();
        staffTerminal.setTerminalId(staffTerminalId);
        staffTerminal.setStaffId(staffId);
//        int i = staffTerminalMapper.updateByPrimaryKeySelective(staffTerminal);
        int i = staffTerminalMapper.updateTerminalBinding(staffId, staffTerminalId);
        return i == 1 ? true : false;
    }

    @Override
    public int addTerminal(StaffTerminal staffTerminal) {
        return staffTerminalMapper.insertSelective(staffTerminal);
    }

    @Override
    public Integer findTerminalInfoByStaffId(Integer staffId) {

            return staffTerminalMapper.selectTerminalIdByStaffId(staffId);
    }

    @Override
    public Page findTerminalInfoByParams(Integer startPage, Integer pageSize, Integer terminalId) {
        StaffTerminal staffTerminal = new StaffTerminal();
        staffTerminal.setTerminalId(terminalId);
        Page page = PageHelper.startPage(startPage, pageSize);
        List<StaffTerminal> list = staffTerminalMapper.selectTerminalsByParams(staffTerminal);
        return page;
    }

    @Override
    public int deleteTerminalByTerminalId(Integer[] ids) {
        int i = 0;
        for (Integer item : ids) {
            if (staffTerminalMapper.deleteByTerminalId(item) == 1)
                i++;
        }
        return i;
    }

    @Override
    public int updateTerminalByTerminalId(StaffTerminal staffTerminal) {
        return staffTerminalMapper.updateByTerminalId(staffTerminal);
    }

    @Override
    public boolean checkTerminalExist(Integer terminalId) {
        return staffTerminalMapper.countTerminalNumByTerminalId(terminalId);
    }

    @Override
    public int insertToUpdataIpByTerminal(Integer terminalId) {
        TerminalIpPort terminalIpPort = new TerminalIpPort();
        terminalIpPort.setTerminalId(terminalId);
        return terminalIpPortMapper.insert(terminalIpPort);
    }

    @Override
    public Map<String, Object> selectStationIpByStationId(Integer stationId) {
        return terminalUpdateIpMapper.selectStationIpByStationId(stationId);
    }

    /**
     * @description: 批量插入终端信息
     * @param: [staffTerminals]
     * @return: int
     * @author: lifeng
     * @date: 2019-04-02
     */
    @Transactional
    @Override
    public int addTerminals(List<StaffTerminal> staffTerminals) {

        int count = staffTerminalMapper.insertStaffTerminals(staffTerminals);
        int count1 = terminalUpdateIpMapper.insertUpdateIpTerminals(staffTerminals);
        return count == count1 ? count : 0;
    }

    @Override
    public Map<String, Object> findTerminalAndPersonInfoByMac(String mac) {
        Map<String, Object> map = staffTerminalMapper.selectTerminalAndPersonInfoByMac(mac);
        return map;
    }

    @Override
    public TerminalUpdateIp findTerminalIdByIpAndPort(String terminalIp, Integer port) {
        return terminalUpdateIpMapper.findTerminalIdByIpAndPort(terminalIp, port);
    }

    @Override
    public Map<String, Object> selectTerminalIpInfoByTerminalId(Integer terminalId) {
        return terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
    }

    @Override
    public void updateIpInfoByTerminalId(TerminalUpdateIp terminalUpdateIp) {
        terminalUpdateIpMapper.updateIpInfoByTerminalId(terminalUpdateIp);
    }

    @Override
    public void updateIpInfoByStationId(TerminalUpdateIp terminalUpdateIp) {
        terminalUpdateIpMapper.updateIpInfoByStationId(terminalUpdateIp);
    }

    @Override
    public Integer getRtBattery(Integer terminalNum) {
        return terminalMonitorClient.getBatteryByTerminalNum(terminalNum);
    }

    @Override
    public void sendGetRtBatteryCmd(Integer terminalNum) {
        RequestData requestData = new RequestData();
        requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
        requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_SEARCH);
        requestData.setLength(34);
        requestData.setNdName(ConstantValue.MSG_BODY_NODE_NAME_CHECK_POWER);
        requestData.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
        requestData.setTerminalId(terminalNum);
        requestData.setStationId(0);
        requestData.setStationIp("0.0");
        requestData.setStationIp1(0);
        requestData.setStationIp2(0);
        requestData.setStationPort(0);
        Map<String, Object> result = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalNum);
        if (null != result){
            String ip = (String)result.get("terminal_ip");
            int ip1 = Integer.parseInt(ip.split("\\.")[0]);
            int ip2 = Integer.parseInt(ip.split("\\.")[1]);
            requestData.setTerminalIp(ip);
            requestData.setTerminalPort((Integer) result.get("terminal_port"));
            requestData.setTerminalIp1(ip1);
            requestData.setTerminalIp2(ip2);
        }
        ResponseData responseData = new ResponseData();
        responseData.setCustomMsg(requestData);
        terminalMonitorClient.sendResponseData(responseData);
    }
}
