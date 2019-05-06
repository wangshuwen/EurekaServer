package com.cst.xinhe.system.service.service;

import com.cst.xinhe.persistence.model.warn_level.GasStandard;
import com.cst.xinhe.persistence.model.warn_level.GasWarnSetting;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;

import java.util.List;
import java.util.Map;

public interface LevelSettingService {
    List<GasStandard> getStandardInfo();

    int removeStandard(Integer standardId);

    int modifyStandard(GasStandard gasStandard);

    int addStandard(GasStandard standard);

    int addWarnLevelSetting(GasWarnSetting gasWarnSetting);

    int modifyWarnLevelSetting(GasWarnSetting gasWarnSetting);

    int deleteWarnLevelSettingByGasLevelId(Integer gasLevelId);

    GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId);

    Map<String, Object> getStandardNameByStandardId(Integer standardId);
}
