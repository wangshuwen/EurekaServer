package com.cst.xinhe.web.service.system.service;

import com.cst.xinhe.persistence.model.warn_level.LevelData;

import java.util.List;
import java.util.Map;

public interface LevelDataService {

    int deleteLevelDataById(Integer id);

    int modifyLevelData(LevelData levelData);

    int insertLevelData(LevelData levelData);

    List<LevelData> findLevelDataByParams(Map<String, Object> params);

    String findRangUrlByLevelDataId(Integer levelDataId);
}
