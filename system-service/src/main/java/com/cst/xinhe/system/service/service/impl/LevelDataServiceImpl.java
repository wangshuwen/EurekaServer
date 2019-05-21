package com.cst.xinhe.system.service.service.impl;

import com.cst.xinhe.persistence.dao.warn_level.LevelDataMapper;
import com.cst.xinhe.persistence.model.warn_level.LevelData;
import com.cst.xinhe.persistence.model.warn_level.LevelDataExample;
import com.cst.xinhe.system.service.service.LevelDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 等级设置实现类
 * @author: lifeng
 * @create: 2019-01-17 09:52
 **/
@Service
public class LevelDataServiceImpl implements LevelDataService {

    @Resource
    private LevelDataMapper levelDataMapper;

    @Override
    public int deleteLevelDataById(Integer id) {
        return levelDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<LevelData> findLevelDataByParams(Map<String, Object> params) {
        LevelDataExample example = new LevelDataExample();
        Integer id = (Integer) params.get("id");
        String levelName = (String) params.get("levelName");
        if (null != id){
            example.createCriteria().andIdEqualTo(id);
        }
        if (null != levelName && !"".equals(levelName)){
            example.createCriteria().andLevelNameLessThanOrEqualTo(levelName);
        }

        return levelDataMapper.selectByExample(example);
    }

    @Override
    public int modifyLevelData(LevelData levelData) {
        return levelDataMapper.updateByPrimaryKey(levelData);
    }

    @Override
    public int insertLevelData(LevelData levelData) {
        return levelDataMapper.insert(levelData);
    }

    public String findRangUrlByLevelDataId(Integer levelDataId){
        Map<String ,Object> map = levelDataMapper.selectRangUrlByLevelDataId(levelDataId);
        String url = (String )map.get("url");
        return url;
    }
}
