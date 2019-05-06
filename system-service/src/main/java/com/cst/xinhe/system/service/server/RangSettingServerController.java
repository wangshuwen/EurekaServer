package com.cst.xinhe.system.service.server;

import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.system.service.service.RangSettingService;
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
    @GetMapping("findRangByType")
    public List<RangSetting> findRangByType(@RequestParam int i){
        return rangSettingService.findRangByType(i);
    }
}
