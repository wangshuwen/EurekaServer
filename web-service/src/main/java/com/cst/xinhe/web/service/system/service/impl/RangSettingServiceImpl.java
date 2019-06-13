package com.cst.xinhe.web.service.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.persistence.dao.rang_setting.RangSettingMapper;
import com.cst.xinhe.persistence.dao.warn_level.LevelDataMapper;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.rang_setting.RangSettingExample;
import com.cst.xinhe.persistence.model.warn_level.LevelData;
import com.cst.xinhe.persistence.model.warn_level.LevelDataExample;
import com.cst.xinhe.web.service.system.service.RangSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/17/10:09
 */
@Service
public class RangSettingServiceImpl implements RangSettingService {
    @Resource
    private RangSettingMapper rangSettingMapper;

    @Resource
    private LevelDataMapper levelDataMapper;

    @Override
    public List<RangSetting> findRangByType(Integer type) {
        RangSettingExample example = new RangSettingExample();
        if(type!=null){
            example.createCriteria().andTypeEqualTo(type);
        }
        return rangSettingMapper.selectByExample(example);
    }

    @Override
    public Integer add(RangSetting rangSetting) {
        return rangSettingMapper.insertSelective(rangSetting);
    }

    @Override
    public Integer delete(Integer id) {
        return rangSettingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer update(RangSetting rangSetting) {
        return rangSettingMapper.updateByPrimaryKeySelective(rangSetting);
    }

    @Override
    public Integer updateStatusById(Integer[] ids) {

        int result=0;
        //把选中的铃声id状态设置为1，其余设置为0
        RangSettingExample example = new RangSettingExample();
        List<RangSetting> list = rangSettingMapper.selectByExample(example);
        for (RangSetting setting : list) {
            setting.setStatus(0);
           for(int i=0;i<ids.length;i++){
               if(setting.getId()==ids[i]){
                   setting.setStatus(1);
               }
           }
            result+=rangSettingMapper.updateByPrimaryKeySelective(setting);
        }

        return  result;
    }

    @Override
    public List<LevelData> findRangByLevelDataId(Integer levelId) {

        LevelDataExample example = new LevelDataExample();
        if(null != levelId){
            example.createCriteria().andIdEqualTo(levelId);
        }
        List<LevelData> list = levelDataMapper.selectByExample(example);

        return  list;
    }

    @Override
    public Integer updateRangIdByLevelId(String jsonStr) {
        Integer result = 0;
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        for(int i=0;i<jsonArray.size();i++){
            Integer levelId = jsonArray.getJSONObject(i).getInteger("levelId");
            Integer setId = jsonArray.getJSONObject(i).getInteger("setId");
            LevelData levelData = levelDataMapper.selectByPrimaryKey(levelId);
            levelData.setRangSettingId(setId);
            result+=levelDataMapper.updateByPrimaryKeySelective(levelData);
        }
        return result;
    }

    @Override
    public Integer restoreDefaultRang() {
        Integer result=0;
        //设置普通铃声，恢复默认选择第一个
        RangSettingExample example = new RangSettingExample();
        List<RangSetting> settings = rangSettingMapper.selectByExample(example);
        for (RangSetting setting : settings) {
            Integer id = setting.getId();
            if(id==1||id==5||id==9||id==13){
                setting.setStatus(1);
            }else{
                setting.setStatus(0);
            }
            result+=rangSettingMapper.updateByPrimaryKeySelective(setting);
        }
        //设置警报气体等级默认选择第一个
        LevelDataExample example1 = new LevelDataExample();
        List<LevelData> levelData = levelDataMapper.selectByExample(example1);
        for (LevelData levelDatum : levelData) {
            levelDatum.setRangSettingId(9);
            result+= levelDataMapper.updateByPrimaryKeySelective(levelDatum);
        }

        return result;
    }

    public static void main(String[] args) {
        String str="[{\n" +
                "\t\t\"levelId\": 1,\n" +
                "\t\t\"setId\": 2\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"levelId\": 1,\n" +
                "\t\t\"setId\": 5\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"levelId\": 1,\n" +
                "\t\t\"setId\": 1\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"levelId\": 1,\n" +
                "\t\t\"setId\": 9\n" +
                "\t}\n" +
                "]";
        JSONArray jsonArray = JSONArray.parseArray(str);
        for(int i=0;i<jsonArray.size();i++){
            Integer levelId = jsonArray.getJSONObject(i).getInteger("levelId");
            Integer setId = jsonArray.getJSONObject(i).getInteger("setId");


        }
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        String levelId = jsonObject.getString("levelId");

        String setId = jsonObject.getString("setId");

        for (Object o : jsonArray) {
            System.out.println(o);

        }


    }


}
