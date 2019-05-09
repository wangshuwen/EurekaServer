package com.cst.xinhe.stationpartition.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.t_partition.Partition;
import com.cst.xinhe.stationpartition.service.service.BaseStationService;
import com.cst.xinhe.stationpartition.service.service.PartitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/11/14:00
 */
@RestController
@RequestMapping("partition/")
@Api(value = "PartitionController", tags = {"区域管理"})
public class PartitionController {
    @Resource
    private PartitionService partitionService;

    @Resource
    private BaseStationService baseStationService;

    @GetMapping("findPartition")
    @ApiOperation(value = "获取区域管理的组织结构", notes = "获取区域管理的组织结构z-tree")
    public String findPartition() {
        List<Partition> list= partitionService.findPartition();
        return ResultUtil.jsonToStringSuccess(list);
    }

    @PostMapping("addPartition")
    @ApiOperation(value = "新增组织结构子节点", notes = "新增树节点")
    public String addPartition(@RequestBody Partition partition) {
        Integer result=partitionService.add(partition);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @DeleteMapping("deletePartition")
    @ApiOperation(value = "删除组织结构子节点", notes = "删除树节点")
    public String deletePartition(@RequestParam Integer id) {
        List<Integer> list = partitionService.getSonIdsById(id);
        if (list != null && !list.isEmpty()){
            int flag = 0;
            for (Integer item: list){
                List<BaseStation> baseStationList = baseStationService.findAllStationByZoneId(item);
                if (baseStationList != null && !baseStationList.isEmpty()){
                    flag += baseStationList.size();
                }
            }
            if (flag > 0){
                return ResultUtil.jsonToStringError(ResultEnum.DEL_PARTITION_FAIL);
            }
        }
        Integer result = partitionService.delete(id);
        return result > 0 ? ResultUtil.jsonToStringSuccess(result) : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @PutMapping("updatePartition")
    @ApiOperation(value = "更新组织结构子节点", notes = "更新树节点")
    public String updatePartition(@RequestBody Partition partition) {
        Integer result=partitionService.update(partition);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


}
