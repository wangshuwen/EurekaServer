package com.cst.xinhe.web.service.station_partition.server;

import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.stationpartition.service.service.BaseStationService;
import com.cst.xinhe.stationpartition.service.service.PartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 17:59
 **/
@RestController
//@RequestMapping("station-partition-service/")
public class StationPartitionServiceController {

    @Autowired
    private BaseStationService baseStationService;

    @Autowired
    private PartitionService partitionService;

    @GetMapping("findFrequencyByStationId")
    public double findFrequencyByStationId(@RequestParam("stationId") Integer stationId){
        return baseStationService.findFrequencyByStationId(stationId);
    }

    @GetMapping("findBaseStationByNum")
    public BaseStation findBaseStationByNum(@RequestParam("basestationid") Integer basestationid){
        return baseStationService.findBaseStationByNum(basestationid);
    }

    @GetMapping("findBaseStationByType")
    public Map<String, Object> findBaseStationByType(@RequestParam("i") int i){
        return baseStationService.findBaseStationByType(i);
    }

    @GetMapping("getSonIdsById")
    public List<Integer> getSonIdsById(@RequestParam("zoneId") Integer zoneId){
        return partitionService.getSonIdsById(zoneId);
    }

    @PostMapping("findBaseStationByZoneIds")
    public List<BaseStation> findBaseStationByZoneIds(@RequestParam("zoneIds") List<Integer> zoneIds){
        return baseStationService.findBaseStationByZoneIds(zoneIds);
    }

    @PutMapping("updateByStationNumSelective")
    public void updateByStationNumSelective(@RequestBody BaseStation baseStation){
        baseStationService.updateByStationNumSelective(baseStation);
    }

}
