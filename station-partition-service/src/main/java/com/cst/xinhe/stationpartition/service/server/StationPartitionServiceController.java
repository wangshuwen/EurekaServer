package com.cst.xinhe.stationpartition.service.server;

import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.stationpartition.service.service.BaseStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 17:59
 **/
@RestController
@RequestMapping("station-partition-service/")
public class StationPartitionServiceController {

    @Autowired
    private BaseStationService baseStationService;
    @GetMapping("findFrequencyByStationId")
    public double findFrequencyByStationId(@RequestParam Integer stationId){
        return baseStationService.findFrequencyByStationId(stationId);
    }

    @GetMapping("findBaseStationByNum")
    public BaseStation findBaseStationByNum(@RequestParam Integer basestationid){
        return baseStationService.findBaseStationByNum(basestationid);
    }

}
