package com.cst.xinhe.web.service.system.service;


import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.warn_level.LevelData;

import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/17/10:10
 */
public interface RangSettingService {
    List<RangSetting> findRangByType(Integer type);

    Integer add(RangSetting rangSetting);

    Integer delete(Integer id);

    Integer update(RangSetting rangSetting);

    Integer updateStatusById(Integer[] id);

    List<LevelData> findRangByLevelDataId(Integer levelId);

    Integer updateRangIdByLevelId(String jsonStr);

    Integer restoreDefaultRang();
}
