package com.cst.xinhe.web.service.server;

import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.web.service.system.service.LevelDataService;
import com.cst.xinhe.web.service.system.service.RangSettingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 11:28
 **/
@RestController
public class RangSettingServerController {

    @Resource
    private RangSettingService rangSettingService;

    @Resource
    private LevelDataService levelDataService;

    @GetMapping("findRangByType")
    public List<RangSetting> findRangByType(@RequestParam int i){
        return rangSettingService.findRangByType(i);
    }


    @GetMapping("findRangUrlByLevelDataId")
    public String findRangUrlByLevelDataId(@RequestParam int contrastParameter){
        return levelDataService.findRangUrlByLevelDataId(contrastParameter);
    }
}
