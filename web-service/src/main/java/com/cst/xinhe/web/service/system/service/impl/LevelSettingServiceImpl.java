package com.cst.xinhe.web.service.system.service.impl;

import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.persistence.dao.warn_level.GasStandardMapper;
import com.cst.xinhe.persistence.dao.warn_level.GasWarnSettingMapper;
import com.cst.xinhe.persistence.dto.warn_level_setting.GasWarnSettingDto;
import com.cst.xinhe.persistence.model.warn_level.GasStandard;
import com.cst.xinhe.persistence.model.warn_level.GasStandardExample;
import com.cst.xinhe.persistence.model.warn_level.GasWarnSetting;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.web.service.system.service.LevelSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 气体设置实现类
 * @author: lifeng
 * @create: 2019-01-19 09:13
 **/
@Service
public class LevelSettingServiceImpl implements LevelSettingService {

    @Resource
    private GasStandardMapper gasStandardMapper;

    @Resource
    private GasWarnSettingMapper gasWarnSettingMapper;

    @Override
    public List<GasStandard> getStandardInfo() {
        GasStandardExample gasStandardExample = new GasStandardExample();
        gasStandardExample.createCriteria();
        List<GasStandard> standardList = new ArrayList<>();
        try {
             standardList = gasStandardMapper.selectByExample(gasStandardExample);
        } catch (Exception e){
            throw new RuntimeServiceException(ErrorCode.NO_DATA);
        }
        return standardList;
    }

    @Override
    public int removeStandard(Integer standardId) {
        return gasStandardMapper.deleteByPrimaryKey(standardId);
    }

    @Override
    public int addStandard(GasStandard standard) {
        return gasStandardMapper.insertSelective(standard);
    }

    @Override
    public int modifyStandard(GasStandard gasStandard) {
        return gasStandardMapper.updateByPrimaryKeySelective(gasStandard);
    }


    @Override
    public int addWarnLevelSetting(GasWarnSetting gasWarnSetting) {
        return gasWarnSettingMapper.insert(gasWarnSetting);
    }

    @Override
    public int modifyWarnLevelSetting(GasWarnSetting gasWarnSetting) {
        return gasWarnSettingMapper.updateByPrimaryKeySelective(gasWarnSetting);
    }

    @Override
    public int deleteWarnLevelSettingByGasLevelId(Integer gasLevelId) {
        return gasWarnSettingMapper.deleteByPrimaryKey(gasLevelId);
    }

    @Override
    public GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId) {
        Map<String ,Object> params = new HashMap<>();
        params.put("standardId", standardId);
        params.put("gasType", 5);
        List<GasWarnSettingDto> o2GasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 1);
        List<GasWarnSettingDto> coGasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 2);
        List<GasWarnSettingDto> ch4GasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 3);
        List<GasWarnSettingDto> tGasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);
        params.put("gasType", 4);
        List<GasWarnSettingDto> hGasWarnSettingList = gasWarnSettingMapper.selectGasWarnSettingByParams(params);

        GasStandard standard = gasStandardMapper.selectByPrimaryKey(standardId);

        GasLevelVO gasLevelVO = new GasLevelVO();

        gasLevelVO.setGasStandard(standard);
        gasLevelVO.setCh4WarnSettingDto(ch4GasWarnSettingList);
        gasLevelVO.setCoWarnSettingDto(coGasWarnSettingList);
        gasLevelVO.setO2WarnSettingDto(o2GasWarnSettingList);
        gasLevelVO.sethWarnSettingDto(hGasWarnSettingList);
        gasLevelVO.settWarnSettingDto(tGasWarnSettingList);

        return gasLevelVO;
    }

    @Override
    public Map<String, Object> getStandardNameByStandardId(Integer standardId) {
        GasStandardExample gasStandardExample = new GasStandardExample();
        gasStandardExample.createCriteria().andStandardIdEqualTo(standardId);
        List<GasStandard> gasStandards = gasStandardMapper.selectByExample(gasStandardExample);
        Map<String, Object> result = null;
        if (gasStandards.size() > 0){
            result = new HashMap<>();
            String standardName = gasStandards.get(0).getStandardName();
            result.put("standardName", standardName);
        }
        return result;
    }

    @Override
    public Map<Integer, String> getStandardNameByStandardIds(Map<Integer, Integer> standardIds) {
        Map<Integer, String > map = new HashMap<>();
        for (Integer item : standardIds.keySet()){
            GasStandard gasStandard = gasStandardMapper.selectByPrimaryKey(standardIds.get(item));
            if (null != gasStandard){
                map.put(item,gasStandard.getStandardName());
            }
        }
        return map;
    }
}
