package com.cst.xinhe.staffgroupterminal.service.service.impl;

import com.cst.xinhe.persistence.dao.lack_electric.LackElectricMapper;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.lack_electric.LackElectricExample;
import com.cst.xinhe.staffgroupterminal.service.service.LackElectricService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/2/13/17:36
 */
@Service
public class LackElectricServiceImpl implements LackElectricService {
    @Resource
    private LackElectricMapper lackElectricMapper;


    @Override
    public Page findLackElectric(Integer pageSize, Integer startPage, Integer terminalId, String staffName) {
        Page page = PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> maps = lackElectricMapper.findLackElectric(terminalId,staffName);
        return page;
    }

    @Override
    public long findIsReadCount() {
        LackElectricExample example = new LackElectricExample();
        example.createCriteria().andIsReadEqualTo(0);
        long count = lackElectricMapper.countByExample(example);
        return count;
    }

    @Override
    public Integer updateIsRead() {
        int result=0;
        LackElectricExample example = new LackElectricExample();
        List<LackElectric> lackElectrics = lackElectricMapper.selectByExample(example);
        for (LackElectric lackElectric : lackElectrics) {
            lackElectric.setIsRead(1);
            result+=lackElectricMapper.updateByPrimaryKeySelective(lackElectric);
        }
        return result;
    }

    @Override
    public Integer findLackElectricCount() {
//        LackElectricExample example = new LackElectricExample();
//        List<LackElectric> lackElectrics = lackElectricMapper.selectByExample(example);
        Integer count = lackElectricMapper.selectIsReadCount();
        return count;
    }

    @Override
    public void deleteLeLackElectricByLackElectric(LackElectric lackElectric) {
        LackElectricExample example = new LackElectricExample();
        example.createCriteria().andUploadIdEqualTo(lackElectric.getUploadId());
        example.createCriteria().andLackTypeEqualTo(lackElectric.getLackType());
        lackElectricMapper.deleteByExample(example);
    }

    @Override
    public void updateLackElectric(LackElectric lackElectric) {
        LackElectricExample example = new LackElectricExample();
        example.createCriteria().andUploadIdEqualTo(lackElectric.getUploadId());
        example.createCriteria().andLackTypeEqualTo(lackElectric.getLackType());
        LackElectric electric = new LackElectric();
        electric.setElectricValue(lackElectric.getElectricValue());
        electric.setUploadId(lackElectric.getUploadId());
        electric.setLackType(lackElectric.getLackType());
        lackElectricMapper.updateByExample(electric,example);
    }

    @Override
    public List<LackElectric> getLackElectricList() {
        LackElectricExample lackElectricExample = new LackElectricExample();
        return lackElectricMapper.selectByExample(lackElectricExample);
    }
}
