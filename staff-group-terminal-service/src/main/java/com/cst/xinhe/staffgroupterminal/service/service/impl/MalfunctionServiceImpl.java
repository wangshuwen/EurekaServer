package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.persistence.dao.malfunction.MalfunctionMapper;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.staffgroupterminal.service.service.MalfunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/11/30/9:30
 */
@Service
public class MalfunctionServiceImpl implements MalfunctionService {
    @Resource
    private MalfunctionMapper malfunctionMapper;

    @Override
    public List<Malfunction> findMalfunctionInfo(Integer status, Integer terminal) {
        return malfunctionMapper.selectMalfunction(status,terminal);
    }

    @Override
    public int updateStatusById(Integer selfCheckId) {
        Malfunction malfunction = new Malfunction();
        malfunction.setSelfCheckId(selfCheckId);
        malfunction.setCh4Error(0);
        malfunction.setWifiError(0);
        malfunction.setVoiceError(0);
        malfunction.setCoError(0);
        malfunction.setCo2Error(0);
        malfunction.setCh4Error(0);
        malfunction.settError(0);
        malfunction.sethError(0);
        malfunction.setO2Error(0);
        malfunction.setStatus(1);
        return malfunctionMapper.updateByPrimaryKeySelective(malfunction);
    }

    @Override
    public Integer deleteMalfunctionByIds(Integer[] ids) {

        int len = 0;

        for (Integer id : ids) {
            int i = malfunctionMapper.deleteByPrimaryKey(id);
            len+=i;
        }
        return len;
    }

    @Override
    public Integer getCount() {
        Map<String, Object> map = malfunctionMapper.selectCountMalfunction();
        Long t_count = (Long) map.get("malfunctionCount");
        Integer count = t_count.intValue();
        return  count;
    }

    @Override
    public void addMalfunction(Malfunction malfunction) {
        malfunctionMapper.insertSelective(malfunction);
    }

    @Override
    public Map<String, Object> getCountMalfunction() {
        return malfunctionMapper.selectCountMalfunction();
    }

    @Override
    public List<Malfunction> findMalfunctionInfoByStatus1() {
        return malfunctionMapper.findMalfunctionInfoByStatus1();
    }
}
