package com.cst.xinhe.web.service.system.server;

import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.web.service.system.service.LevelSettingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 15:59
 **/
@RestController
public class LevelSettingServerController {

    @Resource
    private LevelSettingService levelSettingService;

    @GetMapping("getStandardNameByStandardId")
    public Map<String, Object> getStandardNameByStandardId(@RequestParam Integer standardId){
        return levelSettingService.getStandardNameByStandardId(standardId);
    }

    @GetMapping("getWarnLevelSettingByGasLevelId")
    public GasLevelVO getWarnLevelSettingByGasLevelId(@RequestParam Integer standardId){
        return levelSettingService.getWarnLevelSettingByGasLevelId(standardId);
    }

    @PostMapping("getStandardNameByStandardIds")
    public Map<Integer, String> getStandardNameByStandardIds(@RequestBody Map<Integer, Integer> standardIds){
        return levelSettingService.getStandardNameByStandardIds(standardIds);
    }
}
