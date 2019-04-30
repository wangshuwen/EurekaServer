package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
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

    @Override
    public StaffTerminal getTeminalByNum(Integer terminalId) {

        return staffTerminalMapper.getTeminalByNum(terminalId);
    }

    @Override
    public List<StaffTerminal> getAllTerminal() {
        return staffTerminalMapper.selectAllTerminals();
    }

    @Override
    public List<StaffTerminal> getNotBinDingTerminals() {
        return staffTerminalMapper.getNotBinDingTerminals();
    }

    @Override
    public boolean unBind(Integer staffTerminalId) {
//        StaffTerminal staffTerminal = new StaffTerminal();
//        staffTerminal.setTerminalId(staffTerminalId);
//        staffTerminal.setStaffId(null);
//        int i = staffTerminalMapper.updateByPrimaryKey(staffTerminal);
        int i = staffTerminalMapper.updateTerminalUnBinding(staffTerminalId);
        return i == 1 ? true : false;
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
        TerminalUpdateIp terminalUpdateIp = new TerminalUpdateIp();
        terminalUpdateIp.setTerminalNum(terminalId);
        return terminalUpdateIpMapper.insert(terminalUpdateIp);
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
}
